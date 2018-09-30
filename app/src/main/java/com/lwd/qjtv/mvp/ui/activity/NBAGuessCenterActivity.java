package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.lwd.qjtv.di.component.DaggerNBAGuessCenterComponent;
import com.lwd.qjtv.di.module.NBAGuessCenterModule;
import com.lwd.qjtv.mvp.contract.NBAGuessCenterContract;
import com.lwd.qjtv.mvp.model.entity.BetModelBean;
import com.lwd.qjtv.mvp.model.entity.GuessCenterBean;
import com.lwd.qjtv.mvp.presenter.NBAGuessCenterPresenter;
import com.lwd.qjtv.mvp.ui.adapter.GuessCenterAdapter;
import com.lwd.qjtv.mvp.ui.holder.GuessCenterItemHolder;
import com.lwd.qjtv.view.LoadingPageView;

import java.util.List;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/17.
 */

public class NBAGuessCenterActivity extends BaseActivity<NBAGuessCenterPresenter> implements NBAGuessCenterContract.View, OnRefreshListener, OnLoadmoreListener, GuessCenterItemHolder.GuessClickCallBack, DefaultAdapter.OnRecyclerViewItemClickListener<GuessCenterBean.DataBean> {

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.activity_guess_center_rank_ll)
    LinearLayout rankLl;
    @BindView(R.id.activity_guess_center_my_guess_ll)
    LinearLayout guessLl;
    @BindView(R.id.activity_guess_center_fuhaobang_ll)
    LinearLayout fhbLl;
    @BindView(R.id.activity_guess_center_more_guess_tv)
    TextView moreGuessTv;

    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;
    @BindView(R.id.activity_guess_center_red_point_iv)
    ImageView redPointIv;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        this.mRxPermissions = new RxPermissions(this);
        DaggerNBAGuessCenterComponent
                .builder()
                .appComponent(appComponent)
                .nBAGuessCenterModule(new NBAGuessCenterModule(this))  //首字母改成小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_guess_center;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!Preference.getBoolean(Constant.IS_LOGIN))
            finish();
        //读取局数竞猜多选本地数量
        List<BetModelBean> betModelBeen = WEApplication.getThirdModelBeanDao().loadAll();
        //读取胜负竞猜多选本地数量
        List<BetModelBean> betModelBeen1 = WEApplication.getWinDao().loadAll();
        betModelBeen.addAll(betModelBeen1);
        if (betModelBeen.size() == 0)
            redPointIv.setVisibility(View.GONE);
        else
            redPointIv.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        loadingPageView.startLodingAnim();
        setTitle("NBA竟猜中心");
        initRecyclerview();
        initListener();
        mPresenter.requestGuessCenterList(true);//打开app时自动加载列表
    }

    private void initRecyclerview() {
        View view = findViewById(R.id.recyclerView_layout);
        mSwipeRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.zq_refreshlayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    private void initListener() {
        loadingPageView.setClickReload(() -> mPresenter.requestGuessCenterList(true));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setOnLoadmoreListener(this);
        fhbLl.setOnClickListener(v -> {
            Intent intent = new Intent(WEApplication.getContext(), RankActivity.class);
            intent.putExtra(Constant.IS_GUESS_RANK, false);
            intent.putExtra(Constant.IS_NBA, true);
            startActivity(intent);
        });
        rankLl.setOnClickListener(view -> {
            Intent intent = new Intent(WEApplication.getContext(), RankActivity.class);
            intent.putExtra(Constant.IS_GUESS_RANK, true);
            intent.putExtra(Constant.IS_NBA, true);
            startActivity(intent);
        });
        guessLl.setOnClickListener(view -> startActivity(new Intent(WEApplication.getContext(), MyGuessActivity.class)));
        moreGuessTv.setOnClickListener(view -> startActivity(new Intent(WEApplication.getContext(), MoreBetActivity.class)));
    }


    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
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
    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        GuessCenterAdapter guessCenterAdapter = (GuessCenterAdapter) adapter;
        guessCenterAdapter.setGuessCallBack(this);
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

    //点击竞猜回调
    @Override
    public void clickGuess(GuessCenterBean.DataBean data, int position) {
        Intent intent = new Intent(this, NBAGuessDetailsActivity.class);
        intent.putExtra("id", data.getMatch_id());
        startActivity(intent);
    }

    @Override
    public void onItemClick(View view, int viewType, GuessCenterBean.DataBean data, int position) {

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.requestGuessCenterList(false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.requestGuessCenterList(true);
    }
}
