package com.lwd.qjtv.mvp.presenter;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.RxUtils;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.contract.VideoDetailsContract;
import com.lwd.qjtv.mvp.model.entity.BaseBean;
import com.lwd.qjtv.mvp.model.entity.DianZanBean;
import com.lwd.qjtv.mvp.model.entity.VideoDetailsNewBean;
import com.lwd.qjtv.mvp.model.entity.VideoParse;
import com.lwd.qjtv.mvp.ui.adapter.CommentMessageAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

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
public class VideoDetailsPresenter extends BasePresenter<VideoDetailsContract.Model, VideoDetailsContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;


    private int page = 1;
    private boolean isFirst = true;
    private int preEndIndex;

    private DefaultAdapter mAdapter;

    private List<VideoDetailsNewBean.DataBean.VideoReviewBean> mList = new ArrayList<>();

    @Inject
    public VideoDetailsPresenter(VideoDetailsContract.Model model, VideoDetailsContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void getVideoDetails(String id, String type, final boolean pullToRefresh) {

        if (mAdapter == null) {
            mAdapter = new CommentMessageAdapter(mList);
            mRootView.setAdapter(mAdapter);//设置Adapter
        }
        if (pullToRefresh) page = 1;//上拉刷新默认只请求第一页
        else if (page == 1) page = 2;


        Map<String, String> map = new HashMap<>();
        map.put("video_id", id);
        map.put("v_type", type);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("page", page + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.getVideoDetailsNew(map))
                .subscribe(new ErrorHandleSubscriber<VideoDetailsNewBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull VideoDetailsNewBean videoDetailsBean) {

                        if (pullToRefresh)
                            mRootView.hideLoading();
                        else
                            mRootView.endLoadMore();
                        VideoDetailsNewBean.DataBean data = videoDetailsBean.getData();
                        if (videoDetailsBean == null || data == null) {
//                            mRootView.setData(-1);
//                                mRootView.showMessage("当前无更多数据~");
                            return;
                        }
                        if (!"1".equals(videoDetailsBean.getStatus())) {
//                            mRootView.setData(-1);
                            mRootView.showMessage(videoDetailsBean.getMsg());
                            return;
                        }
                        mRootView.setData(data);

                        List<VideoDetailsNewBean.DataBean.VideoReviewBean> video_review = videoDetailsBean.getData().getVideo_review();
                        if (video_review != null && video_review.size() > 0) {
                            if (pullToRefresh) mList.clear();//如果是上拉刷新则清空列表
                            preEndIndex = mList.size();//更新之前列表总长度,用于确定加载更多的起始位置
                            mList.addAll(video_review);
                            mRootView.setData(mList);
                            if (pullToRefresh)
                                mAdapter.notifyDataSetChanged();
                            else {
                                page++;
                                mAdapter.notifyItemRangeInserted(preEndIndex, video_review.size());
                            }
                        }
                    }
                });
    }

//    public void getPingFen(String id) {
//        Map<String, String> map = new HashMap<>();
//        map.put("video_id", id);
//        map.put("appid", Constant.APPID);
//        map.put("pt", Constant.PT);
//        map.put("ver", WEApplication.getApp_ver());
//        map.put("imei", WEApplication.getIMEI());
//        map.put("uid", Preference.get(Constant.UID, "0"));
//        map.put("t", System.currentTimeMillis() + "");
//        map.put("sign", Utils.buildSign(map, Constant.key));
//        RxUtils.applyNormalSchedulers(mRootView, mModel.addPingFen(map))
//                .subscribe(new ErrorHandleSubscriber<PingFenBean>(mErrorHandler) {
//                    @Override
//                    public void onNext(@NonNull PingFenBean pingFenBean) {
//                        if (pingFenBean != null) {
//                            if (pingFenBean.getData() != null) {
//                                mRootView.setData(pingFenBean.getData());
//                            }
//                        }
//                    }
//                });

