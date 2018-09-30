package com.lwd.qjtv.mvp.ui.holder;

import android.view.View;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class LearnBallItemHolder extends BaseHolder<String> {

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public LearnBallItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(String data, int position) {

//        mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
//                        ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
//                GlideImageConfig
//                        .builder()
//                        .url(null)
//                        .imageView(null)
//                        .build());
    }


    @Override
    protected void onRelease() {
//        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
//                .imageViews(null)
//                .build());
    }
}