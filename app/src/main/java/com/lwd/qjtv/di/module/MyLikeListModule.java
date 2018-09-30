package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.MyLikeListContract;
import com.lwd.qjtv.mvp.model.MyLikeListModel;


@Module
public class MyLikeListModule {
    private MyLikeListContract.View view;

    /**
     * 构建MyLikeListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MyLikeListModule(MyLikeListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyLikeListContract.View provideMyLikeListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyLikeListContract.Model provideMyLikeListModel(MyLikeListModel model) {
        return model;
    }
}