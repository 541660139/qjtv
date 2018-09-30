package com.lwd.qjtv.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.MatchCollectionDetailsModule;

import com.lwd.qjtv.mvp.ui.fragment.other.MatchCollectionDetailsFragment;

@ActivityScope
@Component(modules = MatchCollectionDetailsModule.class, dependencies = AppComponent.class)
public interface MatchCollectionDetailsComponent {
    void inject(MatchCollectionDetailsFragment fragment);
}