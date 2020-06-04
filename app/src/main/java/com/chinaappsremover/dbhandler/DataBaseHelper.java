package com.chinaappsremover.dbhandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chinaappsremover.wrapper.AppInfo;

import java.io.File;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "rca.db";
    public static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AppInfoEntry.TABLE_NAME + " (" +
                    AppInfoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    AppInfoEntry.COLUMN_PACKAGE_NAME + " TEXT," +
                    AppInfoEntry.COLUMN_APP_NAME + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AppInfoEntry.TABLE_NAME;


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.w("Db oncrate called", "Db oncreate called" + SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public List<AppInfo> isExist(List<AppInfo> allInstalledAppInfoList, List<AppInfo> chinaAppInfoList) {
        SQLiteDatabase db = getWritableDatabase();
        chinaAppInfoList.clear();
        for (AppInfo next : allInstalledAppInfoList) {
            Cursor rawQuery = db.rawQuery("select * from  apps where p_name ='" + next.packageName + "'", null);
            if (rawQuery != null && rawQuery.moveToFirst()) {
                chinaAppInfoList.add(next);
                while (rawQuery.moveToNext()) {
                    chinaAppInfoList.add(next);
                }
            }
            if (rawQuery != null) {
                rawQuery.close();
            }
        }
        return chinaAppInfoList;
    }

    public boolean refreshAppInfos(List<AppInfo> appInfoEntries) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(AppInfoEntry.TABLE_NAME, null, null);

            for (AppInfo appInfo : appInfoEntries) {

                ContentValues values = new ContentValues();
                values.put(AppInfoEntry.COLUMN_APP_NAME, appInfo.appName
                );
                values.put(AppInfoEntry.COLUMN_PACKAGE_NAME, appInfo.packageName);

                db.insertOrThrow(AppInfoEntry.TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return false;
    }
}
