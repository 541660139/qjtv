package com.lwd.qjtv.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.MineNewModule;

import com.jess.arms.di.scope.FragmentScope;
import com.lwd.qjtv.mvp.ui.fragment.MineNewFragment;

@FragmentScope
@Component(modules = MineNewModule.class, dependencies = AppComponent.class)
public interface MineNewComponent {
    void inject(MineNewFragment fragment);
}