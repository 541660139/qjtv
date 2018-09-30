package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.MatchCollectionNewListBean;
import com.lwd.qjtv.mvp.ui.holder.MatchCollectionItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/27.
 */

public class MatchCollectionAdapter extends DefaultAdapter<MatchCollectionNewListBean.DataBeanX.MatchListBean.DataBean> {
    public MatchCollectionAdapter(List<MatchCollectionNewListBean.DataBeanX.MatchListBean.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<MatchCollectionNewListBean.DataBeanX.MatchListBean.DataBean> getHolder(View v, int viewType) {
        return new MatchCollectionItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_matchcollection_layout;
    }

}