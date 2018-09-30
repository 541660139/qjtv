package com.lwd.qjtv.mvp.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.BBSDataBean;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/5.
 */

public class CommunityItemHolder extends BaseHolder<BBSDataBean.DataBean.ListBean> {
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    @BindView(R.id.item_community_content_tv)
    TextView contentTv;
    @BindView(R.id.item_community_title_tv)
    TextView titleTv;
    @BindView(R.id.item_community_iv)
    ImageView imageView;
    @BindView(R.id.item_community_num_tv)
    TextView numTv;

    public CommunityItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(BBSDataBean.DataBean.ListBean data, int position) {
        titleTv.setText(data.getTitle() == null ? "" : data.getTitle());
        contentTv.setText(data.getDes() == null ? "" : data.getDes());
        numTv.setText(data.getNum_mes() == null ? "今日：+0" : "今日：+" + data.getNum_mes());
        mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                        ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
                GlideImageConfig
                        .builder()
                        .url(data.getIcon())
                        .imageView(imageView)
                        .build());
    }

    @Override
    protected void onRelease() {
        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
                .imageViews(imageView)
                .build());
    }
}