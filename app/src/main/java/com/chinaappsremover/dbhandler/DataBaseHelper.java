package com.chinaappsremover.dbhandler;

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
    private Context myContext;
    private SQLiteDatabase sqLiteDatabase = getWritableDatabase();

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        myContext = context;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        Log.w("Db oncrate called", "Db oncreate called");
        sQLiteDatabase.execSQL("create table apps (id integer primary key autoincrement, p_name text, a_name text);");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        myContext.getDatabasePath("dest.sqLiteDatabase");
    }

    public boolean attach(File file, boolean z) {
        try {
            sqLiteDatabase.execSQL("attach database ? as sqLiteDatabase", new String[]{file.getAbsolutePath()});
            if (!z) {
                sqLiteDatabase.delete("apps", null, null);
            }
            sqLiteDatabase.execSQL("INSERT INTO apps (p_name, a_name) SELECT  p_name, a_name FROM sqLiteDatabase.apps");
            return file.delete();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<AppInfo> isExist(List<AppInfo> list, List<AppInfo> list2) {
        list2.clear();
        for (AppInfo next : list) {
            Cursor rawQuery = sqLiteDatabase.rawQuery("select * from  apps where p_name ='" + next.packageName + "'", null);
            if (rawQuery != null && rawQuery.moveToFirst()) {
                list2.add(next);
                while (rawQuery.moveToNext()) {
                    list2.add(next);
                }
            }
            if (rawQuery != null) {
                rawQuery.close();
            }
        }
        return list2;
    }
}
