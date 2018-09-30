package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.MyOrderBean;
import com.lwd.qjtv.mvp.ui.holder.MyOrderItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/27.
 */

public class MyOrderAdapter extends DefaultAdapter<MyOrderBean.DataBean> {
    public MyOrderAdapter(List<MyOrderBean.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<MyOrderBean.DataBean> getHolder(View v, int viewType) {
        return new MyOrderItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_my_order_layout;
    }

}