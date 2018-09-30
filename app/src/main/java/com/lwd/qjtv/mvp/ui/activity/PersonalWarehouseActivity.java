package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.di.component.DaggerPersonalWarehouseComponent;
import com.lwd.qjtv.di.module.PersonalWarehouseModule;
import com.lwd.qjtv.mvp.contract.PersonalWarehouseContract;
import com.lwd.qjtv.mvp.model.entity.PersonalWareHouseBean;
import com.lwd.qjtv.mvp.presenter.PersonalWarehousePresenter;
import com.lwd.qjtv.mvp.ui.adapter.PersonalWarehouseAdapter;
import com.lwd.qjtv.mvp.ui.holder.PersonalWarehouseItemHolder;
import com.lwd.qjtv.view.LoadingPageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import timber.log.Timber;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/6.
 */

public class PersonalWarehouseActivity extends BaseActivity<PersonalWarehousePresenter> implements PersonalWarehouseContract.View, SwipeRefreshLayout.OnRefreshListener, DefaultAdapter.OnRecyclerViewItemClickListener<PersonalWareHouseBean.DataBean>, PersonalWarehouseItemHolder.ChooseGiftCallback {

    @BindView(R.id.activity_personal_warehouse_rete_tv)
    TextView rateTv;
    @BindView(R.id.activity_personal_warehouse_change_tv)
    TextView changeTv;
    @BindView(R.id.activity_personal_warehouse_select_all_tv)
    TextView selectAllTv;
    @BindView(R.id.activity_personal_warehouse_back_tv)
    TextView backRl;
    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
    @BindView(R.id.activity_personal_warehouse_recyclerview)
    RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PersonalWarehouseAdapter personalAdapter;
    private boolean isSelectAll;

    private List<String> idList = new ArrayList<>();
    private List<String> numList = new ArrayList<>();
    private String idStr = "";
    private String numStr = "";

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        this.mRxPermissions = new RxPermissions(this);
        DaggerPersonalWarehouseComponent
                .builder()
                .appComponent(appComponent)
                .personalWarehouseModule(new PersonalWarehouseModule(this))  //首字母改成小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_personal_warehouse;
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
        rateTv.setText(Html.fromHtml("<p>\n" +
                "\t<br />\n" +
                "</p>\n" +
                "<pre><span><span style=\"color:#E53333;\"><span style=\"color:#999999;\">温馨提示：</span>1<span style=\"color:#999999;\">爆米花 兑换<span style=\"color:#E53333;\"><span style=\"color:#E53333;\"></span>100</span>积分</span></span></span></pre>\n" +
                "<p>\n" +
                "\t<br />\n" +
                "</p>"));
        mPresenter.requestPersonalWarehouseList(true);//打开app时自动加载列表
        initListener();
    }

    private void initListener() {
        changeTv.setOnClickListener(view -> {
            if (SaveUserInfo.getUserType()) {
                if (idList.size() == 0)
                    showMessage("当前还未选择兑换礼物");
                else
                    changeListToStr();
            }else {
                UiUtils.SnackbarText("请充值成为会员才能礼物兑换");
            }
        });
        backRl.setOnClickListener(v -> finish());
        selectAllTv.setOnClickListener(v -> {
            isSelectAll = !isSelectAll;
            if (personalAdapter != null)
                personalAdapter.setSelectAll(isSelectAll);
            selectAllTv.setText(isSelectAll ? "取消全选" : "全选");
        });
    }

    private void changeListToStr() {
        for (int i = 0; i < (idList == null ? 0 : idList.size()); i++) {
            String id = idList.get(i);
            String num = numList.get(i);
            if (i == idList.size() - 1) {
                idStr += id;
                numStr += num;
            } else {
                idStr += id + ",";
                numStr += num + ",";
            }
        }
        mPresenter.exchangeGift(idStr, numStr);
        idList.clear();
        numList.clear();
        idStr = "";
        numStr = "";
    }

    @Override
    public void onRefresh() {
        mPresenter.requestPersonalWarehouseList(true);
    }

    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
//        mSwipeRefreshLayout.setOnRefreshListener(this);
        UiUtils.configRecycleView(mRecyclerView, new GridLayoutManager(this, 3));
    }


    @Override
    public void showLoading() {
//        Timber.tag(TAG).w("showLoading");
//        Observable.just(1)
//                .observeOn(AndroidSchedulers.mainThread())
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
//        adapter.setOnItemClickListener(this);
        personalAdapter = (PersonalWarehouseAdapter) adapter;
        personalAdapter.setChooseGiftCallBack(this);
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
    public void onItemClick(View view, int viewType, PersonalWareHouseBean.DataBean data, int position) {
        String id = data.getGift_id();
        String num = data.getCount();

//        if (!idList.contains(id + "")) {
//            idList.add(id + "");
//            numList.add(num + "");
//        } else {
//            int index = 0;
//            for (int i = 0; i < (idList == null ? 0 : idList.size()); i++) {
//                if (idList.get(i).equals(id + "")) {
//                    index = i;
//                }
//            }
//            idList.remove(index);
//            numList.remove(index);
//        }
    }

    @Override
    public void chooseGift(int id, int num, boolean isChoose) {
        if (isChoose) {
            idList.add(id + "");
            numList.add(num + "");
        } else {
            int index = 0;
            for (int i = 0; i < (idList == null ? 0 : idList.size()); i++) {
                if (idList.get(i).equals(id + "")) {
                    index = i;
                }
            }
            if (idList.size() != 0) {
                idList.remove(index);
                numList.remove(index);
            }
        }
//        if (!idList.contains(id + "")) {
//
//        } else {
//            int index = 0;
//            for (int i = 0; i < (idList == null ? 0 : idList.size()); i++) {
//                if (idList.get(i).equals(id + "")) {
//                    index = i;
//                }
//            }
//            idList.remove(index);
//            numList.remove(index);
//        }
    }
}
