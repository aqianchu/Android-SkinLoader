package com.sunny.library.manager;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.sunny.library.attr.AttrFactory;
import com.sunny.library.attr.DynamicAttr;
import com.sunny.library.attr.SkinAttr;
import com.sunny.library.model.SkinView;
import com.sunny.library.utils.ListUtils;
import com.sunny.library.utils.SkinConfig;

import java.util.ArrayList;
import java.util.List;

public class SkinFactory implements LayoutInflaterFactory {
    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private static final String ATTR_SKIN_ENABLE = "enable";

    private List<SkinView> skinViews = new ArrayList<>();
    private AppCompatDelegate appCompatDelegate;

    public void setAppCompatDelegate(AppCompatDelegate appCompatDelegate) {
        this.appCompatDelegate = appCompatDelegate;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = null;
        boolean isEnable = attrs.getAttributeBooleanValue(NAMESPACE, ATTR_SKIN_ENABLE, false);
        if (isEnable) {
            view = appCompatDelegate.createView(parent, name, context, attrs);
            if (view == null) {
                view = createView(context, name, attrs);
            }
            if (view != null) {
                parseAttrs(context, attrs, view);
            }
        }
        return view;
    }

    private View createView(Context context, String name, AttributeSet attrs) {
        View view = null;
        if (-1 == name.indexOf('.')) {
            if ("View".equalsIgnoreCase(name)) {
                view = createView(name, context, attrs, "android.view.");
            }
            if (null == view) {
                view = createView(name, context, attrs, "android.widget.");
            }
            if (null == view) {
                view = createView(name, context, attrs, "android.webkit.");
            }
        } else {
            view = createView(name, context, attrs, null);
        }
        return view;
    }

    private View createView(String name, Context context, AttributeSet attrs, String prefix) {
        View view = null;
        try {
            view = LayoutInflater.from(context).createView(name, prefix, attrs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void parseAttrs(Context context, AttributeSet attrs, View view) {
        List<SkinAttr> viewAttrs = new ArrayList<>();
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);
            if (AttrFactory.isSupportedAttr(attrName) && attrValue.startsWith("@")) {
                try {
                    int id = Integer.parseInt(attrValue.substring(1));
                    String entryName = context.getResources().getResourceEntryName(id);
                    String typeName = context.getResources().getResourceTypeName(id);
                    SkinAttr mSkinAttr = AttrFactory.get(attrName, id, entryName, typeName);
                    if (mSkinAttr != null) {
                        viewAttrs.add(mSkinAttr);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!ListUtils.isEmpty(viewAttrs)) {
            SkinView skinView = new SkinView();
            skinView.view = view;
            skinView.attrs = viewAttrs;
            //存入列表中
            skinViews.add(skinView);

            if (SkinManager.SKIN_MODE == SkinConfig.SKIN_EXTER || SkinManager.SKIN_MODE == SkinConfig.SKIN_NIGHT) {
                skinView.apply();
            }
        }
    }


    public void dynamicAddSkinEnableView(Context context, View view, List<DynamicAttr> pDAttrs) {
        List<SkinAttr> viewAttrs = new ArrayList<SkinAttr>();
        SkinView skinView = new SkinView();
        skinView.view = view;

        for (DynamicAttr dAttr : pDAttrs) {
            int id = dAttr.attrValueId;
            String entryName = context.getResources().getResourceEntryName(id);
            String typeName = context.getResources().getResourceTypeName(id);
            SkinAttr mSkinAttr = AttrFactory.get(dAttr.attrName, id, entryName, typeName);
            viewAttrs.add(mSkinAttr);
        }
        skinView.attrs = viewAttrs;
        addSkinView(skinView);
    }

    public void dynamicAddSkinEnableView(Context context, View view, String attrName, int attrValueResId) {
        int id = attrValueResId;
        String entryName = context.getResources().getResourceEntryName(id);
        String typeName = context.getResources().getResourceTypeName(id);
        SkinAttr mSkinAttr = AttrFactory.get(attrName, id, entryName, typeName);
        SkinView skinView = new SkinView();
        skinView.view = view;
        List<SkinAttr> viewAttrs = new ArrayList<SkinAttr>();
        viewAttrs.add(mSkinAttr);
        skinView.attrs = viewAttrs;
        addSkinView(skinView);
    }


    public void addSkinView(SkinView view) {
        skinViews.add(view);
    }

    public void applySkin() {
        if (ListUtils.isEmpty(skinViews)) {
            return;
        }

        for (SkinView sv : skinViews) {
            if (sv.view == null) {
                continue;
            }
            sv.apply();
        }
    }

    public void clean() {
        if (ListUtils.isEmpty(skinViews)) {
            return;
        }

        for (SkinView sv : skinViews) {
            if (sv.view == null) {
                continue;
            }
            sv.clean();
        }
    }

}
