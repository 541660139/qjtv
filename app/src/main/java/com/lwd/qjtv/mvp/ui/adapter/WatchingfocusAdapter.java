package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.ui.holder.WatchingfocusHolder;

import java.util.List;

/**
 * Created by DELL on 2018/6/12.
 */

public class WatchingfocusAdapter extends DefaultAdapter<String> {
    public WatchingfocusAdapter(List<String> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<String> getHolder(View v, int viewType) {
        return new WatchingfocusHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.watchingfocus_item;
    }
}
