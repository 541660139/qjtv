package com.lwd.qjtv.mvp.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.di.component.DaggerAddressManagerComponent;
import com.lwd.qjtv.di.module.AddressManagerModule;
import com.lwd.qjtv.mvp.contract.AddressManagerContract;
import com.lwd.qjtv.mvp.model.entity.AddressBean;
import com.lwd.qjtv.mvp.presenter.AddressManagerPresenter;
import com.lwd.qjtv.mvp.ui.adapter.AddressManagerAdapter;
import com.lwd.qjtv.view.LoadingPageView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.List;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/6.
 */

public class AddressManagerActivity extends BaseActivity<AddressManagerPresenter> implements AddressManagerContract.View, SwipeRefreshLayout.OnRefreshListener, DefaultAdapter.OnRecyclerViewItemClickListener<AddressBean.DataBean>, AddressManagerAdapter.ClickCallback<AddressBean.DataBean> {


    private RxPermissions mRxPermissions;
    @BindView(R.id.receiver_address_lv)
    SwipeMenuRecyclerView mRecyclerView;
    @BindView(R.id.receiver_address_add_tv)
    TextView addTv;
    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;
    //是否来自网页
    private boolean isWeb;
    private AddressManagerAdapter addressManagerAdapter;
    private List<AddressBean.DataBean> dataBeanList;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        this.mRxPermissions = new RxPermissions(this);
        DaggerAddressManagerComponent
                .builder()
                .appComponent(appComponent)
                .addressManagerModule(new AddressManagerModule(this))  //首字母改成小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_receiver_address_manage;
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
        if (!Preference.getBoolean(Constant.IS_LOGIN))
            finish();
    }

    @Override
    public void initData() {
        isWeb = getIntent().getBooleanExtra("isWeb", false);
        loadingPageView.startLodingAnim();
        setTitle("地址管理");
        initListener();
        mPresenter.requestAddressManagerList(true);//打开app时自动加载列表
    }

    private void initListener() {
        loadingPageView.setClickReload(() -> mPresenter.requestAddressManagerList(true));
        addTv.setOnClickListener(view -> startActivity(new Intent(this, AddAddressActivity.class)));
    }

    @Override
    public void onRefresh() {
        mPresenter.requestAddressManagerList(true);
    }

    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        mRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        // 设置监听器。
        mRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
        UiUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(this));
    }

    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = (swipeLeftMenu, swipeRightMenu, viewType) -> {
        // 注意：哪边不想要菜单，那么不要添加即可。
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        int width = UiUtils.dip2px(AddressManagerActivity.this, 70);
        {
            SwipeMenuItem deleteItem = new SwipeMenuItem(AddressManagerActivity.this)
                    .setBackground(R.color.colorOrigin)
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = menuBridge -> {
        menuBridge.closeMenu();

        int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
        int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
        int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

        if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
            // Item被侧滑删除时，删除数据，并更新adapter。
            deleteAddress(adapterPosition);
        }
    };


    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showMessage(String message) {
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
        if (o instanceof List)
            dataBeanList = (List<AddressBean.DataBean>) o;
        loadingPageView.checkData(o);
    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
        initRecycleView();
        mRecyclerView.setAdapter(adapter);
        addressManagerAdapter = (AddressManagerAdapter) adapter;
        addressManagerAdapter.setClickCallback(this);
        if (isWeb) {
            ((AddressManagerAdapter) adapter).setIsWeb();
            adapter.setOnItemClickListener(this);
        }
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
        return mRxPermissions;
    }

    @Override
    protected void onDestroy() {
        DefaultAdapter.releaseAllHolder(mRecyclerView);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
        this.mRxPermissions = null;
    }

    @Override
    public void onItemClick(View view, int viewType, AddressBean.DataBean data, int position) {
        //设置返回值,将地址id返回给上层
        Intent intent = new Intent();
        intent.putExtra("addressId", data.getId());
        setResult(0x001, intent);
        finish();
    }

    @Override
    public void click(AddressBean.DataBean data, int position) {
        //设置返回值,将地址id返回给上层
        Intent intent = new Intent();
        intent.putExtra("addressId", data.getId());
        setResult(0x001, intent);
        finish();
    }

    private void deleteAddress(int postition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddressManagerActivity.this);
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("提示");
        alertDialog.setMessage("您要删除该地址吗？");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", (dialog, which) -> {
            mPresenter.deleteAddress(dataBeanList.get(postition).getId() + "");
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", (dialog, which) -> {
            dialog.dismiss();
        });
        alertDialog.show();

    }
}
