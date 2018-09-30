package com.lwd.qjtv.app.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.lwd.qjtv.app.WEApplication;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Administrator on 2016/11/1.
 */
public class MyHttpUtils {
    /**
     * 2016/11/1.  :王俊林
     * map
     * get请求 键值对
     * 如需添加参数   map.put();
     * 需要okhttp依赖
     */
    public static Map<String, String> normalMap = new TreeMap<>();
    public static Map<String, String> map = new TreeMap<>();
    public static Map<String, String> nMap = new TreeMap<>();

    private static MyHttpUtils httpUtils;


    public static synchronized Map<String, String> initHttpParams(Map<String, String> paramMap) {
        map = new TreeMap<>();
        initHttpNMapParams();
        map.putAll(nMap);
        for (String key : paramMap.keySet()) {
            map.put(key, paramMap.get(key));
            Log.d("MyHttpUtils", "initHttpParams: key = " + key + " value = " + paramMap.get(key));
        }
        map.put("sign", Utils.buildSign(map, Constant.key));
        return map;
    }

    public static void initHttpParams() {
        normalMap.put("appid", Constant.APPID);
        normalMap.put("pt", Constant.PT);
        normalMap.put("ver", WEApplication.getApp_ver());
        normalMap.put("imei", WEApplication.getIMEI());
        normalMap.put("uid", Preference.get(Constant.UID, "0"));
        normalMap.put("t", System.currentTimeMillis() + "");
        normalMap.put("sign", Utils.buildSign(normalMap, Constant.key));
    }

    public static void initHttpNMapParams() {
        nMap.put("appid", Constant.APPID);
        nMap.put("pt", Constant.PT);
        nMap.put("ver", WEApplication.getApp_ver());
        nMap.put("imei", WEApplication.getIMEI());
        nMap.put("uid", Preference.get(Constant.UID, "0"));
        nMap.put("t", System.currentTimeMillis() + "");
    }


    public static MyHttpUtils init() {
        if (httpUtils == null) {
            httpUtils = new MyHttpUtils();
        }
        if (normalMap.size() < 7) {
            initHttpParams();
            initHttpNMapParams();
        }
        return httpUtils;
    }


    public static synchronized Map<String, String> refreshMap(Map<String, String> paramMap) {
        map.clear();
        return initHttpParams(paramMap);
    }

    public static Map<String, String> refreshMap() {
        normalMap.clear();
        initHttpParams();
        return normalMap;
    }

    public static Map<String, String> getMap() {
        return nMap;
    }

    public static String getChannel(Context context, String packageName) {
        String umeng = "";
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_META_DATA);
            umeng = appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return umeng;
    }
}
