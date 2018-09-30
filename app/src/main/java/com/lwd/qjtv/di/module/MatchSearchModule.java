package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.MatchSearchContract;
import com.lwd.qjtv.mvp.model.MatchSearchModel;


@Module
public class MatchSearchModule {
    private MatchSearchContract.View view;

    /**
     * 构建MatchSearchModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MatchSearchModule(MatchSearchContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MatchSearchContract.View provideMatchSearchView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MatchSearchContract.Model provideMatchSearchModel(MatchSearchModel model) {
        return model;
    }
}