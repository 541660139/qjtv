package com.lwd.qjtv.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.BBSDataBean;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DELL on 2018/5/24.
 */

public class StarBallViewHolder extends BaseHolder<BBSDataBean.DataBean.StarListBean.DataListBean> {
    @BindView(R.id.item_headview_name_tv)
    TextView nameTv;
    @BindView(R.id.item_headview_head_civ)
    CircleImageView headCiv;

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public StarBallViewHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(BBSDataBean.DataBean.StarListBean.DataListBean data, int position) {

        nameTv.setText(data.getName() + "");
        if (data.getThumb_img() != null && !"".equals(data.getThumb_img()))
            mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                            ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
                    GlideImageConfig
                            .builder()
                            .url(data.getThumb_img())
                            .imageView(headCiv)
                            .build());
    }


    @Override
    protected void onRelease() {
        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
                .imageViews(headCiv)
                .build());
    }
}
