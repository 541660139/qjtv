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
import com.lwd.qjtv.mvp.contract.GameTabContract;
import com.lwd.qjtv.mvp.model.entity.GameDataBean;
import com.lwd.qjtv.mvp.ui.adapter.GameDataAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class GameTabPresenter extends BasePresenter<GameTabContract.Model, GameTabContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;

    @Inject
    AppManager mAppManager;


    private List<GameDataBean.DataBean> mList = new ArrayList<>();
    private GameDataAdapter mAdapter;
    private int page = 1;
    private boolean isFirst = true;
    private int preEndIndex;

    @Inject
    public GameTabPresenter(GameTabContract.Model model, GameTabContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }


    public void requestCollectionList(final boolean pullToRefresh, String id) {
        if (mAdapter == null) {
            mAdapter = new GameDataAdapter(mList, mApplication);
            mRootView.setAdapter(mAdapter);//设置Adapter
        }

        if (pullToRefresh) page = 1;//上拉刷新默认只请求第一页
        else if (page == 1) page = 2;

        //关于RxCache缓存库的使用请参考 http://www.jianshu.com/p/b58ef6b0624b

        boolean isEvictCache = pullToRefresh;//是否驱逐缓存,为ture即不使用缓存,每次上拉刷新即需要最新数据,则不使用缓存

        if (pullToRefresh && isFirst) {//默认在第一次上拉刷新时使用缓存
            isFirst = false;
            isEvictCache = false;
        }
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("appid", Constant.APPID);
        map.put("id", id);
        map.put("pt", Constant.PT);
        map.put("op", "game_list");
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyListSchedulers(mRootView, mModel.getGameData(map), pullToRefresh)
                .subscribe(new ErrorHandleSubscriber<GameDataBean>(mErrorHandler) {
                    @Override
                    public void onNext(GameDataBean list) {
                        if (list == null || list.getData() == null) {
                            if (mList == null || mList.size() == 0)
                                mRootView.setData(null);
//                            mRootView.showMessage("当前无更多数据~");
                            return;
                        }
                        if (!"1".equals(list.getStatus())) {
                            if (mList == null)
                                mRootView.setData(null);
//                            mRootView.showMessage(list.getMsg());
                            return;
                        }
                        List<GameDataBean.DataBean> data = list.getData();
//                        if (data.size() == 0 && !pullToRefresh && mList != null)
//                            mRootView.showMessage("当前无更多数据~");
                        if (pullToRefresh) mList.clear();//如果是上拉刷新则清空列表
                        preEndIndex = mList.size();//更新之前列表总长度,用于确定加载更多的起始位置
                        mList.addAll(data);
                        mRootView.setData(mList);
                        if (pullToRefresh)
                            mAdapter.notifyDataSetChanged();
                        else {
                            page++;
                            mAdapter.notifyItemRangeInserted(preEndIndex, data.size());
                        }
                    }

                });
    }

}
