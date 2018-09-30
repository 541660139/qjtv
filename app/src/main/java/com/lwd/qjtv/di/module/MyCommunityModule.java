package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.MyCommunityContract;
import com.lwd.qjtv.mvp.model.MyCommunityModel;


@Module
public class MyCommunityModule {
    private MyCommunityContract.View view;

    /**
     * 构建MyCommunityModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MyCommunityModule(MyCommunityContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyCommunityContract.View provideMyCommunityView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyCommunityContract.Model provideMyCommunityModel(MyCommunityModel model) {
        return model;
    }
}