package com.lwd.qjtv.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.NBAGuessDetailsModule;

import com.lwd.qjtv.mvp.ui.activity.NBAGuessDetailsActivity;

@ActivityScope
@Component(modules = NBAGuessDetailsModule.class, dependencies = AppComponent.class)
public interface NBAGuessDetailsComponent {
    void inject(NBAGuessDetailsActivity activity);
}