package com.chinaappsremover.utils;

import com.chinaappsremover.AppController;

import static com.chinaappsremover.AppController.getInstance;

public class Preference {
    private static final Long MIN_SYNC_DURATION = 1000L * 60 * 60 * 12; // 12Hours
    private static final String KEY_LAST_SYNC_MILLS = "last_sync_mills";
    private static final String KEY_FIRST_RUN = "db_initialized";

    public static boolean shouldRefreshData() {
        Long lastSyncedMills = getInstance().getDefaultPreference().getLong(KEY_LAST_SYNC_MILLS, 0L);
        return System.currentTimeMillis() >= lastSyncedMills + MIN_SYNC_DURATION;
    }

    public static void updateLastSyncMills() {
        getInstance().getDefaultPreference().edit().putLong(KEY_LAST_SYNC_MILLS, System.currentTimeMillis()).apply();
    }

    public static boolean isDbInitialized() {
        return AppController.getInstance().getDefaultPreference().getBoolean(KEY_FIRST_RUN, true);
    }

    public static void setDbInitialized() {
        AppController.getInstance().getDefaultPreference().edit().putBoolean(KEY_FIRST_RUN, false).apply();
    }
}
