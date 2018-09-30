package com.lwd.qjtv.app.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class RequestParamUtil {

//	private static final String TAG = "RequestParamUtil";

    public static String getRequestParamString(Map<String, String> paramMap) {
        if (null == paramMap || paramMap.size() < 1) {
            return "";
        }

        List<Map.Entry<String, String>> mappingList = new ArrayList<Map.Entry<String, String>>(paramMap.entrySet());
        Collections.sort(mappingList, new Comparator<Map.Entry<String, String>>() {

            @Override
            public int compare(Map.Entry<String, String> mapping1, Map.Entry<String, String> mapping2) {
                return mapping1.getKey().compareTo(mapping2.getKey());
            }

        });

        String values = "";
        for (Map.Entry<String, String> mapping : mappingList) {
            String key = mapping.getKey().trim();
            String value = mapping.getValue();
            // MCLog.e(TAG, key + ":" + value);
            values += key + "=" + value + "&";
        }
//        MyHttpUtils.map.clear();
        return values.substring(0, values.length() - 1);
    }
}
