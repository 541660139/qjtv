package com.lwd.qjtv.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.DefaultAdapter;
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
import com.lwd.qjtv.mvp.contract.NewsContract;
import com.lwd.qjtv.mvp.model.entity.BBSNewsListBean;
import com.lwd.qjtv.mvp.model.entity.BannerBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ActivityScope
public class NewsPresenter extends BasePresenter<NewsContract.Model, NewsContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private List<BBSNewsListBean.DataBean> mList = new ArrayList<>();
    private DefaultAdapter mAdapter;
    private int page = 1;
    private boolean isFirst = true;
    private int preEndIndex;

    @Inject
    public NewsPresenter(NewsContract.Model model, NewsContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getBanner() {
        Map<String, String> map = new HashMap<>();
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.getBanner(map))
                .subscribe(new ErrorHandleSubscriber<BannerBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BannerBean baseBean) {
                        if (baseBean == null)
                            return;
//                        mRootView.showMessage(baseBean.getMsg());

                        if (baseBean.getStatus().equals("1")) {
                            mRootView.setBannerData(baseBean);
                        }

                    }
                });
    }

}
