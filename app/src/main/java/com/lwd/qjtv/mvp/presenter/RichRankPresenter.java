package com.lwd.qjtv.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.RxUtils;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.contract.RichRankContract;
import com.lwd.qjtv.mvp.model.entity.GuessRankBean;
import com.lwd.qjtv.mvp.ui.adapter.RankAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@FragmentScope
public class RichRankPresenter extends BasePresenter<RichRankContract.Model, RichRankContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    private List<GuessRankBean.DataBean> mList = new ArrayList<>();
    private DefaultAdapter mAdapter;
    private int page = 1;
    private boolean isFirst = true;
    private int preEndIndex;

    @Inject
    public RichRankPresenter(RichRankContract.Model model, RichRankContract.View rootView) {
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


    public void requestRankList(final boolean pullToRefresh) {
        if (mAdapter == null) {
            mAdapter = new RankAdapter(mList);
            mRootView.setAdapter(mAdapter);//设置Adapter
        }

        //请求外部存储权限用于适配android6.0的权限管理机制



        if (pullToRefresh) page = 1;//上拉刷新默认只请求第一页
        else if (page == 1) page = 2;

        //关于RxCache缓存库的使用请参考 http://www.jianshu.com/p/b58ef6b0624b

        boolean isEvictCache = pullToRefresh;//是否驱逐缓存,为ture即不使用缓存,每次上拉刷新即需要最新数据,则不使用缓存

        if (pullToRefresh && isFirst) {//默认在第一次上拉刷新时使用缓存
            isFirst = false;
            isEvictCache = false;
        }
        Map<String, String> map = new HashMap<>();

        map.put("op", "fuhao");
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyListSchedulers(mRootView, mModel.guessRank(map), pullToRefresh)
                .subscribe(new ErrorHandleSubscriber<GuessRankBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull GuessRankBean guessRankBean) {
                        if (guessRankBean == null)
                            return;
                        if (guessRankBean.getData() == null || !"1".equals(guessRankBean.getStatus())) {
                            if (!"1".equals(guessRankBean.getStatus()))
                                mRootView.showMessage(guessRankBean.getMsg());
                            return;
                        }
                        List<GuessRankBean.DataBean> list = guessRankBean.getData();
                        if (list.size() == 0 && !pullToRefresh && mList != null)
                            mRootView.showMessage("当前无更多数据~");
                        if (pullToRefresh)
                            mList.clear();//如果是上拉刷新则清空列表
                        preEndIndex = mList.size();//更新之前列表总长度,用于确定加载更多的起始位置
                        if (pullToRefresh) {
                            if (list.size() == 0)
                                return;
                            List<GuessRankBean.DataBean> threeList = list.subList(0, list.size() > 3 ? 3 : list.size());
                            mRootView.setData(threeList);
                            if (list.size() > 3) {
                                List<GuessRankBean.DataBean> dataBeen = list.subList(3, list.size());
                                mList.addAll(dataBeen);
                            }
                        } else {
                            mList.addAll(list);
                        }
                        if (pullToRefresh)
                            mAdapter.notifyDataSetChanged();
                        else {
                            page++;
                            mAdapter.notifyItemRangeInserted(preEndIndex, list.size());
                        }
                    }
                });
    }
}
