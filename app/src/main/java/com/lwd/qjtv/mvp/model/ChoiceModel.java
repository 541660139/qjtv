package com.lwd.qjtv.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.lwd.qjtv.mvp.contract.ChoiceContract;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.ChoiceDataBean;
import com.lwd.qjtv.mvp.model.entity.DianZanBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.QueryMap;


@FragmentScope
public class ChoiceModel extends BaseModel implements ChoiceContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ChoiceModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<ChoiceDataBean> getChoiceData(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getChoiceData(map);
    }

    @Override
    public Observable<DianZanBean> setDianZan(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).setDianZan(map);
    }
}