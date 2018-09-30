package com.lwd.qjtv.mvp.presenter;

import android.app.Application;
import android.view.View;

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
import com.lwd.qjtv.mvp.contract.ChoiceContract;
import com.lwd.qjtv.mvp.model.entity.ChoiceDataBean;
import com.lwd.qjtv.mvp.model.entity.DianZanBean;
import com.lwd.qjtv.mvp.model.entity.GuessMyBean;
import com.lwd.qjtv.mvp.ui.adapter.ChoiceAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@FragmentScope
public class ChoicePresenter extends BasePresenter<ChoiceContract.Model, ChoiceContract.View> {


    private int page = 1;
    private boolean isFirst = true;
    private int preEndIndex;
    List<String> data = new ArrayList<>();
    //    精选数据
    private List<ChoiceDataBean.DataBean> mChoiceList = new ArrayList<>();

    //    看点数据
    private List<GuessMyBean.DataBean.TzDetailBean> mWatchingfocusList = new ArrayList<>();


    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    private ChoiceAdapter mAdapter;

    @Inject
    public ChoicePresenter(ChoiceContract.Model model, ChoiceContract.View rootView) {
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

    public void requestData(boolean isPullToRefresh) {


//精选
        setChoiceData(isPullToRefresh);


    }


    private void setChoiceData(boolean isPullToRefresh) {


        if (mAdapter == null) {
            mAdapter = new ChoiceAdapter(mChoiceList);
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
//        String op = " op=home_list";
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
        RxUtils.applyListSchedulers(mRootView, mModel.getChoiceData(map), isPullToRefresh)
                .subscribe(new ErrorHandleSubscriber<ChoiceDataBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull ChoiceDataBean t) {

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
                        List<ChoiceDataBean.DataBean> video_list = t.getData();

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


    public void setDianZan(ChoiceDataBean.DataBean data, View view, View view1) {
        if (Preference.get(Constant.UID, "0").equals("0")) {
            mRootView.showMessage("请先登录");
            return;
        }
//        String op = "op=zan_video";
        Map<String, String> map = new HashMap<>();
        map.put("video_id", data.getId());
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.setDianZan(map))
                .subscribe(new ErrorHandleSubscriber<DianZanBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull DianZanBean choiceDianZanBean) {
                        mRootView.setDianZan(choiceDianZanBean, data, view, view1);
                        mRootView.showMessage(choiceDianZanBean.getMsg());
                    }
                });
    }
}
