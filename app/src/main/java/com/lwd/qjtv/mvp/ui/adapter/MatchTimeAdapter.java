package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.ui.holder.MatchTimeItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/27.
 */

public class MatchTimeAdapter extends DefaultAdapter<String> {
    public MatchTimeAdapter(List<String> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<String> getHolder(View v, int viewType) {
        return new MatchTimeItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_match_time_layout;
    }

}