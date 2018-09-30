package com.lwd.qjtv.mvp.presenter;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.RxUtils;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.contract.LearnBallDetailsContract;
import com.lwd.qjtv.mvp.model.entity.PingFenBean;
import com.lwd.qjtv.mvp.model.entity.VideoDetailsBean;
import com.lwd.qjtv.mvp.model.entity.VideoParse;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
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
 * Created by ZhengQian on 2017/5/24.
 */

@ActivityScope
public class LearnBallDetailsPresenter extends BasePresenter<LearnBallDetailsContract.Model, LearnBallDetailsContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public LearnBallDetailsPresenter(LearnBallDetailsContract.Model model, LearnBallDetailsContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void getLearnBallVideo(String videoId, String videoType) {
        Map<String, String> map = new HashMap<>();
        map.put("v_type", videoType);
        map.put("video_id", videoId);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.getVideoDetails(map))
                .subscribe(new ErrorHandleSubscriber<VideoDetailsBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull VideoDetailsBean videoDetailsBean) {
                        if (videoDetailsBean == null || videoDetailsBean.getData() == null || !"1".equals(videoDetailsBean.getStatus())) {
                            mRootView.setData(-1);
                            if(!"1".equals(videoDetailsBean.getStatus()))
                                mRootView.showMessage(videoDetailsBean.getMsg());
                            return;
                        }
                        mRootView.setData(videoDetailsBean.getData());
                    }
                });
    }

    public void getPingFen(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("video_id", id);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.addPingFen(map))
                .subscribe(new ErrorHandleSubscriber<PingFenBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull PingFenBean pingFenBean) {
                        if (pingFenBean == null) {
                            if (pingFenBean.getData() != null) {
                                mRootView.setData(pingFenBean.getData());
                            }
                        }
                    }
                });
    }

//    public void getLearnBallDetails(String starId) {
//        String ac = "intro";
//        String page = "1";
//        Map<String, String> map = new_pic HashMap<>();
//        map.put("ac", ac);
//        map.put("page", page);
//        map.put("starId", starId);
//        map.put("appid", Constant.APPID);
//        map.put("pt", Constant.PT);
//        map.put("ver", WEApplication.getApp_ver());
//        map.put("imei", WEApplication.getIMEI());
//        map.put("uid", Preference.get(Constant.UID, "0"));
//        map.put("t", System.currentTimeMillis() + "");
//        map.put("sign", Utils.buildSign(map, Constant.key));
//        RxUtils.applyNormalSchedulers(mRootView, mModel.getLearnBallDetails(ac, starId, page, map))
//                .subscribe(new_pic ErrorHandleSubscriber<LearnBallDetailsBean>(mErrorHandler) {
//                    @Override
//                    public void onNext(@NonNull LearnBallDetailsBean learnBallDetailsBean) {
//                        LearnBallDetailsBean.DataBean data = learnBallDetailsBean.getData();
//                        if (learnBallDetailsBean == null || data == null || !"1".equals(learnBallDetailsBean.getStatus())) {
//                            mRootView.showMessage(learnBallDetailsBean.getMsg());
//                            return;
//                        }
//                        mRootView.setData(data);
//                    }
//                });
//    }

    public void parseVideoWithParams(String jsonParams, final boolean isDownloladParse) {
        Observable.just(WEApplication.getParseModule().parse(jsonParams))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .observeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<VideoParse>>() {
                    @Override
                    public ObservableSource<VideoParse> apply(@NonNull String resultStr) throws Exception {
                        Log.d("getOrigin", "4:" + resultStr);
                        VideoParse mVideoParse = new Gson().fromJson(resultStr, VideoParse.class);

                        return Observable.just(mVideoParse);
                    }
                }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mRootView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        mRootView.hideLoading();
                    }
                })
                .subscribe(new ErrorHandleSubscriber<VideoParse>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VideoParse videoParse) {
                        if (videoParse == null || videoParse.getCode() != 0) {
                            Toast.makeText(mApplication, "加载中...", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // 回调v中的方法，解析完成
                        if (isDownloladParse) {
                            mRootView.videoDownloadParseFinish(videoParse);
                        }else{
                            mRootView.videoParseFinish(videoParse);
                        }
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




    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

}
