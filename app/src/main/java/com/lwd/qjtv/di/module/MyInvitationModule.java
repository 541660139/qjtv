package com.lwd.qjtv.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.lwd.qjtv.mvp.contract.MyInvitationContract;
import com.lwd.qjtv.mvp.model.MyInvitationModel;


@Module
public class MyInvitationModule {
    private MyInvitationContract.View view;

    /**
     * 构建MyInvitationModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MyInvitationModule(MyInvitationContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyInvitationContract.View provideMyInvitationView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyInvitationContract.Model provideMyInvitationModel(MyInvitationModel model) {
        return model;
    }
}