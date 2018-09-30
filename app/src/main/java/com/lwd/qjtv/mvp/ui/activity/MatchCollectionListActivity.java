package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;

import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.app.utils.GlideConfigGlobal;
import com.lwd.qjtv.di.component.DaggerMatchCollectionListComponent;
import com.lwd.qjtv.di.module.MatchCollectionListModule;
import com.lwd.qjtv.mvp.contract.MatchCollectionListContract;
import com.lwd.qjtv.mvp.model.entity.MatchCollectionNewListBean;
import com.lwd.qjtv.mvp.presenter.MatchCollectionListPresenter;

import com.lwd.qjtv.R;


import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MatchCollectionListActivity extends BaseActivity<MatchCollectionListPresenter> implements MatchCollectionListContract.View {

    @BindView(R.id.activity_match_collection_list_iv)
    ImageView listIv;
    @BindView(R.id.activity_match_collection_list_rv)
    RecyclerView listRv;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMatchCollectionListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .matchCollectionListModule(new MatchCollectionListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_match_collection_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData() {
        String id = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        String pic = getIntent().getStringExtra("pic");
        Glide.with(this).load(pic).into(listIv);
//        GlideConfigGlobal.loadImageView(pic, listIv);
        setTitle(name == null ? "" : name);
        mPresenter.getMatchCollectionList(true, id);
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
        if (o instanceof MatchCollectionNewListBean.DataBeanX) {
            MatchCollectionNewListBean.DataBeanX dataBeanX = (MatchCollectionNewListBean.DataBeanX) o;
            GlideConfigGlobal.loadImageView(dataBeanX.getPic_h(), listIv);
        }
    }


    @Override
    public void startLoadMore() {

    }

    @Override
    public void endLoadMore() {

    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
        UiUtils.configRecycleView(listRv, new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listRv.setAdapter(adapter);
    }
}
