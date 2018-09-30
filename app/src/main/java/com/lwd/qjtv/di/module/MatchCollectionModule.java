package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.MatchCollectionContract;
import com.lwd.qjtv.mvp.model.MatchCollectionModel;


@Module
public class MatchCollectionModule {
    private MatchCollectionContract.View view;

    /**
     * 构建MatchCollectionModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MatchCollectionModule(MatchCollectionContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MatchCollectionContract.View provideMatchCollectionView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MatchCollectionContract.Model provideMatchCollectionModel(MatchCollectionModel model) {
        return model;
    }
}