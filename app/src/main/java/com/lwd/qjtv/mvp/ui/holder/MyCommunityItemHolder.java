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
import com.lwd.qjtv.app.utils.DateTools;
import com.lwd.qjtv.mvp.model.entity.MyCommunityListDataBean;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class MyCommunityItemHolder extends BaseHolder<MyCommunityListDataBean.DataBean> {

    @BindView(R.id.item_community_my_bbs_iv)
    ImageView imageView;
    @BindView(R.id.item_community_my_bbs_title_tv)
    TextView titleTv;
    @BindView(R.id.item_community_content_tv)
    TextView contentTv;
    @BindView(R.id.item_community_my_bbs_time_tv)
    TextView timeTv;

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public MyCommunityItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(MyCommunityListDataBean.DataBean data, int position) {
        String strTime_ymd_hms = DateTools.getStrTime_ymd_hms(data.getCtime());
        titleTv.setText(data.getTitle() == null ? "" : data.getTitle());
        contentTv.setText(data.getMain_txt() == null ? "" : data.getMain_txt());

        timeTv.setText(strTime_ymd_hms == null ? "" : strTime_ymd_hms);

        if (data.getPic_h() != null && data.getPic_h().size() != 0 && data.getPic_h().get(0) != null && !"".equals(data.getPic_h().get(0)))
            mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                            ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
                    GlideImageConfig
                            .builder()
                            .url(data.getPic_h().get(0))
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