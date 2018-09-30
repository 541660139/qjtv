package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.CommunityDataBean;
import com.lwd.qjtv.mvp.ui.holder.CommunityAllIteItemHolder;
import com.lwd.qjtv.mvp.ui.holder.CommunityAllIteMoreItemHolder;

import java.util.List;

/**
 * Created by DELL on 2018/8/27.
 */

public class CommunityAllAdapter extends DefaultAdapter<CommunityDataBean.DataBean> {
    private OnRecyclerViewTvItemClickListener onRecyclerViewTvItemClickListener;

    public CommunityAllAdapter(List<CommunityDataBean.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<CommunityDataBean.DataBean> getHolder(View v, int viewType) {
        if (viewType == 0) {
            CommunityAllIteItemHolder communityAllIteItemHolder = new CommunityAllIteItemHolder(v);
            communityAllIteItemHolder.setOnItemTvClickListener(new CommunityAllIteItemHolder.OnRecyclerViewTvItemClickListener() {
                @Override
                public void onItemTVClick(View view, String starid, int postion) {
                    onRecyclerViewTvItemClickListener.onItemTVClick(view, starid, postion);
                }
            });
            return communityAllIteItemHolder;
        } else {
            CommunityAllIteMoreItemHolder communityAllIteMoreItemHolder = new CommunityAllIteMoreItemHolder(v);
            communityAllIteMoreItemHolder.setOnItemTvClickListener(new CommunityAllIteMoreItemHolder.OnRecyclerViewTvItemClickListener() {
                @Override
                public void onItemTVClick(View view, String starid, int postion) {
                    onRecyclerViewTvItemClickListener.onItemTVClick(view, starid, postion);
                }
            });
            return communityAllIteMoreItemHolder;
        }
    }

    @Override
    public int getLayoutId(int viewType) {
        if (viewType == 0) {
            return R.layout.item_community_all_item_layout;
        } else {
            return R.layout.item_community_all_more_item_layout;
        }
    }


    @Override
    public int getItemViewType(int position) {
        CommunityDataBean.DataBean dataBean = mInfos.get(position);
        int type = 0;
        String[] split = dataBean.getPicture().split(",");
        if (split.length > 1) {
            type = 0;
        } else {
            type = 1;
        }
        return type;
    }


    public interface OnRecyclerViewTvItemClickListener {
        void onItemTVClick(View view, String starid, int postion);
    }

    public void setOnItemTvClickListener(OnRecyclerViewTvItemClickListener listener) {
        this.onRecyclerViewTvItemClickListener = listener;
    }


    public List<CommunityDataBean.DataBean> getAllData() {
        return mInfos;

    }


}
