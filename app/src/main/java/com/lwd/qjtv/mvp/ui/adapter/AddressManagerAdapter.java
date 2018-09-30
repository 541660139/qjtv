package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.AddressBean;
import com.lwd.qjtv.mvp.ui.holder.AddressManagerItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/6.
 */

public class AddressManagerAdapter extends DefaultAdapter<AddressBean.DataBean> {

    private boolean isWeb;
    private AddressManagerItemHolder addressManagerItemHolder;
    private ClickCallback clickCallback;

    public AddressManagerAdapter(List<AddressBean.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<AddressBean.DataBean> getHolder(View v, int viewType) {
        addressManagerItemHolder = new AddressManagerItemHolder(v);
        addressManagerItemHolder.setIsWeb(isWeb);
        addressManagerItemHolder.setCallback(clickCallback);
        return addressManagerItemHolder;
    }


    public void setClickCallback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    public interface ClickCallback<T> {
        void click(T data, int position);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_address_manage_layout;
    }

    public void setIsWeb() {
        isWeb = true;
        notifyDataSetChanged();
    }
}