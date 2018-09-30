package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.GameContract;
import com.lwd.qjtv.mvp.model.GameModel;


@Module
public class GameModule {
    private GameContract.View view;

    /**
     * 构建GameModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public GameModule(GameContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    GameContract.View provideGameView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    GameContract.Model provideGameModel(GameModel model) {
        return model;
    }
}