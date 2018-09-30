package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.ChoiceContract;
import com.lwd.qjtv.mvp.model.ChoiceModel;


@Module
public class ChoiceModule {
    private ChoiceContract.View view;

    /**
     * 构建ChoiceModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ChoiceModule(ChoiceContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    ChoiceContract.View provideChoiceView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    ChoiceContract.Model provideChoiceModel(ChoiceModel model) {
        return model;
    }
}