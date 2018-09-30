package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.OrderExpressBean;
import com.lwd.qjtv.mvp.ui.holder.LogisticsItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/27.
 */

public class LogisticsAdapter extends DefaultAdapter<OrderExpressBean.DataBeanX.DataBean> {
    public LogisticsAdapter(List<OrderExpressBean.DataBeanX.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<OrderExpressBean.DataBeanX.DataBean> getHolder(View v, int viewType) {
        return new LogisticsItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_logistics_layout;
    }

}