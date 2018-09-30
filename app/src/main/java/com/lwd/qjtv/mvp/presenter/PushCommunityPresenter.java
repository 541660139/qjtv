package com.lwd.qjtv.mvp.presenter;

import android.app.Application;
import android.graphics.Bitmap;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MultipartBody;

import javax.inject.Inject;

import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.RxUtils;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.contract.PushCommunityContract;
import com.lwd.qjtv.mvp.model.entity.BaseBean;
import com.lwd.qjtv.mvp.model.entity.UploadPicBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ActivityScope
public class PushCommunityPresenter extends BasePresenter<PushCommunityContract.Model, PushCommunityContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public PushCommunityPresenter(PushCommunityContract.Model model, PushCommunityContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void uploadPic(MultipartBody.Part body) {
        String op = "user_profile";
        Map<String, String> map = new HashMap<>();
        map.put("op", op);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.uploadPicSlk(body, map))
                .subscribe(new ErrorHandleSubscriber<UploadPicBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull UploadPicBean uploadPicBean) {
                        if (uploadPicBean == null)
                            return;
                        if (!"1".equals(uploadPicBean.getStatus()) || uploadPicBean.getData() == null) {
                            if (uploadPicBean.getMsg() != null)
                                mRootView.showMessage(uploadPicBean.getMsg());
                            return;
                        }
                        String img_url = uploadPicBean.getData().getUrl();
                        mRootView.setData(img_url);
                    }
                });
    }

    public void pushCommunity(String title, String content, String picUrl, boolean is_edit) {
        Map<String, String> map = new HashMap<>();
        map.put("op", "add_post");
        map.put("title", title);
        map.put("content", content);
        map.put("picture", picUrl);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.pushCommunity(map))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseBean baseBean) {
                        if (baseBean != null) {
                            mRootView.showMessage(baseBean.getMsg());
                            if ("1".equals(baseBean.getStatus()))
                                mRootView.killMyself();
                        }
                    }
                });
    }


    public void GetImageBitmapsForUrls(List<String> urls) {

//        通过图片url集合获取Bitmap集合
        List<Bitmap> list = new ArrayList<>();


        mRootView.loadImage(list);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

}
