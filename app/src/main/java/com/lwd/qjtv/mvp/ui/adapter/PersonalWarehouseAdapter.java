package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.PersonalWareHouseBean;
import com.lwd.qjtv.mvp.ui.holder.PersonalWarehouseItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/6.
 */

public class PersonalWarehouseAdapter extends DefaultAdapter<PersonalWareHouseBean.DataBean> {
    private PersonalWarehouseItemHolder personalWarehouseItemHolder;
    private PersonalWarehouseItemHolder.ChooseGiftCallback callBack;
    private String id;
    private boolean selectAll;

    public PersonalWarehouseAdapter(List<PersonalWareHouseBean.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<PersonalWareHouseBean.DataBean> getHolder(View v, int viewType) {
        personalWarehouseItemHolder = new PersonalWarehouseItemHolder(v);
        personalWarehouseItemHolder.setChooseGiftCallBack(callBack);
        personalWarehouseItemHolder.setSelectAll(selectAll);
        personalWarehouseItemHolder.checkBox.setChecked(selectAll);

        return personalWarehouseItemHolder;
    }

    @Override
    public void onBindViewHolder(BaseHolder<PersonalWareHouseBean.DataBean> holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.setIsRecyclable(false);
//            ((PersonalWarehouseItemHolder) holder).checkBox.setChecked(false);
        callBack.chooseGift(Integer.parseInt(mInfos.get(position).getGift_id()), Integer.parseInt(mInfos.get(position).getCount()), selectAll);
    }


    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_personal_warehouse_layout;
    }

    public void setChooseGiftCallBack(PersonalWarehouseItemHolder.ChooseGiftCallback callBack) {
        this.callBack = callBack;
    }

    public void setSelectAll(boolean allSelect) {
        this.selectAll = allSelect;
        notifyDataSetChanged();
    }

}