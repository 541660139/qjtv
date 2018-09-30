package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;

import com.jess.arms.utils.UiUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.lwd.qjtv.di.component.DaggerLikeAvaterListComponent;
import com.lwd.qjtv.di.module.LikeAvaterListModule;
import com.lwd.qjtv.mvp.contract.LikeAvaterListContract;
import com.lwd.qjtv.mvp.presenter.LikeAvaterListPresenter;

import com.lwd.qjtv.R;


import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class LikeAvaterListActivity extends BaseActivity<LikeAvaterListPresenter> implements LikeAvaterListContract.View, OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.activity_like_avater_list_smartrefreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.activity_like_avater_list_recyclerview)
    RecyclerView recyclerView;
    private String id;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerLikeAvaterListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .likeAvaterListModule(new LikeAvaterListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_like_avater_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData() {
        id = getIntent().getStringExtra("id");
        initListener();
        mPresenter.requestList(true,id);
    }

    private void initListener() {
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.SnackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void setData(Object o) {

    }


    @Override
    public void startLoadMore() {

    }

    @Override
    public void endLoadMore() {
        smartRefreshLayout.finishLoadmore();
    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
        recyclerView.setAdapter(adapter);
        UiUtils.configRecycleView(recyclerView,new LinearLayoutManager(this));
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.requestList(true,id);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.requestList(true,id);
    }
}
