package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.MatchCollectionDetailsContract;
import com.lwd.qjtv.mvp.model.MatchCollectionDetailsModel;


@Module
public class MatchCollectionDetailsModule {
    private MatchCollectionDetailsContract.View view;

    /**
     * 构建MatchCollectionDetailsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MatchCollectionDetailsModule(MatchCollectionDetailsContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MatchCollectionDetailsContract.View provideMatchCollectionDetailsView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MatchCollectionDetailsContract.Model provideMatchCollectionDetailsModel(MatchCollectionDetailsModel model) {
        return model;
    }
}