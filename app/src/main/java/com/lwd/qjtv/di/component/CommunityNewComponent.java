package com.lwd.qjtv.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lwd.qjtv.di.module.CommunityNewModule;

import com.jess.arms.di.scope.FragmentScope;
import com.lwd.qjtv.mvp.ui.fragment.CommunityNewFragment;

@FragmentScope
@Component(modules = CommunityNewModule.class, dependencies = AppComponent.class)
public interface CommunityNewComponent {
    void inject(CommunityNewFragment fragment);
}