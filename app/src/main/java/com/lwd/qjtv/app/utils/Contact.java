package com.lwd.qjtv.app.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.mvp.model.api.Api;
import com.lwd.qjtv.mvp.ui.activity.WebNewActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhengQian on 2018/1/30.
 * E-mail : wwwiiivip@yeah.net
 * If anyelse things , mail to it . I'll be soon to reply .
 */

public class Contact {

    private Context context;

    public Contact(Context context) {
        this.context = context;
    }

    public void startWebActivity(String tp) {
        Intent intent = new Intent(context, WebNewActivity.class);
        intent.putExtra("url", getWebUrl(tp));
        context.startActivity(intent);
    }

    public void startWebActivity(String tp, Map<String, String> map) {
        Intent intent = new Intent(context, WebNewActivity.class);
        String webUrl = getWebUrl(tp, map);
        Log.d("Contact", "startWebActivity: " + webUrl);
        intent.putExtra("url", webUrl);
        context.startActivity(intent);
    }

    @NonNull
    public String getWebUrl(String tp) {
        return Api.APP_DOMAIN + Api.INDEX + "tp=" + tp + "&" +
                ParamsProvider.getParams(getParams());
    }

    @NonNull
    public String getWebUrl(String tp, Map<String, String> map) {
        return Api.APP_DOMAIN + Api.INDEX + "tp=" + tp + "&" +
                ParamsProvider.getParams(getParams(map));
    }

    private Map<String, String> getParams() {
        Map<String, String> map = new HashMap<>();
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        return map;
    }

    private Map<String, String> getParams(Map<String, String> paramMap) {
        Map<String, String> map = new HashMap<>();
        map.putAll(paramMap);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        return map;
    }

}
