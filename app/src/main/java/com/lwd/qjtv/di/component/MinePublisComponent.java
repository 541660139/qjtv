package com.lwd.qjtv.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.MinePublisModule;

import com.jess.arms.di.scope.ActivityScope;
import com.lwd.qjtv.mvp.ui.activity.MinePublisActivity;

@ActivityScope
@Component(modules = MinePublisModule.class, dependencies = AppComponent.class)
public interface MinePublisComponent {
    void inject(MinePublisActivity activity);
}