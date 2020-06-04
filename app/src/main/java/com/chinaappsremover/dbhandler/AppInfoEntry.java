package com.chinaappsremover.dbhandler;

import android.provider.BaseColumns;

class AppInfoEntry implements BaseColumns {
    public static final String TABLE_NAME = "apps";
    public static final String COLUMN_PACKAGE_NAME = "p_name";
    public static final String COLUMN_APP_NAME = "a_name";
}
