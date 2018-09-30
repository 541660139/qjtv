package com.lwd.qjtv.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.MyLikeListModule;

import com.lwd.qjtv.mvp.ui.activity.MyLikeListActivity;

@ActivityScope
@Component(modules = MyLikeListModule.class, dependencies = AppComponent.class)
public interface MyLikeListComponent {
    void inject(MyLikeListActivity activity);
}