package com.lwd.qjtv.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.RxUtils;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.contract.LikeAvaterListContract;
import com.lwd.qjtv.mvp.model.entity.LikeAvaterListBean;
import com.lwd.qjtv.mvp.ui.adapter.LikeAvaterAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ActivityScope
public class LikeAvaterListPresenter extends BasePresenter<LikeAvaterListContract.Model, LikeAvaterListContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private List<LikeAvaterListBean.DataBean> mList = new ArrayList<>();
    private DefaultAdapter mAdapter;
    private int page = 1;
    private boolean isFirst = true;
    private int preEndIndex;

    @Inject
    public LikeAvaterListPresenter(LikeAvaterListContract.Model model, LikeAvaterListContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void requestList(final boolean pullToRefresh,String id) {
        if (mAdapter == null) {
            mAdapter = new LikeAvaterAdapter(mList);
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
        map.put("op", "avater_list");
        map.put("card_id", id);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyListSchedulers(mRootView, mModel.getLikeAvaterList(map), pullToRefresh)
                .subscribe(new ErrorHandleSubscriber<LikeAvaterListBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull LikeAvaterListBean likeAvaterListBean) {
                        if (likeAvaterListBean == null || likeAvaterListBean.getData() == null) {
//                            mRootView.showMessage("当前无更多数据~");
                            return;
                        }
                        if (!"1".equals(likeAvaterListBean.getStatus())) {
                            mRootView.showMessage(likeAvaterListBean.getMsg());
                            return;
                        }
                        List<LikeAvaterListBean.DataBean> list = likeAvaterListBean.getData();
//                        if (list.size() == 0&& !pullToRefresh && mList != null)
//                            mRootView.showMessage("当前无更多数据~");
                        if (pullToRefresh) mList.clear();//如果是上拉刷新则清空列表
                        preEndIndex = mList.size();//更新之前列表总长度,用于确定加载更多的起始位置
                        mList.addAll(list);
                        mRootView.setData(mList);
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
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

}
