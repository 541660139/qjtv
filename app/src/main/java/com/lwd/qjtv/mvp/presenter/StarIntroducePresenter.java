package com.lwd.qjtv.mvp.presenter;

import android.app.Application;

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
import com.lwd.qjtv.mvp.contract.StarIntroduceContract;
import com.lwd.qjtv.mvp.model.entity.BaseBean;
import com.lwd.qjtv.mvp.model.entity.StarCommentBean;
import com.lwd.qjtv.mvp.model.entity.StarDetailsBean;
import com.lwd.qjtv.mvp.ui.adapter.StarCommentAdapter;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/5.
 */

@ActivityScope
public class StarIntroducePresenter extends BasePresenter<StarIntroduceContract.Model, StarIntroduceContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;
    private List<StarCommentBean.DataBean.CommentsListBean> mList = new ArrayList<>();
    private DefaultAdapter mAdapter;
    private int page = 1;
    private boolean isFirst = true;
    private int preEndIndex;

    @Inject
    public StarIntroducePresenter(StarIntroduceContract.Model model, StarIntroduceContract.View rootView, RxErrorHandler handler
            , AppManager appManager, Application application) {
        super(model, rootView);
        this.mApplication = application;
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
    }

    public void getStarIntro(String starId, boolean pullToRefresh) {
        Map<String, String> map = new HashMap<>();
        map.put("starId", starId);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.getStarDetails(map))
                .subscribe(new ErrorHandleSubscriber<StarDetailsBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull StarDetailsBean starDetailsBean) {
                        StarDetailsBean.DataBean data = starDetailsBean.getData();
                        if (starDetailsBean == null || data == null)
                            return;
                        if (!"1".equals(starDetailsBean.getStatus())) {
                            mRootView.showMessage(starDetailsBean.getMsg());
                            return;
                        }
                        mRootView.setData(data);
                    }
                });
    }

    public void likeComment(String starId, String commentId) {
        Map<String, String> map = new HashMap<>();
        map.put("comments_id", commentId);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.likeStarComment(map))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseBean baseBean) {
//                        if (baseBean != null)
//                            mRootView.showMessage(baseBean.getMsg());
                        getStarComment(starId, true);
                    }
                });
    }


    public void addStarComment(String starId, String content) {
        Map<String, String> map = new HashMap<>();
        map.put("starId", starId);
        map.put("content", content);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.addStarComment(map))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseBean baseBean) {
                        getStarComment(starId, true);
                    }
                });
//                .subscribe(new_pic ErrorHandleSubscriber<String>(mErrorHandler) {
//                    @Override
//                    public void onNext(@NonNull String s) {
//                        Log.d(TAG, "onNext: " + s);
//                        try {
//                            JSONObject jsonObject = new_pic JSONObject(s);
//                            String msg = jsonObject.getString("msg");
//                            mRootView.showMessage(msg);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });

    }

    public void getStarComment(String starId, final boolean pullToRefresh) {
        if (mAdapter == null) {
            mAdapter = new StarCommentAdapter(mList);
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
        map.put("starId", starId);
        map.put("appid", Constant.APPID);
        map.put("page", page + "");
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyListSchedulers(mRootView, mModel.getStarCommentList(map), pullToRefresh)
                .subscribe(new ErrorHandleSubscriber<StarCommentBean>(mErrorHandler) {
                    @Override
                    public void onNext(StarCommentBean list) {
                        if (list == null || list.getData() == null) {
//                            mRootView.showMessage("当前无数据~");
                            return;
                        }
//                        mRootView.setData(list.getData());
                        List<StarCommentBean.DataBean.CommentsListBean> comments_list = list.getData().getComments_list();
//                            mRootView.showMessage("当前无更多数据~");
                        if (pullToRefresh) mList.clear();//如果是上拉刷新则清空列表
                        preEndIndex = mList.size();//更新之前列表总长度,用于确定加载更多的起始位置
                        mList.addAll(comments_list);
                        if (pullToRefresh)
                            mAdapter.notifyDataSetChanged();
                        else {
                            page++;
                            mAdapter.notifyItemRangeInserted(preEndIndex, comments_list.size());
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
