package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.MoreVideoBean;
import com.lwd.qjtv.mvp.ui.holder.MoreVideoItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/15.
 */

public class MoreVideoAdapter extends DefaultAdapter<MoreVideoBean.DataBean> {
    public MoreVideoAdapter(List<MoreVideoBean.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<MoreVideoBean.DataBean> getHolder(View v, int viewType) {
        return new MoreVideoItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_fragment_watchball_title_layout;
    }

}