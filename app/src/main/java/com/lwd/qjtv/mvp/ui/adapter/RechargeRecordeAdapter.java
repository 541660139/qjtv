package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.DetailsMoneyBean;
import com.lwd.qjtv.mvp.ui.holder.RechargeRecordeItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/24.
 */

public class RechargeRecordeAdapter extends DefaultAdapter<DetailsMoneyBean.DataBean> {
    public RechargeRecordeAdapter(List<DetailsMoneyBean.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<DetailsMoneyBean.DataBean> getHolder(View v, int viewType) {
        return new RechargeRecordeItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_recharge_recorde_layout;
    }

}