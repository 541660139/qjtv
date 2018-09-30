package com.lwd.qjtv.mvp.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.StarCommentBean;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/5.
 */

public class StarCommentItemHolder extends BaseHolder<StarCommentBean.DataBean.CommentsListBean> {

    @BindView(R.id.item_message_layout_head_civ)
    CircleImageView headCiv;
    @BindView(R.id.item_message_layout_good_number_tv)
    TextView numTv;
    @BindView(R.id.item_message_layout_good_iv)
    ImageView goodIv;
    @BindView(R.id.item_message_layout_name_tv)
    TextView nameTv;
    @BindView(R.id.item_message_layout_content_tv)
    TextView contentTv;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private boolean isLike;
    private LikeCallBack likeCallback;

    public StarCommentItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(StarCommentBean.DataBean.CommentsListBean data, int position) {
        isLike = data.getIs_liked() == 1;
        if(isLike)
            goodIv.setImageResource(R.mipmap.good_select);
        else
            goodIv.setImageResource(R.mipmap.good);
        numTv.setOnClickListener(view -> {
            if (likeCallback != null)
                likeCallback.clickLikeButton(data);
        });
        goodIv.setOnClickListener(view -> {
            if (likeCallback != null)
                likeCallback.clickLikeButton(data);
        });
        numTv.setText(data.getLike_num());
        nameTv.setText(data.getUsername());
        String content = data.getContent();
        try {
            String strUTF8 = URLDecoder.decode(content, "UTF-8");
            contentTv.setText(strUTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (data.getAvater() != null && !"".equals(data.getAvater()))
            Glide.with(itemView.getContext()).load(data.getAvater()).into(headCiv);
    }


    @Override
    protected void onRelease() {
        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
                .imageViews(headCiv)
                .build());
    }

    public void setLikeCallback(LikeCallBack likeCallback) {
        this.likeCallback = likeCallback;
    }

    public interface LikeCallBack {
        void clickLikeButton(StarCommentBean.DataBean.CommentsListBean data);
    }
}