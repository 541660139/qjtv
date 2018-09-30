package com.lwd.qjtv.mvp.ui.holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.mvp.model.entity.BetModelBean;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/13.
 */

public class MoreBetItemHolder extends BaseHolder<BetModelBean> {
    @BindView(R.id.item_more_bet_left_civ)
    CircleImageView leftCiv;
    @BindView(R.id.item_more_bet_right_civ)
    CircleImageView rightCiv;
    @BindView(R.id.item_more_bet_left_name_tv)
    TextView leftNameTv;
    @BindView(R.id.item_more_bet_right_name_tv)
    TextView rightNameTv;
    @BindView(R.id.item_more_bet_match_name_tv)
    TextView matchNameTv;
    @BindView(R.id.item_more_bet_jc_name_tv)
    TextView jcNameTv;
    @BindView(R.id.item_more_bet_jc_peilv_tv)
    TextView peilvTv;
    @BindView(R.id.item_more_bet_tip_tv)
    TextView tipTv;
    @BindView(R.id.ll)
    LinearLayout linearLayout;
    @BindView(R.id.item_more_bet_check_box)
    public CheckBox checkBox;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public MoreBetItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(BetModelBean data, int position) {
        leftNameTv.setText(data.getLeft_name());
        rightNameTv.setText(data.getRight_name());
        peilvTv.setText(data.getJc_peilv());
        jcNameTv.setText(data.getJc_name());
        matchNameTv.setText(data.getMatch_name());
        mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                        ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
                GlideImageConfig
                        .builder()
                        .url(data.getLeft_pic())
                        .imageView(leftCiv)
                        .build());
        mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                        ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
                GlideImageConfig
                        .builder()
                        .url(data.getRight_pic())
                        .imageView(rightCiv)
                        .build());
        if (data.getOver_time() != null) {
            if (Long.parseLong(data.getOver_time()) * 1000 <= System.currentTimeMillis()) {
                //结束了，删掉
                List<BetModelBean> betModelBeen = WEApplication.getWinDao().loadAll();
                List<BetModelBean> betModelBeen1 = WEApplication.getThirdModelBeanDao().loadAll();
                if (betModelBeen.contains(data))
                    WEApplication.getWinDao().delete(data);
                else if (betModelBeen1.contains(data))
                    WEApplication.getThirdModelBeanDao().delete(data);
            }
        }
    }

    public void setTip() {
        tipTv.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onRelease() {
        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
                .imageViews(leftCiv, rightCiv)
                .build());
    }
}