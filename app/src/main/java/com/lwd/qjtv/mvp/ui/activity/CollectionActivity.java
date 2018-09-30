package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.di.component.DaggerCollectionComponent;
import com.lwd.qjtv.di.module.CollectionModule;
import com.lwd.qjtv.mvp.contract.CollectionContract;
import com.lwd.qjtv.mvp.model.entity.VideoCollectionBean;
import com.lwd.qjtv.mvp.presenter.CollectionPresenter;
import com.lwd.qjtv.mvp.ui.adapter.CollectionAdapter;
import com.lwd.qjtv.view.LoadingPageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/5.
 */

public class CollectionActivity extends BaseActivity<CollectionPresenter> implements CollectionContract.View, OnRefreshListener, OnLoadmoreListener, DefaultAdapter.OnRecyclerViewItemClickListener {

    //返回按钮
    @BindView(R.id.activity_collection_back_tv)
    TextView backTv;
    //编辑按钮
    @BindView(R.id.activity_collection_edit_tv)
    TextView editTv;
    //删除按钮
    @BindView(R.id.activity_collection_delete_tv)
    TextView deleteTv;
    //全选按钮
    @BindView(R.id.activity_collection_select_all_tv)
    TextView allSelectTv;
    //编辑底部视图
    @BindView(R.id.activity_collection_edit_ll)
    LinearLayout editLl;
    //收藏视频列表
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.zq_refreshlayout)
    SmartRefreshLayout mSwipeRefreshLayout;

    //是否处于编辑状态下
    private boolean isEdit = true;
    //收藏视频列表适配器
    private CollectionAdapter collectionAdapter;
    //是否全选
    private boolean isAllSelect;
    //删除id集合
    private List<String> deleteFid = new ArrayList<>();
    //全选集合
    private List<String> allFid = new ArrayList<>();
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;

    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String fid = "";
            //将删除id添加进集合
            for (int i = 0; i < deleteFid.size(); i++) {
                fid += deleteFid.get(i);
                if (i != deleteFid.size() - 1)
                    fid += ",";
            }

            mPresenter.deleteVideoCollection(fid);
        }
    };

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        this.mRxPermissions = new RxPermissions(this);
        DaggerCollectionComponent
                .builder()
                .appComponent(appComponent)
                .collectionModule(new CollectionModule(this))  //首字母改成小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_collection;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!Preference.getBoolean(Constant.IS_LOGIN))
            finish();
    }

    @Override
    public void initData() {
        loadingPageView.startLodingAnim();
        mPresenter.requestCollectionList(true);//打开app时自动加载列表
        initListener();
    }

    private void initListener() {
        loadingPageView.setClickReload(() -> mPresenter.requestCollectionList(true));
        backTv.setOnClickListener(view -> finish());
        deleteTv.setOnClickListener(view -> {
            handler.sendEmptyMessage(1);
            editTv.callOnClick();
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setOnLoadmoreListener(this);
        allSelectTv.setOnClickListener(view -> {
            //清空删除集合
            deleteFid.clear();
            //判断是否全选
            if (!isAllSelect)
                deleteFid.addAll(allFid);
            //通知适配器 全选
            collectionAdapter.setSelectAll();
            //改变全选状态
            isAllSelect = !isAllSelect;
            //刷新适配器
            collectionAdapter.notifyDataSetChanged();
        });
    }


    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        UiUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(WEApplication.getContext()));
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
        deleteFid.clear();
        loadingPageView.checkData(o);
        List<VideoCollectionBean.DataBean> list = (List<VideoCollectionBean.DataBean>) o;
        for (int i = 0; i < list.size(); i++) {
            allFid.add(list.get(i).getId());
        }
    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
        collectionAdapter = (CollectionAdapter) adapter;
        adapter.setOnItemClickListener(this);
        initEditListener();
        initRecycleView();
        mRecyclerView.setAdapter(adapter);
    }

    private void initEditListener() {
        editTv.setOnClickListener(view -> {
            //判断是否处于编辑状态下
            if (isEdit) {
                //编辑底部视图显示
                editLl.setVisibility(View.VISIBLE);
                editTv.setText("取消");
                //通知适配器显示复选框
                collectionAdapter.setIsVisible(true);
                //刷新适配器
                collectionAdapter.notifyDataSetChanged();
            } else {
                //编辑底部视图隐藏
                editLl.setVisibility(View.GONE);
                editTv.setText("编辑");
                //通知适配器隐藏复选框
                collectionAdapter.setIsVisible(false);
                //刷新适配器
                collectionAdapter.notifyDataSetChanged();
            }
            //改变编辑状态
            isEdit = !isEdit;
        });
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
        mSwipeRefreshLayout.finishLoadmore();
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
    public void onItemClick(View view, int viewType, Object data, int position) {
        VideoCollectionBean.DataBean dataBean = (VideoCollectionBean.DataBean) data;
        //判断是否处于编辑状态
        if (!isEdit) {
            //如果删除列表有此id，则移除
            if (deleteFid.contains(dataBean.getId() + "")) {
                deleteFid.remove(dataBean.getId() + "");
                //如果删除列表没有此id，则添加
                Log.d("deleteFid", "remove:" + dataBean.getId());
            } else {
                deleteFid.add(dataBean.getId() + "");
                Log.d("deleteFid", "add:" + dataBean.getId());
            }
            //改变item的选中状态
            dataBean.setCheck(!dataBean.isCheck());
            //通知适配器刷新
            collectionAdapter.notifyDataSetChanged();
        } else {
            //跳转视频详情页
            Intent intent = new Intent(WEApplication.getContext(), VideoDetailsActivity.class);
            intent.putExtra("pic", dataBean.getBitmap());
            intent.putExtra("id", dataBean.getVideo_id());
            intent.putExtra("type", dataBean.getAnalysis_type());
            intent.putExtra("title", dataBean.getVideo_title());
            startActivity(intent);
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.requestCollectionList(false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.requestCollectionList(true);
    }
}
