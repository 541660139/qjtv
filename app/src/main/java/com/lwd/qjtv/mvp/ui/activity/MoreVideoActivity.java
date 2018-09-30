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
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.di.component.DaggerMoreVideoComponent;
import com.lwd.qjtv.di.module.MoreVideoModule;
import com.lwd.qjtv.mvp.contract.MoreVideoContract;
import com.lwd.qjtv.mvp.model.entity.MoreVideoBean;
import com.lwd.qjtv.mvp.presenter.MoreVideoPresenter;
import com.lwd.qjtv.view.CustomLoadingListItemCreator;
import com.lwd.qjtv.view.LoadingPageView;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/15.
 */

public class MoreVideoActivity extends BaseActivity<MoreVideoPresenter> implements MoreVideoContract.View, OnRefreshListener, OnLoadmoreListener, DefaultAdapter.OnRecyclerViewItemClickListener<MoreVideoBean.DataBean> {

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
        DaggerMoreVideoComponent
                .builder()
                .appComponent(appComponent)
                .moreVideoModule(new MoreVideoModule(this))  //首字母改成小写
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
        if (!Preference.getBoolean(Constant.IS_LOGIN)){
            showMessage("请登录");
            finish();
        }
    }

    @Override
    public void initData() {
        loadingPageView.startLodingAnim();
        getParseIntent();
        setTitle(title);
        mPresenter.requestMoreVideoList(true, type);//打开app时自动加载列表
        initListener();
    }

    private void initListener() {
        loadingPageView.setClickReload(() ->  mPresenter.requestMoreVideoList(true, type));
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
        UiUtils.makeText(this, message);
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
            mPaginate.setHasMoreDataToLoad(true);
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
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.requestMoreVideoList(false, type);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.requestMoreVideoList(true, type);
    }

    @Override
    public void onItemClick(View view, int viewType, MoreVideoBean.DataBean data, int position) {
        if (!SaveUserInfo.getLogin()) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if(!data.getV_type().equals("5")){
            //跳转视频详情页
            Intent intent = new Intent(this, VideoDetailsActivity.class);
            intent.putExtra("pic", data.getPic_h());
            intent.putExtra("id", data.getId());
            intent.putExtra("type", data.getV_type());
            intent.putExtra("url", data.getUrl());
            intent.putExtra("name", data.getName());
            startActivity(intent);
        } else {
            //跳转视频详情页
//            Intent intent = new Intent(this, MatchCollectionActivity.class);
//
//            startActivity(intent);
        }
    }
}
