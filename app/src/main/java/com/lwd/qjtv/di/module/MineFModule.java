package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.MineFContract;
import com.lwd.qjtv.mvp.model.MineFModel;


@Module
public class MineFModule {
    private MineFContract.View view;

    /**
     * 构建MineFModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MineFModule(MineFContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MineFContract.View provideMineFView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MineFContract.Model provideMineFModel(MineFModel model) {
        return model;
    }
}