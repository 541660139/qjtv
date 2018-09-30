package com.lwd.qjtv.mvp.ui.holder;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.mvp.model.entity.RelatedVideoBean;
import com.lwd.qjtv.view.RoundImageViewTo;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/24.
 */

public class WonderfulVideoItemHolder extends BaseHolder<RelatedVideoBean.DataBean> {
    @BindView(R.id.item_fragment_watchball_big_riv)
    RoundImageViewTo bigRiv;
    @BindView(R.id.item_fragment_watchball_score_tv)
    TextView scoreTv;
    @BindView(R.id.item_wonderful_video_title_tv)
    TextView titelTv;
    @BindView(R.id.item_wonderful_video_match_tv)
    TextView matchTv;
    @BindView(R.id.item_wonderful_video_time_tv)
    TextView timeTv;
    @BindView(R.id.item_fragment_watchball_layout_rl)
    RelativeLayout rl;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public WonderfulVideoItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
        bigRiv.setType(1);
        bigRiv.setBorderRadius(5);
        int screenWidth = UiUtils.getScreenWidth(WEApplication.getContext());
        int height = screenWidth / 2 * 9 / 16;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rl.getLayoutParams();
        layoutParams.height = height;
        rl.setLayoutParams(layoutParams);
    }

    @Override
    public void setData(RelatedVideoBean.DataBean data, int position) {
        scoreTv.setText(data.getScore());
        titelTv.setText(data.getName());
        matchTv.setText(data.getMatchPeople());
        timeTv.setText(data.getVideo_length());
        if (data.getPic_h() != null && !"".equals(data.getPic_h()))
            mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                            ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
                    GlideImageConfig
                            .builder()
                            .url(data.getPic_h())
                            .imageView(bigRiv)
                            .build());
    }


    @Override
    protected void onRelease() {
        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
                .imageViews(bigRiv)
                .build());
    }
}