package com.lwd.qjtv.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.WatchBallBean;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/5.
 */

public class OtherGuessItemHolder extends BaseHolder<WatchBallBean.DataBean.NbaListBean.DataListBeanX> {
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    @BindView(R.id.item_other_guess_num_tv)
    TextView numTv;
    @BindView(R.id.item_other_guess_civ)
    CircleImageView guessCiv;
    @BindView(R.id.item_other_guess_title_tv)
    TextView titleTv;
    @BindView(R.id.item_other_guess_content_tv)
    TextView contentTv;

    public OtherGuessItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(WatchBallBean.DataBean.NbaListBean.DataListBeanX data, int position) {
        titleTv.setText(data.getMatch_name() == null ? "" : data.getMatch_name());
        contentTv.setText(data.getMsg() == null ? "" : data.getMsg());
        numTv.setText(position + 1 + ".");


        if (data.getIcon() != null && !"".equals(data.getIcon()))
            mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                            ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
                    GlideImageConfig
                            .builder()
                            .url(data.getIcon())
                            .imageView(guessCiv)
                            .build());
    }

    @Override
    protected void onRelease() {
        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
                .imageViews(guessCiv)
                .build());
    }
}