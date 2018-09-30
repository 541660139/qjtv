package com.lwd.qjtv.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.ChoiceModule;

import com.jess.arms.di.scope.FragmentScope;
import com.lwd.qjtv.mvp.ui.fragment.HamePage.ChoiceFragment;

@FragmentScope
@Component(modules = ChoiceModule.class, dependencies = AppComponent.class)
public interface ChoiceComponent {
    void inject(ChoiceFragment fragment);
}