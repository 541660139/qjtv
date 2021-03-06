package com.lwd.qjtv.mvp.ui.fragment.guess;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
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
import com.lwd.qjtv.di.component.DaggerGuessMyComponent;
import com.lwd.qjtv.di.module.GuessMyModule;
import com.lwd.qjtv.mvp.contract.GuessMyContract;
import com.lwd.qjtv.mvp.presenter.GuessMyPresenter;
import com.lwd.qjtv.view.CustomLoadingListItemCreator;
import com.lwd.qjtv.view.LoadingPageView;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/8/1.
 */

public class GuessMyFragment extends BaseFragment<GuessMyPresenter> implements GuessMyContract.View, OnRefreshListener, OnLoadmoreListener {

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
    @BindView(R.id.fragment_guess_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_guess_swiperefreshlayout)
    SmartRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.fragment_guess_loadingpage)
    LoadingPageView loadingPageView;
    private int type;


    public static GuessMyFragment newInstance(int type) {
        GuessMyFragment fragment = new GuessMyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        //fragment保存参数，传入一个Bundle对象
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerGuessMyComponent
                .builder()
                .appComponent(appComponent)
                .guessMyModule(new GuessMyModule(this))//请将GuessMyModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_guess_layout, container, false);
        mSwipeRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.zq_refreshlayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        if(getArguments()!=null){
            //取出保存的值
            type = getArguments().getInt("type", 0);
        }
        return view;
    }

    @Override
    public void initData() {
        loadingPageView.startLodingAnim();
        mPresenter.requestGuessMyList(true,type);//打开app时自动加载列表
    }

    @Override
    public void setData(Object data) {
        loadingPageView.checkData(data);
    }


    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setOnLoadmoreListener(this);
        UiUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(getContext()));
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
        UiUtils.makeText(getContext(), message);
    }

    @Override
    public void launchActivity(Intent intent) {
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
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
                    .setLoadingTriggerThreshold(0).setLoadingListItemCreator(new CustomLoadingListItemCreator())
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    @Override
    public void onDestroy() {
        DefaultAdapter.releaseAllHolder(mRecyclerView);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
        this.mRxPermissions = null;
        this.mPaginate = null;
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.requestGuessMyList(false,type);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.requestGuessMyList(true,type);
    }
}
