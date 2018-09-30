package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.FansOrFollerBean;
import com.lwd.qjtv.mvp.ui.holder.CommunityAllIteItemHolder;
import com.lwd.qjtv.mvp.ui.holder.FansOrFollowItemHolder;

import java.util.List;

/**
 * Created by DELL on 2018/8/29.
 */

public class FansOrFollowAdapter extends DefaultAdapter<FansOrFollerBean.DataBean> {

    private CommunityAllAdapter.OnRecyclerViewTvItemClickListener onRecyclerViewTvItemClickListener;


    public FansOrFollowAdapter(List<FansOrFollerBean.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<FansOrFollerBean.DataBean> getHolder(View v, int viewType) {
        FansOrFollowItemHolder fansOrFollowItemHolder = new FansOrFollowItemHolder(v);
        fansOrFollowItemHolder.setOnItemTvClickListener(new CommunityAllIteItemHolder.OnRecyclerViewTvItemClickListener() {
            @Override
            public void onItemTVClick(View view, String starid, int postion) {
                onRecyclerViewTvItemClickListener.onItemTVClick(view, starid, postion);
            }
        });
        return fansOrFollowItemHolder;

    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_fan_or_follow;
    }


    public interface OnRecyclerViewTvItemClickListener {
        void onItemTVClick(View view, String starid, int postion);
    }

    public void setOnItemTvClickListener(CommunityAllAdapter.OnRecyclerViewTvItemClickListener listener) {
        this.onRecyclerViewTvItemClickListener = listener;
    }

}
