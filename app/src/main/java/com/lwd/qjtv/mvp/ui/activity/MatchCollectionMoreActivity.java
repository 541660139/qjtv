package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;

import com.jess.arms.utils.UiUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.lwd.qjtv.di.component.DaggerMatchCollectionMoreComponent;
import com.lwd.qjtv.di.module.MatchCollectionMoreModule;
import com.lwd.qjtv.mvp.contract.MatchCollectionMoreContract;
import com.lwd.qjtv.mvp.model.entity.MatchCollectionMoreBean;
import com.lwd.qjtv.mvp.presenter.MatchCollectionMorePresenter;

import com.lwd.qjtv.R;
import com.lwd.qjtv.view.LoadingPageView;


import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MatchCollectionMoreActivity extends BaseActivity<MatchCollectionMorePresenter> implements MatchCollectionMoreContract.View, DefaultAdapter.OnRecyclerViewItemClickListener<MatchCollectionMoreBean.DataBean> {

    @BindView(R.id.swipeRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private String id;

    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMatchCollectionMoreComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .matchCollectionMoreModule(new MatchCollectionMoreModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_match_collection_more; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData() {
        setTitle("赛事合集");
        loadingPageView.startLodingAnim();
        mPresenter.getMatchCollectionMoreList(true);
        initListener();
    }

    private void initListener() {
        loadingPageView.setClickReload(() -> mPresenter.getMatchCollectionMoreList(true));
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
        loadingPageView.checkData(o);
    }


    @Override
    public void startLoadMore() {

    }

    @Override
    public void endLoadMore() {

    }

    @Override
    public void setAdapter(DefaultAdapter defaultAdapter) {
        UiUtils.configRecycleView(recyclerView, new GridLayoutManager(this, 2));
        recyclerView.setAdapter(defaultAdapter);
        defaultAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, int viewType, MatchCollectionMoreBean.DataBean data, int position) {
        Intent intent = new Intent(this, MatchCollectionListActivity.class);
        intent.putExtra("id", data.getBigMatch_id());
        intent.putExtra("name", data.getName());
        intent.putExtra("pic", data.getPic_h());
        startActivity(intent);
    }
}
