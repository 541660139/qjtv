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
import com.lwd.qjtv.mvp.model.entity.MallListBean;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class MallItemHolder extends BaseHolder<MallListBean.DataBean> {

    @BindView(R.id.item_fragment_mall_big_iv)
    ImageView bigIv;
    @BindView(R.id.item_fragment_mall_price_tv)
    TextView priceTv;
    @BindView(R.id.item_fragment_mall_desc_tv)
    TextView descTv;

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public MallItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(MallListBean.DataBean data, int position) {
        priceTv.setText(data.getShop_price() + " 积分");
        descTv.setText(data.getGoods_name());
        if (data.getThumb_img() != null && !"".equals(data.getThumb_img()))
//            GlideConfigGlobal.loadImageView(data.getThumb_img(),bigIv);
            mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                            ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
                    GlideImageConfig
                            .builder()
                            .url(data.getThumb_img())
                            .placeholder(R.mipmap.video_place_holder)
                            .imageView(bigIv)
                            .build());
    }


    @Override
    protected void onRelease() {
//        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
//                .imageViews(null)
//                .build());
    }
}