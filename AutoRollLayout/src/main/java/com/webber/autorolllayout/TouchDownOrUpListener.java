package com.webber.autorolllayout;

import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/11/26.
 */
public interface TouchDownOrUpListener {
    void onDownListener(MotionEvent event);
    void onUpListener(MotionEvent event);
}
