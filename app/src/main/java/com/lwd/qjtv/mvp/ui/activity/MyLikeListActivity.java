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
import com.lwd.qjtv.di.component.DaggerMyLikeListComponent;
import com.lwd.qjtv.di.module.MyLikeListModule;
import com.lwd.qjtv.mvp.contract.MyLikeListContract;
import com.lwd.qjtv.mvp.presenter.MyLikeListPresenter;

import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.ui.adapter.MyLikeItemAdapter;
import com.lwd.qjtv.mvp.ui.holder.MyLikeItemHolder;


import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MyLikeListActivity extends BaseActivity<MyLikeListPresenter> implements MyLikeListContract.View, OnRefreshListener, OnLoadmoreListener, MyLikeItemHolder.ClickMoreCallback {
    @BindView(R.id.activity_my_like_list_recyclerview)
    RecyclerView likeRv;
    @BindView(R.id.activity_my_like_list_smartrefreshlayout)
    SmartRefreshLayout smartRefreshLayout;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMyLikeListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myLikeListModule(new MyLikeListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_my_like_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData() {
        setTitle("我的点赞");
        initListener();
        mPresenter.getMyCommentList(true);
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
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.getMyCommentList(true);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.getMyCommentList(false);
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
        likeRv.setAdapter(adapter);
        MyLikeItemAdapter myLikeItemAdapter = (MyLikeItemAdapter) adapter;
        myLikeItemAdapter.setClickMoreCallback(this);
        UiUtils.configRecycleView(likeRv, new LinearLayoutManager(this));
    }

    @Override
    public void clickMore(String id) {
        Intent intent = new Intent(this, LikeAvaterListActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}
