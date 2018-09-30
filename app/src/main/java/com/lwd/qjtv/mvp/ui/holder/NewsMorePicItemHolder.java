package com.lwd.qjtv.mvp.ui.holder;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.HotPointBean;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class NewsMorePicItemHolder extends BaseHolder<HotPointBean.DataBean> {

    @BindView(R.id.item_consult_more_pic_title_tv)
    TextView itemConsultMorePicTitleTv;
    @BindView(R.id.item_consult_pic_one_iv)
    ImageView itemConsultPicOneIv;
    @BindView(R.id.item_consult_pic_two_iv)
    ImageView itemConsultPicTwoIv;
    @BindView(R.id.item_consult_pic_three_iv)
    ImageView itemConsultPicThreeIv;
    @BindView(R.id.item_consult_name_tv)
    TextView itemConsultNameTv;
    @BindView(R.id.item_consult_date_tv)
    TextView itemConsultDateTv;
    @BindView(R.id.item_consult_like_tv)
    TextView itemConsultLikeTv;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public NewsMorePicItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(HotPointBean.DataBean data, int position) {
//        DisplayMetrics dm = getResources().getDisplayMetrics();


        DisplayMetrics displayMetrics = mAppComponent.appManager().getCurrentActivity().getResources().getDisplayMetrics();

        int widthPixels = displayMetrics.widthPixels;
        int i = (widthPixels - UiUtils.dip2px(mAppComponent.Application(), 10)) / 3;
        int heigth = i * 9 / 16;

        ViewGroup.LayoutParams layoutParams = itemConsultPicOneIv.getLayoutParams();

        layoutParams.width = i;
        layoutParams.height = heigth;


        ViewGroup.LayoutParams layoutParams1 = itemConsultPicTwoIv.getLayoutParams();

        layoutParams1.width = i;
        layoutParams1.height = heigth;


        ViewGroup.LayoutParams layoutParams2 = itemConsultPicThreeIv.getLayoutParams();

        layoutParams2.width = i;
        layoutParams2.height = heigth;


        itemConsultMorePicTitleTv.setText(data.getTitle() == null ? "" : data.getTitle());
        itemConsultNameTv.setText(data.getAuthor() == null ? "" : data.getAuthor());
        itemConsultDateTv.setText(data.getCreate_time() == null ? "" : data.getCreate_time());
        itemConsultLikeTv.setText(data.getBrowse_number() == null ? "" : data.getBrowse_number());

        String[] split = data.getImg_url().split(",");
        if (split[0] != null && !TextUtils.isEmpty(split[0]))
            Glide.with(itemView.getContext()).load(split[0])
                    .placeholder(R.mipmap.video_place_holder)
                    .error(R.mipmap.video_place_holder).into(itemConsultPicOneIv);

        if (split[1] != null && !TextUtils.isEmpty(split[1]))
            Glide.with(itemView.getContext()).load(split[1])
                    .placeholder(R.mipmap.video_place_holder)
                    .error(R.mipmap.video_place_holder).into(itemConsultPicTwoIv);
        if (split[2] != null && !TextUtils.isEmpty(split[2])) {
            Glide.with(itemView.getContext()).load(split[2])
                    .placeholder(R.mipmap.video_place_holder)
                    .error(R.mipmap.video_place_holder).into(itemConsultPicThreeIv);
        } else {
            itemConsultPicThreeIv.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    protected void onRelease() {
        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
                .imageViews(itemConsultPicOneIv, itemConsultPicTwoIv, itemConsultPicThreeIv)
                .build());
    }

    private void loadImg(String url, ImageView imageView) {
        mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                        ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
                GlideImageConfig
                        .builder()
                        .url(url)
                        .imageView(imageView)
                        .build());
    }
}