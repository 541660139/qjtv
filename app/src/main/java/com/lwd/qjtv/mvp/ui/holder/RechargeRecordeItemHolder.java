package com.lwd.qjtv.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.DetailsMoneyBean;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/24.
 */

public class RechargeRecordeItemHolder extends BaseHolder<DetailsMoneyBean.DataBean> {
    @BindView(R.id.item_recharge_recorde_type_tv)
    TextView typeTv;
    @BindView(R.id.item_recharge_recorde_time_tv)
    TextView timeTv;
    @BindView(R.id.item_recharge_recorde_cost_tv)
    TextView costTv;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public RechargeRecordeItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(DetailsMoneyBean.DataBean data, int position) {
        typeTv.setText(data.getR_name());
        costTv.setText(data.getAmount());
        if (data.getR_type().equals("1")) {
            costTv.setTextColor(UiUtils.getColor(itemView.getContext(), R.color.color666666));
        }
        timeTv.setText(data.getCtime());
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