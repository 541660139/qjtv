package com.lwd.qjtv.app.utils;


import com.lwd.qjtv.app.WEApplication;

public class CommonUtil {

    public static int dip2px(float dpValue) {
        float scale = WEApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
