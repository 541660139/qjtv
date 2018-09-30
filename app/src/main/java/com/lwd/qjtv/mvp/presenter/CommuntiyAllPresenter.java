package com.lwd.qjtv.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.RxUtils;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.contract.CommuntiyAllContract;
import com.lwd.qjtv.mvp.model.entity.BaseBean;
import com.lwd.qjtv.mvp.model.entity.CommunityDataBean;
import com.lwd.qjtv.mvp.ui.adapter.CommunityAllAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@FragmentScope
public class CommuntiyAllPresenter extends BasePresenter<CommuntiyAllContract.Model, CommuntiyAllContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    private int page = 1;
    private boolean isFirst = true;
    private int preEndIndex;
    List<String> data = new ArrayList<>();
    //    精选数据
    private List<CommunityDataBean.DataBean> mChoiceList = new ArrayList<>();
    private CommunityAllAdapter mAdapter;

    @Inject
    public CommuntiyAllPresenter(CommuntiyAllContract.Model model, CommuntiyAllContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getCommunity(boolean isPullToRefresh, String cat_id) {
        if (mAdapter == null) {
            mAdapter = new CommunityAllAdapter(mChoiceList);
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

        map.put("page", page + "");
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("unamekey", SaveUserInfo.getUnamekey());
        map.put("sign", Utils.buildSign(map, Constant.key));
        map.put("cat_id", cat_id);

        RxUtils.applyListSchedulers(mRootView, mModel.getCommunity(map), isPullToRefresh)
                .subscribe(new ErrorHandleSubscriber<CommunityDataBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull CommunityDataBean t) {

                        if (isPullToRefresh)
                            mRootView.hideLoading();
                        else
                            mRootView.endLoadMore();
                        if (t == null || t.getData() == null) {
                            if (mChoiceList == null || mChoiceList.size() == 0)
                                mRootView.setData(null);
                            return;
                        }
                        if (!"1".equals(t.getStatus())) {
                            if (mChoiceList == null)
                                mRootView.setData(null);
                            mRootView.showMessage(t.getMsg());
                            return;
                        }

                        List<CommunityDataBean.DataBean> video_list = t.getData();

                        if (isPullToRefresh) mChoiceList.clear();//如果是上拉刷新则清空列表
                        preEndIndex = mChoiceList.size();//更新之前列表总长度,用于确定加载更多的起始位置
                        mChoiceList.addAll(video_list);
                        mRootView.setData(mChoiceList);
                        if (isPullToRefresh)
                            mAdapter.notifyDataSetChanged();
                        else {
                            page++;
                            mAdapter.notifyItemRangeInserted(preEndIndex, video_list.size());
                        }

                    }
                });
    }

    public void setFollow(String starid) {

        Map<String, String> map = new HashMap<>();

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
