package com.lwd.qjtv.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.GuessChampionBean;
import com.lwd.qjtv.view.GuessChampionRaceView;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/12.
 */

public class GuessChampionItemHolder extends BaseHolder<GuessChampionBean.DataBean.MatchPlayerListBean> {

    @BindView(R.id.item_guess_champion_civ)
    CircleImageView circleImageView;
    @BindView(R.id.item_guess_champion_name_tv)
    TextView nameTv;
    @BindView(R.id.item_guess_champion_rank_tv)
    TextView rankTv;
    @BindView(R.id.item_guess_champion_raceview)
    GuessChampionRaceView raceView;

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private String title;

    public GuessChampionItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(GuessChampionBean.DataBean.MatchPlayerListBean data, int position) {
        nameTv.setText(data.getName());
        rankTv.setText("世界排名：" + data.getWorld_rank());
//        if (data.getIs_play() == 1) {
//            raceView.setClickable(false);
//            raceView.setData(data.getPlayer_peilv(), "比赛中", data.getId());
//            raceView.setEnable();
//        } else {
//            raceView.setData(data.getPlayer_peilv(), "冠军", data.getId());
//            raceView.setApiParam(title, data.getName() + " 冠军", data.getPlayer_peilv(), data.getMatch_id(), data.getId());
//        }
        long l = System.currentTimeMillis();
        if (Long.parseLong(data.getStartTime()) * 1000 >= l) {
            raceView.setData(data.getPlayer_peilv(), "冠军", data.getId());
            raceView.setApiParam(title, data.getName() + " 冠军", data.getPlayer_peilv(), data.getMatch_id(), data.getId());
            raceView.setClick();
        } else {
            raceView.setClickable(false);
            raceView.setData(data.getPlayer_peilv(), "比赛中", data.getId());
            raceView.setEnable();
        }
        if (data.getAvater() != null && !"".equals(data.getAvater()))
            mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                            ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
                    GlideImageConfig
                            .builder()
                            .url(data.getAvater())
                            .imageView(circleImageView)
                            .build());
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    protected void onRelease() {
        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
                .imageViews(circleImageView)
                .build());
    }
}