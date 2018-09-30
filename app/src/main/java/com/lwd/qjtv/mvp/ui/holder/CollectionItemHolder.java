package com.lwd.qjtv.mvp.ui.holder;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.mvp.model.entity.VideoCollectionBean;
import com.lwd.qjtv.view.RoundImageViewTo;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/5.
 */

public class CollectionItemHolder extends BaseHolder<VideoCollectionBean.DataBean> {
    @BindView(R.id.imageView)
    RoundImageViewTo itemBigIV;
    @BindView(R.id.item_fragment_watchball_score_tv)
    TextView scoreTv;
    @BindView(R.id.follower_list_item_intro_tv)
    TextView introTv;
    @BindView(R.id.item_video_collection_match_people_tv)
    TextView matchPeopleTv;
    @BindView(R.id.follower_manager_checkbox)
    public CheckBox checkBox;
    @BindView(R.id.item_video_collection_time_tv)
    TextView timeTv;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public CollectionItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
        WindowManager systemService = (WindowManager) WEApplication.getContext().getSystemService(WEApplication.getContext().WINDOW_SERVICE);// 获取屏幕宽度
        int width = systemService.getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams lp = itemBigIV.getLayoutParams();
        lp.width = width * 2 / 5;
        lp.height = width * 21 / 34 * 2 / 5;
        itemBigIV.setType(1);
        itemBigIV.setBorderRadius(5);
        itemBigIV.setLayoutParams(lp);
        itemBigIV.setMaxWidth(width);
        itemBigIV.setMaxHeight(width * 5);
    }

    @Override
    public void setData(VideoCollectionBean.DataBean data, int position) {
//        matchPeopleTv.setText(data.getMatchPeople());
        introTv.setText(data.getVideo_title());
//        timeTv.setText(data.getVideo_length());
//        scoreTv.setText(data.getScore());
        if (data.getBitmap() != null && !"".equals(data.getBitmap()))
            mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                            ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
                    GlideImageConfig
                            .builder()
                            .url(data.getBitmap())
                            .imageView(itemBigIV)
                            .build());
    }

    public void setVisible(boolean isVisible) {
        checkBox.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }


    @Override
    protected void onRelease() {
        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
                .imageViews(itemBigIV)
                .build());
    }
}