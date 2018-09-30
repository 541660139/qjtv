package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.CommuntiyAllContract;
import com.lwd.qjtv.mvp.model.CommuntiyAllModel;


@Module
public class CommuntiyAllModule {
    private CommuntiyAllContract.View view;

    /**
     * 构建CommuntiyAllModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CommuntiyAllModule(CommuntiyAllContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    CommuntiyAllContract.View provideCommuntiyAllView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    CommuntiyAllContract.Model provideCommuntiyAllModel(CommuntiyAllModel model) {
        return model;
    }
}