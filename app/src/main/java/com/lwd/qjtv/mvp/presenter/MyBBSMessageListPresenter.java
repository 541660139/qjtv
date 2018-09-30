package com.lwd.qjtv.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.RxUtils;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.contract.MyBBSMessageListContract;
import com.lwd.qjtv.mvp.model.entity.BBSMessageListBean;

import java.util.HashMap;
import java.util.Map;


@ActivityScope
public class MyBBSMessageListPresenter extends BasePresenter<MyBBSMessageListContract.Model, MyBBSMessageListContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public MyBBSMessageListPresenter(MyBBSMessageListContract.Model model, MyBBSMessageListContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void getMyBBSMessageData() {
        Map<String, String> map = new HashMap<>();
        map.put("op", "list");
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mModel.getMsgList(map))
                .subscribe(new ErrorHandleSubscriber<BBSMessageListBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BBSMessageListBean bbsMessageListBean) {
                        if (mRootView == null)
                            return;
                        if (bbsMessageListBean == null || bbsMessageListBean.getData() == null || !"1".equals(bbsMessageListBean.getStatus())) {
                            mRootView.setData(-1);
                            if (!"1".equals(bbsMessageListBean.getStatus()))
                                mRootView.showMessage(bbsMessageListBean.getMsg());
                            return;
                        }
                        mRootView.setData(bbsMessageListBean.getData());
                    }
                });
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
