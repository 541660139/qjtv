package com.lwd.qjtv.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.LearnBallMoreModule;

import com.lwd.qjtv.mvp.ui.activity.LearnBallMoreActivity;

@ActivityScope
@Component(modules = LearnBallMoreModule.class, dependencies = AppComponent.class)
public interface LearnBallMoreComponent {
    void inject(LearnBallMoreActivity activity);
}