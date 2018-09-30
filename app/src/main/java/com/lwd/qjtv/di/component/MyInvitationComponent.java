package com.lwd.qjtv.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.MyInvitationModule;

import com.lwd.qjtv.mvp.ui.activity.MyInvitationActivity;

@ActivityScope
@Component(modules = MyInvitationModule.class, dependencies = AppComponent.class)
public interface MyInvitationComponent {
    void inject(MyInvitationActivity activity);
}