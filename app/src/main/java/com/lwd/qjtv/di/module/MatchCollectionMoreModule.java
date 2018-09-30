package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.MatchCollectionMoreContract;
import com.lwd.qjtv.mvp.model.MatchCollectionMoreModel;


@Module
public class MatchCollectionMoreModule {
    private MatchCollectionMoreContract.View view;

    /**
     * 构建MatchCollectionMoreModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MatchCollectionMoreModule(MatchCollectionMoreContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MatchCollectionMoreContract.View provideMatchCollectionMoreView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MatchCollectionMoreContract.Model provideMatchCollectionMoreModel(MatchCollectionMoreModel model) {
        return model;
    }
}