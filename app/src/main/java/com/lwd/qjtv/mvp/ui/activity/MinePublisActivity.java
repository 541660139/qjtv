package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.di.component.DaggerMinePublisComponent;
import com.lwd.qjtv.di.module.MinePublisModule;
import com.lwd.qjtv.mvp.contract.MinePublisContract;
import com.lwd.qjtv.mvp.presenter.MinePublisPresenter;
import com.lwd.qjtv.mvp.ui.adapter.PublishAllAdapter;
import com.lwd.qjtv.view.LoadingPageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MinePublisActivity extends BaseActivity<MinePublisPresenter> implements MinePublisContract.View, OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.iv_fb_back)
    ImageView iv_fb_back;
    @BindView(R.id.tv_title_fb)
    TextView tv_title_fb;

    @BindView(R.id.receiver_fb)
    RecyclerView receiver_fb;


    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;

    @BindView(R.id.fragment_community_all_smartrefreshlayout)
    SmartRefreshLayout fragment_community_all_smartrefreshlayout;
    @BindView(R.id.tv_guanli)
    TextView tv_guanli;


    @BindView(R.id.checkbox)
    CheckBox checkbox;

    @BindView(R.id.rl_edittext_deleter)
    RelativeLayout rl_edittext_deleter;


    private String other_uid;
    private PublishAllAdapter fansOrFollowAdapter;


    private boolean isVisible = true;

    public static boolean isSelecter = true;


    private List<Boolean> ls = new ArrayList<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMinePublisComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .minePublisModule(new MinePublisModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_mine_publis;
    }

    @Override
    public void initData() {

        other_uid = getIntent().getStringExtra("other_uid");
        if (other_uid == null || TextUtils.isEmpty(other_uid) || SaveUserInfo.getUid().equals(other_uid)) {


            mPresenter.getPublish(true);
        } else {
            tv_guanli.setVisibility(View.GONE);
            rl_edittext_deleter.setVisibility(View.GONE);
            mPresenter.getOhterPublish(true, other_uid);
        }

        initList();

    }

    private void initList() {
        loadingPageView.setClickReload(new LoadingPageView.ClickReloadCallback() {
            @Override
            public void clickReload() {
                if (other_uid == null || TextUtils.isEmpty(other_uid) || SaveUserInfo.getUid().equals(other_uid)) {
                    mPresenter.getPublish(true);
                } else {
                    mPresenter.getOhterPublish(true, other_uid);
                }
            }
        });
        fragment_community_all_smartrefreshlayout.setOnRefreshListener(this);
        fragment_community_all_smartrefreshlayout.setEnableLoadmore(true);
        fragment_community_all_smartrefreshlayout.setOnLoadmoreListener(this);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        fragment_community_all_smartrefreshlayout.finishRefresh();

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
    public void setData(Object o) {
        loadingPageView.checkData(o);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void startLoadMore() {

    }

    @Override
    public void endLoadMore() {
        fragment_community_all_smartrefreshlayout.finishLoadmore();

    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
        fansOrFollowAdapter = (PublishAllAdapter) adapter;
        UiUtils.configRecycleView(receiver_fb, new LinearLayoutManager(this));
        receiver_fb.setAdapter(adapter);
//        fansOrFollowAdapter.setOnItemTvClickListener(new PublishAllAdapter.OnRecyclerViewCheckBoxItemClickListener() {
//            @Override
//            public void onItemTVClick() {
//
////                setAllSeleterTreuOrFlase(fansOrFollowAdapter);
//            }
//        });

    }

    @OnClick({R.id.iv_fb_back, R.id.tv_guanli, R.id.checkbox, R.id.tv_deleter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_fb_back:
                finish();
                break;
            case R.id.tv_guanli:
                if (fansOrFollowAdapter != null) {
                    fansOrFollowAdapter.setIsVisible(isVisible);
                    isVisible = !isVisible;
                }
                break;
            case R.id.checkbox:
                if (fansOrFollowAdapter != null) {
                    fansOrFollowAdapter.setAllSeleter(isSelecter);
                    isSelecter = !isSelecter;
                }
                break;
            case R.id.tv_deleter:
                if (fansOrFollowAdapter != null) {
                    String allSeleterId = fansOrFollowAdapter.getAllSeleterId();
                    mPresenter.deleterPublis(allSeleterId);
                }


                break;

            default:

        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        if (other_uid == null || TextUtils.isEmpty(other_uid) || SaveUserInfo.getUid().equals(other_uid)) {
            mPresenter.getPublish(true);
        } else {
            mPresenter.getOhterPublish(true, other_uid);
        }

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        if (other_uid == null || TextUtils.isEmpty(other_uid) || SaveUserInfo.getUid().equals(other_uid)) {
            mPresenter.getPublish(false);
        } else {
            mPresenter.getOhterPublish(false, other_uid);
        }

    }

    @Override
    public void setDeleterData() {
        if (other_uid == null || TextUtils.isEmpty(other_uid) || SaveUserInfo.getUid().equals(other_uid)) {
            mPresenter.getPublish(true);
        } else {
            mPresenter.getOhterPublish(true, other_uid);
        }
    }


//    void setAllSeleterTreuOrFlase(PublishAllAdapter publishAllAdapter) {
//        if (publishAllAdapter != null) {
//
//            new Handler().postDelayed(new Runnable() {
//                public void run() {
//                    List<PublisDataBean.DataBean> allDta = publishAllAdapter.getAllDta();
//                    for (int i = 0; i < allDta.size(); i++) {
//                        ls.add(allDta.get(i).isSeleter());
//
//                        Log.d("SeleterTreuOrFlase", "isSeleter():" + allDta.get(i).isSeleter());
//                    }
//
//                    if (ls.contains(false)) {
//                        checkbox.setChecked(false);
//                    } else {
//                        checkbox.setChecked(true);
//                    }
//                }
//            }, 100);
//
//        }
//    }
}
