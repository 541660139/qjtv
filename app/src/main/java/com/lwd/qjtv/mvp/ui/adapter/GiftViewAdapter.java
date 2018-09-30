package com.lwd.qjtv.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.GiftBean;
import com.lwd.qjtv.mvp.ui.holder.GiftViewViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/19.
 */

public class GiftViewAdapter extends RecyclerView.Adapter<GiftViewViewHolder> {
    private List<GiftBean> resList = new ArrayList<>();
    private GiftViewViewHolder mHolder;

    protected GiftViewAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public GiftViewAdapter(List<GiftBean> resList) {
        this.resList = resList;
    }

    @Override
    public GiftViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GiftViewViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gift_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(GiftViewViewHolder holder, int position) {
        holder.setData(resList.get(position));
        holder.setOnItemClickListener((view, position1) -> {
            if (mOnItemClickListener != null && resList.size() > 0) {
                if (mHolder != null)
                    mHolder.bgIv.setVisibility(View.GONE);
                mHolder = holder;
                mHolder.bgIv.setVisibility(View.VISIBLE);
                mOnItemClickListener.onItemClick(view, resList.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resList == null ? 0 : resList.size();
    }


    public interface OnRecyclerViewItemClickListener<T> {
        void onItemClick(View view, T data, int position);
    }

    public void setOnItemClickListener(GiftViewAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


}
