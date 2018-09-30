package com.lwd.qjtv.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.GuessMyBean;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/8/1.
 */

public class GuessMyItemHolder extends BaseHolder<GuessMyBean.DataBean.TzDetailBean> {

    @BindView(R.id.item_myguess_info_tv)
    TextView infoTv;
    @BindView(R.id.item_myguess_top_starinfo_name_tv)
    TextView topNameTv;
    @BindView(R.id.item_myguess_bottom_starinfo_name_tv)
    TextView bottomNameTv;
    @BindView(R.id.item_myguess_top_starinfo_score_tv)
    TextView topScoreTv;
    @BindView(R.id.item_myguess_bottom_starinfo_score_tv)
    TextView bottomScoreTv;
    @BindView(R.id.item_myguess_status_tv)
    TextView statusTv;
    @BindView(R.id.item_myguess_money_tv)
    TextView moneyTv;
    @BindView(R.id.item_myguess_top_starinfo_civ)
    CircleImageView topCiv;
    @BindView(R.id.item_myguess_bottom_starinfo_civ)
    CircleImageView bottomCiv;
    @BindView(R.id.item_guess_wagger_tv)
    TextView waggerTv;
    @BindView(R.id.item_guess_peilv_tv)
    TextView peilvTv;

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public GuessMyItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(GuessMyBean.DataBean.TzDetailBean data, int position) {
        infoTv.setText(data.getCtime() + " " + data.getBigMatch_name());
        List<GuessMyBean.DataBean.TzDetailBean.StarInfoBean> star_info = data.getStarInfo();
        GuessMyBean.DataBean.TzDetailBean.StarInfoBean starTopBean = star_info.get(0);
        GuessMyBean.DataBean.TzDetailBean.StarInfoBean starBottomBean = star_info.get(1);
        topNameTv.setText(starTopBean.getName() + "  " + data.getJc_name());
        bottomNameTv.setText(starBottomBean.getName());
        topScoreTv.setText(data.getA_win_num());
        bottomScoreTv.setText(data.getB_win_num());
        waggerTv.setText("投注金额：" + data.getWager());
        peilvTv.setText("赔率：" + data.getPeilv());
        Glide.with(itemView.getContext()).load(starTopBean.getThumb_img()).into(topCiv);
        Glide.with(itemView.getContext()).load(starBottomBean.getThumb_img()).into(bottomCiv);
        if ("2".equals(data.getIsWin())) {
            statusTv.setText("输");
            statusTv.setTextColor(itemView.getContext().getResources().getColor(R.color.color666666));
            moneyTv.setText("-" + data.getProfit_loss());
            moneyTv.setVisibility(View.VISIBLE);
            moneyTv.setTextColor(itemView.getContext().getResources().getColor(R.color.color666666));
        } else if ("1".equals(data.getIsWin())) {
            statusTv.setText("赢");
            statusTv.setTextColor(itemView.getContext().getResources().getColor(R.color.colorOrigin));
            moneyTv.setText("+" + data.getProfit_loss());
            moneyTv.setVisibility(View.VISIBLE);
            moneyTv.setTextColor(itemView.getContext().getResources().getColor(R.color.colorOrigin));
        } else if ("0".equals(data.getIsWin())) {
            statusTv.setText("即将揭晓");
            statusTv.setTextColor(itemView.getContext().getResources().getColor(R.color.color0066cd));
            moneyTv.setVisibility(View.GONE);
        } else if ("3".equals(data.getIsWin())) {
            statusTv.setText("流局");
            statusTv.setTextColor(itemView.getContext().getResources().getColor(R.color.color0066cd));
            moneyTv.setVisibility(View.GONE);
        }
    }

}