package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.GameTabContract;
import com.lwd.qjtv.mvp.model.GameTabModel;


@Module
public class GameTabModule {
    private GameTabContract.View view;

    /**
     * 构建GameTabModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public GameTabModule(GameTabContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    GameTabContract.View provideGameTabView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    GameTabContract.Model provideGameTabModel(GameTabModel model) {
        return model;
    }
}