package com.lwd.qjtv.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.MatchCollectionListModule;

import com.lwd.qjtv.mvp.ui.activity.MatchCollectionListActivity;

@ActivityScope
@Component(modules = MatchCollectionListModule.class, dependencies = AppComponent.class)
public interface MatchCollectionListComponent {
    void inject(MatchCollectionListActivity activity);
}