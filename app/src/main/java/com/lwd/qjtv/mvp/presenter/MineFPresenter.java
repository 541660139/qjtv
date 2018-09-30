package com.lwd.qjtv.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.RxUtils;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.contract.MineFContract;
import com.lwd.qjtv.mvp.model.entity.BaseBean;
import com.lwd.qjtv.mvp.model.entity.FansOrFollerBean;
import com.lwd.qjtv.mvp.ui.adapter.FansOrFollowAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class MineFPresenter extends BasePresenter<MineFContract.Model, MineFContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;

    @Inject
    AppManager mAppManager;


    private int page = 1;
    private boolean isFirst = true;
    private int preEndIndex;

    //    数据
    private List<FansOrFollerBean.DataBean> fansOrFollerList = new ArrayList<>();
    private FansOrFollowAdapter mAdapter;

    @Inject
    public MineFPresenter(MineFContract.Model model, MineFContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }

    public void getPersonalAll(int type, boolean isPullToRefresh, String keyword) {
        if (mAdapter == null) {
            mAdapter = new FansOrFollowAdapter(fansOrFollerList);
            mRootView.setAdapter(mAdapter);//设置Adapter
        }

        if (isPullToRefresh) page = 1;//上拉刷新默认只请求第一页
        else if (page == 1) page = 2;

        //关于RxCache缓存库的使用请参考 http://www.jianshu.com/p/b58ef6b0624b

        boolean isEvictCache = isPullToRefresh;//是否驱逐缓存,为ture即不使用缓存,每次上拉刷新即需要最新数据,则不使用缓存

        if (isPullToRefresh && isFirst) {//默认在第一次上拉刷新时使用缓存
            isFirst = false;
            isEvictCache = false;
        }

        Map<String, String> map = new HashMap<>();

        if (!TextUtils.isEmpty(keyword)) {
            map.put("keyword", keyword);
        }
        if (type == 1) {
//            关注

            map.put("op", "follow_list");
        } else {
//            粉丝
            map.put("op", "fans_list");
        }
        map.put("page", page + "");
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));


        RxUtils.applyListSchedulers(mRootView, mModel.getPersonalAll(map), isPullToRefresh)
                .subscribe(new ErrorHandleSubscriber<FansOrFollerBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull FansOrFollerBean t) {

                        if (isPullToRefresh)
                            mRootView.hideLoading();
                        else
                            mRootView.endLoadMore();
                        if (t == null || t.getData() == null) {
                            if (fansOrFollerList == null || fansOrFollerList.size() == 0)
                                mRootView.setData(null);
                            return;
                        }
                        if (!"1".equals(t.getStatus())) {
                            if (fansOrFollerList == null)
                                mRootView.setData(null);
                            mRootView.showMessage(t.getMsg());
                            return;
                        }

                        List<FansOrFollerBean.DataBean> video_list = t.getData();

                        if (isPullToRefresh) fansOrFollerList.clear();//如果是上拉刷新则清空列表
                        preEndIndex = fansOrFollerList.size();//更新之前列表总长度,用于确定加载更多的起始位置
                        fansOrFollerList.addAll(video_list);
                        mRootView.setData(fansOrFollerList);
                        if (isPullToRefresh)
                            mAdapter.notifyDataSetChanged();
                        else {
                            page++;
                            mAdapter.notifyItemRangeInserted(preEndIndex, video_list.size());
                        }

                    }
                });

    }


    public void setFollow(String starid, int type) {

        Map<String, String> map = new HashMap<>();
        if (type != 1) {
//            取消粉丝
            map.put("op", "follow_fan");
        }
        map.put("page", "1");
        map.put("star_id", starid);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.setFollow(map))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseBean learnBallBean) {

                        mRootView.setFollowData(learnBallBean);
                        if (learnBallBean != null && learnBallBean.getMsg() != null)
                            mRootView.showMessage(learnBallBean.getMsg());
                    }
                });
    }
}
