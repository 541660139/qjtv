package com.lwd.qjtv.mvp.ui.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.WatchHistoryBean;
import com.lwd.qjtv.view.RoundImageViewTo;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class WatchRecordeItemHolder extends BaseHolder<WatchHistoryBean> {

    @BindView(R.id.imageView)
    RoundImageViewTo bigRiv;
    @BindView(R.id.home_fragment_grid_item_big_iv_box)
    RelativeLayout relativeLayout;
    @BindView(R.id.item_fragment_watchball_score_tv)
    TextView scoreTv;
    @BindView(R.id.follower_list_item_intro_tv)
    TextView introTv;
    @BindView(R.id.item_video_collection_match_people_tv)
    TextView matchPeopleTv;
    @BindView(R.id.item_video_collection_time_tv)
    TextView timeTv;
    @BindView(R.id.follower_manager_checkbox)
    public CheckBox checkBox;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public WatchRecordeItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
        int width = UiUtils.getScreenWidth(itemView.getContext());
        ViewGroup.LayoutParams lp = bigRiv.getLayoutParams();
        lp.width = width * 2 / 5;
        lp.height = width * 21 / 34 * 2 / 5;
        checkBox.setTag(-2);
        bigRiv.setType(1);
        bigRiv.setLayoutParams(lp);
        bigRiv.setMaxWidth(width);
        bigRiv.setMaxHeight(width * 5);
    }


    @Override
    public void setData(WatchHistoryBean data, int position) {
        scoreTv.setText(data.getScore());
        introTv.setText(data.getTitle());
        matchPeopleTv.setText(data.getMatchPeople());
        timeTv.setText(data.getTime());
        mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                        ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
                GlideImageConfig
                        .builder()
                        .url(data.getPic())
                        .imageView(bigRiv)
                        .build());
    }

    public void setVisible(boolean isVisible) {
        checkBox.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }


    @Override
    protected void onRelease() {
        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
                .imageViews(bigRiv)
                .build());
    }
}