package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.CommuntiyDetailContract;
import com.lwd.qjtv.mvp.model.CommuntiyDetailModel;


@Module
public class CommuntiyDetailModule {
    private CommuntiyDetailContract.View view;

    /**
     * 构建CommuntiyDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CommuntiyDetailModule(CommuntiyDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CommuntiyDetailContract.View provideCommuntiyDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CommuntiyDetailContract.Model provideCommuntiyDetailModel(CommuntiyDetailModel model) {
        return model;
    }
}