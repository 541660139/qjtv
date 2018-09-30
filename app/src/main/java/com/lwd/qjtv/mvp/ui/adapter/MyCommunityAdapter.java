package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.MyCommunityListDataBean;
import com.lwd.qjtv.mvp.ui.holder.MyCommunityItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class MyCommunityAdapter extends DefaultAdapter<MyCommunityListDataBean.DataBean> {

    public MyCommunityAdapter(List<MyCommunityListDataBean.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<MyCommunityListDataBean.DataBean> getHolder(View v, int viewType) {
        return new MyCommunityItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_community_my_bbs_layout;
    }

}