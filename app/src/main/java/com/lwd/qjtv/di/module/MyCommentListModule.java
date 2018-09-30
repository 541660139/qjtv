package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.MyCommentListContract;
import com.lwd.qjtv.mvp.model.MyCommentListModel;


@Module
public class MyCommentListModule {
    private MyCommentListContract.View view;

    /**
     * 构建MyCommentListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MyCommentListModule(MyCommentListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyCommentListContract.View provideMyCommentListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyCommentListContract.Model provideMyCommentListModel(MyCommentListModel model) {
        return model;
    }
}