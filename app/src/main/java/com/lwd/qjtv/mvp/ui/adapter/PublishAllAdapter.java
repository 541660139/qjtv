package com.lwd.qjtv.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.PublisDataBean;
import com.lwd.qjtv.mvp.ui.holder.PublisPictereItemHolder;
import com.lwd.qjtv.mvp.ui.holder.PublisTextItemHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/8/27.
 */

public class PublishAllAdapter extends DefaultAdapter<PublisDataBean.DataBean> {
    private List<String> seleterIds = new ArrayList<>();

    private OnRecyclerViewCheckBoxItemClickListener onRecyclerViewCheckBoxItemClickListener;

    public PublishAllAdapter(List<PublisDataBean.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<PublisDataBean.DataBean> getHolder(View v, int viewType) {

        if (viewType == 0) {
            PublisPictereItemHolder publisPictereItemHolder = new PublisPictereItemHolder(v);
//            publisPictereItemHolder.setOnItemTvClickListener(new PublisPictereItemHolder.OnRecyclerViewTvItemClickListener() {
//                @Override
//                public void onItemTVClick() {
//                    onRecyclerViewCheckBoxItemClickListener.onItemTVClick();
//                }
//            });

            return publisPictereItemHolder;
        } else {
            PublisTextItemHolder publisTextItemHolder = new PublisTextItemHolder(v);

//            publisTextItemHolder.setOnItemTvClickListener(new PublisTextItemHolder.OnRecyclerViewTvItemClickListener() {
//                @Override
//                public void onItemTVClick() {
//                    onRecyclerViewCheckBoxItemClickListener.onItemTVClick();
//                }
//            });
            return publisTextItemHolder;

        }

    }


    @Override
    public int getLayoutId(int viewType) {
        if (viewType == 0) {
            return R.layout.item_publis_picture_item_layout;
        } else {
            return R.layout.item_publis_text_item_layout;
        }
    }


    @Override
    public int getItemViewType(int position) {
        PublisDataBean.DataBean dataBean = mInfos.get(position);
        int type = 0;
        String[] split = dataBean.getPicture().split(",");
        if (split.length > 1) {
            type = 0;
        } else {
            type = 1;
        }
        return type;
    }

    public void setIsVisible(boolean isVisible) {
        for (int i = 0; i < mInfos.size(); i++) {
            mInfos.get(i).setVisible(isVisible);
        }
        notifyDataSetChanged();
    }


    public void setAllSeleter(boolean isSeleter) {

        for (int i = 0; i < mInfos.size(); i++) {
            mInfos.get(i).setSeleter(isSeleter);
        }
        notifyDataSetChanged();
    }

    public String getAllSeleterId() {
        for (int i = 0; i < mInfos.size(); i++) {
            if (mInfos.get(i).isSeleter()) {
                seleterIds.add(mInfos.get(i).getPost_id());
            }

        }

        StringBuilder string = new StringBuilder();
        for (int i = 0; i < seleterIds.size(); i++) {
            if (i < seleterIds.size() - 1) {
                string.append(seleterIds.get(i) + ",");
            } else {
                string.append(seleterIds.get(i));
            }

        }

        return string.toString();
    }


    public interface OnRecyclerViewCheckBoxItemClickListener {
        void onItemTVClick();
    }

    public void setOnItemTvClickListener(OnRecyclerViewCheckBoxItemClickListener listener) {
        this.onRecyclerViewCheckBoxItemClickListener = listener;
    }

    public List<PublisDataBean.DataBean> getAllDta(){
        return mInfos;
    }


}
