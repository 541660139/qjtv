package com.lwd.qjtv.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.GuessMoreBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/8/2.
 */

public class GuessChildItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<GuessMoreBean.DataBean.TzDetailBean> list = new ArrayList<>();

    public GuessChildItemAdapter(List<GuessMoreBean.DataBean.TzDetailBean> tz_detail) {
        this.list = tz_detail;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guess_child_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder mHolder = (ItemHolder) holder;
        mHolder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_guess_child_left_name_tv)
        TextView leftNameTv;
        @BindView(R.id.item_guess_child_right_name_tv)
        TextView rightNameTv;
        @BindView(R.id.item_guess_child_status_tv)
        TextView statusTv;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(GuessMoreBean.DataBean.TzDetailBean tzDetailBean) {
            leftNameTv.setText(tzDetailBean.getStarInfo().get(0).getName());
            rightNameTv.setText(tzDetailBean.getStarInfo().get(1).getName());
            statusTv.setText(tzDetailBean.getTz_name() + "|赔率：" + tzDetailBean.getPeilv());
        }
    }
}
