package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.SearchCollectionBean;
import com.lwd.qjtv.mvp.ui.holder.SearchMatchCollectionItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/27.
 */

public class SearchMatchCollectionAdapter extends DefaultAdapter<SearchCollectionBean.DataBean> {
    public SearchMatchCollectionAdapter(List<SearchCollectionBean.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<SearchCollectionBean.DataBean> getHolder(View v, int viewType) {
        return new SearchMatchCollectionItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_matchcollection_layout;
    }

}