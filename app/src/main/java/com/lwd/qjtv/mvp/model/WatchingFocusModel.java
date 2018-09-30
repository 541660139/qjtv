package com.lwd.qjtv.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.lwd.qjtv.mvp.contract.WatchingFocusContract;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.HotPointBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.QueryMap;


@FragmentScope
public class WatchingFocusModel extends BaseModel implements WatchingFocusContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public WatchingFocusModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<HotPointBean> getHotPoint(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getHotPoint(map);
    }
}