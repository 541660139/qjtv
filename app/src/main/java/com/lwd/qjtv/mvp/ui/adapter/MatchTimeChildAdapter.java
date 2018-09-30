package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.MatchTimeList;
import com.lwd.qjtv.mvp.ui.holder.MatchTimeChildItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/7.
 */

public class MatchTimeChildAdapter extends DefaultAdapter<MatchTimeList.DataBean> {
    public MatchTimeChildAdapter(List<MatchTimeList.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<MatchTimeList.DataBean> getHolder(View v, int viewType) {
        return new MatchTimeChildItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_match_time_layout;
    }

}