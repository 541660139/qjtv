package com.lwd.qjtv.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.MyMessageModule;

import com.lwd.qjtv.mvp.ui.activity.MyMessageActivity;

@ActivityScope
@Component(modules = MyMessageModule.class, dependencies = AppComponent.class)
public interface MyMessageComponent {
    void inject(MyMessageActivity activity);
}