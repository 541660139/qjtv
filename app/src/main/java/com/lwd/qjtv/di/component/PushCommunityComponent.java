package com.lwd.qjtv.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.PushCommunityModule;

import com.lwd.qjtv.mvp.ui.activity.PushCommunityActivity;

@ActivityScope
@Component(modules = PushCommunityModule.class, dependencies = AppComponent.class)
public interface PushCommunityComponent {
    void inject(PushCommunityActivity activity);
}