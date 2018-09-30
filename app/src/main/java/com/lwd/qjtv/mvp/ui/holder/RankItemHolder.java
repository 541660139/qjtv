package com.lwd.qjtv.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.GuessRankBean;
import com.lwd.qjtv.view.RoundImageView;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class RankItemHolder extends BaseHolder<GuessRankBean.DataBean> {

    @BindView(R.id.item_rank_name_tv)
    TextView nameTv;
    @BindView(R.id.item_rank_avater_riv)
    RoundImageView avaterRiv;
    @BindView(R.id.item_rank_num_tv)
    TextView moneyNumTv;
    @BindView(R.id.item_rank_n_tv)
    TextView nTv;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public RankItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(GuessRankBean.DataBean data, int position) {
//        numIv.setBackground(itemView.getResources().getDrawable(R.mipmap.ic_launcher));
        nTv.setText(position + 4 + "");
        moneyNumTv.setText(data.getAll_wager());
        nameTv.setText(data.getUsername());
        Glide.with(itemView.getContext()).load(data.getAvater()).placeholder(R.mipmap.video_place_holder).error(R.mipmap.video_place_holder).into(avaterRiv);

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