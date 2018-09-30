package com.lwd.qjtv.mvp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.WatchHistoryBean;
import com.lwd.qjtv.mvp.ui.holder.WatchRecordeItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class WatchRecordeAdapter extends DefaultAdapter<WatchHistoryBean> {

    private boolean isVisible;
    private ChooseCallback callback;
    private boolean isCheck;
    private boolean isSelectAll;

    public WatchRecordeAdapter(List<WatchHistoryBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<WatchHistoryBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
        WatchRecordeItemHolder mHolder = (WatchRecordeItemHolder) getHolder(view, viewType);
        mHolder.checkBox.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        mHolder.setOnItemClickListener(new BaseHolder.OnViewClickListener() {//设置Item点击事件
            @Override
            public void onViewClick(View view, int position) {
                if (isVisible) {
                    mHolder.checkBox.setChecked(!mHolder.checkBox.isChecked());
                }
                mOnItemClickListener.onItemClick(view, viewType, mInfos.get(position), position);
            }
        });
        return mHolder;
    }

    @Override
    public void onBindViewHolder(BaseHolder<WatchHistoryBean> holder, int position) {
        super.onBindViewHolder(holder, position);
        WatchRecordeItemHolder mHolder = (WatchRecordeItemHolder) holder;
        mHolder.checkBox.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        if (isSelectAll) {
            mHolder.checkBox.setChecked(isCheck);
        } else
            mHolder.checkBox.setChecked(mInfos.get(position).getIsSelect());
        mHolder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (callback != null)
                callback.chooseItem(mInfos.get(position), isChecked);
        });
    }

    @Override
    public BaseHolder<WatchHistoryBean> getHolder(View v, int viewType) {
        return new WatchRecordeItemHolder(v);
    }

    public void setChooseCallback(ChooseCallback callback) {
        this.callback = callback;
    }

    public interface ChooseCallback {
        void chooseItem(WatchHistoryBean bean, boolean isDelete);
    }

    public void setCheck(boolean isCheck) {
        this.isCheck = isCheck;
        isSelectAll = true;
        notifyDataSetChanged();
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_watch_history_layout;
    }


    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

}