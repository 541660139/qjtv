package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.MatchCollectionMoreBean;
import com.lwd.qjtv.mvp.ui.holder.MoreCollectionMatchItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/15.
 */

public class MatchCollectionMoreAdapter extends DefaultAdapter<MatchCollectionMoreBean.DataBean> {
    public MatchCollectionMoreAdapter(List<MatchCollectionMoreBean.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<MatchCollectionMoreBean.DataBean> getHolder(View v, int viewType) {
        return new MoreCollectionMatchItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_fragment_watchball_title_layout;
    }

}