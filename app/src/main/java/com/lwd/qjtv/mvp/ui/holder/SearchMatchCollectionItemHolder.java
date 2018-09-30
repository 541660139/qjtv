package com.lwd.qjtv.mvp.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.SearchCollectionBean;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/5.
 */

public class SearchMatchCollectionItemHolder extends BaseHolder<SearchCollectionBean.DataBean> {
    @BindView(R.id.item_match_collection_no_tv)
    TextView itemMatchCollectionNoTv;
    @BindView(R.id.item_match_collection_left_head_riv)
    CircleImageView itemMatchCollectionLeftHeadRiv;
    @BindView(R.id.item_match_collection_left_top_civ)
    CircleImageView itemMatchCollectionLeftTopCiv;
    @BindView(R.id.item_match_collection_left_name_tv)
    TextView itemMatchCollectionLeftNameTv;
    @BindView(R.id.item_match_time_win_left_iv)
    ImageView itemMatchTimeWinLeftIv;
    @BindView(R.id.item_match_collection_time_tv)
    TextView itemMatchCollectionTimeTv;
    @BindView(R.id.item_guess_center_guess_tv)
    TextView itemGuessCenterGuessTv;
    @BindView(R.id.item_match_collection_player_type_tv)
    TextView itemMatchCollectionPlayerTypeTv;
    @BindView(R.id.item_guess_center_bifen_tv)
    TextView itemGuessCenterBifenTv;
    @BindView(R.id.item_match_collection_right_head_riv)
    CircleImageView itemMatchCollectionRightHeadRiv;
    @BindView(R.id.item_match_collection_right_top_civ)
    CircleImageView itemMatchCollectionRightTopCiv;
    @BindView(R.id.item_match_collection_right_name_tv)
    TextView itemMatchCollectionRightNameTv;
    @BindView(R.id.item_match_time_win_right_iv)
    ImageView itemMatchTimeWinRightIv;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public SearchMatchCollectionItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();

    }

    @Override
    public void setData(SearchCollectionBean.DataBean data, int position) {
        itemMatchCollectionNoTv.setText(data.getMatch_num() == null ? "NO.1" : "NO." + data.getMatch_num());
        itemMatchCollectionTimeTv.setText(data.getStartTime() == null ? "1月1日 00:00" : data.getStartTime());
        itemMatchCollectionLeftNameTv.setText(data.getPlayer_a_name() == null ? "" : data.getPlayer_a_name());
        itemMatchCollectionRightNameTv.setText(data.getPlayer_b_name() == null ? "" : data.getPlayer_b_name());
        itemMatchTimeWinRightIv.setVisibility(View.GONE);
        itemMatchCollectionRightTopCiv.setVisibility(View.GONE);
        itemMatchTimeWinLeftIv.setVisibility(View.GONE);
        itemMatchCollectionLeftTopCiv.setVisibility(View.GONE);
        if (data.getPlayer_a_avater() != null)
            mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                            ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
                    GlideImageConfig
                            .builder()
                            .url(data.getPlayer_a_avater())
                            .imageView(itemMatchCollectionLeftHeadRiv)
                            .build());
        if (data.getPlayer_b_avater() != null)
            mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                            ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
                    GlideImageConfig
                            .builder()
                            .url(data.getPlayer_b_avater())
                            .imageView(itemMatchCollectionRightHeadRiv)
                            .build());
    }


    @Override
    protected void onRelease() {

    }
}