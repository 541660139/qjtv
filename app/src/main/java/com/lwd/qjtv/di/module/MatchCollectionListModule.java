package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.MatchCollectionListContract;
import com.lwd.qjtv.mvp.model.MatchCollectionListModel;


@Module
public class MatchCollectionListModule {
    private MatchCollectionListContract.View view;

    /**
     * 构建MatchCollectionListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MatchCollectionListModule(MatchCollectionListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MatchCollectionListContract.View provideMatchCollectionListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MatchCollectionListContract.Model provideMatchCollectionListModel(MatchCollectionListModel model) {
        return model;
    }
}