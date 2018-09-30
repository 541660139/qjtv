package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.MinePublisContract;
import com.lwd.qjtv.mvp.model.MinePublisModel;


@Module
public class MinePublisModule {
    private MinePublisContract.View view;

    /**
     * 构建MinePublisModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MinePublisModule(MinePublisContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MinePublisContract.View provideMinePublisView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MinePublisContract.Model provideMinePublisModel(MinePublisModel model) {
        return model;
    }
}