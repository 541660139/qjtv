package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.GuessRankBean;
import com.lwd.qjtv.mvp.ui.holder.RankItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class RankAdapter extends DefaultAdapter<GuessRankBean.DataBean> {
    public RankAdapter(List<GuessRankBean.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<GuessRankBean.DataBean> getHolder(View v, int viewType) {
        return new RankItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_rank_layout;
    }

}