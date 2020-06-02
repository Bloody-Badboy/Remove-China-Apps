package com.chinaappsremover.dbhandler;

import android.content.Context;
import android.util.Log;

import com.chinaappsremover.AppController;
import com.chinaappsremover.utils.Preference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DbUtils {
    public File getDatabasePath(Context context, String str) {
        return context.getDatabasePath(str);
    }

    public static void attachDB(Context context) {
        File databasePath = context.getDatabasePath("dest.sqLiteDatabase");
        if (getDbFile(context, DataBaseHelper.DATABASE_NAME, databasePath.getAbsolutePath()) && AppController.getDbHelper().attach(databasePath, false)) {
            Preference.setAttachedDb();
        }
    }

    public static boolean getDbFile(Context context, String str, String str2) {
        try {
            InputStream open = context.getAssets().open(str);
            FileOutputStream fileOutputStream = new FileOutputStream(str2);
            Log.v("Tag assets", fileOutputStream.toString());
            byte[] bArr = new byte[1024];
            while (true) {
                int read = open.read(bArr);
                if (read > 0) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    open.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isDatabaseAttached(Context context, String str) {
        if (getDatabasePath(context, str) == null) {
            return false;
        }
        return Preference.isDBAttached();
    }
}
