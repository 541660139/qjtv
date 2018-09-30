package com.lwd.qjtv.mvp.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.GiftBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/19.
 */

public class GiftViewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.item_gift_iv)
    ImageView giftIv;
    @BindView(R.id.item_gift_coin_tv)
    TextView coinTv;
    @BindView(R.id.item_gift_name_tv)
    TextView nameTv;
    @BindView(R.id.item_gift_bg_iv)
    public ImageView bgIv;
    protected BaseHolder.OnViewClickListener mOnViewClickListener = null;

    public GiftViewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void setData(GiftBean giftBean) {
        giftIv.setBackground(itemView.getContext().getResources().getDrawable(giftBean.getIdRes()));
        coinTv.setText(giftBean.getCoin() == null || "".equals(giftBean.getCoin()) ? "0积分" : giftBean.getCoin() + "积分");
        nameTv.setText(giftBean.getGiftName());
    }

    @Override
    public void onClick(View view) {
        if (mOnViewClickListener != null) {
            mOnViewClickListener.onViewClick(view, this.getPosition());
        }
    }

    public void setOnItemClickListener(BaseHolder.OnViewClickListener listener) {
        this.mOnViewClickListener = listener;
    }
}
