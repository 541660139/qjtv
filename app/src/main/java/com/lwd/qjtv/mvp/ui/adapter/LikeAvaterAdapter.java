package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.LikeAvaterListBean;
import com.lwd.qjtv.mvp.ui.holder.LikeAvaterItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class LikeAvaterAdapter extends DefaultAdapter<LikeAvaterListBean.DataBean> {
    public LikeAvaterAdapter(List<LikeAvaterListBean.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<LikeAvaterListBean.DataBean> getHolder(View v, int viewType) {
        return new LikeAvaterItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_like_avater_layout;
    }

}