package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.NBAGuessDetailsContract;
import com.lwd.qjtv.mvp.model.NBAGuessDetailsModel;


@Module
public class NBAGuessDetailsModule {
    private NBAGuessDetailsContract.View view;

    /**
     * 构建NBAGuessDetailsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public NBAGuessDetailsModule(NBAGuessDetailsContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    NBAGuessDetailsContract.View provideNBAGuessDetailsView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    NBAGuessDetailsContract.Model provideNBAGuessDetailsModel(NBAGuessDetailsModel model) {
        return model;
    }
}