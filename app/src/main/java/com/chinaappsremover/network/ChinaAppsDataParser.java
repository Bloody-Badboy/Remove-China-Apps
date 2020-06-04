package com.chinaappsremover.network;

import com.chinaappsremover.wrapper.AppInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class ChinaAppsDataParser {
    private static final String KEY_PACKAGE_NAME = "p_name";
    private static final String KEY_APP_NAME = "a_name";

    private ChinaAppsDataParser() {
    }

    public static List<AppInfo> parse(Response response) {
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            try {
                return parse(responseBody.string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @SuppressWarnings("CharsetObjectCanBeUsed")
    public static List<AppInfo> parse(InputStream inputStream) {
        if (inputStream != null) {
            try {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                StringBuilder builder = new StringBuilder();

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }
                return parse(builder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<AppInfo> parse(String json) {
        if (json != null) {
            try {
                List<AppInfo> appInfoList = new ArrayList<>();

                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    AppInfo appInfo = new AppInfo();
                    appInfo.appName = jsonObject.getString(KEY_APP_NAME);
                    appInfo.packageName = jsonObject.getString(KEY_PACKAGE_NAME);
                    appInfoList.add(appInfo);
                }

                return appInfoList;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
