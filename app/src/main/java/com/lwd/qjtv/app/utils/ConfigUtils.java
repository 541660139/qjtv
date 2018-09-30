package com.lwd.qjtv.app.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CK on 2017/4/12.
 */

public class ConfigUtils {


    public static String toMD5Mb(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        // 16位加密，从第9位到25位
        return md5StrBuff.toString().toLowerCase();
    }

    //--MD5
    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String toHexString(byte[] b) { // String to byte
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    public static String getRequestParamString(String md5) {
//        md5+=APPKEY;
        String md5key = toMD5Mb(md5).trim();
        return md5key;

    }


    public static String getUrl(Map<String, String> map) {
//        // TODO: 2017/12/13  https    图片无法加载 
        String url = "http://a.langweida.cn/index.php?tp=mobile/bbs_card_info";
        StringBuffer sb = null;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (sb == null) {
                sb = new StringBuffer();
                sb.append("&");
            } else {
                sb.append("&");
            }
            sb.append(entry.getKey() + "=" + entry.getValue());
        }
        url = url + sb.toString();
        return url;

    }


    public static Map<String, String> splitHomeWorkItemParams(String url) {
        Map<String, String> map = new HashMap<>();
//        tp=api/index & op=trylist & ID=1
        if (url.contains("&")) {
            String[] split = url.split("&");
            for (int i = 0; i < split.length; i++) {
                if (split[i].contains("=")) {
                    String[] split1 = split[i].split("=");
                    if (split1.length >= 2) {
                        map.put(split1[0], split1[1]);
                    }
                }
            }
        } else if (url.contains("=")) {
            String[] split = url.split("=");
            if (split.length >= 2) {
                map.put(split[0], split[1]);
            }
        }

        return map;
    }


    private static DecimalFormat fileIntegerFormat = new DecimalFormat("#0");
    private static DecimalFormat fileDecimalFormat = new DecimalFormat("#0.#");

    /**
     * 单位换算
     *
     * @param size      单位为B
     * @param isInteger 是否返回取整的单位
     * @return 转换后的单位
     */
    public static String formatFileSize(long size, boolean isInteger) {
        DecimalFormat df = isInteger ? fileIntegerFormat : fileDecimalFormat;
        String fileSizeString = "0M";
        if (size < 1024 && size > 0) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1024 * 1024) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1024 * 1024 * 1024) {
            fileSizeString = df.format((double) size / (1024 * 1024)) + "M";
        } else {
            fileSizeString = df.format((double) size / (1024 * 1024 * 1024)) + "G";
        }
        return fileSizeString;
    }
}
