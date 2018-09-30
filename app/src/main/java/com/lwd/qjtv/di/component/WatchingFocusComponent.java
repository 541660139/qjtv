package com.lwd.qjtv.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.WatchingFocusModule;

import com.jess.arms.di.scope.FragmentScope;
import com.lwd.qjtv.mvp.ui.fragment.HamePage.WatchingFocusFragment;

@FragmentScope
@Component(modules = WatchingFocusModule.class, dependencies = AppComponent.class)
public interface WatchingFocusComponent {
    void inject(WatchingFocusFragment fragment);
}