package com.lwd.qjtv.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.RxUtils;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.contract.MineNewContract;
import com.lwd.qjtv.mvp.model.entity.UserInfro;

import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@FragmentScope
public class MineNewPresenter extends BasePresenter<MineNewContract.Model, MineNewContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;


    @Inject
    AppManager mAppManager;

    @Inject
    public MineNewPresenter(MineNewContract.Model model, MineNewContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }

    public void getPersonalCenter() {
        Map<String, String> map = new TreeMap<>();
        map.put("op", "personal_show");
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.getPersonalCenter(map))
                .subscribe(new ErrorHandleSubscriber<UserInfro>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull UserInfro s) {
                        mRootView.setData(s);
//                        mRootView.showMessage(s.getMsg());

                    }
                });
    }
}
