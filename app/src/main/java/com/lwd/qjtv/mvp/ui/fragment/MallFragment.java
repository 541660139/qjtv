package com.lwd.qjtv.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.AdapterViewPager;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.di.module.MallModule;
import com.paginate.Paginate;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;

import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.di.component.DaggerMallComponent;

import com.lwd.qjtv.mvp.contract.MallContract;
import com.lwd.qjtv.mvp.presenter.MallPresenter;
import com.lwd.qjtv.mvp.ui.activity.LoginActivity;
import com.lwd.qjtv.mvp.ui.activity.MallSearchActivity;
import com.lwd.qjtv.mvp.ui.activity.MyOrderActivity;
import com.lwd.qjtv.mvp.ui.fragment.mall.MallChildFragment;
import com.lwd.qjtv.view.CustomLoadingListItemCreator;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class MallFragment extends BaseFragment<MallPresenter> implements IFragment, MallContract.View, SwipeRefreshLayout.OnRefreshListener {

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
    private RecyclerView mRecyclerView;
    private static MallFragment mallFragment;
    @BindView(R.id.fragment_mall_tablayout)
    TabLayout mTablayout;
    @BindView(R.id.fragment_mall_order_tv)
    TextView orderTv;
    @BindView(R.id.fragment_mall_search_iv)
    ImageView searchIv;
    @BindView(R.id.fragment_mall_viewpager)
    ViewPager mViewPager;
    private AdapterViewPager adapterViewPager;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private String[] titleList = {
            "价格由低到高",
            "价格由高到低",
            "销量优先"
    };
    private MallChildFragment oneFragment;
    private MallChildFragment twoFragment;
    private MallChildFragment threeFragment;

    public static MallFragment newInstance() {
        if (mallFragment == null)
            mallFragment = new MallFragment();
        return mallFragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerMallComponent
                .builder()
                .appComponent(appComponent)
                .mallModule(new MallModule(this))//请将MallModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_mall, container, false);
        View recyclerviewlayout = view.findViewById(R.id.recyclerView_layout);
        mRecyclerView = (RecyclerView) recyclerviewlayout.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void initData() {
        initTablayout();
        initFragment();
        initViewPager();
        initListener();
        mPresenter.requestMallList(true);//打开app时自动加载列表
    }

    private void initListener() {
        searchIv.setOnClickListener(view -> {
            if (!SaveUserInfo.getLogin()) {
                getContext().startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                return;
            }
            startActivity(new Intent(WEApplication.getContext(), MallSearchActivity.class));
        });
        orderTv.setOnClickListener(view -> {
            if (!SaveUserInfo.getLogin()) {
                getContext().startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                return;
            }
            startActivity(new Intent(WEApplication.getContext(), MyOrderActivity.class));
        });
    }

    private void initTablayout() {
        mTablayout.setTabTextColors(getResources().getColor(R.color.color666666), getResources().getColor(R.color.colorOrigin));
        mTablayout.setSelectedTabIndicatorHeight(0);
    }

    private void initViewPager() {
        adapterViewPager = new AdapterViewPager(getFragmentManager());
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(adapterViewPager);
        mTablayout.setupWithViewPager(mViewPager);
        adapterViewPager.bindData(fragmentList, titleList);
    }

    private void initFragment() {
        twoFragment = MallChildFragment.newInstance("2");
        fragmentList.add(twoFragment);
        oneFragment = MallChildFragment.newInstance("1");
        fragmentList.add(oneFragment);
        threeFragment = MallChildFragment.newInstance("3");
        fragmentList.add(threeFragment);
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void onRefresh() {
        mPresenter.requestMallList(true);
    }

    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//        UiUtils.configRecycleView(mRecyclerView, new_pic GridLayoutManager(WEApplication.getContext(), 2));
    }


    @Override
    public void showLoading() {
        Timber.tag(TAG).w("showLoading");
//        Observable.just(1)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(integer -> mSwipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void hideLoading() {
        Timber.tag(TAG).w("hideLoading");
//        mSwipeRefreshLayout.setRefreshing(false);
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
//        oneFragment.setAdapter(adapter);
//        twoFragment.setAdapter(adapter);
//        threeFragment.setAdapter(adapter);
//        mRecyclerView.setAdapter(adapter);
//        initRecycleView();
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
                    mPresenter.requestMallList(false);
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
    public void onDestroy() {
        DefaultAdapter.releaseAllHolder(mRecyclerView);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
        this.mRxPermissions = null;
        this.mPaginate = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }
}
