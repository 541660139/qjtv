package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.GuessRankContract;
import com.lwd.qjtv.mvp.model.GuessRankModel;


@Module
public class GuessRankModule {
    private GuessRankContract.View view;

    /**
     * 构建GuessRankModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public GuessRankModule(GuessRankContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    GuessRankContract.View provideGuessRankView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    GuessRankContract.Model provideGuessRankModel(GuessRankModel model) {
        return model;
    }
}