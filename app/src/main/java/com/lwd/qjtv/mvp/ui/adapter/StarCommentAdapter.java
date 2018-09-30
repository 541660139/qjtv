package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.StarCommentBean;
import com.lwd.qjtv.mvp.ui.holder.StarCommentItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/5.
 */

public class StarCommentAdapter extends DefaultAdapter<StarCommentBean.DataBean.CommentsListBean> {
    private StarCommentItemHolder.LikeCallBack likeCallback;

    public StarCommentAdapter(List<StarCommentBean.DataBean.CommentsListBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<StarCommentBean.DataBean.CommentsListBean> getHolder(View v, int viewType) {
        StarCommentItemHolder starCommentItemHolder = new StarCommentItemHolder(v);
        starCommentItemHolder.setLikeCallback(likeCallback);
        return starCommentItemHolder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_message_layout;
    }

    public void setLikeCallback(StarCommentItemHolder.LikeCallBack likeCallback) {
        this.likeCallback = likeCallback;
    }
}