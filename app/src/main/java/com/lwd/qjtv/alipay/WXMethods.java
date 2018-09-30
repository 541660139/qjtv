
package com.lwd.qjtv.alipay;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.apache.http.NameValuePair;
import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.switfpass.pay.utils.Constants;
import com.switfpass.pay.utils.MD5;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.lwd.qjtv.app.utils.Constant;


/**
 * 通用工具类,封装了很多常用方法
 */
public class WXMethods {
    private static final String TAG = "WXMethods";
    public static IWXAPI msgApi;
    StringBuffer sb;

    public static Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            // 实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("orion", e.toString());
        }
        return null;

    }

    public static String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    public static long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 生成签名
     */

    public static String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constant.API_KEY);

        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion", packageSign);
        return packageSign;
    }

    public static String genPackageSign(Map<String, String> params) {
        Map treeMap = new TreeMap(params);
        Iterator<String> iterator = treeMap.keySet().iterator();
        StringBuilder stringBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = params.get(key);
            stringBuilder.append("&").append(key).append("=").append(value);
        }
        stringBuilder.append("&key=");
        stringBuilder.append(Constant.API_KEY);
        String s = stringBuilder.toString();
        s = s.replaceFirst("&", "");
        String packageSign = MD5.getMessageDigest(s.getBytes()).toUpperCase();
        Log.e("orion", packageSign);
        return packageSign;
    }

    public static String genAppSign(List<NameValuePair> params, String APIKEY) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(APIKEY);

        String appSign = MD5.getMessageDigest(sb.toString().getBytes());
        Log.e("orion", appSign);
        return appSign;
    }

    public String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");

            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");

        Log.e("orion", sb.toString());
        return sb.toString();
    }

    public static String isSurpportWX(Context context) {
        String results = "";
        msgApi = WXAPIFactory.createWXAPI(context, null);
        msgApi.registerApp(Constants.APP_ID);

        boolean isPaySupported = msgApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;

        if (msgApi.isWXAppInstalled()) {
            if (isPaySupported) {
                results = "success";
            } else {
                results = "您的微信尚未安装,请安装微信客户端";
            }
        } else {
            results = "您的微信尚未安装,请安装微信客户端";
        }
        return results;
    }
}
