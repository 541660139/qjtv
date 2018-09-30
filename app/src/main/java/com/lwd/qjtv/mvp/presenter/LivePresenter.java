package com.lwd.qjtv.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.RxUtils;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.contract.LiveContract;
import com.lwd.qjtv.mvp.model.entity.BaseBean;
import com.lwd.qjtv.mvp.model.entity.LiveParserBean;
import com.lwd.qjtv.mvp.model.entity.VideoParse;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

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
 * Created by ZhengQian on 2017/6/6.
 */

@ActivityScope
public class LivePresenter extends BasePresenter<LiveContract.Model, LiveContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public LivePresenter(LiveContract.Model model, LiveContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void parseVideoWithParams(String jsonParams, final boolean isDownloladParse) {
        Observable.just(WEApplication.getParseModule().parse(jsonParams))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .observeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<LiveParserBean>>() {
                    @Override
                    public ObservableSource<LiveParserBean> apply(@NonNull String resultStr) throws Exception {
                        Log.d(TAG, "apply: " + resultStr);
                        LiveParserBean mVideoParse = new Gson().fromJson(resultStr, LiveParserBean.class);
                        return Observable.just(mVideoParse);
                    }
                }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        if (mRootView != null)
                            mRootView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (mRootView != null)
                            mRootView.hideLoading();
                    }
                })
                .subscribe(new Observer<LiveParserBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull LiveParserBean videoParse) {
                        if (videoParse == null || videoParse.getCode() != 0) {
                            UiUtils.SnackbarText("资源解析失败");
                            return;
                        }
                        // 回调v中的方法，解析完成
                        if (isDownloladParse) {
                            if (mRootView != null)
                                mRootView.videoDownloadParseFinish(videoParse);
                        } else {
                            if (mRootView != null)
                                mRootView.videoParseFinish(videoParse);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * v-[0] : "Referer: http://www.acfun.cn/"
     * v-[1] : "User-Agent: acfun0.37818449964081713"
     *
     * @param streamsBean
     * @return
     */
    public Map<String, String> getHeaders(VideoParse.DataBean.StreamsBean streamsBean) {
        HashMap<String, String> headersMap = new HashMap<>();
        if (streamsBean.getHeaders() != null && streamsBean.getHeaders().size() != 0) {
            for (String header : streamsBean.getHeaders()) {
                if (header != null && header.contains("Referer")) {
                    headersMap.put("Referer", (header.substring("Referer".length() + 2)).trim());
                } else if (header != null && header.contains("User-Agent")) {
                    headersMap.put("User-Agent", (header.substring("User-Agent".length() + 2)).trim());
                }
            }
        }
        return headersMap;
    }

    public void liveUser(boolean isEnter, String liveId) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", Constant.APPID);
        map.put("op", isEnter ? "jin" : "chu");
        map.put("match_live_id", liveId);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.liveUser(map))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseBean baseBean) {

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
