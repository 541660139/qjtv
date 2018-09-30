package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.CommunityContract;
import com.lwd.qjtv.mvp.model.CommunityModel;


@Module
public class CommunityModule {
    private CommunityContract.View view;

    /**
     * 构建CommunityModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CommunityModule(CommunityContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CommunityContract.View provideCommunityView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CommunityContract.Model provideCommunityModel(CommunityModel model) {
        return model;
    }
}