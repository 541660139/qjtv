package com.lwd.qjtv.mvp.ui.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.FansOrFollerBean;

import butterknife.BindView;

/**
 * Created by DELL on 2018/8/29.
 */

public class FansOrFollowItemHolder extends BaseHolder<FansOrFollerBean.DataBean> {

    private CommunityAllIteItemHolder.OnRecyclerViewTvItemClickListener onRecyclerViewTvItemClickListener;


    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架


    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_gz)
    TextView tv_gz;
    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;


    public FansOrFollowItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(FansOrFollerBean.DataBean data, int position) {

        tv_name.setText(data.getUsername());

        tv_gz.setTextColor(data.getIs_follow() == 0 ? mAppComponent.Application().getResources().getColor(R.color.bgColor_overlay) : mAppComponent.Application().getResources().getColor(R.color.white));
        tv_gz.setBackgroundResource(data.getIs_follow() == 0 ? R.drawable.shape_gz_bg : R.drawable.shape_gz_bg_selector);
        tv_gz.setText(data.getIs_follow() == 0 ? "未关注" : "已关注");
        if (data.getAvater() != null && !TextUtils.isEmpty(data.getAvater())) {
            Glide.with(mAppComponent.Application()).load(data.getAvater()).placeholder(R.mipmap.video_place_holder).into(iv_avatar);

        }

        tv_gz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.getStar_id() != null && !TextUtils.isEmpty(data.getStar_id())) {
                    onRecyclerViewTvItemClickListener.onItemTVClick(view, data.getStar_id(), position);
                } else {
                    onRecyclerViewTvItemClickListener.onItemTVClick(view, data.getFan_id(), position);
                }
            }
        });
     

    }


    @Override
    protected void onRelease() {
        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
                .imageViews(iv_avatar)
                .build());
    }


    public interface OnRecyclerViewTvItemClickListener {
        void onItemTVClick(View view, String starid, int postion);
    }

    public void setOnItemTvClickListener(CommunityAllIteItemHolder.OnRecyclerViewTvItemClickListener listener) {
        this.onRecyclerViewTvItemClickListener = listener;
    }
}
