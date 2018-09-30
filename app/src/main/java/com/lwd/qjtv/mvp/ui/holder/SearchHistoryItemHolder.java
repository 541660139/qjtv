package com.lwd.qjtv.mvp.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.R;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/16.
 */

public class SearchHistoryItemHolder extends BaseHolder<String> {
    @BindView(R.id.cursor_text)
    public TextView cursorTv;
    @BindView(R.id.search_delete_iv)
    public ImageView deleteIv;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private CursorItemCLickListener cursorItemCLickListener;

    public SearchHistoryItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(String data, int position) {
        cursorTv.setText(data);
        deleteIv.setOnClickListener(view -> cursorItemCLickListener.click(data));
    }

    public void setOnCursorItemClickListener(CursorItemCLickListener cursorItemClickListener) {
        this.cursorItemCLickListener = cursorItemClickListener;
    }

    public interface CursorItemCLickListener {
        void click(String name);
    }

}