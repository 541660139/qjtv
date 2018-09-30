package com.lwd.qjtv.mvp.ui.holder;

import android.content.Intent;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
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

public class CommunityAllIteItemHolder extends BaseHolder<CommunityDataBean.DataBean> {

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
    @BindView(R.id.iv_community_item1)
    ImageView iv_community_item1;

    @BindView(R.id.iv_community_item2)
    ImageView iv_community_item2;
    @BindView(R.id.iv_community_item3)
    ImageView iv_community_item3;


    private OnRecyclerViewTvItemClickListener onRecyclerViewTvItemClickListener;

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public CommunityAllIteItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(CommunityDataBean.DataBean data, int position) {


        DisplayMetrics displayMetrics = mAppComponent.appManager().getCurrentActivity().getResources().getDisplayMetrics();

        int widthPixels = displayMetrics.widthPixels;
        int i = (widthPixels - UiUtils.dip2px(mAppComponent.Application(), 10)) / 3;
        int heigth = i * 9 / 16;


        ViewGroup.LayoutParams layoutParams = iv_community_item1.getLayoutParams();

        layoutParams.width = i;
        layoutParams.height = heigth;


        ViewGroup.LayoutParams layoutParams1 = iv_community_item2.getLayoutParams();

        layoutParams1.width = i;
        layoutParams1.height = heigth;


        ViewGroup.LayoutParams layoutParams2 = iv_community_item3.getLayoutParams();
        layoutParams2.width = i;
        layoutParams2.height = heigth;


        tv_user_name.setText(data.getUsername() == null ? "張三" : data.getUsername());
        tv_date.setText(data.getCreate_time() == null ? "2018-8-8" : data.getCreate_time());
        tv_gz.setText(data.getIs_follow() == 0 ? "未关注" : "已关注");
        tv_gz.setTextColor(data.getIs_follow() == 0 ? mAppComponent.Application().getResources().getColor(R.color.bgColor_overlay) : mAppComponent.Application().getResources().getColor(R.color.white));
        tv_gz.setBackgroundResource(data.getIs_follow() == 0 ? R.drawable.shape_gz_bg : R.drawable.shape_gz_bg_selector);


        tv_title.setText(data.getTitle() == null ? "索尼大法好" : data.getTitle());
        if (data.getAvater() != null && !TextUtils.isEmpty(data.getAvater())) {
            loadImg(data.getAvater(), item_message_layout_head_civ);
        }


        String[] split = data.getPicture().split(",");


        if (split.length == 1) {
            if (split[0] != null && !TextUtils.isEmpty(split[0])) {
                loadImg(split[0], iv_community_item1);
            }
        }
        if (split.length == 2) {
            if (split[0] != null && !TextUtils.isEmpty(split[0])) {
                loadImg(split[0], iv_community_item1);
            }
            if (split[1] != null && !TextUtils.isEmpty(split[1])) {
                loadImg(split[1], iv_community_item2);
            }

        }
        if (split.length >= 3) {
            if (split[0] != null && !TextUtils.isEmpty(split[0])) {
                loadImg(split[0], iv_community_item1);
            }
            if (split[1] != null && !TextUtils.isEmpty(split[1])) {
                loadImg(split[1], iv_community_item2);
            }
            if (split[2] != null && !TextUtils.isEmpty(split[2])) {
                loadImg(split[2], iv_community_item3);
            } else {
                iv_community_item3.setVisibility(View.INVISIBLE);
            }
        }


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
                .imageViews(item_message_layout_head_civ, iv_community_item1, iv_community_item2, iv_community_item3)
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
        void onItemTVClick(View view, String starid, int postion);
    }

    public void setOnItemTvClickListener(OnRecyclerViewTvItemClickListener listener) {
        this.onRecyclerViewTvItemClickListener = listener;
    }
}
