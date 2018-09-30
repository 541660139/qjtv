package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.GuessCenterBean;
import com.lwd.qjtv.mvp.ui.holder.GuessCenterItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/17.
 */

public class GuessCenterAdapter extends DefaultAdapter<GuessCenterBean.DataBean> {
    private GuessCenterItemHolder guessCenterItemHolder;
    private GuessCenterItemHolder.GuessClickCallBack guessClickCallBack;

    public GuessCenterAdapter(List<GuessCenterBean.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<GuessCenterBean.DataBean> getHolder(View v, int viewType) {
        guessCenterItemHolder = new GuessCenterItemHolder(v);
        guessCenterItemHolder.setGuessClickCallback(guessClickCallBack);
        return guessCenterItemHolder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_guess_center_layout;
    }

    public void setGuessCallBack(GuessCenterItemHolder.GuessClickCallBack guessClickCallBack) {
        this.guessClickCallBack = guessClickCallBack;
        if (guessCenterItemHolder != null)
            guessCenterItemHolder.setGuessClickCallback(guessClickCallBack);
    }
}