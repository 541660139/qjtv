package com.lwd.qjtv.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.ChampionGuess;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/8/1.
 */

public class ChampionGuessItemHolder extends BaseHolder<ChampionGuess.DataBean> {
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
    @BindView(R.id.item_guess_wagger_tv)
    TextView waggerTv;
    @BindView(R.id.item_myguess_top_starinfo_civ)
    CircleImageView topCiv;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public ChampionGuessItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(ChampionGuess.DataBean data, int position) {
        infoTv.setText(data.getCtime() + " " + data.getBigMatch_name());
        topNameTv.setText(data.getPlayer());
        bottomNameTv.setText(data.getPhaseMatch_id());
        topScoreTv.setText("夺冠");
        bottomScoreTv.setText("赔率："+data.getPeilv());
        waggerTv.setText("投注金额："+data.getWager());
        Glide.with(itemView.getContext()).load(data.getThumb_img()).into(topCiv);
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
        }else if ("3".equals(data.getIsWin())) {
            statusTv.setText("流局");
            statusTv.setTextColor(itemView.getContext().getResources().getColor(R.color.color0066cd));
            moneyTv.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onRelease() {
    }
}