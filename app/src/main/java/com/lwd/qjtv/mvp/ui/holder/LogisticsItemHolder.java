package com.lwd.qjtv.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.OrderExpressBean;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/27.
 */

public class LogisticsItemHolder extends BaseHolder<OrderExpressBean.DataBeanX.DataBean> {

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    @BindView(R.id.item_logistics_content_tv)
    TextView contentTv;
    @BindView(R.id.item_logistics_time_tv)
    TextView timeTv;

    public LogisticsItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(OrderExpressBean.DataBeanX.DataBean data, int position) {
        contentTv.setText(data.getContext());
        timeTv.setText(data.getTime());
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