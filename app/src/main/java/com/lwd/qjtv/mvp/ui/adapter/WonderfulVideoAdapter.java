package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.RelatedVideoBean;
import com.lwd.qjtv.mvp.ui.holder.WonderfulVideoItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/24.
 */

public class WonderfulVideoAdapter extends DefaultAdapter<RelatedVideoBean.DataBean> {
    public WonderfulVideoAdapter(List<RelatedVideoBean.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<RelatedVideoBean.DataBean> getHolder(View v, int viewType) {
        return new WonderfulVideoItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_wonderful_video_layout;
    }
}