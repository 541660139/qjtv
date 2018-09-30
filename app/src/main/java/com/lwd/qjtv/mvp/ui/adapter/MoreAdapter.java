package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.MoreTeachListBean;
import com.lwd.qjtv.mvp.ui.holder.MoreItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class MoreAdapter extends DefaultAdapter<MoreTeachListBean.DataBean> {
    public MoreAdapter(List<MoreTeachListBean.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<MoreTeachListBean.DataBean> getHolder(View v, int viewType) {
        return new MoreItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_fragment_watchball_title_layout;
    }

}