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
import com.lwd.qjtv.mvp.model.entity.MyOrderBean;
import com.lwd.qjtv.view.ExpressDialog;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/27.
 */

public class MyOrderItemHolder extends BaseHolder<MyOrderBean.DataBean> {

    @BindView(R.id.item_my_order_logistics_number_tv)
    TextView logisticsNumTv;
    @BindView(R.id.item_my_order_logistics_status_tv)
    TextView statusTv;
    @BindView(R.id.item_my_order_shop_iv)
    ImageView shopIv;
    @BindView(R.id.item_my_order_name_tv)
    TextView nameTv;
    @BindView(R.id.item_my_order_price_tv)
    TextView priceTv;
    @BindView(R.id.item_my_order_color_tv)
    TextView colorTv;
    @BindView(R.id.item_my_order_watch_logistics_tv)
    TextView watchLogisticsTv;

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public MyOrderItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(MyOrderBean.DataBean data, int position) {
        priceTv.setText("￥" + data.getSubtotal());
        switch (data.getPay_status()) {
            case "0":
                statusTv.setText("未支付");
                break;
            case "1":
                statusTv.setText("待发货");
                break;
            case "2":
                statusTv.setText("待收货");
                break;
            case "3":
                statusTv.setText("已收货");
                break;
        }
        logisticsNumTv.setText("物流编号：" + data.getOrder_sn());
        nameTv.setText(data.getGoods_name());
        colorTv.setText(data.getGoods_brief());
        /**
         *  我的订单按钮隐藏，点击无效
         * */
        watchLogisticsTv.setOnClickListener(view -> {
            ExpressDialog expressDialog = new ExpressDialog(itemView.getContext());
            expressDialog.setExpressNum(data.getOrder_sn());
            expressDialog.show();
        });
        mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                        ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
                GlideImageConfig
                        .builder()
                        .url(data.getGoods_img())
                        .placeholder(R.mipmap.video_place_holder)
                        .imageView(shopIv)
                        .build());
    }


    @Override
    protected void onRelease() {
        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
                .imageViews(shopIv)
                .build());
    }
}