package com.lwd.qjtv.mvp.ui.holder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.mvp.model.entity.MatchTimeList;
import com.lwd.qjtv.mvp.ui.activity.GuessDetailsActivity;
import com.lwd.qjtv.mvp.ui.activity.LoginActivity;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/7.
 */

public class MatchTimeChildItemHolder extends BaseHolder<MatchTimeList.DataBean> {
    @BindView(R.id.item_match_time_win_left_iv)
    ImageView leftWinIv;
    @BindView(R.id.item_match_time_left_civ)
    CircleImageView leftHeadCiv;
    @BindView(R.id.item_match_time_left_top_civ)
    CircleImageView leftTopCiv;
    @BindView(R.id.item_match_time_left_name_tv)
    TextView leftNameTv;
    @BindView(R.id.item_match_time_time_tv)
    TextView matchTimeTv;
    @BindView(R.id.item_guess_center_bifen_tv)
    TextView jifenTv;
    @BindView(R.id.item_match_time_right_civ)
    CircleImageView rightHeadCiv;
    @BindView(R.id.item_match_time_right_top_civ)
    CircleImageView rightTopCiv;
    @BindView(R.id.item_match_time_win_right_iv)
    ImageView rightWinIv;
    @BindView(R.id.item_match_time_right_name_tv)
    TextView rightNameTv;
    @BindView(R.id.item_guess_center_guess_tv)
    TextView guessTv;
    @BindView(R.id.item_match_time_num_tv)
    TextView numTv;

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public MatchTimeChildItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(MatchTimeList.DataBean data, int position) {
        numTv.setText("NO." + data.getId());
        leftWinIv.setVisibility(View.GONE);
        rightTopCiv.setVisibility(View.GONE);
        rightWinIv.setVisibility(View.GONE);
        leftTopCiv.setVisibility(View.GONE);
        List<MatchTimeList.DataBean.StarInfoBean> starInfo = data.getStarInfo();
        if (starInfo.size() != 2)
            return;
        MatchTimeList.DataBean.StarInfoBean leftInfoBean = starInfo.get(0);
        MatchTimeList.DataBean.StarInfoBean rightInfoBean = starInfo.get(1);
        leftNameTv.setText(leftInfoBean.getName());
        rightNameTv.setText(rightInfoBean.getName());
        matchTimeTv.setText(data.getStartTime());
        jifenTv.setText(data.getMatchResult() == null ? "" : data.getMatchResult());
        String winner = data.getWinner();
        if (data.getIsOver().equals("1")) {
            if (leftInfoBean.getId().equals(winner)) {
                leftWinIv.setVisibility(View.VISIBLE);
                rightTopCiv.setVisibility(View.VISIBLE);
                rightNameTv.setTextColor(UiUtils.getColor(WEApplication.getContext(), R.color.color999999));
            } else {
                rightWinIv.setVisibility(View.VISIBLE);
                leftTopCiv.setVisibility(View.VISIBLE);
                leftNameTv.setTextColor(UiUtils.getColor(WEApplication.getContext(), R.color.color999999));
            }
        }
        long l = System.currentTimeMillis();
        if (data.getJc_start_time() != null && data.getJc_over_time() != null) {
            if (Long.parseLong(data.getJc_start_time()) * 1000 <= l && l <= Long.parseLong(data.getJc_over_time()) * 1000) {
                guessTv.setVisibility(View.VISIBLE);
                jifenTv.setVisibility(View.GONE);
                guessTv.setOnClickListener(v -> {
                    if (Preference.getBoolean(Constant.IS_LOGIN)) {
                        Intent intent = new Intent(itemView.getContext(), GuessDetailsActivity.class);
                        intent.putExtra("id", data.getId());
                        itemView.getContext().startActivity(intent);
                    } else
                        itemView.getContext().startActivity(new Intent(itemView.getContext(), LoginActivity.class));
                });
                guessTv.setBackground(itemView.getContext().getResources().getDrawable(R.drawable.circle_corner_shape));
                guessTv.setTextColor(itemView.getContext().getResources().getColor(R.color.white));
            } else {
                guessTv.setVisibility(View.GONE);
                jifenTv.setVisibility(View.VISIBLE);
                jifenTv.setText(data.getMatchResult());
            }
        } else {
            guessTv.setVisibility(View.GONE);
            jifenTv.setVisibility(View.VISIBLE);
            jifenTv.setText(data.getMatchResult());
//            guessTv.setClickable(false);
//            guessTv.setBackground(itemView.getContext().getResources().getDrawable(R.drawable.circle_corner_gray));
//            guessTv.setTextColor(itemView.getContext().getResources().getColor(R.color.color666666));
        }
        if (!Preference.getBoolean(Constant.IS_GUESS)||!Preference.getBoolean(Constant.ON_OFF)) {
            guessTv.setVisibility(View.GONE);
        }
        if (leftInfoBean.getThumb_img() != null && !"".equals(leftInfoBean.getThumb_img()))
            mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                            ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
                    GlideImageConfig
                            .builder()
                            .url(leftInfoBean.getThumb_img())
                            .imageView(leftHeadCiv)
//                        .errorPic(R.mipmap.ttzb_place_holder)
//                        .placeholder(R.mipmap.ttzb_place_holder)
                            .build());
        if (rightInfoBean.getThumb_img() != null && !"".equals(rightInfoBean.getThumb_img()))
            mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                            ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
                    GlideImageConfig
                            .builder()
                            .url(rightInfoBean.getThumb_img())
                            .imageView(rightHeadCiv)
                            .build());
    }


    @Override
    protected void onRelease() {
        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
                .imageViews(leftHeadCiv)
                .build());
        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
                .imageViews(rightHeadCiv)
                .build());
    }
}