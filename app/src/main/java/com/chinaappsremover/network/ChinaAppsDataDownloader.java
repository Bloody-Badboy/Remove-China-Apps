package com.chinaappsremover.network;

import android.content.Context;
import android.util.Log;

import com.chinaappsremover.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ChinaAppsDataDownloader {
    private static volatile ChinaAppsDataDownloader sInstance = null;

    private final OkHttpClient okHttpClient;

    public static ChinaAppsDataDownloader getInstance(Context applicationContext) {
        if (sInstance == null) {
            synchronized (ChinaAppsDataDownloader.class) {
                if (sInstance == null) {
                    sInstance = new ChinaAppsDataDownloader(applicationContext);
                }
            }
        }
        return sInstance;
    }

    private ChinaAppsDataDownloader(Context context) {

        ArrayList<Protocol> protocols = new ArrayList<>();
        protocols.add(Protocol.HTTP_1_1);
        protocols.add(Protocol.HTTP_2);

        long cacheSize = 2L * 1024 * 1024; // 2 MiB
        File cacheDir = context.getDir("china_apps_data", Context.MODE_PRIVATE);
        Cache cache = new Cache(cacheDir, cacheSize);

        okHttpClient = new OkHttpClient.Builder()
                .protocols(protocols)
                .cache(cache)
                .build();

        if (sInstance != null) {
            throw new AssertionError(
                    "Another instance of "
                            + ChinaAppsDataDownloader.class.getName()
                            + " class already exists, Can't create a new instance.");
        }
    }

    public Response fetch(boolean fromCache) throws IOException {
        String url = BuildConfig.BLACKLISTED_APP_JSON_URL;
        Request request = new Request.Builder()
                .url(url)
                .cacheControl(fromCache ? CacheControl.FORCE_CACHE : CacheControl.FORCE_NETWORK)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                Log.d("ChinaAppsDataDownloader", fromCache ? "Loaded cache. bytes: " + responseBody.contentLength() : "Fetched bytes: " + responseBody.contentLength());
            }
            if (response.code() == 504) {
                return null;
            }
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Network error");
        }
    }
}