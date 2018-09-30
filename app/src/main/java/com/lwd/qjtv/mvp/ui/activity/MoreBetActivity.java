package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.usher.greendao_demo.greendao.gen.BetModelBeanDao;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.di.component.DaggerMoreBetComponent;
import com.lwd.qjtv.di.module.MoreBetModule;
import com.lwd.qjtv.mvp.contract.MoreBetContract;
import com.lwd.qjtv.mvp.model.entity.BetModelBean;
import com.lwd.qjtv.mvp.presenter.MoreBetPresenter;
import com.lwd.qjtv.mvp.ui.adapter.MoreBetAdapter;
import com.lwd.qjtv.view.LoadingPageView;
import com.lwd.qjtv.view.MoreBetDialog;
import com.lwd.qjtv.view.MoreBetDialog.BetSuccess;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/13.
 */

public class MoreBetActivity extends BaseActivity<MoreBetPresenter> implements MoreBetContract.View, SwipeRefreshLayout.OnRefreshListener, MoreBetAdapter.MoreBetChooseCallback, BetSuccess {


    private RxPermissions mRxPermissions;
    @BindView(R.id.activity_more_guess_recyclerview)
    SwipeMenuRecyclerView mRecyclerView;
    @BindView(R.id.activity_more_guess_match_tv)
    TextView matchTv;
    @BindView(R.id.activity_more_peilv_tv)
    TextView peilvTv;
    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;
    @BindView(R.id.activity_more_bet_add_ll)
    RelativeLayout addLL;
    private String manyJc;
    private float peilv;
    private BetModelBeanDao betModelBeanDao;
    private BetModelBeanDao thirdModelBeanDao;
    private List<BetModelBean> betList = new ArrayList<>();
    private DefaultAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 每秒刷新多选列表状态
            if (adapter != null)
                adapter.notifyDataSetChanged();
            handler.sendEmptyMessageDelayed(0, 1000);
        }
    };

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        this.mRxPermissions = new RxPermissions(this);
        DaggerMoreBetComponent
                .builder()
                .appComponent(appComponent)
                .moreBetModule(new MoreBetModule(this))  //首字母改成小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_more_bet;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!Preference.getBoolean(Constant.IS_LOGIN))
            finish();
    }

    @Override
    public void initData() {
        setTitle("多选");
        showTip();
        betModelBeanDao = WEApplication.getWinDao();
        thirdModelBeanDao = WEApplication.getThirdModelBeanDao();
        initDao();
        loadingPageView.startLodingAnim();
        mPresenter.requestMoreBetList(true);//打开app时自动加载列表
        initlistener();
    }

    private void showTip() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("温馨提示").setMessage("请在所选的比赛开始前，结算相应投注，否则投注失败!").setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("确定", (dialog, which) -> dialog.dismiss()).create().show();
    }

    private void initDao() {
        List<BetModelBean> betModelBeen = betModelBeanDao.loadAll();
        List<BetModelBean> betModelBeen1 = thirdModelBeanDao.loadAll();
        betList.addAll(betModelBeen);
        betList.addAll(betModelBeen1);
        initManyJc();
    }


    private void initlistener() {
        addLL.setOnClickListener(view -> {
            initManyJc();
            if (betList.size() == 0) {
                showMessage("当前没有投注比赛");
                return;
            }
            MoreBetDialog moreBetDialog = new MoreBetDialog(this);
            moreBetDialog.setData("title", "", String.format("%.2f", peilv), this);
            moreBetDialog.setManyJC(manyJc);
            moreBetDialog.show();
        });
    }

    @Override
    public void onRefresh() {
        mPresenter.requestMoreBetList(true);
    }

    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        //设置侧滑菜单
        mRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        // 设置监听器。
        mRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
        UiUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(this));
    }

    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = (swipeLeftMenu, swipeRightMenu, viewType) -> {
        // 注意：哪边不想要菜单，那么不要添加即可。
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        int width = UiUtils.dip2px(MoreBetActivity.this, 70);
        {
            SwipeMenuItem deleteItem = new SwipeMenuItem(MoreBetActivity.this)
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
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                // Item被侧滑删除时，删除数据，并更新adapter。
                //获取该item的数据对象
                BetModelBean betModelBean = betList.get(adapterPosition);
                //判断是属于哪个下注数据库的 1 为 胜负竞猜 ; 2 为 局数竞猜
                if (betModelBean.getJc_type().equals("1"))
                    betModelBeanDao.delete(betModelBean);
                if (betModelBean.getJc_type().equals("2")) {
                    thirdModelBeanDao.delete(betModelBean);
                }
                List<BetModelBean> betModelBeen2 = betModelBeanDao.loadAll();
                List<BetModelBean> betModelBeen1 = thirdModelBeanDao.loadAll();
                //本地保存移除
                betList.remove(adapterPosition);
                //刷新列表
                mPresenter.requestMoreBetList(true);
                //刷新竞猜赔率
                initManyJc();
            }
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
        loadingPageView.checkData(o);
    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
        this.adapter = adapter;
        handler.sendEmptyMessage(1);
        MoreBetAdapter moreBetAdapter = (MoreBetAdapter) adapter;
        mRecyclerView.setAdapter(adapter);
        moreBetAdapter.setChooseCallback(this);
        initRecycleView();
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

    private void initManyJc() {
        StringBuilder sb = new StringBuilder();
        peilv = 1;
        for (int i = 0; i < betList.size(); i++) {
            BetModelBean moreBetBean = betList.get(i);
            if (i == betList.size() - 1)
                sb.append(moreBetBean.toString());
            else
                sb.append(moreBetBean.toString() + ",");
            peilv *= Float.parseFloat(moreBetBean.getJc_peilv());
        }
        manyJc = "{\"many_jc\":[" + sb.toString() + "]}";
        peilvTv.setText("赔率：" + String.format("%.2f", peilv));
    }

    @Override
    public void chooseCallback(BetModelBean moreBetBean, boolean isChoose) {
        if (isChoose)
            betList.add(moreBetBean);
        else
            betList.remove(moreBetBean);
        initManyJc();
    }

    @Override
    public void betSuccess() {
        betModelBeanDao.deleteAll();
        thirdModelBeanDao.deleteAll();
        finish();
    }
}
