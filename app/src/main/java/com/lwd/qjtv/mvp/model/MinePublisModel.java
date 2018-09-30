package com.lwd.qjtv.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lwd.qjtv.mvp.contract.MinePublisContract;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.BaseBean;
import com.lwd.qjtv.mvp.model.entity.PublisDataBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.QueryMap;


@ActivityScope
public class MinePublisModel extends BaseModel implements MinePublisContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MinePublisModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<PublisDataBean> getPersonalPublis(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getMeIndex(map);
    }

    @Override
    public Observable<PublisDataBean> getPersonalIndex(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getPersonalIndex(map);
    }

    @Override
    public Observable<BaseBean> deleterPublis(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).deleterPublis(map);
    }


}