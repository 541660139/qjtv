package com.lwd.qjtv.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.RxUtils;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.contract.CommuntiyDetailContract;
import com.lwd.qjtv.mvp.model.entity.BaseBean;
import com.lwd.qjtv.mvp.model.entity.CommunityAllBean;
import com.lwd.qjtv.mvp.model.entity.CommunityContentBean;
import com.lwd.qjtv.mvp.model.entity.CommunityHfBean;
import com.lwd.qjtv.mvp.ui.adapter.CommunityPostDetailAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class CommuntiyDetailPresenter extends BasePresenter<CommuntiyDetailContract.Model, CommuntiyDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;

    @Inject
    AppManager mAppManager;

    private int page = 1;
    private boolean isFirst = true;
    private int preEndIndex;
    private CommunityPostDetailAdapter mAdapter;
    private List<CommunityContentBean> all;

    @Inject
    public CommuntiyDetailPresenter(CommuntiyDetailContract.Model model, CommuntiyDetailContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }


    public void getCommunityDetail(boolean isPullToRefresh, String post_id) {
        if (mAdapter == null) {
            mAdapter = new CommunityPostDetailAdapter(mApplication.getBaseContext(), R.layout.item_community_comments);
            mRootView.setAdapter(mAdapter);//设置Adapter
            all = mAdapter.getAll();
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
        map.put("post_id", post_id);

        RxUtils.applyListSchedulers(mRootView, mModel.getCommunityDetail(map), isPullToRefresh)
                .subscribe(new ErrorHandleSubscriber<CommunityAllBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull CommunityAllBean t) {

                        if (isPullToRefresh)
                            mRootView.hideLoading();
                        else
                            mRootView.endLoadMore();
                        if (t == null || t.getData() == null) {
                            if (all == null || all.size() == 0)
                                mRootView.setData(null);
                            return;
                        }
                        if (!"1".equals(t.getStatus())) {
                            if (all == null)
                                mRootView.setData(null);
                            mRootView.showMessage(t.getMsg());
                            return;
                        }
                        List<CommunityContentBean> data = t.getData();

                        if (isPullToRefresh) all.clear();//如果是上拉刷新则清空列表
                        preEndIndex = all.size();//更新之前列表总长度,用于确定加载更多的起始位置
                        all.addAll(data);
                        mRootView.setData(all);
                        if (isPullToRefresh)
                            mAdapter.notifyDataSetChanged();
                        else {
                            page++;
                            mAdapter.notifyItemRangeInserted(preEndIndex, data.size());
                        }


                    }
                });
    }


    public void sendCommunitycontent(String content, String post_id) {
        Map<String, String> map = new HashMap<>();
        map.put("post_id", post_id);
        map.put("op", "post_review");
        map.put("content", content);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));

        RxUtils.applyNormalSchedulers(mRootView, mModel.sendCommunitycontent(map))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseBean baseBean) {
                        if (baseBean == null)
                            return;
                        mRootView.showMessage(baseBean.getMsg());

                        if (baseBean.getStatus().equals("1")) {

                            getCommunityDetail(true, post_id);
                        }

                    }
                });
    }


    public void sendCommunitycontent(String content, String post_id, String review_id, String one_id, int type) {
        Map<String, String> map = new HashMap<>();
        if (type == 1 || type == 2) {
            map.put("review_id", review_id);
            map.put("one_id", one_id);

        }
        map.put("post_id", post_id);
        map.put("op", "post_review");
        map.put("content", content);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));

        RxUtils.applyNormalSchedulers(mRootView, mModel.sendCommunityHfcontent(map))
                .subscribe(new ErrorHandleSubscriber<CommunityHfBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull CommunityHfBean baseBean) {
                        if (baseBean == null)
                            return;
                        mRootView.showMessage(baseBean.getMsg());

                        if (baseBean.getStatus().equals("1")) {
                            mRootView.setSendContenData(baseBean);

                        }

                    }
                });
    }
}
