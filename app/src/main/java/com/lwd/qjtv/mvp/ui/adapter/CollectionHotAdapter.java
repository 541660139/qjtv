package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.ui.holder.MatchSearchCollectionItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class CollectionHotAdapter extends DefaultAdapter<String> {
    public CollectionHotAdapter(List<String> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<String> getHolder(View v, int viewType) {
        return new MatchSearchCollectionItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_search_collection_hot_layout;
    }

}