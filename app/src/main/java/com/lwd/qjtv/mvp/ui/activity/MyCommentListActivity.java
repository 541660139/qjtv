package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Contact;
import com.lwd.qjtv.di.component.DaggerMyCommentListComponent;
import com.lwd.qjtv.di.module.MyCommentListModule;
import com.lwd.qjtv.mvp.contract.MyCommentListContract;
import com.lwd.qjtv.mvp.model.entity.MyCommentListBean;
import com.lwd.qjtv.mvp.presenter.MyCommentListPresenter;

import java.util.HashMap;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MyCommentListActivity extends BaseActivity<MyCommentListPresenter> implements MyCommentListContract.View, OnLoadmoreListener, OnRefreshListener {

    @BindView(R.id.activity_my_comment_list_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.activity_my_comment_list_smartrefreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    private Contact contact;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMyCommentListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myCommentListModule(new MyCommentListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_my_comment_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData() {
        setTitle("我的评论");
        initListener();
        mPresenter.getMyCommentList(true);
    }

    private void initListener() {
        smartRefreshLayout.setOnLoadmoreListener(this);
        smartRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        smartRefreshLayout.finishRefresh();
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
        smartRefreshLayout.finishLoadmore();
    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
        UiUtils.configRecycleView(recyclerView, new LinearLayoutManager(this));
        Log.d(TAG, "setAdapter:  ");

        adapter.setOnItemClickListener((view, viewType, data, position) -> {
            Log.d(TAG, "setAdapter: " + "startActivity1");
            MyCommentListBean.DataBean dataBean = (MyCommentListBean.DataBean) data;
            HashMap<String, String> map = new HashMap<>();
            map.put("card_id", dataBean.getCard_id());
            map.put("p", "1");
            if (contact == null)
                contact = new Contact(this);
            String webUrl = contact.getWebUrl(Constant.BBS_CARD_INFO, map);
            Intent intent = new Intent(MyCommentListActivity.this, WebNewActivity.class);
            intent.putExtra("url", webUrl);
            intent.putExtra("is_bbs_details", true);
            Log.d(TAG, "setAdapter: ItemClick ");
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.getMyCommentList(false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.getMyCommentList(true);
    }
}
