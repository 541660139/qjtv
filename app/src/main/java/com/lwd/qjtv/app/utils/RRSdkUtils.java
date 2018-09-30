package com.lwd.qjtv.app.utils;

import android.content.Context;

import com.rrmj.zhongduomei.videoparsesdk.parseurl.IOnParseUrlListener;
import com.rrmj.zhongduomei.videoparsesdk.parseurl.ParseUrl;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/3.
 */

public class RRSdkUtils {

    public static void parseUrl(Context context, String url, IOnParseUrlListener iOnParseUrlListener) {
        ParseUrl.parseUrl(context, url, "super", iOnParseUrlListener);
    }

    public static void parseUrl(Context context, String url, String qualityString, IOnParseUrlListener iOnParseUrlListener) {
        ParseUrl.parseUrl(context, url, qualityString, iOnParseUrlListener);
    }
}
