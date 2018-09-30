package com.lwd.qjtv.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.lwd.qjtv.R;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/19.
 */

public class RaceNumView extends LinearLayout {
    public RaceNumView(Context context) {
        this(context,null);
    }

    public RaceNumView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RaceNumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.item_race_view_layout, this);
    }
}
