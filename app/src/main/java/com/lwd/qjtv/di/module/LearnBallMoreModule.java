package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.LearnBallMoreContract;
import com.lwd.qjtv.mvp.model.LearnBallMoreModel;


@Module
public class LearnBallMoreModule {
    private LearnBallMoreContract.View view;

    /**
     * 构建LearnBallMoreModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public LearnBallMoreModule(LearnBallMoreContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    LearnBallMoreContract.View provideLearnBallMoreView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    LearnBallMoreContract.Model provideLearnBallMoreModel(LearnBallMoreModel model) {
        return model;
    }
}