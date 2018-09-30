package com.lwd.qjtv.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lwd.qjtv.mvp.contract.MatchCollectionContract;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.MatchCollectionDetailsBean;
import com.lwd.qjtv.mvp.model.entity.MatchCollectionTitleBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.QueryMap;


@ActivityScope
public class MatchCollectionModel extends BaseModel implements MatchCollectionContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public MatchCollectionModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<MatchCollectionDetailsBean> getMatchCollectionDetails(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getMatchCollectionDetails(map);
    }

    @Override
    public Observable<MatchCollectionTitleBean> getMatchCollectionTitle(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getMatchCollectionTitle(map);
    }
}