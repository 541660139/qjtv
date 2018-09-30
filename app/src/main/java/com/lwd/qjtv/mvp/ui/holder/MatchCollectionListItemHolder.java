package com.lwd.qjtv.mvp.ui.holder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.model.entity.MatchCollectionNewListBean;
import com.lwd.qjtv.mvp.ui.adapter.MatchCollectionAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/5.
 */

public class MatchCollectionListItemHolder extends BaseHolder<MatchCollectionNewListBean.DataBeanX.MatchListBean> {

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    @BindView(R.id.item_matchcollection_list_tv)
    TextView listTv;
    @BindView(R.id.item_matchcollection_list_rv)
    RecyclerView listRv;

    public MatchCollectionListItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(MatchCollectionNewListBean.DataBeanX.MatchListBean data, int position) {
        listTv.setText(data.getMatch_name() == null ? "" : data.getMatch_name());
        List<MatchCollectionNewListBean.DataBeanX.MatchListBean.DataBean> data1 = data.getData();
        MatchCollectionAdapter matchCollectionAdapter = new MatchCollectionAdapter(data1);
        listRv.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL, false));
        listRv.setAdapter(matchCollectionAdapter);
    }


    @Override
    protected void onRelease() {

    }
}