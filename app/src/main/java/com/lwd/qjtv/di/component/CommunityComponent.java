package com.lwd.qjtv.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.CommunityModule;

import com.lwd.qjtv.mvp.ui.fragment.CommunityFragment;

@ActivityScope
@Component(modules = CommunityModule.class, dependencies = AppComponent.class)
public interface CommunityComponent {
    void inject(CommunityFragment fragment);
}