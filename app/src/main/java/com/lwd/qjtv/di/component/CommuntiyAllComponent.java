package com.lwd.qjtv.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.CommuntiyAllModule;

import com.jess.arms.di.scope.FragmentScope;
import com.lwd.qjtv.mvp.ui.fragment.community.CommuntiyAllFragment;

@FragmentScope
@Component(modules = CommuntiyAllModule.class, dependencies = AppComponent.class)
public interface CommuntiyAllComponent {
    void inject(CommuntiyAllFragment fragment);
}