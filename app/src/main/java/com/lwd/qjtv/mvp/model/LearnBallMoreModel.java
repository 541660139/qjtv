package com.lwd.qjtv.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lwd.qjtv.mvp.contract.LearnBallMoreContract;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.LearnBallBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.QueryMap;


@ActivityScope
public class LearnBallMoreModel extends BaseModel implements LearnBallMoreContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public LearnBallMoreModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<LearnBallBean> getLearnBall(boolean update, @QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getLearnBall(map);
    }
}