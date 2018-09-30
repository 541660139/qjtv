package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.di.component.DaggerWatchRecordeComponent;
import com.lwd.qjtv.di.module.WatchRecordeModule;
import com.lwd.qjtv.mvp.contract.WatchRecordeContract;
import com.lwd.qjtv.mvp.model.entity.WatchHistoryBean;
import com.lwd.qjtv.mvp.presenter.WatchRecordePresenter;
import com.lwd.qjtv.mvp.ui.adapter.WatchRecordeAdapter;
import com.lwd.qjtv.view.LoadingPageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class WatchRecordeActivity extends BaseActivity<WatchRecordePresenter> implements WatchRecordeContract.View, OnRefreshListener, WatchRecordeAdapter.ChooseCallback, DefaultAdapter.OnRecyclerViewItemClickListener<WatchHistoryBean> {
    @BindView(R.id.activity_watch_history_back_tv)
    TextView backTv;
    @BindView(R.id.activity_watch_history_edit_tv)
    TextView editTv;
    @BindView(R.id.activity_watch_history_delete_tv)
    TextView deleteTv;
    @BindView(R.id.activity_watch_history_select_all_tv)
    TextView allTv;
    @BindView(R.id.activity_watch_history_edit_ll)
    LinearLayout editLl;

    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSwipeRefreshLayout;
    private boolean isEdit = true;
    private WatchRecordeAdapter watchRecordeAdapter;
    private List<WatchHistoryBean> mlist;
    private boolean isCheck;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        this.mRxPermissions = new RxPermissions(this);
        DaggerWatchRecordeComponent
                .builder()
                .appComponent(appComponent)
                .watchRecordeModule(new WatchRecordeModule(this))  //首字母改成小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
//        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_watch_history, null);
//        mSwipeRefreshLayout = (VerticalSwipeRefreshLayout) view.findViewById(R.id.zq_refreshlayout);
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        return R.layout.activity_watch_history;
    }


    @Override
    public void initData() {
        if (!SaveUserInfo.getLogin()) {
            startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
            finish();
        }
        loadingPageView.startLodingAnim();
        findRefreshView();
        mSwipeRefreshLayout.setEnableLoadmore(false);
        mSwipeRefreshLayout.setEnableRefresh(false);
        initRecycleView();
        List<WatchHistoryBean> watchHistoryBeen = WEApplication.getWatchHistoryDao().loadAll();
        setData(watchHistoryBeen);
//        Collections.reverse(watchHistoryBeen);
        watchRecordeAdapter = new WatchRecordeAdapter(watchHistoryBeen);
        watchRecordeAdapter.setChooseCallback(this);
        watchRecordeAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(watchRecordeAdapter);
        initListener();
//        mPresenter.requestWatchRecordeList(true);//打开app时自动加载列表
    }

    private void findRefreshView() {
        View view = findViewById(R.id.recyclerView_layout);
        mSwipeRefreshLayout = view.findViewById(R.id.zq_refreshlayout);
        mRecyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }


    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        UiUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(this));
    }


    private void initListener() {
        loadingPageView.setClickReload(() -> onRefresh(mSwipeRefreshLayout));
        backTv.setOnClickListener(view -> finish());
        allTv.setOnClickListener(v -> {
            isCheck = !isCheck;
            watchRecordeAdapter.setCheck(isCheck);
        });
        deleteTv.setOnClickListener(v -> {
            if (isCheck)
                WEApplication.getWatchHistoryDao().deleteAll();
            else {
                for (int i = 0; i < mlist.size(); i++) {
                    WatchHistoryBean watchHistoryBean = mlist.get(i);
                    WEApplication.getWatchHistoryDao().delete(watchHistoryBean);
                }
            }
            initData();
            editTv.callOnClick();
        });
        editTv.setOnClickListener(view -> {
            if (mlist == null)
                mlist = new ArrayList<>();
            if (isEdit) {
                editLl.setVisibility(View.VISIBLE);
                editTv.setText("取消");
                watchRecordeAdapter.setIsVisible(true);
                watchRecordeAdapter.notifyDataSetChanged();
            } else {
                editLl.setVisibility(View.GONE);
                editTv.setText("编辑");
                watchRecordeAdapter.setIsVisible(false);
                watchRecordeAdapter.notifyDataSetChanged();
            }
            isEdit = !isEdit;
        });
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
    public void chooseItem(WatchHistoryBean bean, boolean isDelete) {
        if (isDelete)
            mlist.add(bean);
        else
            mlist.remove(bean);
    }

    @Override
    public void onItemClick(View view, int viewType, WatchHistoryBean data, int position) {
        if (!isEdit) {
            data.setIsSelect(!data.getIsSelect());
        } else {
            Intent intent = null;
//            if (data.getIsTeach()) {
//                intent = new Intent(this, LearnBallDetailsActivity.class);
//                intent.putExtra("starId", data.getStarId());
//                intent.putExtra("videoId", data.getId() + "");
//                intent.putExtra("v_type", data.getType());
//                intent.putExtra("pic", data.getPic());
//                intent.putExtra("time", data.getIntTime());
//            } else {
            intent = new Intent(this, VideoDetailsActivity.class);
            intent.putExtra("pic", data.getPic());
            intent.putExtra("id", data.getId() + "");
            intent.putExtra("type", data.getType());
            intent.putExtra("time", data.getIntTime());
            intent.putExtra("title", data.getTitle());

//            }
            startActivity(intent);
        }
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.requestWatchRecordeList(true);
    }
}
