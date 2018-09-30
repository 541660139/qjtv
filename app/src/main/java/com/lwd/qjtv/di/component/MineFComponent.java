package com.lwd.qjtv.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.MineFModule;

import com.jess.arms.di.scope.ActivityScope;
import com.lwd.qjtv.mvp.ui.activity.MineFActivity;

@ActivityScope
@Component(modules = MineFModule.class, dependencies = AppComponent.class)
public interface MineFComponent {
    void inject(MineFActivity activity);
}