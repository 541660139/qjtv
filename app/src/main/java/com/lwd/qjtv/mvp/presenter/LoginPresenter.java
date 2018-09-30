package com.lwd.qjtv.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.LiveKit;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.RxUtils;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.contract.LoginContract;
import com.lwd.qjtv.mvp.model.entity.GetLevelBean;
import com.lwd.qjtv.mvp.model.entity.GetToken;
import com.lwd.qjtv.mvp.model.entity.LoginBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import io.rong.imlib.RongIMClient;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;


/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */


/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void login(String phone, String pwd) {
        String op = "phone_login";
        Map<String, String> map = new HashMap<>();
        map.put("op", op);
        map.put("phone", phone);
        map.put("pwd", pwd);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.login(map))
                .subscribe(new ErrorHandleSubscriber<LoginBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull LoginBean loginBean) {
                        LoginBean.DataBean data = loginBean.getData();
                        if (loginBean == null || data == null || !"1".equals(loginBean.getStatus())) {
                            mRootView.showMessage(loginBean.getMsg());
                            return;
                        }
                        SaveUserInfo.saveUser(data.getUid(), data.getUsername(), data.getPhone(), data.getAvatar(), data.getSex(), data.getMemberLevel());
                        SaveUserInfo.setCoin(data.getB_coin());
                        SaveUserInfo.setUnamekey(data.getUnamekey());
                        SaveUserInfo.setUserType(data.getUser_type());
                        SaveUserInfo.setExperienceValue(data.getExperienceValue());
                        SaveUserInfo.setYqLink(data.getYq_link());
                        SaveUserInfo.setExperienceMax(data.getExperienceValueMax());
                        Preference.putBoolean(Constant.ON_OFF, loginBean.getData().getOn_off().equals("1"));
                        mRootView.setData(null);
                        mRootView.showMessage(loginBean.getMsg());
                        mRootView.killMyself();
                    }
                });
    }

    public void getUserInfo() {
        String op = "getMemberLevel";
        Map<String, String> map = new HashMap<>();
        map.put("op", op);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        mModel.getUserInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<GetLevelBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull GetLevelBean getLevelBean) {
                        if (getLevelBean == null || !"1".equals(getLevelBean.getStatus()))
                            return;
                        SaveUserInfo.setExperienceMax(getLevelBean.getData().getExperienceValueMax());
                        SaveUserInfo.setExperienceValue(getLevelBean.getData().getExperienceValue());
                        SaveUserInfo.setCoin(getLevelBean.getData().getB_coin());
                        SaveUserInfo.setUserType(getLevelBean.getData().getUser_type() + "");
                        SaveUserInfo.setYqLink(getLevelBean.getData().getYq_link());
                        mRootView.killMyself();
                    }
                });
    }

    public void getToken() {
        Map<String, String> map = new HashMap<>();
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        mModel.getToken(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<GetToken>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull GetToken getToken) {

                        Log.d("LoginActivity", "getTokencode: "+getToken.getCode()+"----etToken:"+getToken.getToken());
                        if (getToken.getCode() == 200) {
                            SaveUserInfo.setToken(getToken.getToken());

//                            RongYunUtils.connect("BFV0JCmgGdaiNXno8uRBcnA+UvCT5kYYzboKQfrCSdK8sV0wpWsOu+Ci5K5xPldXWAWTxdDum5M=");

                            LiveKit.connect(getToken.getToken(), new RongIMClient.ConnectCallback() {
                                @Override
                                public void onTokenIncorrect() {
                                    Log.d("LoginActivity", "onTokenIncorrect: ");
                                }

                                @Override
                                public void onSuccess(String s) {
                                    Log.d("LoginActivity", "onSuccess: ");
                                }

                                @Override
                                public void onError(RongIMClient.ErrorCode errorCode) {
                                    Log.d("LoginActivity", "onError: ");
                                }
                            });
                        }
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
