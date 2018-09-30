package com.lwd.qjtv.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.GuessCenterBean;
import com.lwd.qjtv.view.RoundImageView;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/17.
 */

public class GuessCenterItemHolder extends BaseHolder<GuessCenterBean.DataBean> {

    @BindView(R.id.item_guess_center_left_head_riv)
    RoundImageView leftHeadRiv;
    @BindView(R.id.item_guess_center_right_head_riv)
    RoundImageView rightHeadRiv;
    @BindView(R.id.item_guess_center_left_name_tv)
    TextView leftNameTv;
    @BindView(R.id.item_guess_center_right_name_tv)
    TextView rightNameTv;
    @BindView(R.id.item_guess_center_time_tv)
    TextView timeTv;
    @BindView(R.id.item_guess_center_guess_tv)
    TextView guessTv;
    @BindView(R.id.item_guess_center_bifen_tv)
    TextView bifenTv;

    private GuessClickCallBack guessClickCallBack;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public GuessCenterItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(GuessCenterBean.DataBean data, int position) {
        GuessCenterBean.DataBean.StarInfoBean leftBean = data.getStarInfo().get(0);
        GuessCenterBean.DataBean.StarInfoBean rightBean = data.getStarInfo().get(1);
        leftNameTv.setText(leftBean.getName());
        rightNameTv.setText(rightBean.getName());
        timeTv.setText(data.getStart_time());
        bifenTv.setText(data.getMatch_result() == null ? "" : data.getMatch_result());
        guessTv.setOnClickListener(view -> {
            if (guessClickCallBack != null) guessClickCallBack.clickGuess(data, position);
        });
        if (leftBean.getThumb_img() != null)
            mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                            ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
                    GlideImageConfig
                            .builder()
                            .url(leftBean.getThumb_img())
                            .imageView(leftHeadRiv)
                            .build());
        if (rightBean.getThumb_img() != null)
            mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                            ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
                    GlideImageConfig
                            .builder()
                            .url(rightBean.getThumb_img())
                            .imageView(rightHeadRiv)
                            .build());
    }


    @Override
    protected void onRelease() {
        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
                .imageViews(rightHeadRiv, leftHeadRiv)
                .build());
    }

    public void setGuessClickCallback(GuessClickCallBack guessClickCallBack) {
        this.guessClickCallBack = guessClickCallBack;
    }

    public interface GuessClickCallBack {
        void clickGuess(GuessCenterBean.DataBean data, int position);
    }
}