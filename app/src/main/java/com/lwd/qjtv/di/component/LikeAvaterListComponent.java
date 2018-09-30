package com.lwd.qjtv.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.LikeAvaterListModule;

import com.lwd.qjtv.mvp.ui.activity.LikeAvaterListActivity;

@ActivityScope
@Component(modules = LikeAvaterListModule.class, dependencies = AppComponent.class)
public interface LikeAvaterListComponent {
    void inject(LikeAvaterListActivity activity);
}