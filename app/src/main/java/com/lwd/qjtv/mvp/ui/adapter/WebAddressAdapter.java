package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.AddressBean;
import com.lwd.qjtv.mvp.ui.holder.WebAddressItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/6.
 */

public class WebAddressAdapter extends DefaultAdapter<AddressBean.DataBean> {
    private WebAddressItemHolder addressManagerItemHolder;

    public WebAddressAdapter(List<AddressBean.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<AddressBean.DataBean> getHolder(View v, int viewType) {
        addressManagerItemHolder = new WebAddressItemHolder(v);
        return addressManagerItemHolder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_address_manage_web_layout;
    }

}