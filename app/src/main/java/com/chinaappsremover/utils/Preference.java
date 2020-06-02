package com.chinaappsremover.utils;

import com.chinaappsremover.AppController;

public class Preference {
    private static final String isDBAttached = "isDBAttached";

    public static boolean isDBAttached() {
        return AppController.getInstace().getDefaultPreference().getBoolean(isDBAttached, false);
    }

    public static void setAttachedDb() {
        AppController.getInstace().getDefaultPreference().edit().putBoolean(isDBAttached, false).apply();
    }
}