//    }

    public void setCollect(String videoId) {

        Map<String, String> map = new HashMap<>();
        map.put("video_id", videoId);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        map.put("op", "collect");
        RxUtils.applyNormalSchedulers(mRootView, mModel.setCollect(map))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseBean s) {
                        if (s != null) {
                            mRootView.setScState(s.getStatus());
                            mRootView.showMessage(s.getMsg());
                        }

                    }
                });
    }

    public void addMessage(String videoId, String videoType, String str) {
        String content = Utils.toUtf8(str);
        Map<String, String> map = new HashMap<>();
        map.put("video_id", videoId);
        map.put("v_type", videoType);
        map.put("content", content);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        map.put("op", "review");
        RxUtils.applyNormalSchedulers(mRootView, mModel.addCommentMessage(map))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseBean s) {
                        if (s != null)
                            mRootView.showMessage(s.getMsg());
                        getVideoDetails(videoId, videoType, true);
                    }
                });
    }

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
//                        mRootView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
//                        mRootView.hideLoading();
                    }
                })
                .subscribe(videoParse -> {
                    if (videoParse == null || videoParse.getCode() != 0) {
                        Toast.makeText(mApplication, "加载中...", Toast.LENGTH_SHORT).show();
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


//    public void requestCommentMessageList(final boolean pullToRefresh, String videoId, String videoType) {
//        if (mAdapter == null) {
//            mAdapter = new CommentMessageAdapter(mList);
//            mRootView.setAdapter(mAdapter);//设置Adapter
//        }
//
////        //请求外部存储权限用于适配android6.0的权限管理机制
////        PermissionUtil.externalStorage(() -> {
////            //request permission success, do something.
////        }, mRootView.getRxPermissions(), mRootView, mErrorHandler);
//
//
//        if (pullToRefresh) page = 1;//上拉刷新默认只请求第一页
//        else if (page == 1) page = 2;
//
//        //关于RxCache缓存库的使用请参考 http://www.jianshu.com/p/b58ef6b0624b
//
//        boolean isEvictCache = pullToRefresh;//是否驱逐缓存,为ture即不使用缓存,每次上拉刷新即需要最新数据,则不使用缓存
//
//        if (pullToRefresh && isFirst) {//默认在第一次上拉刷新时使用缓存
//            isFirst = false;
//            isEvictCache = false;
//        }
//        Map<String, String> map = new HashMap<>();
//        map.put("video_id", videoId);
//        map.put("v_type", videoType);
//        map.put("page", page + "");
//        map.put("appid", Constant.APPID);
//        map.put("pt", Constant.PT);
//        map.put("ver", WEApplication.getApp_ver());
//        map.put("imei", WEApplication.getIMEI());
//        map.put("uid", Preference.get(Constant.UID, "0"));
//        map.put("t", System.currentTimeMillis() + "");
//        map.put("sign", Utils.buildSign(map, Constant.key));
//        RxUtils.applyListSchedulers((IViewList) mRootView, mModel.getCommentMessage(map), pullToRefresh)
//                .subscribe(new ErrorHandleSubscriber<CommentMessageBean>(mErrorHandler) {
//                    @Override
//                    public void onNext(CommentMessageBean commentMessageBean) {
//                        if (commentMessageBean == null || commentMessageBean.getData() == null) {
////                            mRootView.showMessage("当前无更多数据~");
//                            return;
//                        }
//                        if (!"1".equals(commentMessageBean.getStatus())) {
//                            mRootView.showMessage(commentMessageBean.getMsg());
//                            return;
//                        }
//                        List<CommentMessageBean.DataBean.CommentsListBean> list = commentMessageBean.getData().getComments_list();
////                        if (list.size() == 0&& !pullToRefresh && mList != null)
////                            mRootView.showMessage("当前无更多数据~");
//                        if (pullToRefresh) mList.clear();//如果是上拉刷新则清空列表
//                        preEndIndex = mList.size();//更新之前列表总长度,用于确定加载更多的起始位置
//                        mList.addAll(list);
//                        mRootView.setData(mList);
//                        if (pullToRefresh)
//                            mAdapter.notifyDataSetChanged();
//                        else {
//                            page++;
//                            mAdapter.notifyItemRangeInserted(preEndIndex, list.size());
//                        }
//                    }
//                });
//    }


    public void likeComment(String videoId, String videoType, String review_id) {
        Map<String, String> map = new HashMap<>();
//        map.put("comments_id", commentId);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("video_id", videoId);

        map.put("op", "review_zan");
        map.put("review_id", review_id);
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.likeComment(map))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseBean s) {
                        if (s != null)
                            mRootView.showMessage(s.getMsg());
                        getVideoDetails(videoId, videoType, true);
                    }
                });
    }

    public void likeVideo(String videoId) {
        if (Preference.get(Constant.UID, "0").equals("0")) {
            mRootView.showMessage("请先登录");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("video_id", videoId);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.likeVideo(map))
                .subscribe(new ErrorHandleSubscriber<DianZanBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull DianZanBean s) {
                        if (s != null) {
                            mRootView.showMessage(s.getMsg());
                            mRootView.setVideoZan(s);
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
