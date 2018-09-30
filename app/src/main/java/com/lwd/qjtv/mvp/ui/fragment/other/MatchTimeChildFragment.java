package com.lwd.qjtv.mvp.ui.fragment.other;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import com.lwd.qjtv.di.component.DaggerMatchTimeChildComponent;
import com.lwd.qjtv.di.module.MatchTimeChildModule;
import com.lwd.qjtv.mvp.contract.MatchTimeChildContract;
import com.lwd.qjtv.mvp.presenter.MatchTimeChildPresenter;
import com.lwd.qjtv.view.CustomLoadingListItemCreator;
import com.lwd.qjtv.view.LoadingPageView;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/7.
 */

public class MatchTimeChildFragment extends BaseFragment<MatchTimeChildPresenter> implements MatchTimeChildContract.View, OnLoadmoreListener, OnRefreshListener {

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
    @BindView(R.id.fragment_match_time_child_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.loading_page_view)
    LoadingPageView loadingPageView;
    private String matchId;
    //    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DefaultAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (adapter != null)
                adapter.notifyDataSetChanged();
            handler.sendEmptyMessageDelayed(0, 1000);
        }
    };


    public static MatchTimeChildFragment newInstance() {
        MatchTimeChildFragment fragment = new MatchTimeChildFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerMatchTimeChildComponent
                .builder()
                .appComponent(appComponent)
                .matchTimeChildModule(new MatchTimeChildModule(this))//请将MatchTimeChildModule()第一个首字母改为小写
                .build()
                .inject(this);
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_match_time_child_layout, container, false);
//        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.zq_refreshlayout);
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void initData() {
        loadingPageView.startLodingAnim();
        getParseArguments();
        mPresenter.requestMatchTimeChildList(true, matchId);//打开app时自动加载列表
    }

    private void getParseArguments() {
        matchId = getArguments().getString("id");
    }

    @Override
    public void setData(Object data) {
        loadingPageView.checkData(data);
    }


    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
        UiUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(getContext()));
    }


    @Override
    public void showLoading() {
        Timber.tag(TAG).w("showLoading");
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
//                    mSwipeRefreshLayout.setRefreshing(true)
                });
    }

    @Override
    public void hideLoading() {
        Timber.tag(TAG).w("hideLoading");
//        mSwipeRefreshLayout.setRefreshing(false);
        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void showMessage(String message) {
//        UiUtils.SnackbarText(message);

        UiUtils.makeText(getContext(),message);
    }

    @Override
    public void launchActivity(Intent intent) {
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
//        finish();
    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
        initRecycleView();
        this.adapter = adapter;
        handler.sendEmptyMessageDelayed(0, 1000);
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
        smartRefreshLayout.finishLoadmore();
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
                    .setLoadingTriggerThreshold(4)
                    .setLoadingListItemCreator(new CustomLoadingListItemCreator())
                    .build();
//            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    @Override
    public void onDestroy() {
        DefaultAdapter.releaseAllHolder(mRecyclerView);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        handler.removeMessages(0);
        handler = null;
        super.onDestroy();
        this.mRxPermissions = null;
        this.mPaginate = null;
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.requestMatchTimeChildList(false, matchId);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.requestMatchTimeChildList(true, matchId);
    }
}
