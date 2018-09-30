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
import com.lwd.qjtv.mvp.contract.LearnBallMoreContract;
import com.lwd.qjtv.mvp.model.entity.LearnBallBean;

import java.util.Map;
import java.util.TreeMap;


@ActivityScope
public class LearnBallMorePresenter extends BasePresenter<LearnBallMoreContract.Model, LearnBallMoreContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public LearnBallMorePresenter(LearnBallMoreContract.Model model, LearnBallMoreContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void requestLearnBallList(boolean pullToRefresh) {
        boolean isEvictCache = pullToRefresh;//是否驱逐缓存,为ture即不使用缓存,每次上拉刷新即需要最新数据,则不使用缓存
        String ac = "home";
        Map<String, String> map = new TreeMap<>();
        map.put("v_type", "1");
        map.put("page", "1");
        map.put("ac", ac);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.getLearnBall(isEvictCache, map))
                .subscribe(new ErrorHandleSubscriber<LearnBallBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull LearnBallBean learnBallBean) {
                        LearnBallBean.DataBean data = learnBallBean.getData();
                        if (learnBallBean == null || data == null || !"1".equals(learnBallBean.getStatus())) {
                            mRootView.setData(-1);
                            if (!"1".equals(learnBallBean.getStatus()))
                                mRootView.showMessage(learnBallBean.getMsg());
                            return;
                        }
                        mRootView.setData(data);
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
