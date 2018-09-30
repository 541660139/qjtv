package com.lwd.qjtv.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.MyCommentListModule;

import com.lwd.qjtv.mvp.ui.activity.MyCommentListActivity;

@ActivityScope
@Component(modules = MyCommentListModule.class, dependencies = AppComponent.class)
public interface MyCommentListComponent {
    void inject(MyCommentListActivity activity);
}