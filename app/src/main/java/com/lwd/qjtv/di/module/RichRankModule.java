package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.RichRankContract;
import com.lwd.qjtv.mvp.model.RichRankModel;


@Module
public class RichRankModule {
    private RichRankContract.View view;

    /**
     * 构建RichRankModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public RichRankModule(RichRankContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    RichRankContract.View provideRichRankView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    RichRankContract.Model provideRichRankModel(RichRankModel model) {
        return model;
    }
}