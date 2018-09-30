package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.di.component.DaggerGuessChampionComponent;
import com.lwd.qjtv.di.module.GuessChampionModule;
import com.lwd.qjtv.mvp.contract.GuessChampionContract;
import com.lwd.qjtv.mvp.presenter.GuessChampionPresenter;
import com.lwd.qjtv.view.LoadingPageView;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/12.
 */

public class GuessChampionActivity extends BaseActivity<GuessChampionPresenter> implements GuessChampionContract.View, OnRefreshListener, OnLoadmoreListener {

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
    @BindView(R.id.activity_guess_champion_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;
    @BindView(R.id.activity_guess_champion_swiperefresh)
    SmartRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.activity_guess_champion_match_tv)
    TextView matchTv;
    private String id;
    private DefaultAdapter adapter;

    //每秒刷新数据
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (adapter != null)
                adapter.notifyDataSetChanged();
            handler.sendEmptyMessageDelayed(1, 1000);
        }
    };


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        this.mRxPermissions = new RxPermissions(this);
        DaggerGuessChampionComponent
                .builder()
                .appComponent(appComponent)
                .guessChampionModule(new GuessChampionModule(this))  //首字母改成小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_guess_champion;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!Preference.getBoolean(Constant.IS_LOGIN))
            finish();
    }

    @Override
    public void initData() {
        setTitle("猜冠军");
        loadingPageView.startLodingAnim();
        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
            mPresenter.requestGuessChampionList(id, true);//打开app时自动加载列表
        }
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
    }

    @Override
    public void showMessage(String message) {
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
        if (o instanceof String) {
            matchTv.setText((CharSequence) o);
            return;
        }
        loadingPageView.checkData(o);
    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
        this.adapter = adapter;
        mRecyclerView.setAdapter(adapter);
        handler.sendEmptyMessageDelayed(1, 1000);
        initRecycleView();
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


    @Override
    protected void onDestroy() {
        DefaultAdapter.releaseAllHolder(mRecyclerView);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
        this.mRxPermissions = null;
        this.mPaginate = null;
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.requestGuessChampionList(id, true);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.requestGuessChampionList(id, false);
    }
}
