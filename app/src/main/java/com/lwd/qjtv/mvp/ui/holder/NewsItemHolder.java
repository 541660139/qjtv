package com.lwd.qjtv.mvp.ui.holder;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.mvp.model.entity.HotPointBean;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class NewsItemHolder extends BaseHolder<HotPointBean.DataBean> {

    @BindView(R.id.item_consult_title_title_tv)
    TextView itemConsultTitleTitleTv;
    @BindView(R.id.item_consult_name_tv)
    TextView itemConsultNameTv;
    @BindView(R.id.item_consult_date_tv)
    TextView itemConsultDateTv;
    @BindView(R.id.item_consult_like_tv)
    TextView itemConsultLikeTv;
    @BindView(R.id.item_consult_pic_iv)
    ImageView picIv;
    @BindView(R.id.item_consult_rl)
    RelativeLayout rl;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public NewsItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
        ViewGroup.LayoutParams layoutParams = rl.getLayoutParams();

        int width = UiUtils.getScreenWidth(WEApplication.getContext()) / 3;
        layoutParams.width = width;
        layoutParams.height = width / 8 * 5;
        rl.setLayoutParams(layoutParams);
    }

    @Override
    public void setData(HotPointBean.DataBean data, int position) {
        itemConsultTitleTitleTv.setText(data.getTitle() == null ? "" : data.getTitle());
        itemConsultNameTv.setText(data.getAuthor() == null ? "" : data.getAuthor());
        itemConsultDateTv.setText(data.getCreate_time() == null ? "" : data.getCreate_time());
        itemConsultLikeTv.setText(data.getBrowse_number() == null ? "" : data.getBrowse_number());
        if (data.getImg_url() != null && !TextUtils.isEmpty(data.getImg_url())) {
            loadImg(data.getImg_url(), picIv);
        } else {
            picIv.setImageResource(R.mipmap.video_place_holder);
        }
    }


    @Override
    protected void onRelease() {
        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
                .imageViews(picIv)
                .build());
    }

    private void loadImg(String url, ImageView imageView) {
        mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                        ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
                GlideImageConfig
                        .builder()
                        .url(url)
                        .placeholder(R.mipmap.video_place_holder)
                        .errorPic(R.mipmap.video_place_holder)
                        .imageView(imageView)
                        .build());
    }


}