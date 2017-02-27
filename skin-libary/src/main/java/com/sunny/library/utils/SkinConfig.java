package com.sunny.library.utils;

import android.content.Context;

public class SkinConfig {

    public static final String SKIN_PATH = "skin_path";
    public static final String SKIN_MODE = "skin_mode";
    public static final int SKIN_DEFAULT = 0;
    public static final int SKIN_NIGHT = 1;
    public static final int SKIN_EXTER = 2;

    public static String getSkinPath(Context context) {
        return PreferencesUtils.getString(context, SKIN_PATH, "");
    }

    public static void saveSkinPath(Context context, String path) {
        PreferencesUtils.putString(context, SKIN_PATH, path);
    }

    public static int getSkinMode(Context context) {
        return PreferencesUtils.getInt(context, SKIN_MODE, SKIN_DEFAULT);
    }

    public static void saveSkinMode(Context context, int skinMode) {
        PreferencesUtils.putInt(context, SKIN_MODE, skinMode);
    }


    public static boolean isDefaultSkin(Context context) {
        return SKIN_DEFAULT == getSkinMode(context);
    }

}
