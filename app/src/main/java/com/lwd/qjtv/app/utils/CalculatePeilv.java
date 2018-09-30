package com.lwd.qjtv.app.utils;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/29.
 */

public class CalculatePeilv {

    public static String calculate(String guessMoney, String peilv) {
        if (guessMoney == null || peilv == null || guessMoney.equals("") || peilv.equals(""))
            return "0";
        int money = Integer.parseInt(guessMoney);
        float pl = Float.parseFloat(peilv);
        return (int) (money * pl * 100) + "    ";
    }
}
