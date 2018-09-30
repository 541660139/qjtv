package com.lwd.qjtv.mvp.presenter;

import android.app.Application;
import android.os.Message;

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
import com.lwd.qjtv.mvp.contract.UserInfoContract;
import com.lwd.qjtv.mvp.model.entity.ChangeNameBean;
import com.lwd.qjtv.mvp.model.entity.UploadPicBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MultipartBody;

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
public class UserInfoPresenter extends BasePresenter<UserInfoContract.Model, UserInfoContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public UserInfoPresenter(UserInfoContract.Model model, UserInfoContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void changeName(String userName) {
        String op = "modify_username";
        String uid = Preference.get(Constant.UID, "0");
        Map<String, String> map = new HashMap<>();
        map.put("op", op);
        map.put("uid", uid);
        map.put("username", userName);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.changeName(map))
                .subscribe(new ErrorHandleSubscriber<ChangeNameBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull ChangeNameBean changeNameBean) {
                        ChangeNameBean.DataBean data = changeNameBean.getData();
                        if (changeNameBean == null || data == null) {

                            mRootView.showMessage(changeNameBean.getMsg());
                            return;
                        }
                        Message message = new Message();
                        message.what = 1;
                        SaveUserInfo.setUserName(data.getUsername());
                        mRootView.setData(message);
                        mRootView.showMessage(changeNameBean.getMsg());
                    }
                });
    }

    public void uploadPic(MultipartBody.Part body) {
        String op = "change_avater";
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
                        SaveUserInfo.setAvater(img_url);
                        Message message = new Message();
                        message.what = 2;
                        mRootView.setData(message);
                        mRootView.showMessage(uploadPicBean.getMsg());
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
