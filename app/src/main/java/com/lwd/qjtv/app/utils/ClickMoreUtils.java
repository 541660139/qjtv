package com.lwd.qjtv.app.utils;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/29.
 */

public class ClickMoreUtils {
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    private static long lastClickTime;
}
