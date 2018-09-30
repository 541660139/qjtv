package com.lwd.qjtv.mvp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.VideoCollectionBean;
import com.lwd.qjtv.mvp.ui.holder.CollectionItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/5.
 */

public class CollectionAdapter extends DefaultAdapter<VideoCollectionBean.DataBean> {

    private boolean isVisible;
    private boolean isSelectAll;
    private boolean allSelect;

    public CollectionAdapter(List<VideoCollectionBean.DataBean> infos) {
        super(infos);
        this.mInfos = infos;
    }

    @Override
    public BaseHolder<VideoCollectionBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
        CollectionItemHolder mHolder = (CollectionItemHolder) getHolder(view, viewType);
        mHolder.checkBox.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        mHolder.setOnItemClickListener(new BaseHolder.OnViewClickListener() {//设置Item点击事件
            @Override
            public void onViewClick(View view, int position) {
                if (mOnItemClickListener != null && mInfos.size() > 0) {
                    mOnItemClickListener.onItemClick(view, viewType, mInfos.get(position), position);
                }
            }
        });
        return mHolder;
    }

    @Override
    public void onBindViewHolder(BaseHolder<VideoCollectionBean.DataBean> holder, int position) {
        super.onBindViewHolder(holder, position);
        if (mInfos != null && mInfos.size() != 0 && mInfos.get(position) != null)
            holder.setData(mInfos.get(position), position);
        CollectionItemHolder mHolder = (CollectionItemHolder) holder;
        mHolder.checkBox.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        mHolder.checkBox.setTag(position);
        mHolder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int pos = (int) buttonView.getTag();
            mInfos.get(pos).setCheck(isChecked);
        });
        if (isSelectAll) {
            mInfos.get(position).setCheck(allSelect);
            if (position == mInfos.size() - 1)
                isSelectAll = false;
        }
        mHolder.checkBox.setChecked(mInfos.get(position).isCheck());
    }

    @Override
    public BaseHolder<VideoCollectionBean.DataBean> getHolder(View v, int viewType) {
        return new CollectionItemHolder(v);
    }

    public void setSelectAll() {
        allSelect = !allSelect;
        isSelectAll = true;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }


    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_video_collection_layout;
    }
}