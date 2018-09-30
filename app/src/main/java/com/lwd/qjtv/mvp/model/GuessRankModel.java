package com.lwd.qjtv.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.lwd.qjtv.mvp.contract.GuessRankContract;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.GuessRankBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.QueryMap;


@FragmentScope
public class GuessRankModel extends BaseModel implements GuessRankContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public GuessRankModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<GuessRankBean> guessRank(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).guessRank(map);
    }
}