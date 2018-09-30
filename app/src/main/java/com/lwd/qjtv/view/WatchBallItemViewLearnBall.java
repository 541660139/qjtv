package com.lwd.qjtv.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.GlideConfigGlobal;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.mvp.model.entity.LearnBallBean;
import com.lwd.qjtv.mvp.ui.activity.LearnBallDetailsActivity;
import com.lwd.qjtv.mvp.ui.activity.LoginActivity;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/13.
 */

public class WatchBallItemViewLearnBall extends LinearLayout {
    private Context context;
    private TextView scoreTv;
    private TextView tittleTv;
    private RoundImageViewTo watchballBigRiv;
    private RelativeLayout rl;
    private AppComponent appComponent;
    private ImageLoader imageLoader;

    public WatchBallItemViewLearnBall(Context context) {
        this(context, null);
    }

    public WatchBallItemViewLearnBall(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WatchBallItemViewLearnBall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.item_fragment_watchball_layout, this);
        this.context = context;
        scoreTv = (TextView) findViewById(R.id.item_fragment_watchball_score_tv);
        watchballBigRiv = (RoundImageViewTo) findViewById(R.id.item_fragment_watchball_big_riv);
        rl = (RelativeLayout) findViewById(R.id.item_fragment_watchball_layout_rl);
        tittleTv = (TextView) findViewById(R.id.item_fragment_watchball_title_tv);
        watchballBigRiv.setType(1);
        watchballBigRiv.setBorderRadius(5);
        int screenWidth = UiUtils.getScreenWidth(context);
        int height = screenWidth / 2 * 21 / 34;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rl.getLayoutParams();
        layoutParams.height = height;
        rl.setLayoutParams(layoutParams);
        appComponent = ((App) context.getApplicationContext()).getAppComponent();
        imageLoader = appComponent.imageLoader();
    }

    public void setData(LearnBallBean.DataBean.XilieBean.DataListBeanXX dataListBeanX) {
        scoreTv.setText(dataListBeanX.getScore());
        tittleTv.setText(dataListBeanX.getName());
//        Glide.with(context).load(dataListBeanX.getPic_h()).into(watchballBigRiv);
        imageLoader.loadImage(appComponent.appManager().getCurrentActivity() == null
                ? appComponent.Application() : appComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(dataListBeanX.getPic_h(), watchballBigRiv));
//        GlideConfigGlobal.loadImageView(dataListBeanX.getPic_h(),watchballBigRiv);
        this.setOnClickListener(view -> {
            if (!SaveUserInfo.getLogin()) {
                context.startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                return;
            }
            Intent intent = new Intent(context, LearnBallDetailsActivity.class);
            intent.putExtra("starId", dataListBeanX.getId());
            intent.putExtra("videoId", dataListBeanX.getId());
            intent.putExtra("v_type", dataListBeanX.getV_type());
            intent.putExtra("pic", dataListBeanX.getPic_h());
            context.startActivity(intent);
        });
    }

    public void setData(LearnBallBean.DataBean.DashiBean.DataListBeanX dataListBeanX) {
        scoreTv.setText(dataListBeanX.getScore());
        tittleTv.setText(dataListBeanX.getName());
//        GlideConfigGlobal.loadImageView(dataListBeanX.getPic_h(),watchballBigRiv);
        imageLoader.loadImage(appComponent.appManager().getCurrentActivity() == null
                ? appComponent.Application() : appComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(dataListBeanX.getPic_h(), watchballBigRiv));
//        Glide.with(context).load(dataListBeanX.getPic_h()).into(watchballBigRiv);
        this.setOnClickListener(view -> {
            if (!SaveUserInfo.getLogin()) {
                context.startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                return;
            }
            Intent intent = new Intent(context, LearnBallDetailsActivity.class);
            intent.putExtra("starId", dataListBeanX.getId());
            intent.putExtra("videoId", dataListBeanX.getId());
            intent.putExtra("v_type", dataListBeanX.getV_type());
            intent.putExtra("pic", dataListBeanX.getPic_h());
            context.startActivity(intent);
        });
    }

    public void setData(LearnBallBean.DataBean.XiaobianBean.DataListBean dataListBeanX) {
        scoreTv.setText(dataListBeanX.getScore());
        tittleTv.setText(dataListBeanX.getName());
//        GlideConfigGlobal.loadImageView(dataListBeanX.getPic_h(),watchballBigRiv);
        imageLoader.loadImage(appComponent.appManager().getCurrentActivity() == null
                ? appComponent.Application() : appComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(dataListBeanX.getPic_h(), watchballBigRiv));
//        Glide.with(context).load(dataListBeanX.getPic_h()).into(watchballBigRiv);
        this.setOnClickListener(view -> {
            if (!SaveUserInfo.getLogin()) {
                context.startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                return;
            }
            Intent intent = new Intent(context, LearnBallDetailsActivity.class);
            intent.putExtra("starId", dataListBeanX.getId());
            intent.putExtra("videoId", dataListBeanX.getId());
            intent.putExtra("v_type", dataListBeanX.getV_type());
            intent.putExtra("pic", dataListBeanX.getPic_h());
            context.startActivity(intent);
        });
    }

    public void setData(LearnBallBean.DataBean.SinglePoleBean.DataListBeanXXX dataListBeanX) {
        scoreTv.setText(dataListBeanX.getScore());
        tittleTv.setText(dataListBeanX.getName());
//        GlideConfigGlobal.loadImageView(dataListBeanX.getPic_h(),watchballBigRiv);
        imageLoader.loadImage(appComponent.appManager().getCurrentActivity() == null
                ? appComponent.Application() : appComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(dataListBeanX.getPic_h(), watchballBigRiv));
//        Glide.with(context).load(dataListBeanX.getPic_h()).into(watchballBigRiv);
        this.setOnClickListener(view -> {
            if (!SaveUserInfo.getLogin()) {
                context.startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                return;
            }
            Intent intent = new Intent(context, LearnBallDetailsActivity.class);
            intent.putExtra("starId", dataListBeanX.getId());
            intent.putExtra("videoId", dataListBeanX.getId());
            intent.putExtra("v_type", dataListBeanX.getV_type());
            intent.putExtra("pic", dataListBeanX.getPic_h());
            context.startActivity(intent);
        });
    }
}
