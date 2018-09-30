package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.SearchMallBean;
import com.lwd.qjtv.mvp.ui.holder.MallSearchItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/15.
 */

public class MallSearchAdapter extends DefaultAdapter<SearchMallBean.DataBean> {
    public MallSearchAdapter(List<SearchMallBean.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<SearchMallBean.DataBean> getHolder(View v, int viewType) {
        return new MallSearchItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_fragment_mall_layout;
    }

}