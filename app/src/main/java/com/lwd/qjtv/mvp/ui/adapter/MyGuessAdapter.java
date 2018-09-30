package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.GuessMyBean;
import com.lwd.qjtv.mvp.ui.holder.MyGuessItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/17.
 */

public class MyGuessAdapter extends DefaultAdapter<GuessMyBean.DataBean.TzDetailBean> {
    public MyGuessAdapter(List<GuessMyBean.DataBean.TzDetailBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<GuessMyBean.DataBean.TzDetailBean> getHolder(View v, int viewType) {
        return new MyGuessItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_my_guess_layout;
    }


}