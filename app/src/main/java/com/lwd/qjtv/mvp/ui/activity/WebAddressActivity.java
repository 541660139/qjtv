package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.lwd.qjtv.R;
import com.lwd.qjtv.di.component.DaggerWebAddressComponent;
import com.lwd.qjtv.di.module.WebAddressModule;
import com.lwd.qjtv.mvp.contract.WebAddressContract;
import com.lwd.qjtv.mvp.model.entity.AddressBean;
import com.lwd.qjtv.mvp.presenter.WebAddressPresenter;
import com.lwd.qjtv.mvp.ui.adapter.WebAddressAdapter;
import com.lwd.qjtv.view.LoadingPageView;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/8/24.
 */

public class WebAddressActivity extends BaseActivity<WebAddressPresenter> implements WebAddressContract.View, SwipeRefreshLayout.OnRefreshListener, DefaultAdapter.OnRecyclerViewItemClickListener<AddressBean.DataBean>{
    private RxPermissions mRxPermissions;
    @BindView(R.id.receiver_address_lv)
    RecyclerView mRecyclerView;
    @BindView(R.id.receiver_address_add_tv)
    TextView addTv;
    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;
    //是否来自网页
    private boolean isWeb;
    private WebAddressAdapter addressManagerAdapter;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerWebAddressComponent
                .builder()
                .appComponent(appComponent)
                .webAddressModule(new WebAddressModule(this))  //首字母改成小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.web_activity_receiver_address_manage;
    }

    @Override
    public void initData() {
        loadingPageView.startLodingAnim();
        setTitle("地址管理");
        initListener();
        mPresenter.requestWebAddressList(true);//打开app时自动加载列表
    }

    private void initListener() {
        loadingPageView.setClickReload(() -> mPresenter.requestWebAddressList(true));
        addTv.setOnClickListener(view -> startActivity(new Intent(this, AddAddressActivity.class)));
    }


    @Override
    public void onRefresh() {
        mPresenter.requestWebAddressList(true);
    }

    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
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
        UiUtils.SnackbarText(message);
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
        addressManagerAdapter = (WebAddressAdapter) adapter;
        addressManagerAdapter.setOnItemClickListener(this);
    }

    /**
     * 开始加载更多
     */
    @Override
    public void startLoadMore() {
    }

    /**
     * 结束加载更多
     */
    @Override
    public void endLoadMore() {
    }

    @Override
    public RxPermissions getRxPermissions() {
        return null;
    }



    @Override
    protected void onDestroy() {
        DefaultAdapter.releaseAllHolder(mRecyclerView);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
    }

    @Override
    public void onItemClick(View view, int viewType, AddressBean.DataBean data, int position) {
        //设置返回值,将地址id返回给上层
        Intent intent = new Intent();
        intent.putExtra("addressId", data.getId());
        setResult(0x001, intent);
        finish();
    }



}
