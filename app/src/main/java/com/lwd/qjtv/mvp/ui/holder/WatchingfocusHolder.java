package com.lwd.qjtv.mvp.ui.holder;

import android.view.View;
import android.widget.RelativeLayout;

import com.jess.arms.base.BaseHolder;
import com.lwd.qjtv.R;

import butterknife.BindView;

/**
 * Created by DELL on 2018/6/12.
 */

public class WatchingfocusHolder extends BaseHolder<String> {

    @BindView(R.id.singlepic_item)
    RelativeLayout singlepic_item;


    @BindView(R.id.more_pic_item)
    RelativeLayout more_pic_item;

    public WatchingfocusHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(String data, int position) {

        if (position == 0) {
            singlepic_item.setVisibility(View.VISIBLE);
            more_pic_item.setVisibility(View.GONE);
        } else {
            singlepic_item.setVisibility(View.GONE);
            more_pic_item.setVisibility(View.VISIBLE);
        }

    }
}
