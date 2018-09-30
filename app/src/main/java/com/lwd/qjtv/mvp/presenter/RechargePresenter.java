package com.lwd.qjtv.mvp.presenter;

import android.app.Application;
import android.os.Message;
import android.util.Log;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.RxUtils;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.contract.RechargeContract;
import com.lwd.qjtv.mvp.model.entity.AliH5PayBean;
import com.lwd.qjtv.mvp.model.entity.AliPayBean;
import com.lwd.qjtv.mvp.model.entity.GetLevelBean;
import com.lwd.qjtv.mvp.model.entity.WechatH5PayBean;
import com.lwd.qjtv.mvp.model.entity.WechatPayBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.annotations.NonNull;
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
 * Created by ZhengQian on 2017/5/13.
 */

@ActivityScope
public class RechargePresenter extends BasePresenter<RechargeContract.Model, RechargeContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public RechargePresenter(RechargeContract.Model model, RechargeContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void getUserCoin() {
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
        RxUtils.applyNormalSchedulers(mRootView, mModel.getUserInfo(map))
                .subscribe(new ErrorHandleSubscriber<GetLevelBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull GetLevelBean baseBean) {
                        if (baseBean != null) {
                            SaveUserInfo.setExperienceMax(baseBean.getData().getExperienceValueMax());
                            SaveUserInfo.setExperienceValue(baseBean.getData().getExperienceValue());
                            SaveUserInfo.setCoin(baseBean.getData().getB_coin());
                            SaveUserInfo.setUserType(baseBean.getData().getUser_type() + "");
                            SaveUserInfo.setYqLink(baseBean.getData().getYq_link());
                            mRootView.setData(null);
                        }
                    }

                });
    }

    public void wechatPay(String price) {
        Map<String, String> map = new HashMap<>();
        map.put("price", price);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));

        RxUtils.applyNormalSchedulers(mRootView, mModel.wechatPay(map))
                .subscribe(new ErrorHandleSubscriber<WechatPayBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull WechatPayBean wechatPayBean) {
                        if (wechatPayBean != null) {
                            WechatPayBean.DataBean data = wechatPayBean.getData();
                            Message message = new Message();
                            message.what = 2;
                            message.obj = data;
                            mRootView.setData(message);
                        }
                    }
                });
    }


    public void wechatH5Pay(String price) {
        Map<String, String> map = new HashMap<>();
        map.put("price", price);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));

        RxUtils.applyNormalSchedulers(mRootView, mModel.wechatH5Pay(map))
                .subscribe(new ErrorHandleSubscriber<WechatH5PayBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull WechatH5PayBean wechatH5PayBean) {
                        if (wechatH5PayBean != null) {
                            if (wechatH5PayBean.getStatus().equals("1")) {
                                WechatH5PayBean.DataBean data = wechatH5PayBean.getData();
                                Message message = new Message();
                                message.what = 4;
                                message.obj = data;
                                mRootView.setData(message);
                            } else {
                                Log.d("sad", "无法充值");
                                mRootView.showMessage(wechatH5PayBean.getMsg());
                            }
                        }
                    }
                });
    }


    public void aliPay(String price) {
        Map<String, String> map = new HashMap<>();
        map.put("price", price);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.aliPay(map))
                .subscribe(new ErrorHandleSubscriber<AliPayBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull AliPayBean aliPayBean) {
                        if (aliPayBean != null) {
                            AliPayBean.DataBean data = aliPayBean.getData();
                            Message message = new Message();
                            message.what = 3;
                            message.obj = data;
                            mRootView.setData(message);
                        }
                    }
                });
    }

    public void aliH5Pay(String price) {
        Map<String, String> map = new HashMap<>();
        map.put("price", price);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.aliH5Pay(map))
                .subscribe(new ErrorHandleSubscriber<AliH5PayBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull AliH5PayBean aliPayBean) {
                        if (aliPayBean != null) {
                            if (aliPayBean.getStatus().equals("1")) {
                                AliH5PayBean.DataBean data = aliPayBean.getData();
                                Message message = new Message();
                                message.what = 5;
                                message.obj = data;
                                mRootView.setData(message);
                            } else {
                                Log.d("sad", "无法充值");
                                mRootView.showMessage(aliPayBean.getMsg());
                            }
                        }
                    }
                });
    }

    public void getSwiftPassPay(String price) {
        Map<String, String> map = new HashMap<>();
        map.put("price", price);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mModel.getSwiftPassPay(map))
                .subscribe(swiftPassPayBean -> {
                    if (swiftPassPayBean != null && swiftPassPayBean.getStatus().equals("1")) {
                        Message message = new Message();
                        message.what = 1;
                        message.obj = swiftPassPayBean.getData().getToken_id();
                        mRootView.setData(message);
                        Preference.put("recharge_money", price);
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
