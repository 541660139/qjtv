package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.PushCommunityContract;
import com.lwd.qjtv.mvp.model.PushCommunityModel;


@Module
public class PushCommunityModule {
    private PushCommunityContract.View view;

    /**
     * 构建PushCommunityModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PushCommunityModule(PushCommunityContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PushCommunityContract.View providePushCommunityView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PushCommunityContract.Model providePushCommunityModel(PushCommunityModel model) {
        return model;
    }
}