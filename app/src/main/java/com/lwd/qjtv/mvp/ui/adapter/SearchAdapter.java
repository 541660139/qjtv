package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.SearchBean;
import com.lwd.qjtv.mvp.ui.holder.SearchItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class SearchAdapter extends DefaultAdapter<SearchBean.DataBean> {
    public SearchAdapter(List<SearchBean.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<SearchBean.DataBean> getHolder(View v, int viewType) {
        return new SearchItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_fragment_watchball_title_layout;
    }

}