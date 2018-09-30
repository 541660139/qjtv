package com.lwd.qjtv.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.MyCommunityModule;

import com.lwd.qjtv.mvp.ui.activity.MyCommunityActivity;

@ActivityScope
@Component(modules = MyCommunityModule.class, dependencies = AppComponent.class)
public interface MyCommunityComponent {
    void inject(MyCommunityActivity activity);
}