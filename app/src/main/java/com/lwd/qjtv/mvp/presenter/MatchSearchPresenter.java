package com.lwd.qjtv.mvp.presenter;

import android.app.Application;
import android.os.Message;

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
import com.lwd.qjtv.mvp.contract.MatchSearchContract;
import com.lwd.qjtv.mvp.model.entity.HotSearchCollectionBean;
import com.lwd.qjtv.mvp.model.entity.SearchCollectionBean;
import com.lwd.qjtv.mvp.ui.adapter.SearchMatchCollectionAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ActivityScope
public class MatchSearchPresenter extends BasePresenter<MatchSearchContract.Model, MatchSearchContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private List<SearchCollectionBean.DataBean> mList = new ArrayList<>();
    private DefaultAdapter mAdapter;
    private int page = 1;
    private boolean isFirst = true;
    private int preEndIndex;

    @Inject
    public MatchSearchPresenter(MatchSearchContract.Model model, MatchSearchContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void requestSearchList(boolean pullToRefresh, String words, String id) {
        if (mAdapter == null) {
            mAdapter = new SearchMatchCollectionAdapter(mList);
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
        map.put("bigMatch_id", id);
        map.put("words", words);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyListSchedulers(mRootView, mModel.searchMatchCollection(map), pullToRefresh)
                .subscribe(new ErrorHandleSubscriber<SearchCollectionBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull SearchCollectionBean list) {
                        if (list == null || list.getData() == null) {
//                            mRootView.setData(list == null ? list : list.getData());
                            mRootView.showMessage("当前无更多数据~");
                            return;
                        }
                        if (!"1".equals(list.getStatus())) {
                            mRootView.showMessage(list.getMsg());
                            return;
                        }
                        List<SearchCollectionBean.DataBean> data = list.getData();
                        if (data.size() == 0 && !pullToRefresh && mList != null)
                            mRootView.showMessage("当前无更多数据~");
                        if (pullToRefresh) mList.clear();//如果是上拉刷新则清空列表
                        preEndIndex = mList.size();//更新之前列表总长度,用于确定加载更多的起始位置
                        mList.addAll(data);
                        Message message = new Message();
                        message.what = 1;
                        message.obj = mList;
                        mRootView.setData(message);
                        if (pullToRefresh)
                            mAdapter.notifyDataSetChanged();
                        else {
                            page++;
                            mAdapter.notifyItemRangeInserted(preEndIndex, data.size());
                        }
                    }
                });
    }

    public void getHotSearch() {
        Map<String, String> map = new HashMap<>();
        map.put("op", "getHotSearch");
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.getHotSearchMatchCollection(map))
                .subscribe(new ErrorHandleSubscriber<HotSearchCollectionBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull HotSearchCollectionBean hotSearchCollectionBean) {
                        if (hotSearchCollectionBean != null && "1".equals(hotSearchCollectionBean.getStatus())) {
                            Message message = new Message();
                            message.what = 2;
                            message.obj = hotSearchCollectionBean.getData();
                            mRootView.setData(message);
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
