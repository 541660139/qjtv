package com.lwd.qjtv.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.CommunityContentBean;
import com.lwd.qjtv.mvp.model.entity.ReplyBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by wangfeng on 2018/8/31.
 **/
public class CommunityPostDetailAdapter extends CommonRecycleViewAdapter<CommunityContentBean> {
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;
    private ViewGroup.LayoutParams layoutParams;

    public CommunityPostDetailAdapter(Context context, int layoutId) {
        super(context, layoutId);
        mContext = context;
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public void convert(ViewHolderHelper helper, CommunityContentBean communityPostDetailBean) {

        CircleImageView ivAvater = helper.getView(R.id.iv_avater);


        if (communityPostDetailBean.getAvater() != null && !TextUtils.isEmpty(communityPostDetailBean.getAvater())) {
            Glide.with(mContext).load(communityPostDetailBean.getAvater()).placeholder(R.mipmap.video_place_holder).into(ivAvater);
        }
        helper.setText(R.id.tv_name, communityPostDetailBean.getUsername());
        int rank = helper.getAdapterPosition() + 1;
        helper.setText(R.id.tv_rank, rank + "楼");
        helper.setText(R.id.tv_time, communityPostDetailBean.getCreate_time());
//        String result = EmojiParser.parseToUnicode(communityPostDetailBean.getContent());
        helper.setText(R.id.tv_comment, communityPostDetailBean.getContent());
//        helper.setText(R.id.item_my_comment_item_see_tv, communityPostDetailBean.getLike_num());
        ImageView ivLike = helper.getView(R.id.item_my_comment_item_like_iv);
//        int is_zan = communityPostDetailBean.getIs_zan();
//        if (is_zan == 1) {
//            ivLike.setImageResource(R.mipmap.content_comment_press);
//        } else {
//            ivLike.setImageResource(R.mipmap.content_comment);
//        }
        //实例化子recycleview
        RecyclerView childRecycleview = helper.getView(R.id.childrecyclerview);
        childRecycleview.setLayoutManager(new LinearLayoutManager(mContext));
        ReplyAdapter replyAdapter = new ReplyAdapter(mContext, R.layout.comment_reply_item_layout, communityPostDetailBean, helper.getAdapterPosition());
        childRecycleview.setAdapter(replyAdapter);
        if (communityPostDetailBean.getReview_reply_list().size() >= 1) {//有回复列表
            List<ReplyBean> review_reply_list = communityPostDetailBean.getReview_reply_list();
            if (null != review_reply_list) {
                replyAdapter.replaceAll(review_reply_list);
            }
        }

        ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemLikeClickListener(helper.getAdapterPosition());
            }
        });
        helper.getView(R.id.tv_reply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemReplyClickListener(helper.getAdapterPosition());
            }
        });
        if (communityPostDetailBean.getReview_reply_list().size() > 3 && !communityPostDetailBean.isShowAll()) {
            helper.getView(R.id.tv_whatch).setVisibility(View.VISIBLE);


        } else {
            helper.getView(R.id.tv_whatch).setVisibility(View.GONE);


        }

        helper.getView(R.id.tv_whatch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemWatchClickListener(helper.getAdapterPosition());
            }
        });

    }

    private class ReplyAdapter extends CommonRecycleViewAdapter<ReplyBean> {
        private CommunityContentBean mCommunityPostDetailBean;
        private int position;

        public ReplyAdapter(Context context, int layoutId, CommunityContentBean mCommunityPostDetailBean, int position) {
            super(context, layoutId);
            this.mCommunityPostDetailBean = mCommunityPostDetailBean;
            this.position = position;
        }

        @Override
        public int getItemCount() {
            if (!mCommunityPostDetailBean.isShowAll() && mCommunityPostDetailBean.getReview_reply_list().size() > 3) {
                return 3;
            } else {
                return super.getItemCount();
            }
        }

        @Override
        public void convert(ViewHolderHelper helper, ReplyBean replyBean) {

            String replyUser = replyBean.getReply_username();
            String toUser = replyBean.getTo_name();
            if (!TextUtils.isEmpty(replyUser)) {
                helper.setText(R.id.reply_item_user, replyUser);
            } else {
                helper.setText(R.id.reply_item_user, "未知用户");
            }
            TextView tv_tip = helper.getView(R.id.reply_item_tip);
            TextView tv_toName = helper.getView(R.id.reply_item_touser);
            if (toUser.equals(mCommunityPostDetailBean.getUsername())) {//评论楼主
                tv_tip.setVisibility(View.GONE);
                tv_toName.setVisibility(View.GONE);
            } else {//评论子评论
                tv_tip.setVisibility(View.VISIBLE);
                tv_toName.setVisibility(View.VISIBLE);
            }
            if (!TextUtils.isEmpty(toUser)) {
                tv_toName.setText(toUser);
            } else {
                tv_toName.setText("未知用户");
            }

            helper.setText(R.id.reply_item_content, replyBean.getContent());


            helper.getView(R.id.ll_root).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onChildItemReplyClickListener(position, helper.getAdapterPosition());
                }
            });
        }
    }


    public interface OnItemClickListener {
        void onItemLikeClickListener(int position);

        void onItemReplyClickListener(int position);

        void onChildItemReplyClickListener(int position, int childPosition);

        void onItemWatchClickListener(int position);
    }

}
