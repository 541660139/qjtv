package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.BBSDataBean;
import com.lwd.qjtv.mvp.ui.holder.StarBallViewHolder;

import java.util.List;

/**
 * Created by DELL on 2018/5/24.
 */

public class StarAdapter extends DefaultAdapter<BBSDataBean.DataBean.StarListBean.DataListBean> {

    public StarAdapter(List<BBSDataBean.DataBean.StarListBean.DataListBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<BBSDataBean.DataBean.StarListBean.DataListBean> getHolder(View v, int viewType) {
        return new StarBallViewHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {

        return R.layout.item_headview_layout;
    }
}
