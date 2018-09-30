package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.MallActivityContract;
import com.lwd.qjtv.mvp.model.MallActivityModel;


@Module
public class MallActivityModule {
    private MallActivityContract.View view;

    /**
     * 构建MallActivityModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MallActivityModule(MallActivityContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MallActivityContract.View provideMallActivityView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MallActivityContract.Model provideMallActivityModel(MallActivityModel model) {
        return model;
    }
}