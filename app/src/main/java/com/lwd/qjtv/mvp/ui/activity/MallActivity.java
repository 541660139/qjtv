package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.AdapterViewPager;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;

import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.di.component.DaggerMallActivityComponent;
import com.lwd.qjtv.di.module.MallActivityModule;
import com.lwd.qjtv.mvp.contract.MallActivityContract;
import com.lwd.qjtv.mvp.presenter.MallActivityPresenter;

import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.ui.fragment.MallFragment;
import com.lwd.qjtv.mvp.ui.fragment.mall.MallChildFragment;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MallActivity extends BaseActivity<MallActivityPresenter> implements MallActivityContract.View {
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

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMallActivityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mallActivityModule(new MallActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_mall; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData() {
        initTablayout();
        initFragment();
        initViewPager();
        initListener();
//        mPresenter.requestMallList(true);//打开app时自动加载列表
    }

    private void initListener() {
        searchIv.setOnClickListener(view -> {
            if (!SaveUserInfo.getLogin()) {
                startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                return;
            }
            startActivity(new Intent(WEApplication.getContext(), MallSearchActivity.class));
        });
        orderTv.setOnClickListener(view -> {
            if (!SaveUserInfo.getLogin()) {
                startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
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
        adapterViewPager = new AdapterViewPager(getSupportFragmentManager());
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
    public void startLoadMore() {

    }

    @Override
    public void endLoadMore() {

    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {

    }
}
