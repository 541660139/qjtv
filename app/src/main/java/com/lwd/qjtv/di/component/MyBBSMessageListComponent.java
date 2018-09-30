package com.lwd.qjtv.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.MyBBSMessageListModule;

import com.lwd.qjtv.mvp.ui.activity.MyBBSMessageListActivity;

@ActivityScope
@Component(modules = MyBBSMessageListModule.class, dependencies = AppComponent.class)
public interface MyBBSMessageListComponent {
    void inject(MyBBSMessageListActivity activity);
}