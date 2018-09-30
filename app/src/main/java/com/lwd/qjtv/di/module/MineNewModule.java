package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.MineNewContract;
import com.lwd.qjtv.mvp.model.MineNewModel;


@Module
public class MineNewModule {
    private MineNewContract.View view;

    /**
     * 构建MineNewModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MineNewModule(MineNewContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    MineNewContract.View provideMineNewView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    MineNewContract.Model provideMineNewModel(MineNewModel model) {
        return model;
    }
}