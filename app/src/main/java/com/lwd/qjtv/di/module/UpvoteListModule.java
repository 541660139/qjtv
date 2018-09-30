package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.UpvoteListContract;
import com.lwd.qjtv.mvp.model.UpvoteListModel;


@Module
public class UpvoteListModule {
    private UpvoteListContract.View view;

    /**
     * 构建UpvoteListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public UpvoteListModule(UpvoteListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UpvoteListContract.View provideUpvoteListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    UpvoteListContract.Model provideUpvoteListModel(UpvoteListModel model) {
        return model;
    }
}