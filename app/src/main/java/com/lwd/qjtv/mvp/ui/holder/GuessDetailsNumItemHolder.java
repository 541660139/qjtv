package com.lwd.qjtv.mvp.ui.holder;

import android.view.View;
import android.widget.CheckBox;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.GuessDetailsBean;
import com.lwd.qjtv.view.RaceView;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/26.
 */

public class GuessDetailsNumItemHolder extends BaseHolder<GuessDetailsBean.DataBean.GuessGameNumberBean.PlInfoBeanX> {

    @BindView(R.id.item_guess_details_rv)
    public RaceView raceView;
    @BindView(R.id.item_guess_details_checkbox)
    public CheckBox checkBox;
    private GuessDetailsBean.DataBean.GuessGameNumberBean.PlInfoBeanX data;

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public GuessDetailsNumItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComp
    }

    @Override
    public void setData(GuessDetailsBean.DataBean.GuessGameNumberBean.PlInfoBeanX data, int position) {
        raceView.setData(data.getJc_peilv(), data.getJc_name(), data.getId() + "");
        this.data = data;
    }

    public void setApiParams(GuessDetailsBean.DataBean dataBean) {
        String title = dataBean.getGuess_detail().getStarInfo().get(0).getName() + " VS " + dataBean.getGuess_detail().getStarInfo().get(1).getName();
        raceView.setApiParam(title, data.getJc_name(), data.getJc_peilv(), dataBean.getGuess_detail().getMatch_id() + "", dataBean.getGuess_gameNumber().getId(), data.getId() + "", dataBean.getGuess_gameNumber().getJc_type());
    }
}