package com.lwd.qjtv.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.RxUtils;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.contract.LearnBallVideoDetailsContract;
import com.lwd.qjtv.mvp.model.entity.BaseBean;
import com.lwd.qjtv.mvp.model.entity.CommentMessageBean;
import com.lwd.qjtv.mvp.model.entity.LearnBallDetailsBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/13.
 */

@ActivityScope
public class LearnBallVideoDetailsPresenter extends BasePresenter<LearnBallVideoDetailsContract.Model, LearnBallVideoDetailsContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;
    private List<CommentMessageBean.DataBean.CommentsListBean> mList = new ArrayList<>();
//    private DefaultAdapter mAdapter;
    private int page = 1;
    private boolean isFirst = true;
    private int preEndIndex;

    @Inject
    public LearnBallVideoDetailsPresenter(LearnBallVideoDetailsContract.Model model, LearnBallVideoDetailsContract.View rootView, RxErrorHandler handler
            , AppManager appManager, Application application) {
        super(model, rootView);
        this.mApplication = application;
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
    }

    public void requestCommentMessageList(final boolean pullToRefresh, String videoId, String videoType) {
//        if (mAdapter == null) {
//            mAdapter = new CommentMessageAdapter(mList);
//            mRootView.setAdapter(mAdapter);//设置Adapter
//        }

//        //请求外部存储权限用于适配android6.0的权限管理机制
//        PermissionUtil.externalStorage(() -> {
//            //request permission success, do something.
//        }, mRootView.getRxPermissions(), mRootView, mErrorHandler);


        if (pullToRefresh) page = 1;//上拉刷新默认只请求第一页
        else if (page == 1) page = 2;

        //关于RxCache缓存库的使用请参考 http://www.jianshu.com/p/b58ef6b0624b

        boolean isEvictCache = pullToRefresh;//是否驱逐缓存,为ture即不使用缓存,每次上拉刷新即需要最新数据,则不使用缓存

        if (pullToRefresh && isFirst) {//默认在第一次上拉刷新时使用缓存
            isFirst = false;
            isEvictCache = false;
        }
        Map<String, String> map = new TreeMap<>();
        map.put("video_id", videoId);
        map.put("v_type", videoType);
        map.put("page", page + "");
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyListSchedulers(mRootView, mModel.getCommentMessage(map), pullToRefresh)
                .subscribe(new ErrorHandleSubscriber<CommentMessageBean>(mErrorHandler) {
                    @Override
                    public void onNext(CommentMessageBean commentMessageBean) {
                        if (commentMessageBean == null || commentMessageBean.getData() == null){
                            mRootView.showMessage("当前无更多数据~");
                            return;
                        }
                        if (!"1".equals(commentMessageBean.getStatus())) {
                            mRootView.showMessage(commentMessageBean.getMsg());
                            return;
                        }
                        List<CommentMessageBean.DataBean.CommentsListBean> list = commentMessageBean.getData().getComments_list();
                        if(list.size() == 0&& !pullToRefresh && mList != null)
                            mRootView.showMessage("当前无更多数据~");
                        if (pullToRefresh) mList.clear();//如果是上拉刷新则清空列表
                        preEndIndex = mList.size();//更新之前列表总长度,用于确定加载更多的起始位置
                        mList.addAll(list);
                        mRootView.setData(mList);
//                        if (pullToRefresh)
//                            mAdapter.notifyDataSetChanged();
//                        else {
//                            page++;
//                            mAdapter.notifyItemRangeInserted(preEndIndex, list.size());
//                        }
                    }
                });
    }

    public void addMessage(String videoId, String videoType, String content) {
        Map<String, String> map = new TreeMap<>();
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
        RxUtils.applyNormalSchedulers(mRootView, mModel.addCommentMessage(map))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseBean s) {
                        if (s != null)
                            mRootView.showMessage(s.getMsg());
                        requestCommentMessageList(true, videoId, videoType);
                    }
                });
    }

    public void likeComment(String videoId, String videoType, String commentId) {
        Map<String, String> map = new HashMap<>();
        map.put("comments_id", commentId);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.likeComment(map))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseBean s) {
                        if (s != null)
                            mRootView.showMessage(s.getMsg());
                        requestCommentMessageList(true, videoId, videoType);
                    }
                });
    }

    public void getDetailsInfo(String starId) {
        String ac = "intro";
        String page = "1";
        Map<String, String> map = new TreeMap<>();
        map.put("ac", ac);
        map.put("page", page);
        map.put("starId", starId);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.getLearnBallDetails(map))
                .subscribe(new ErrorHandleSubscriber<LearnBallDetailsBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull LearnBallDetailsBean learnBallDetailsBean) {
                        LearnBallDetailsBean.DataBean data = learnBallDetailsBean.getData();
                        if (learnBallDetailsBean == null || data == null || !"1".equals(learnBallDetailsBean.getStatus())) {
                            mRootView.showMessage(learnBallDetailsBean.getMsg());
                            return;
                        }
                        mRootView.setData(data);
                    }
                });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        this.mAdapter = null;
        this.mList = null;
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }
}
