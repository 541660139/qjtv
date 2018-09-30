package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.ChampionGuess;
import com.lwd.qjtv.mvp.ui.holder.ChampionGuessItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/8/1.
 */

public class ChampionGuessAdapter extends DefaultAdapter<ChampionGuess.DataBean> {
    public ChampionGuessAdapter(List<ChampionGuess.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<ChampionGuess.DataBean> getHolder(View v, int viewType) {
        return new ChampionGuessItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_champion_guess_layout;
    }

}