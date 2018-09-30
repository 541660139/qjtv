package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.di.component.DaggerMyOrderComponent;
import com.lwd.qjtv.di.module.MyOrderModule;
import com.lwd.qjtv.mvp.contract.MyOrderContract;
import com.lwd.qjtv.mvp.presenter.MyOrderPresenter;
import com.lwd.qjtv.view.CustomLoadingListItemCreator;
import com.lwd.qjtv.view.LoadingPageView;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/27.
 */

public class MyOrderActivity extends BaseActivity<MyOrderPresenter> implements MyOrderContract.View, OnRefreshListener, OnLoadmoreListener {

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
    @BindView(R.id.activity_my_order_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_my_order_swiperefreshlayout)
    SmartRefreshLayout mSwipeRefreshLayout;
    private AlertDialog logisticsDialog;
    private RecyclerView logisticsRecyclerview;

    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;
    @BindView(R.id.activity_my_order_back_tv)
    TextView backTv;
    @BindView(R.id.activity_my_order_contact_iv)
    ImageView contactIv;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        this.mRxPermissions = new RxPermissions(this);
        DaggerMyOrderComponent
                .builder()
                .appComponent(appComponent)
                .myOrderModule(new MyOrderModule(this))  //首字母改成小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!Preference.getBoolean(Constant.IS_LOGIN))
            finish();
    }

    @Override
    public void initData() {
        loadingPageView.startLodingAnim();
//        initLogisticsDialog();
        mPresenter.requestMyOrderList(true);//打开app时自动加载列表
        initListener();
    }

    private void initListener() {
        loadingPageView.setClickReload(() -> onRefresh(mSwipeRefreshLayout));
        backTv.setOnClickListener(v -> finish());
        contactIv.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View contactView = LayoutInflater.from(this).inflate(R.layout.dialog_contact_layout, null);
            builder.setView(contactView);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            contactView.findViewById(R.id.dialog_contact_tv).setOnClickListener(v1 -> alertDialog.dismiss());
        });
    }


    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setOnLoadmoreListener(this);
        UiUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(this));
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
//        logisticsRecyclerview.setAdapter(new_pic LogisticsAdapter(new_pic ArrayList<String>()));
    }

    private void initLogisticsDialog() {
        logisticsDialog = new AlertDialog.Builder(WEApplication.getContext()).create();
        View view = LayoutInflater.from(WEApplication.getContext()).inflate(R.layout.dialog_logistics_layout, null);
        logisticsRecyclerview = (RecyclerView) view.findViewById(R.id.dialog_logistics_layout_recyclerview);
        UiUtils.configRecycleView(logisticsRecyclerview, new LinearLayoutManager(WEApplication.getContext()));
        logisticsDialog.setView(view);
    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
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
                    .setLoadingTriggerThreshold(10).setLoadingListItemCreator(new CustomLoadingListItemCreator())
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
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.requestMyOrderList(false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.requestMyOrderList(true);
    }
}
