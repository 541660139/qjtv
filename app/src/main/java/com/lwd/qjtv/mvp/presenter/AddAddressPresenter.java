package com.lwd.qjtv.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.RxUtils;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.contract.AddAddressContract;
import com.lwd.qjtv.mvp.model.entity.AddAddressBean;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
 * Created by ZhengQian on 2017/5/18.
 */

@ActivityScope
public class AddAddressPresenter extends BasePresenter<AddAddressContract.Model, AddAddressContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public AddAddressPresenter(AddAddressContract.Model model, AddAddressContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void addAddress(String receiver, String mobile, String address, String zone, String is_default) {
        String op = "addAddress";
        Map<String, String> map = new TreeMap<>();
        map.put("op", op);
        map.put("receiver", receiver);
        map.put("mobile", mobile);
        map.put("address", address);
        map.put("zone", zone);
        map.put("is_default", is_default);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.addAddress(map))
                .subscribe(new ErrorHandleSubscriber<AddAddressBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull AddAddressBean s) {
                        mRootView.showMessage(s.getMsg());
                        mRootView.killMyself();
                    }
                });
    }

    public void changeAddress(String receiver, String mobile, String address, String zone, String is_default, String id) {
        String op = "modifyAddress";
        Map<String, String> map = new HashMap<>();
        map.put("op", op);
        map.put("receiver", receiver);
        map.put("mobile", mobile);
        map.put("address", address);
        map.put("zone", zone);
        map.put("address_id", id);
        map.put("is_default", is_default);
        RxUtils.applyNormalSchedulers(mRootView, mModel.changeAddress(map))
                .subscribe(new ErrorHandleSubscriber<AddAddressBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull AddAddressBean s) {
                        mRootView.showMessage(s.getMsg());
                        mRootView.killMyself();
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
