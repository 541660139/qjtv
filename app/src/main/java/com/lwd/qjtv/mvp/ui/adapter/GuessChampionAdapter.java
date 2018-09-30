package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.GuessChampionBean;
import com.lwd.qjtv.mvp.ui.holder.GuessChampionItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/12.
 */

public class GuessChampionAdapter extends DefaultAdapter<GuessChampionBean.DataBean.MatchPlayerListBean> {
    private String title;
    public GuessChampionAdapter(List<GuessChampionBean.DataBean.MatchPlayerListBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<GuessChampionBean.DataBean.MatchPlayerListBean> getHolder(View v, int viewType) {
        GuessChampionItemHolder guessChampionItemHolder = new GuessChampionItemHolder(v);
        guessChampionItemHolder.setTitle(title);
        return guessChampionItemHolder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_guess_champion_layout;
    }

    public void setTitle(String title){
        this.title = title;
    }
}