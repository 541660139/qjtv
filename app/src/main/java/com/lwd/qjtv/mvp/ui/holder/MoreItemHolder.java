package com.lwd.qjtv.mvp.ui.holder;

import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.mvp.model.entity.MoreTeachListBean;
import com.lwd.qjtv.view.RoundImageViewTo;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class MoreItemHolder extends BaseHolder<MoreTeachListBean.DataBean> {

    @BindView(R.id.item_fragment_watchball_big_riv)
    RoundImageViewTo itemBigIV;
    @BindView(R.id.item_fragment_watchball_score_tv)
    TextView scoreTv;
    @BindView(R.id.item_fragment_watchball_title_tv)
    TextView titleTv;
    @BindView(R.id.item_fragment_watchball_matchpeople_tv)
    TextView matchPeopleTv;
    @BindView(R.id.item_fragment_watchball_title_layout_rl)
    RelativeLayout rl;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public MoreItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
        WindowManager systemService = (WindowManager) WEApplication.getContext().getSystemService(WEApplication.getContext().WINDOW_SERVICE);// 获取屏幕宽度
        int width = systemService.getDefaultDisplay().getWidth();
        itemBigIV.setType(1);
        itemBigIV.setBorderRadius(5);
        int height = width / 2 * 21 / 34;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rl.getLayoutParams();
        layoutParams.height = height;
        rl.setLayoutParams(layoutParams);
    }

    @Override
    public void setData(MoreTeachListBean.DataBean data, int position) {
        scoreTv.setText(data.getScore());
        matchPeopleTv.setText(data.getMatchPeople());
        titleTv.setText(data.getName());
        if (data.getPic_h() != null && !"".equals(data.getPic_h()))
            Glide.with(itemView.getContext()).load(data.getPic_h()).into(itemBigIV);
//            mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
//                            ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
//                    GlideImageConfig
//                            .builder()
//                            .url(data.getPic_h())
//                            .imageView(itemBigIV)
//                            .build());
    }


    @Override
    protected void onRelease() {
        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
                .imageViews(itemBigIV)
                .build());
    }
}