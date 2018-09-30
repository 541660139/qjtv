package com.lwd.qjtv.mvp.ui.holder;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Contact;
import com.lwd.qjtv.app.utils.GlideConfigGlobal;
import com.lwd.qjtv.mvp.model.entity.MyCommentListBean;
import com.lwd.qjtv.mvp.ui.activity.WebNewActivity;

import java.util.HashMap;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class MyCommentItemHolder extends BaseHolder<MyCommentListBean.DataBean> {
    @BindView(R.id.ll_all_content)
    LinearLayout ll_all_content;
    @BindView(R.id.item_my_comment_item_avater_civ)
    CircleImageView itemMyCommentItemAvaterCiv;
    @BindView(R.id.item_my_comment_item_name_tv)
    TextView itemMyCommentItemNameTv;
    @BindView(R.id.item_my_comment_item_time_tv)
    TextView itemMyCommentItemTimeTv;
    @BindView(R.id.item_my_comment_item_ll)
    LinearLayout itemMyCommentItemLl;
    @BindView(R.id.item_my_comment_item_see_tv)
    TextView itemMyCommentItemSeeTv;
    @BindView(R.id.item_my_comment_item_content_tv)
    TextView itemMyCommentItemContentTv;
    @BindView(R.id.item_my_comment_item_comment_tv)
    TextView itemMyCommentItemCommentTv;
    @BindView(R.id.item_my_comment_message_iv)
    ImageView itemMyCommentMessageIv;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private Contact contact;

    public MyCommentItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(MyCommentListBean.DataBean data, int position) {
        itemMyCommentItemNameTv.setText(data.getUsername() == null ? "" : data.getUsername());
        itemMyCommentItemTimeTv.setText(data.getCtime() == null ? "" : data.getCtime());
        itemMyCommentItemContentTv.setText(data.getTitle() == null ? "" : data.getTitle());
        itemMyCommentItemCommentTv.setText(data.getContent() == null ? "" : data.getContent());
        if (!TextUtils.isEmpty(data.getAvater()))
            mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                    ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(data.getAvater(), itemMyCommentItemAvaterCiv));
        if (!TextUtils.isEmpty(data.getPic_h()))
            mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                    ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(data.getPic_h(), itemMyCommentMessageIv));

        ll_all_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "setAdapter: " + "startActivity1");

                HashMap<String, String> map = new HashMap<>();
                map.put("card_id", data.getCard_id());
                map.put("p", "1");
                if (contact == null)
                    contact = new Contact(mAppComponent.appManager().getCurrentActivity());
                String webUrl = contact.getWebUrl(Constant.BBS_CARD_INFO, map);
                Intent intent = new Intent(mAppComponent.appManager().getCurrentActivity(), WebNewActivity.class);
                intent.putExtra("url", webUrl);
                intent.putExtra("is_bbs_details", true);
                Log.d(TAG, "setAdapter: ItemClick ");
                mAppComponent.appManager().getCurrentActivity().startActivity(intent);
            }
        });

    }


    @Override
    protected void onRelease() {
        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
                .imageViews(itemMyCommentItemAvaterCiv, itemMyCommentMessageIv)
                .build());
    }

}