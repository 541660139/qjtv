package com.lwd.qjtv.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.lwd.qjtv.mvp.contract.CommuntiyDetailContract;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.BaseBean;
import com.lwd.qjtv.mvp.model.entity.CommunityAllBean;
import com.lwd.qjtv.mvp.model.entity.CommunityHfBean;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.http.QueryMap;


@ActivityScope
public class CommuntiyDetailModel extends BaseModel implements CommuntiyDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CommuntiyDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<CommunityAllBean> getCommunityDetail(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getCommunityDetail(map);
    }

    @Override
    public Observable<BaseBean> sendCommunitycontent(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).sendCommunitycontent(map);
    }

    @Override
    public Observable<CommunityHfBean> sendCommunityHfcontent(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).sendCommunityHfcontent(map);
    }
}