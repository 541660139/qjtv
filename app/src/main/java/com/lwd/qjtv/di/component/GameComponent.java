package com.lwd.qjtv.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.GameModule;

import com.jess.arms.di.scope.FragmentScope;
import com.lwd.qjtv.mvp.ui.fragment.GameFragment;

@FragmentScope
@Component(modules = GameModule.class, dependencies = AppComponent.class)
public interface GameComponent {
    void inject(GameFragment fragment);
}