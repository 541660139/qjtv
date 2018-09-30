package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.MatchCollectionNewListBean;
import com.lwd.qjtv.mvp.ui.holder.MatchCollectionListItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/27.
 */

public class MatchCollectionListAdapter extends DefaultAdapter<MatchCollectionNewListBean.DataBeanX.MatchListBean> {
    public MatchCollectionListAdapter(List<MatchCollectionNewListBean.DataBeanX.MatchListBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<MatchCollectionNewListBean.DataBeanX.MatchListBean> getHolder(View v, int viewType) {
        return new MatchCollectionListItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_matchcollection_list_layout;
    }

}