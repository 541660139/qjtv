package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.BetModelBean;
import com.lwd.qjtv.mvp.ui.holder.MoreBetItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/13.
 */

public class MoreBetAdapter extends DefaultAdapter<BetModelBean> {

    private MoreBetChooseCallback chooseCallback;
    private MoreBetDeleteCallback deleteCallback;

    public MoreBetAdapter(List<BetModelBean> infos) {
        super(infos);
    }

    @Override
    public void onBindViewHolder(BaseHolder<BetModelBean> holder, int position) {
        super.onBindViewHolder(holder, position);
        MoreBetItemHolder moreBetItemHolder = (MoreBetItemHolder) holder;
        if (mInfos.size() - 1 == position)
            moreBetItemHolder.setTip();
        moreBetItemHolder.checkBox.setOnCheckedChangeListener((compoundButton, b) ->
                chooseCallback.chooseCallback(mInfos.get(position), b));
    }

    @Override
    public BaseHolder<BetModelBean> getHolder(View v, int viewType) {
        MoreBetItemHolder moreBetItemHolder = new MoreBetItemHolder(v);
        return moreBetItemHolder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_more_bet_layout;
    }

    public void setChooseCallback(MoreBetChooseCallback chooseCallback) {
        this.chooseCallback = chooseCallback;
    }

    public interface MoreBetDeleteCallback {
        void deleteItem(BetModelBean moreBetBean);
    }

    public interface MoreBetChooseCallback {
        void chooseCallback(BetModelBean betModelBean, boolean isChoose);
    }
}