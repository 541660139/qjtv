package com.lwd.qjtv.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.lwd.qjtv.mvp.contract.WatchRecordeContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

@ActivityScope
public class WatchRecordePresenter extends BasePresenter<WatchRecordeContract.Model, WatchRecordeContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;
    private List<String> mList = new ArrayList<>();
    private DefaultAdapter mAdapter;
    private int page = 1;
    private boolean isFirst = true;
    private int preEndIndex;

    @Inject
    public WatchRecordePresenter(WatchRecordeContract.Model model, WatchRecordeContract.View rootView, RxErrorHandler handler
            , AppManager appManager, Application application) {
        super(model, rootView);
        this.mApplication = application;
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
    }

    public void requestWatchRecordeList(final boolean pullToRefresh) {
//        if (mAdapter == null) {
//            mAdapter = new_pic WatchRecordeAdapter(mList);
//            mRootView.setAdapter(mAdapter);//设置Adapter
//        }
//
//        //请求外部存储权限用于适配android6.0的权限管理机制
//        PermissionUtil.externalStorage(() -> {
//            //request permission success, do something.
//        }, mRootView.getRxPermissions(), mRootView, mErrorHandler);
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
//
//            RxUtils.applyListSchedulers(mRootView,mModel.getWatchRecorde(),pullToRefresh)
//                .subscribe(new_pic ErrorHandleSubscriber<List<String>>(mErrorHandler) {
//                    @Override
//                    public void onNext(List<String> list) {
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
