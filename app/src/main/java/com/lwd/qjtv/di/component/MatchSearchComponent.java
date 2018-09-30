package com.lwd.qjtv.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.MatchSearchModule;

import com.lwd.qjtv.mvp.ui.activity.MatchSearchActivity;

@ActivityScope
@Component(modules = MatchSearchModule.class, dependencies = AppComponent.class)
public interface MatchSearchComponent {
    void inject(MatchSearchActivity activity);
}