package com.lwd.qjtv.app.utils;

import android.widget.ImageView;

import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.lwd.qjtv.R;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/14.
 */

public class GlideConfigGlobal {

    public static GlideImageConfig loadImageView(String url, ImageView imageView){
       return GlideImageConfig
                .builder()
                .url(url)
                .errorPic(R.mipmap.video_place_holder)
                .placeholder(R.mipmap.video_place_holder)
                .imageView(imageView)
                .build();
    }
}
