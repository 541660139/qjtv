package com.lwd.qjtv.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.GameTabModule;

import com.jess.arms.di.scope.ActivityScope;
import com.lwd.qjtv.mvp.ui.fragment.game.GameTabFragment;

@ActivityScope
@Component(modules = GameTabModule.class, dependencies = AppComponent.class)
public interface GameTabComponent {


    void inject(GameTabFragment fragment);
}