package com.lwd.qjtv.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lwd.qjtv.mvp.contract.MallActivityContract;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.MallListBean;

import java.util.Map;

import io.reactivex.Observable;


@ActivityScope
public class MallActivityModel extends BaseModel implements MallActivityContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public MallActivityModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<MallListBean> getMall(Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getMall(map);
    }
}