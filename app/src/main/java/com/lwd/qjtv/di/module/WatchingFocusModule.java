package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.WatchingFocusContract;
import com.lwd.qjtv.mvp.model.WatchingFocusModel;


@Module
public class WatchingFocusModule {
    private WatchingFocusContract.View view;

    /**
     * 构建WatchingFocusModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public WatchingFocusModule(WatchingFocusContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    WatchingFocusContract.View provideWatchingFocusView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    WatchingFocusContract.Model provideWatchingFocusModel(WatchingFocusModel model) {
        return model;
    }
}