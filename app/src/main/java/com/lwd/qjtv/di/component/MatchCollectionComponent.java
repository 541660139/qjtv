package com.lwd.qjtv.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.MatchCollectionModule;

import com.lwd.qjtv.mvp.ui.activity.MatchCollectionActivity;

@ActivityScope
@Component(modules = MatchCollectionModule.class, dependencies = AppComponent.class)
public interface MatchCollectionComponent {
    void inject(MatchCollectionActivity activity);
}