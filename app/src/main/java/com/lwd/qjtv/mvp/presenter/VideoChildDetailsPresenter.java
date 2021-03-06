package com.lwd.qjtv.mvp.presenter;

import android.app.Application;
import android.os.Message;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.RxUtils;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.contract.VideoChildDetailsContract;
import com.lwd.qjtv.mvp.model.entity.AddCollectionBean;
import com.lwd.qjtv.mvp.model.entity.RelatedVideoBean;
import com.lwd.qjtv.mvp.ui.adapter.WonderfulVideoAdapter;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/5.
 */

@ActivityScope
public class VideoChildDetailsPresenter extends BasePresenter<VideoChildDetailsContract.Model, VideoChildDetailsContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;
    private List<RelatedVideoBean.DataBean> mList = new ArrayList<>();
    private DefaultAdapter mAdapter;
    private int page = 1;
    private boolean isFirst = true;
    private int preEndIndex;

    @Inject
    public VideoChildDetailsPresenter(VideoChildDetailsContract.Model model, VideoChildDetailsContract.View rootView, RxErrorHandler handler
            , AppManager appManager, Application application) {
        super(model, rootView);
        this.mApplication = application;
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
    }

    public void addVideoCollection(String video_id, String v_type) {
        String ac = "addFavorite";
        Map<String, String> map = new HashMap<>();
        map.put("video_id", video_id);
        map.put("v_type", v_type);
        map.put("ac", ac);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.addVideoCollection(map))
                .subscribe(new ErrorHandleSubscriber<AddCollectionBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull AddCollectionBean s) {
                        if (s == null || s.getData() == null || !"1".equals(s.getStatus())) {
                            mRootView.showMessage(s.getMsg());
                            return;
                        }
                        mRootView.showMessage(s.getMsg());
                        Message message = new Message();
                        message.what = 1;
                        mRootView.setData(message);
                    }
                });
    }

    public void requestVideoChildDetailsList(String videoId, String videoType, String op, String starId, final boolean pullToRefresh) {
        if (mAdapter == null) {
            mAdapter = new WonderfulVideoAdapter(mList);
            mRootView.setAdapter(mAdapter);//设置Adapter
        }

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
        Map<String, String> map = new HashMap<>();
        map.put("video_id", videoId);
        map.put("v_type", videoType);
        map.put("starId", starId);
        map.put("op", op);
        map.put("page", page + "");
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyListSchedulers(mRootView, mModel.getRelatedVideoList(map), pullToRefresh)
                .subscribe(new ErrorHandleSubscriber<RelatedVideoBean>(mErrorHandler) {
                    @Override
                    public void onNext(RelatedVideoBean relatedVideoBean) {
                        if (relatedVideoBean == null || relatedVideoBean.getData() == null) {
//                            mRootView.showMessage("当前无更多数据~");
                            return;
                        }
                        if (!"1".equals(relatedVideoBean.getStatus())) {
                            mRootView.showMessage(relatedVideoBean.getMsg());
                            return;
                        }
                        List<RelatedVideoBean.DataBean> list = relatedVideoBean.getData();
//                            mRootView.showMessage("当前无更多数据~");
                        if (pullToRefresh) mList.clear();//如果是上拉刷新则清空列表
                        preEndIndex = mList.size();//更新之前列表总长度,用于确定加载更多的起始位置
                        mList.addAll(list);
                        if (pullToRefresh)
                            mAdapter.notifyDataSetChanged();
                        else {
                            page++;
                            mAdapter.notifyItemRangeInserted(preEndIndex, list.size());
                        }
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAdapter = null;
        this.mList = null;
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }
}
