package com.lwd.qjtv.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/17.
 */

public class RefreshLayout extends VerticalSwipeRefreshLayout {

    private OnRefreshListener onRefreshListener;

    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener){
        this.onRefreshListener = onRefreshListener;
    }
}
