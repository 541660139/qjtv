package com.lwd.qjtv.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.MatchCollectionMoreModule;

import com.lwd.qjtv.mvp.ui.activity.MatchCollectionMoreActivity;

@ActivityScope
@Component(modules = MatchCollectionMoreModule.class, dependencies = AppComponent.class)
public interface MatchCollectionMoreComponent {
    void inject(MatchCollectionMoreActivity activity);
}