package com.lwd.qjtv.mvp.ui.fragment.star;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.di.component.DaggerWonderfulVideoComponent;
import com.lwd.qjtv.di.module.WonderfulVideoModule;
import com.lwd.qjtv.mvp.contract.WonderfulVideoContract;
import com.lwd.qjtv.mvp.model.entity.RelatedVideoBean;
import com.lwd.qjtv.mvp.presenter.WonderfulVideoPresenter;
import com.lwd.qjtv.mvp.ui.activity.VideoDetailsActivity;
import com.lwd.qjtv.view.CustomLoadingListItemCreator;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/5.
 */

public class WonderfulVideoFragment extends BaseFragment<WonderfulVideoPresenter> implements WonderfulVideoContract.View, SwipeRefreshLayout.OnRefreshListener ,DefaultAdapter.OnRecyclerViewItemClickListener<RelatedVideoBean.DataBean>{

    private static String starId;
    private static String videoId;
    private static String v_type;
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
    @BindView(R.id.fragment_wonderful_video_recyclerview)
    RecyclerView mRecyclerView;
    //    private SwipeRefreshLayout mSwipeRefreshLayout;
    private static String op = "wonderfulVideo";


    public static WonderfulVideoFragment newInstance() {
        WonderfulVideoFragment fragment = new WonderfulVideoFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerWonderfulVideoComponent
                .builder()
                .appComponent(appComponent)
                .wonderfulVideoModule(new WonderfulVideoModule(this))//请将WonderfulVideoModule()第一个首字母改为小写
                .build()
                .inject(this);
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_wonderful_video, container, false);
//        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.zq_refreshlayout);
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void initData() {
        getParseArguments();
        mPresenter.requestWonderfulVideoList(videoId, v_type, starId, op, true);//打开app时自动加载列表
    }

    private void getParseArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            videoId = arguments.getString("videoId");
            v_type = arguments.getString("v_type");
            op = arguments.getString("op");
            starId = arguments.getString("starId");
        }
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void onRefresh() {
//        mPresenter.requestWonderfulVideoList(true);
    }

    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
//        mSwipeRefreshLayout.setOnRefreshListener(this);
        UiUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(WEApplication.getContext()));
    }


    @Override
    public void showLoading() {
        Timber.tag(TAG).w("showLoading");
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread());
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
        adapter.setOnItemClickListener(this);
        initRecycleView();
        initPaginate();
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
                    mPresenter.requestWonderfulVideoList(videoId, v_type, starId, op, false);
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
    public void onItemClick(View view, int viewType, RelatedVideoBean.DataBean data, int position) {
        RelatedVideoBean.DataBean dataBean = (RelatedVideoBean.DataBean) data;
        Intent intent = new Intent(getContext(), VideoDetailsActivity.class);
        intent.putExtra("pic",dataBean.getPic_h());
        intent.putExtra("type", dataBean.getV_type());
        intent.putExtra("id", dataBean.getId());
        startActivity(intent);
    }
}
