package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.HotPointBean;
import com.lwd.qjtv.mvp.ui.holder.NewsItemHolder;
import com.lwd.qjtv.mvp.ui.holder.NewsMorePicItemHolder;
import com.lwd.qjtv.mvp.ui.holder.NewsVideoItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class NewsAdapter extends DefaultAdapter<HotPointBean.DataBean> {

    private NewsVideoItemHolder newsVideoItemHolder;

    public NewsAdapter(List<HotPointBean.DataBean> infos) {
        super(infos);
    }


    @Override
    public BaseHolder<HotPointBean.DataBean> getHolder(View v, int viewType) {
        if (viewType == 0) {
            return new NewsItemHolder(v);
        } else if (viewType == 1) {
            return new NewsMorePicItemHolder(v);
        } else {
            newsVideoItemHolder = new NewsVideoItemHolder(v);
            return newsVideoItemHolder;
        }

    }

    @Override
    public int getItemViewType(int position) {
        HotPointBean.DataBean dataBean = mInfos.get(position);
        String is_more = dataBean.getContent_type();
        int type = 0;
//        资讯类型 0一张图 | 1三张图 | 2 视频
        switch (is_more) {
            case "0":
                type = 0;
                break;
            case "1":
                type = 1;
                break;
            case "2":
                type = 2;
                break;
            default:
        }
        return type;
    }

    @Override
    public int getLayoutId(int viewType) {
        if (viewType == 0) {
            return R.layout.item_consult_title_layout;
        } else if (viewType == 1) {
            return R.layout.item_consult_more_pic_layout;
        } else {
            return R.layout.item_consult_video_layout;
        }
    }


    public void setListGSYVideoPlayerState(int state) {
        if (newsVideoItemHolder != null) {
            if (newsVideoItemHolder.danmaku_player != null) {
                if (state == 2) {
                    newsVideoItemHolder.danmaku_player.onVideoPause();
                }

            }
        }

    }


}