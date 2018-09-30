package com.lwd.qjtv.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lwd.qjtv.mvp.contract.PushCommunityContract;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.BaseBean;
import com.lwd.qjtv.mvp.model.entity.UploadPicBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;


@ActivityScope
public class PushCommunityModel extends BaseModel implements PushCommunityContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public PushCommunityModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<UploadPicBean> uploadPicSlk(@Part MultipartBody.Part requestBody, @QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).uploadPicSlk(requestBody, map);
    }

    @Override
    public Observable<BaseBean> pushCommunity(@QueryMap Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).pushCommunity(map);
    }
}