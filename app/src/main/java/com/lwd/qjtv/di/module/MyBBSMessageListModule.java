package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.MyBBSMessageListContract;
import com.lwd.qjtv.mvp.model.MyBBSMessageListModel;


@Module
public class MyBBSMessageListModule {
    private MyBBSMessageListContract.View view;

    /**
     * 构建MyBBSMessageListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MyBBSMessageListModule(MyBBSMessageListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyBBSMessageListContract.View provideMyBBSMessageListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyBBSMessageListContract.Model provideMyBBSMessageListModel(MyBBSMessageListModel model) {
        return model;
    }
}