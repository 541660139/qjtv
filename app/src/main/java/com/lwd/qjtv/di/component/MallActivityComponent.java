package com.lwd.qjtv.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.MallActivityModule;

import com.lwd.qjtv.mvp.ui.activity.MallActivity;

@ActivityScope
@Component(modules = MallActivityModule.class, dependencies = AppComponent.class)
public interface MallActivityComponent {
    void inject(MallActivity activity);
}