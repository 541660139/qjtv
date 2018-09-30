package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.BBSDataBean;
import com.lwd.qjtv.mvp.ui.holder.CommunityItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/19.
 */

public class CommunityAdapter extends DefaultAdapter<BBSDataBean.DataBean.ListBean> {


    public CommunityAdapter(List<BBSDataBean.DataBean.ListBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<BBSDataBean.DataBean.ListBean> getHolder(View v, int viewType) {
        return new CommunityItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_community_layout;
    }
}