package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.di.component.DaggerMoreComponent;
import com.lwd.qjtv.di.module.MoreModule;
import com.lwd.qjtv.mvp.contract.MoreContract;
import com.lwd.qjtv.mvp.model.entity.MoreTeachListBean;
import com.lwd.qjtv.mvp.presenter.MorePresenter;
import com.lwd.qjtv.view.CustomLoadingListItemCreator;
import com.lwd.qjtv.view.LoadingPageView;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class MoreActivity extends BaseActivity<MorePresenter> implements MoreContract.View,OnRefreshListener, OnLoadmoreListener, DefaultAdapter.OnRecyclerViewItemClickListener<MoreTeachListBean.DataBean> {
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SmartRefreshLayout mSwipeRefreshLayout;
    private String type;

    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;
    private String title;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        this.mRxPermissions = new RxPermissions(this);
        DaggerMoreComponent
                .builder()
                .appComponent(appComponent)
                .moreModule(new MoreModule(this))  //首字母改成小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_more;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!Preference.getBoolean(Constant.IS_LOGIN))
            finish();
    }

    @Override
    public void initData() {
        loadingPageView.startLodingAnim();
        getParseIntent();
        mPresenter.requestMoreList(true, type);//打开app时自动加载列表
        setTitle(title);
        initListener();
    }

    private void getParseIntent() {
        if (getIntent() != null) {
            type = getIntent().getStringExtra("type");
            title = getIntent().getStringExtra("title");
        }
    }


    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setOnLoadmoreListener(this);
        UiUtils.configRecycleView(mRecyclerView, new GridLayoutManager(this, 2));
    }


    private void initListener() {
        loadingPageView.setClickReload(() -> onRefresh(mSwipeRefreshLayout));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.finishRefresh();
    }

    @Override
    public void showMessage(String message) {
//        UiUtils.SnackbarText(message);
        UiUtils.makeText(this,message);
    }

    @Override
    public void launchActivity(Intent intent) {
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void setData(Object o) {
        loadingPageView.checkData(o);
    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
        initRecycleView();
//        initPaginate();
    }

    /**
     * 开始加载更多
     */
    @Override
    public void startLoadMore() {
        isLoadingMore = true;
    }

    /**
     * 结束加载更多
     */
    @Override
    public void endLoadMore() {
        isLoadingMore = false;
        mSwipeRefreshLayout.finishLoadmore();
    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    /**
     * 初始化Paginate,用于加载更多
     */
    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {

                }

                @Override
                public boolean isLoading() {
                    return isLoadingMore;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return false;
                }
            };

            mPaginate = Paginate.with(mRecyclerView, callbacks)
                    .setLoadingTriggerThreshold(10)
                    .setLoadingListItemCreator(new CustomLoadingListItemCreator())
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    @Override
    protected void onDestroy() {
        DefaultAdapter.releaseAllHolder(mRecyclerView);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
        this.mRxPermissions = null;
        this.mPaginate = null;
    }

    @Override
    public void onItemClick(View view, int viewType, MoreTeachListBean.DataBean data, int position) {
        //跳转视频详情页
        Intent intent = new Intent(this, VideoDetailsActivity.class);
        intent.putExtra("pic", data.getPic_h());
        intent.putExtra("id", data.getId());
        intent.putExtra("type", data.getV_type());
        intent.putExtra("url",data.getUrl());
        intent.putExtra("name",data.getName());
        startActivity(intent);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.requestMoreList(false, type);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.requestMoreList(true, type);
    }
}
