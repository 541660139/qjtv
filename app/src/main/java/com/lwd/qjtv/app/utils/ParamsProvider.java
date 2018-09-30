package com.lwd.qjtv.app.utils;

import android.support.annotation.NonNull;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/6 0006.
 * 拼接参数
 */

public class ParamsProvider {
    public static String getParams(@NonNull Map<String,String> params){
        Iterator<String> iterator=params.keySet().iterator();
        StringBuilder stringBuilder=new StringBuilder();
        while (iterator.hasNext()){
            String key=iterator.next();
            String value=params.get(key);
            stringBuilder.append("&").append(key).append("=").append(value);
        }
        String s=stringBuilder.toString();
        s=s.replaceFirst("&","");
        return s;
    }
}
