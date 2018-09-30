package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.MallListBean;
import com.lwd.qjtv.mvp.ui.holder.MallItemHolder;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class MallAdapter extends DefaultAdapter<MallListBean.DataBean> {
    public MallAdapter(List<MallListBean.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<MallListBean.DataBean> getHolder(View v, int viewType) {
        return new MallItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_fragment_mall_layout;
    }

}