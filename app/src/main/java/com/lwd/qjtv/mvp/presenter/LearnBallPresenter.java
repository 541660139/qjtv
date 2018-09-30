package com.lwd.qjtv.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.contract.LearnBallContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

import com.lwd.qjtv.app.utils.RxUtils;
import com.lwd.qjtv.mvp.model.entity.LearnBallBean;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

@ActivityScope
public class LearnBallPresenter extends BasePresenter<LearnBallContract.Model, LearnBallContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;
    private List<String> mList = new ArrayList<>();
    private DefaultAdapter mAdapter;
    private int page = 1;
    private boolean isFirst = true;
    private int preEndIndex;

    @Inject
    public LearnBallPresenter(LearnBallContract.Model model, LearnBallContract.View rootView, RxErrorHandler handler
            , AppManager appManager, Application application) {
        super(model, rootView);
        this.mApplication = application;
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
    }

    public void requestLearnBallList(boolean pullToRefresh) {
        boolean isEvictCache = pullToRefresh;//是否驱逐缓存,为ture即不使用缓存,每次上拉刷新即需要最新数据,则不使用缓存

        if (pullToRefresh && isFirst) {//默认在第一次上拉刷新时使用缓存
            isFirst = false;
            isEvictCache = false;
        }
        String ac = "home";
        String v_type = "1";
        String pageLenth = "1";
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
        this.mAdapter = null;
        this.mList = null;
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }
}
