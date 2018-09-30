package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.GuessMoreBean;
import com.lwd.qjtv.mvp.ui.holder.GuessMoreItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/8/2.
 */

public class GuessMoreAdapter extends DefaultAdapter<GuessMoreBean.DataBean> {
    public GuessMoreAdapter(List<GuessMoreBean.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<GuessMoreBean.DataBean> getHolder(View v, int viewType) {
        return new GuessMoreItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_guess_more_layout;
    }

}