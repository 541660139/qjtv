package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.mvp.model.entity.WatchBallBean;
import com.lwd.qjtv.mvp.ui.holder.WatchBallItemHolder;

import java.util.List;

import com.lwd.qjtv.R;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class WatchBallAdapter extends DefaultAdapter<WatchBallBean.DataBean.StarListBean.DataListBeanXXXXX> {
    public WatchBallAdapter(List<WatchBallBean.DataBean.StarListBean.DataListBeanXXXXX> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<WatchBallBean.DataBean.StarListBean.DataListBeanXXXXX> getHolder(View v, int viewType) {
        return new WatchBallItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_headview_layout;
    }

}