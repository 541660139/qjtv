package com.lwd.qjtv.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.RichRankModule;

import com.jess.arms.di.scope.FragmentScope;
import com.lwd.qjtv.mvp.ui.fragment.RichRankFragment;

@FragmentScope
@Component(modules = RichRankModule.class, dependencies = AppComponent.class)
public interface RichRankComponent {
    void inject(RichRankFragment fragment);
}