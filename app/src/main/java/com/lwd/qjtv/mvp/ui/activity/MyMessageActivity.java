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
import com.lwd.qjtv.di.component.DaggerMyMessageComponent;
import com.lwd.qjtv.di.module.MyMessageModule;
import com.lwd.qjtv.mvp.contract.MyMessageContract;
import com.lwd.qjtv.mvp.presenter.MyMessagePresenter;

import com.lwd.qjtv.R;


import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MyMessageActivity extends BaseActivity<MyMessagePresenter> implements MyMessageContract.View, OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.activity_my_message_smartrefreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.activity_my_message_recyclerview)
    RecyclerView recyclerView;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMyMessageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myMessageModule(new MyMessageModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_my_message; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData() {
        initListener();
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
    public void onRefresh(RefreshLayout refreshlayout) {

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {

    }

    @Override
    public void startLoadMore() {

    }

    @Override
    public void endLoadMore() {

    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
        recyclerView.setAdapter(adapter);
        UiUtils.configRecycleView(recyclerView, new LinearLayoutManager(this));
    }

    private void initRecyclerView() {

    }
}
