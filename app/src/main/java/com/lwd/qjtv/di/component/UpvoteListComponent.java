package com.lwd.qjtv.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.UpvoteListModule;

import com.lwd.qjtv.mvp.ui.activity.UpvoteListActivity;

@ActivityScope
@Component(modules = UpvoteListModule.class, dependencies = AppComponent.class)
public interface UpvoteListComponent {
    void inject(UpvoteListActivity activity);
}