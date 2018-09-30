package com.lwd.qjtv.mvp.presenter;

import android.app.Application;
import android.support.annotation.NonNull;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.RxUtils;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.contract.GameContract;
import com.lwd.qjtv.mvp.model.entity.GameTabBean;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@FragmentScope
public class GamePresenter extends BasePresenter<GameContract.Model, GameContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;


    @Inject
    AppManager mAppManager;

    @Inject
    public GamePresenter(GameContract.Model model, GameContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;

        this.mApplication = null;
    }

    public void getGameTable() {

        Map<String, String> map = new HashMap<>();
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        map.put("op", "sport_list");
        RxUtils.applyNormalSchedulers(mRootView, mModel.getGameTab(map))
                .subscribe(new ErrorHandleSubscriber<GameTabBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull GameTabBean baseBean) {
                        if (baseBean == null)
                            return;
//                        mRootView.showMessage(baseBean.getMsg());

                        if (baseBean.getStatus().equals("1")) {
                            mRootView.setData(baseBean);
                        }

                    }
                });
    }
}
