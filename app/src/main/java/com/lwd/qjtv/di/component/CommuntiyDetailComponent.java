package com.lwd.qjtv.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.CommuntiyDetailModule;

import com.jess.arms.di.scope.ActivityScope;
import com.lwd.qjtv.mvp.ui.activity.CommuntiyDetailActivity;

@ActivityScope
@Component(modules = CommuntiyDetailModule.class, dependencies = AppComponent.class)
public interface CommuntiyDetailComponent {
    void inject(CommuntiyDetailActivity activity);
}