package com.lwd.qjtv.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lwd.qjtv.mvp.contract.MatchSearchContract;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.HotSearchCollectionBean;
import com.lwd.qjtv.mvp.model.entity.SearchCollectionBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.QueryMap;


@ActivityScope
public class MatchSearchModel extends BaseModel implements MatchSearchContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public MatchSearchModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<SearchCollectionBean> searchMatchCollection(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).searchMatchCollection(map);
    }

    @Override
    public Observable<HotSearchCollectionBean> getHotSearchMatchCollection(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getHotSearchMatchCollection(map);
    }
}