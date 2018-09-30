package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.LikeAvaterListContract;
import com.lwd.qjtv.mvp.model.LikeAvaterListModel;


@Module
public class LikeAvaterListModule {
    private LikeAvaterListContract.View view;

    /**
     * 构建LikeAvaterListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public LikeAvaterListModule(LikeAvaterListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    LikeAvaterListContract.View provideLikeAvaterListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    LikeAvaterListContract.Model provideLikeAvaterListModel(LikeAvaterListModel model) {
        return model;
    }
}