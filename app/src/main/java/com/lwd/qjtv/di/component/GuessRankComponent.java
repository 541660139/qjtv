package com.lwd.qjtv.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.GuessRankModule;

import com.jess.arms.di.scope.FragmentScope;
import com.lwd.qjtv.mvp.ui.fragment.GuessRankFragment;

@FragmentScope
@Component(modules = GuessRankModule.class, dependencies = AppComponent.class)
public interface GuessRankComponent {
    void inject(GuessRankFragment fragment);
}