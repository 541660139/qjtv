package com.lwd.qjtv.mvp.ui.holder;

import android.view.View;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.GuessDetailsBean;
import com.lwd.qjtv.view.RaceView;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/23.
 */

public class GuessRangfenItemHolder extends BaseHolder<GuessDetailsBean.DataBean.GuessRangfenBean.PlInfoBeanXXX> {

    @BindView(R.id.activity_guess_details_right_win_rv)
    RaceView raceView;
    private String title, match_id, jc_type, jc_id;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public GuessRangfenItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(GuessDetailsBean.DataBean.GuessRangfenBean.PlInfoBeanXXX leftWinBean, int position) {
        raceView.setData(leftWinBean.getJc_peilv(), leftWinBean.getJc_name(), leftWinBean.getId() + "");
        raceView.setApiParam(title, leftWinBean.getJc_name(), leftWinBean.getJc_peilv(), match_id, jc_id, leftWinBean.getId() + "", jc_type);
    }

    public void setApiparm(String title, String match_id, String jc_type, String jc_id) {
        this.title = title;
        this.match_id = match_id;
        this.jc_type = jc_type;
        this.jc_id = jc_id;
    }


    @Override
    protected void onRelease() {

    }
}