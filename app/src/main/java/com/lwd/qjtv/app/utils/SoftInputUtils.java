package com.lwd.qjtv.app.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/8/8.
 */

public class SoftInputUtils {

    public static void hideSoftInput(View view,Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }
}
