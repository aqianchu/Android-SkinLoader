package com.sunny.library.manager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.sunny.library.listener.OnLoaderListener;
import com.sunny.library.listener.SkinObservable;
import com.sunny.library.listener.SkinObserver;
import com.sunny.library.utils.SkinConfig;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SkinManager implements SkinObservable {

    private Resources resources;
    private Context context;
    public static int SKIN_MODE = SkinConfig.SKIN_DEFAULT;
    private String skinPackageName;
    private List<SkinObserver> skinObservers;


    public void loadSkin() {
        int mode = SkinConfig.getSkinMode(context);
        loadSkin(mode);
    }


    public void loadSkin(int skinMode) {
        String skinPath = "";
        if (skinMode == SkinConfig.SKIN_EXTER) {
            skinPath = SkinConfig.getSkinPath(context);
        }
        if (TextUtils.isEmpty(skinPath) && (skinMode == SkinConfig.SKIN_EXTER)) {
            skinMode = SkinConfig.SKIN_DEFAULT;
        }
        loadSkin(skinMode, skinPath, null);
    }

    public void loadSkin(String skinPath, OnLoaderListener onLoaderListener) {
        loadSkin(SkinConfig.SKIN_EXTER, skinPath, onLoaderListener);
    }

    public void loadSkin(int skinMode, String skinPath, OnLoaderListener onLoaderListener) {
        if (skinMode == SKIN_MODE) {
            return;
        }
        SKIN_MODE = skinMode;
        SkinConfig.saveSkinMode(context, SKIN_MODE);
        switch (skinMode) {
            case SkinConfig.SKIN_DEFAULT:
                resources = updateNightMode(false);
                notifySkinUpdate();
                break;
            case SkinConfig.SKIN_NIGHT:
                resources = updateNightMode(true);
                notifySkinUpdate();
                break;
            case SkinConfig.SKIN_EXTER:
                load(skinPath, onLoaderListener);
                break;
        }
    }

    private void load(String skinPackagePath, final OnLoaderListener listener) {
        new AsyncTask<String, Void, Resources>() {

            @Override
            protected void onPreExecute() {
                if (listener != null) {
                    listener.onStart();
                }
            }

            @Override
            protected Resources doInBackground(String... params) {
                try {
                    if (params.length == 1) {
                        String skinPkgPath = params[0];
                        File file = new File(skinPkgPath);
                        if (file == null || !file.exists()) {
                            return null;
                        }
                        PackageManager mgr = context.getPackageManager();
                        PackageInfo info = mgr.getPackageArchiveInfo(skinPkgPath, PackageManager.GET_ACTIVITIES);
                        skinPackageName = info.packageName;

                        AssetManager assetManager = AssetManager.class.newInstance();
                        Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                        addAssetPath.invoke(assetManager, skinPkgPath);

                        Resources superRes = context.getResources();
                        Resources skinResource = new Resources(assetManager, superRes.getDisplayMetrics(),
                                superRes.getConfiguration());

                        SkinConfig.saveSkinPath(context, skinPkgPath);
                        SKIN_MODE = SkinConfig.SKIN_EXTER;
                        return skinResource;
                    }
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            protected void onPostExecute(Resources result) {
                resources = result;
                if (resources != null) {
                    if (listener != null) {
                        listener.onSuccess();
                    }
                    notifySkinUpdate();
                } else {
                    SKIN_MODE = SkinConfig.SKIN_DEFAULT;
                    if (listener != null) {
                        listener.onFailed();
                    }
                }
            }

            ;
        }.execute(skinPackagePath);
    }

    public void init(Context ctx) {
        context = ctx.getApplicationContext();
        resources = ctx.getResources();
    }


    private Resources updateNightMode(boolean on) {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaa:" + on);
        Resources resource = context.getResources();
        DisplayMetrics dm = resource.getDisplayMetrics();
        Configuration config = resource.getConfiguration();
        final int uiModeNightMaskOrigin = config.uiMode &= ~Configuration.UI_MODE_TYPE_MASK;
        final int uiModeNightMaskNew = on ? Configuration.UI_MODE_NIGHT_YES : Configuration.UI_MODE_NIGHT_NO;
        if (uiModeNightMaskOrigin != uiModeNightMaskNew) {
            config.uiMode &= ~Configuration.UI_MODE_NIGHT_MASK;
            config.uiMode |= uiModeNightMaskNew;
            resource.updateConfiguration(config, dm);
        }
        skinPackageName = context.getPackageName();
        return resource;
    }

    public int getColor(int resId) {
        int originColor = ContextCompat.getColor(context, resId);
        if (resources == null) {
            return originColor;
        }
        String resName = context.getResources().getResourceEntryName(resId);
        int trueResId = resources.getIdentifier(resName, "color", skinPackageName);
        int trueColor = 0;
        try {
            trueColor = ResourcesCompat.getColor(resources, trueResId, null);
        } catch (NotFoundException e) {
            e.printStackTrace();
            trueColor = originColor;
        }
        return trueColor;
    }

    public Drawable getDrawable(int resId) {
        Drawable originDrawable = ContextCompat.getDrawable(context, resId);
        if (resources == null) {
            return originDrawable;
        }
        String resName = context.getResources().getResourceEntryName(resId);
        int trueResId = resources.getIdentifier(resName, "drawable", skinPackageName);
        Drawable trueDrawable = null;
        try {
            trueDrawable = ResourcesCompat.getDrawable(resources, trueResId, null);
        } catch (NotFoundException e) {
            e.printStackTrace();
            trueDrawable = originDrawable;
        }
        return trueDrawable;
    }

    public ColorStateList getColorStateList(int resId) {
        ColorStateList originColor = ContextCompat.getColorStateList(context, resId);
        if (resources == null) {
            return originColor;
        }
        String resName = context.getResources().getResourceEntryName(resId);
        int trueResId = resources.getIdentifier(resName, "color", skinPackageName);
        ColorStateList trueColor = null;
        try {
            trueColor = ResourcesCompat.getColorStateList(resources, trueResId, null);
        } catch (NotFoundException e) {
            e.printStackTrace();
            trueColor = originColor;
        }
        return trueColor;
    }

    private SkinManager() {
    }

    public static SkinManager getInstance() {
        return InstanceHolder.holder;
    }

    private static class InstanceHolder {
        private static SkinManager holder = new SkinManager();
    }

    @Override
    public void attach(SkinObserver observer) {
        if (skinObservers == null) {
            skinObservers = new ArrayList<SkinObserver>();
        }
        if (!skinObservers.contains(observer)) {
            skinObservers.add(observer);
        }
    }

    @Override
    public void detach(SkinObserver observer) {
        if (skinObservers == null)
            return;
        if (skinObservers.contains(observer)) {
            skinObservers.remove(observer);
        }
    }

    @Override
    public void notifySkinUpdate() {
        if (skinObservers == null)
            return;
        for (SkinObserver observer : skinObservers) {
            observer.onThemeUpdate();
        }
    }

}
