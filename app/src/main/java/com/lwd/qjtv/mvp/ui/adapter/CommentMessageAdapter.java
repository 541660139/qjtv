package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.VideoDetailsNewBean;
import com.lwd.qjtv.mvp.ui.holder.CommentMessageItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/19.
 */

public class CommentMessageAdapter extends DefaultAdapter<VideoDetailsNewBean.DataBean.VideoReviewBean> {

    private CommentMessageItemHolder.VideoCommentLike likeCallback;

    public CommentMessageAdapter(List<VideoDetailsNewBean.DataBean.VideoReviewBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<VideoDetailsNewBean.DataBean.VideoReviewBean> getHolder(View v, int viewType) {
        CommentMessageItemHolder commentMessageItemHolder = new CommentMessageItemHolder(v);
        commentMessageItemHolder.setLikeCallback(likeCallback);
        return commentMessageItemHolder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_message_layout;
    }

    public void setLikeCallback(CommentMessageItemHolder.VideoCommentLike likeCallback) {
        this.likeCallback = likeCallback;
    }

}