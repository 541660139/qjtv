package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.GuessMyBean;
import com.lwd.qjtv.mvp.ui.holder.GuessMyItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/8/1.
 */

public class GuessMyAdapter extends DefaultAdapter<GuessMyBean.DataBean.TzDetailBean> {
    public GuessMyAdapter(List<GuessMyBean.DataBean.TzDetailBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<GuessMyBean.DataBean.TzDetailBean> getHolder(View v, int viewType) {
        return new GuessMyItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_my_guess_layout;
    }

}