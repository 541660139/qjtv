package com.lwd.qjtv.mvp.ui.holder;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.mvp.model.entity.CommunityDataBean;
import com.lwd.qjtv.mvp.ui.activity.MinePublisActivity;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DELL on 2018/8/27.
 */

public class CommunityAllIteMoreItemHolder extends BaseHolder<CommunityDataBean.DataBean> {
    @BindView(R.id.item_message_layout_head_civ)
    CircleImageView item_message_layout_head_civ;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_gz)
    TextView tv_gz;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_community_more)
    ImageView iv_community_more;


    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    private OnRecyclerViewTvItemClickListener onRecyclerViewTvItemClickListener;

    public CommunityAllIteMoreItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(CommunityDataBean.DataBean data, int position) {
        if (data.getAvater() != null && !TextUtils.isEmpty(data.getAvater())) {
            loadImg(data.getAvater(), item_message_layout_head_civ);
        }

        if (data.getPicture() != null && !TextUtils.isEmpty(data.getPicture())) {
            loadImg(data.getPicture(), iv_community_more);
        } else {
            iv_community_more.setVisibility(View.GONE);
        }

        tv_user_name.setText(data.getUsername() == null ? "張三" : data.getUsername());
        tv_date.setText(data.getCreate_time() == null ? "2018-8-8" : data.getCreate_time());
        tv_gz.setText(data.getIs_follow() == 0 ? "未关注" : "已关注");
        tv_gz.setTextColor(data.getIs_follow() == 0 ? mAppComponent.Application().getResources().getColor(R.color.bgColor_overlay) : mAppComponent.Application().getResources().getColor(R.color.white));
        tv_gz.setBackgroundResource(data.getIs_follow() == 0 ? R.drawable.shape_gz_bg : R.drawable.shape_gz_bg_selector);
        tv_title.setText(data.getTitle() == null ? "索尼大法好" : data.getTitle());


        tv_gz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecyclerViewTvItemClickListener.onItemTVClick(view, data.getUid(), position);
            }
        });


        if (data.getUid().equals(SaveUserInfo.getUid())) {
            tv_gz.setVisibility(View.GONE);
        } else {
            tv_gz.setVisibility(View.VISIBLE);
        }


        item_message_layout_head_civ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mAppComponent.Application(), MinePublisActivity.class);
                intent.putExtra("other_uid", data.getUid());
                mAppComponent.Application().startActivity(intent);
            }
        });
    }

    @Override
    protected void onRelease() {
        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
                .imageViews(item_message_layout_head_civ, iv_community_more)
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


    public interface OnRecyclerViewTvItemClickListener {
        void onItemTVClick(View view, String starid, int position);
    }

    public void setOnItemTvClickListener(OnRecyclerViewTvItemClickListener listener) {
        this.onRecyclerViewTvItemClickListener = listener;
    }

}
