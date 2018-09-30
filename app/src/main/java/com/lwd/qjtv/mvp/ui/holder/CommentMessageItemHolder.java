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
import com.lwd.qjtv.mvp.model.entity.VideoDetailsNewBean;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/19.
 */

public class CommentMessageItemHolder extends BaseHolder<VideoDetailsNewBean.DataBean.VideoReviewBean> {
    @BindView(R.id.item_message_layout_head_civ)
    CircleImageView headCiv;
    @BindView(R.id.item_message_layout_name_tv)
    TextView nameTv;
    @BindView(R.id.item_message_layout_good_number_tv)
    TextView numTv;
    @BindView(R.id.item_message_layout_content_tv)
    TextView contentTv;
    @BindView(R.id.item_message_layout_good_iv)
    ImageView goodIv;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private CommentMessageItemHolder.VideoCommentLike likeCallback;

    public CommentMessageItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }



    @Override
    public void setData(VideoDetailsNewBean.DataBean.VideoReviewBean data, int position) {
        boolean isLike = data.getIs_review_like() == 1;
        if (!isLike)
            goodIv.setImageResource(R.mipmap.good);
        else
            goodIv.setImageResource(R.mipmap.good_select);

        goodIv.setOnClickListener(view -> {
            if (likeCallback != null)
                likeCallback.likeVideoComment(data);
        });
        numTv.setOnClickListener(view -> {
            if (likeCallback != null)
                likeCallback.likeVideoComment(data);
        });
        nameTv.setText(data.getUsername());
        numTv.setText(data.getLike_num());
        String content = data.getContent();
        try {
            String strUTF8 = URLDecoder.decode(content, "UTF-8");
            contentTv.setText(strUTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (data.getAvater() != null && !"".equals(data.getAvater()) && itemView.getContext() != null)
            Glide.with(itemView.getContext()).load(data.getAvater()).into(headCiv);
//            mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
//                            ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
//                    GlideImageConfig
//                            .builder()
//                            .url(data.getAvater())
//                            .imageView(headCiv)
//                            .build());
    }

    public void setLikeCallback(CommentMessageItemHolder.VideoCommentLike likeCallback) {
        this.likeCallback = likeCallback;
    }

    @Override
    protected void onRelease() {
        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
                .imageViews(headCiv)
                .build());
    }

    public interface VideoCommentLike {
        void likeVideoComment(VideoDetailsNewBean.DataBean.VideoReviewBean data);
    }
}