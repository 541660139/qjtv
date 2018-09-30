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
import com.lwd.qjtv.app.utils.GlideConfigGlobal;
import com.lwd.qjtv.mvp.model.entity.WatchBallBean;
import com.lwd.qjtv.mvp.ui.activity.MatchCollectionListActivity;
import com.lwd.qjtv.mvp.ui.activity.VideoDetailsActivity;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/13.
 */

public class WatchBallItemViewTwo extends LinearLayout {
    private Context context;
    private TextView scoreTv;
    private TextView tittleTv;
    private RoundImageViewTo watchballBigRiv;
    private RelativeLayout rl;
    private AppComponent appComponent;
    private ImageLoader imageLoader;

    public WatchBallItemViewTwo(Context context) {
        this(context, null);
    }

    public WatchBallItemViewTwo(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WatchBallItemViewTwo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.item_fragment_watchball_layout, this);
        this.context = context;
        scoreTv = (TextView) findViewById(R.id.item_fragment_watchball_score_tv);
        watchballBigRiv = (RoundImageViewTo) findViewById(R.id.item_fragment_watchball_big_riv);
        rl = (RelativeLayout) findViewById(R.id.item_fragment_watchball_layout_rl);
        tittleTv = (TextView) findViewById(R.id.item_fragment_watchball_title_tv);
        //设置圆角图片
        watchballBigRiv.setType(1);
        //设置圆角弧度5
        watchballBigRiv.setBorderRadius(5);
        //获取屏幕宽度
        int screenWidth = UiUtils.getScreenWidth(context);
        //宽高比 21 : 34
        int height = screenWidth / 2 * 21 / 34;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rl.getLayoutParams();
        layoutParams.height = height;
        rl.setLayoutParams(layoutParams);
        appComponent = ((App) context.getApplicationContext()).getAppComponent();
        imageLoader = appComponent.imageLoader();
    }

    public void setData(WatchBallBean.DataBean.WonderfulSetBean.DataListBeanXXXX dataListBeanX) {
        //分数
        scoreTv.setText(dataListBeanX.getScore());
        //标题
        tittleTv.setText(dataListBeanX.getName());
        //图片
//        GlideConfigGlobal.loadImageView(dataListBeanX.getPic_h(),watchballBigRiv);
        imageLoader.loadImage(appComponent.appManager().getCurrentActivity() == null
                ? appComponent.Application() : appComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(dataListBeanX.getPic_h(), watchballBigRiv));
//        Glide.with(context).load(dataListBeanX.getPic_h()).into(watchballBigRiv);
        //点击事件
        this.setOnClickListener(view -> {
            //跳转视频详情页
            Intent intent = new Intent(context, VideoDetailsActivity.class);
            intent.putExtra("pic", dataListBeanX.getPic_h());
            intent.putExtra("id", dataListBeanX.getId());
            intent.putExtra("type", dataListBeanX.getV_type());
            context.startActivity(intent);
        });
    }

    public void setData(WatchBallBean.DataBean.SinglePoleBean.DataListBeanXXXXXX dataListBeanX) {
        //分数
        scoreTv.setText(dataListBeanX.getScore());
        //标题
        tittleTv.setText(dataListBeanX.getName());
        //图片
//        GlideConfigGlobal.loadImageView(dataListBeanX.getPic_h(),watchballBigRiv);
        imageLoader.loadImage(appComponent.appManager().getCurrentActivity() == null
                ? appComponent.Application() : appComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(dataListBeanX.getPic_h(), watchballBigRiv));
//        Glide.with(context).load(dataListBeanX.getPic_h()).into(watchballBigRiv);
        //点击事件
        this.setOnClickListener(view -> {
            //跳转视频详情页
            Intent intent = new Intent(context, VideoDetailsActivity.class);
            intent.putExtra("pic", dataListBeanX.getPic_h());
            intent.putExtra("id", dataListBeanX.getId());
            intent.putExtra("type", dataListBeanX.getV_type());
            context.startActivity(intent);
        });
    }

    public void setData(WatchBallBean.DataBean.GameCollectionBean.DataListBeanXX dataListBeanX) {
        scoreTv.setVisibility(GONE);
        tittleTv.setText(dataListBeanX.getName());
//        matchpeople.setText(dataListBeanX.getName());
//        Glide.with(context).load(dataListBeanX.getPic_h()).into(watchballBigRiv);
        GlideConfigGlobal.loadImageView(dataListBeanX.getPic_h(), watchballBigRiv);
        appComponent = ((App) getContext().getApplicationContext()).getAppComponent();
        imageLoader = appComponent.imageLoader();
        imageLoader.loadImage(appComponent.appManager().getCurrentActivity() == null
                ? appComponent.Application() : appComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(dataListBeanX.getPic_h(), watchballBigRiv));
        this.setOnClickListener(view -> {
            //跳转视频详情页
            Intent intent = new Intent(context, MatchCollectionListActivity.class);
            intent.putExtra("id", dataListBeanX.getBigMatch_id());
            intent.putExtra("name", dataListBeanX.getName());
            intent.putExtra("pic", dataListBeanX.getPic_h());
            context.startActivity(intent);
        });
    }
}
