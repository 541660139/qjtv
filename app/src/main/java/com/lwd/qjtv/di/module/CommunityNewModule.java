package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.CommunityNewContract;
import com.lwd.qjtv.mvp.model.CommunityNewModel;


@Module
public class CommunityNewModule {
    private CommunityNewContract.View view;

    /**
     * 构建CommunityNewModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CommunityNewModule(CommunityNewContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    CommunityNewContract.View provideCommunityNewView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    CommunityNewContract.Model provideCommunityNewModel(CommunityNewModel model) {
        return model;
    }
}