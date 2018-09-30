package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.di.component.DaggerCommentComponent;
import com.lwd.qjtv.di.module.CommentModule;
import com.lwd.qjtv.mvp.contract.CommentContract;
import com.lwd.qjtv.mvp.presenter.CommentPresenter;

import com.lwd.qjtv.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class CommentActivity extends BaseActivity<CommentPresenter> implements CommentContract.View {


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerCommentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .commentModule(new CommentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_comment; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData() {

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

    }


}
