package com.lwd.qjtv.mvp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.ui.holder.SearchHistoryItemHolder;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/16.
 */

public class SearchHistoryAdapter extends DefaultAdapter<String> {

    private SearchHistoryItemHolder.CursorItemCLickListener cursorItemCLickListener;
    private BaseHolder mHolder;

    public SearchHistoryAdapter(List<String> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<String> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
        mHolder = getHolder(view, viewType);
        mHolder.setOnItemClickListener(new BaseHolder.OnViewClickListener() {//设置Item点击事件
            @Override
            public void onViewClick(View view, int position) {
                if (mOnItemClickListener != null && mInfos.size() > 0) {
                    mOnItemClickListener.onItemClick(view, viewType, mInfos.get(position), position);
                }
            }
        });
        ((SearchHistoryItemHolder) mHolder).setOnCursorItemClickListener(cursorItemCLickListener);
        return mHolder;
    }

    @Override
    public BaseHolder<String> getHolder(View v, int viewType) {
        return new SearchHistoryItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.cursor_text_style;
    }

    public void setOnCursorItemClickListener(SearchHistoryItemHolder.CursorItemCLickListener cursorItemClickListener) {
        this.cursorItemCLickListener = cursorItemClickListener;
    }

}