package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.mvp.ui.holder.LearnBallItemHolder;

import java.util.List;

import com.lwd.qjtv.R;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class LearnBallAdapter extends DefaultAdapter<String> {
    public LearnBallAdapter(List<String> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<String> getHolder(View v, int viewType) {
        return new LearnBallItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_test_layout;
    }

}