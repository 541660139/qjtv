package com.lwd.qjtv.mvp.ui.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.MyLikeListBean;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/5.
 */

public class MyLikeItemHolder extends BaseHolder<MyLikeListBean.DataBean.AvaterBean> {
    @BindView(R.id.item_my_like_item_avater_one_civ)
    CircleImageView itemMyLikeItemAvaterOneCiv;
    @BindView(R.id.item_my_like_item_avater_two_civ)
    CircleImageView itemMyLikeItemAvaterTwoCiv;
    @BindView(R.id.item_my_like_item_avater_three_civ)
    CircleImageView itemMyLikeItemAvaterThreeCiv;
    @BindView(R.id.item_my_like_item_avater_four_civ)
    CircleImageView itemMyLikeItemAvaterFourCiv;
    @BindView(R.id.item_my_like_item_avater_more_civ)
    CircleImageView itemMyLikeItemAvaterMoreCiv;
    @BindView(R.id.item_my_like_item_content_tv)
    TextView itemMyLikeItemContentTv;
    @BindView(R.id.item_my_like_item_time_tv)
    TextView itemMyLikeItemTimeTv;
    @BindView(R.id.item_my_like_message_iv)
    ImageView itemMyCommentMessageIv;
    @BindView(R.id.item_my_like_item_msg_tv)
    TextView itemMyCommentItemMsgTv;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private ClickMoreCallback clickMoreCallback;
    private String card_id;

    public MyLikeItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(MyLikeListBean.DataBean.AvaterBean data, int position) {
        int size = data.getAvater().size();
        card_id = data.getCard_id();
        if (size == 0) {
            itemMyLikeItemAvaterOneCiv.setVisibility(View.INVISIBLE);
            itemMyLikeItemAvaterTwoCiv.setVisibility(View.INVISIBLE);
            itemMyLikeItemAvaterThreeCiv.setVisibility(View.INVISIBLE);
            itemMyLikeItemAvaterFourCiv.setVisibility(View.INVISIBLE);
            itemMyLikeItemAvaterMoreCiv.setVisibility(View.INVISIBLE);
        } else if (size == 1) {
            itemMyLikeItemAvaterOneCiv.setVisibility(View.VISIBLE);
            itemMyLikeItemAvaterTwoCiv.setVisibility(View.INVISIBLE);
            itemMyLikeItemAvaterThreeCiv.setVisibility(View.INVISIBLE);
            itemMyLikeItemAvaterFourCiv.setVisibility(View.INVISIBLE);
            itemMyLikeItemAvaterMoreCiv.setVisibility(View.INVISIBLE);
            if (!TextUtils.isEmpty(data.getAvater().get(0)))
                loadImg(data.getAvater().get(0), itemMyLikeItemAvaterOneCiv);
        } else if (size == 2) {
            itemMyLikeItemAvaterOneCiv.setVisibility(View.VISIBLE);
            itemMyLikeItemAvaterTwoCiv.setVisibility(View.VISIBLE);
            itemMyLikeItemAvaterThreeCiv.setVisibility(View.INVISIBLE);
            itemMyLikeItemAvaterFourCiv.setVisibility(View.INVISIBLE);
            itemMyLikeItemAvaterMoreCiv.setVisibility(View.INVISIBLE);
            if (!TextUtils.isEmpty(data.getAvater().get(0)))
                loadImg(data.getAvater().get(0), itemMyLikeItemAvaterOneCiv);
            if (!TextUtils.isEmpty(data.getAvater().get(1)))
                loadImg(data.getAvater().get(1), itemMyLikeItemAvaterTwoCiv);
        } else if (size == 3) {
            itemMyLikeItemAvaterOneCiv.setVisibility(View.VISIBLE);
            itemMyLikeItemAvaterTwoCiv.setVisibility(View.VISIBLE);
            itemMyLikeItemAvaterThreeCiv.setVisibility(View.VISIBLE);
            itemMyLikeItemAvaterFourCiv.setVisibility(View.INVISIBLE);
            itemMyLikeItemAvaterMoreCiv.setVisibility(View.INVISIBLE);
            if (!TextUtils.isEmpty(data.getAvater().get(0)))
                loadImg(data.getAvater().get(0), itemMyLikeItemAvaterOneCiv);
            if (!TextUtils.isEmpty(data.getAvater().get(1)))
                loadImg(data.getAvater().get(1), itemMyLikeItemAvaterTwoCiv);
            if (!TextUtils.isEmpty(data.getAvater().get(2)))
                loadImg(data.getAvater().get(2), itemMyLikeItemAvaterThreeCiv);
        } else if (size >= 4) {
            itemMyLikeItemAvaterOneCiv.setVisibility(View.VISIBLE);
            itemMyLikeItemAvaterTwoCiv.setVisibility(View.VISIBLE);
            itemMyLikeItemAvaterThreeCiv.setVisibility(View.VISIBLE);
            itemMyLikeItemAvaterFourCiv.setVisibility(View.VISIBLE);
            itemMyLikeItemAvaterMoreCiv.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(data.getAvater().get(0)))
                loadImg(data.getAvater().get(0), itemMyLikeItemAvaterOneCiv);
            if (!TextUtils.isEmpty(data.getAvater().get(1)))
                loadImg(data.getAvater().get(1), itemMyLikeItemAvaterTwoCiv);
            if (!TextUtils.isEmpty(data.getAvater().get(2)))
                loadImg(data.getAvater().get(2), itemMyLikeItemAvaterThreeCiv);
            if (!TextUtils.isEmpty(data.getAvater().get(3)))
                loadImg(data.getAvater().get(3), itemMyLikeItemAvaterFourCiv);
        }
        itemMyCommentItemMsgTv.setText(data.getMsg() == null ? "" : data.getMsg());
        itemMyLikeItemTimeTv.setText(data.getCtime() == null ? "" : data.getCtime());
        itemMyLikeItemContentTv.setText(data.getTitle() == null ? "" : data.getTitle());
        if (!TextUtils.isEmpty(data.getPic_h()))
            loadImg(data.getPic_h(), itemMyCommentMessageIv);
    }

    @Override
    protected void onRelease() {
        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
                .imageViews(itemMyLikeItemAvaterOneCiv, itemMyLikeItemAvaterTwoCiv, itemMyLikeItemAvaterThreeCiv, itemMyLikeItemAvaterFourCiv, itemMyLikeItemAvaterMoreCiv)
                .build());
    }

    public void setClickMoreCallback(ClickMoreCallback clickMoreCallback) {
        if (clickMoreCallback != null) {
            this.clickMoreCallback = clickMoreCallback;
            itemMyLikeItemAvaterOneCiv.setOnClickListener(v -> clickMoreCallback.clickMore(card_id));
        }
    }

    public interface ClickMoreCallback {
        void clickMore(String id);
    }

    private void loadImg(String url, ImageView imageView) {
        mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                        ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
                GlideImageConfig
                        .builder()
                        .url(url)
                        .imageView(imageView)
                        .build());
    }
}