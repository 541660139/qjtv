package com.lwd.qjtv.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PermissionUtil;

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
import com.lwd.qjtv.mvp.contract.GuessDetailsContract;
import com.lwd.qjtv.mvp.model.entity.BaseBean;
import com.lwd.qjtv.mvp.model.entity.GuessDetailsBean;
import com.lwd.qjtv.mvp.ui.adapter.GuessDetailsAdapter;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/23.
 */

@ActivityScope
public class GuessDetailsPresenter extends BasePresenter<GuessDetailsContract.Model, GuessDetailsContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;
    private List<GuessDetailsBean.DataBean.GuessScoreBean.PlInfoBeanXX> mList = new ArrayList<>();
    private DefaultAdapter mAdapter;
    private int page = 1;
    private boolean isFirst = true;
    private int preEndIndex;

    @Inject
    public GuessDetailsPresenter(GuessDetailsContract.Model model, GuessDetailsContract.View rootView, RxErrorHandler handler
            , AppManager appManager, Application application) {
        super(model, rootView);
        this.mApplication = application;
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
    }

    public void requestGuessDetailsList(String jc_id, final boolean pullToRefresh) {
        if (mAdapter == null) {
            mAdapter = new GuessDetailsAdapter(mList);
            mRootView.setAdapter(mAdapter);//设置Adapter
        }

        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.externalStorage(() -> {
            //request permission success, do something.
        }, mRootView.getRxPermissions(), mRootView, mErrorHandler);


        if (pullToRefresh) page = 1;//上拉刷新默认只请求第一页
        else if (page == 1) page = 2;

        //关于RxCache缓存库的使用请参考 http://www.jianshu.com/p/b58ef6b0624b

        boolean isEvictCache = pullToRefresh;//是否驱逐缓存,为ture即不使用缓存,每次上拉刷新即需要最新数据,则不使用缓存

        if (pullToRefresh && isFirst) {//默认在第一次上拉刷新时使用缓存
            isFirst = false;
            isEvictCache = false;
        }
        String op = "guess_detail";
        Map<String, String> map = new HashMap<>();
        map.put("op", op);
        map.put("page", page+"");
        map.put("match_id", jc_id);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyListSchedulers(mRootView, mModel.getGuessDetails(map), pullToRefresh)
                .subscribe(new ErrorHandleSubscriber<GuessDetailsBean>(mErrorHandler) {
                    @Override
                    public void onNext(GuessDetailsBean guessDetailsBean) {
                        if (guessDetailsBean == null || guessDetailsBean.getData() == null || !"1".equals(guessDetailsBean.getStatus())) {
                            if (mList == null)
                                mRootView.setData(-1);
                            if(!"1".equals(guessDetailsBean.getStatus()))
                            mRootView.showMessage(guessDetailsBean.getMsg());
                            return;
                        }
                        GuessDetailsBean.DataBean data = guessDetailsBean.getData();
                        mRootView.setData(data);
                        if (data.getGuess_score() == null)
                            return;
                        List<GuessDetailsBean.DataBean.GuessScoreBean.PlInfoBeanXX> plInfo = data.getGuess_score().getPlInfo();
                        if(plInfo.size() == 0&& !pullToRefresh && mList != null)
                            mRootView.showMessage("当前无更多数据~");
                        if (pullToRefresh) mList.clear();//如果是上拉刷新则清空列表
                        preEndIndex = mList.size();//更新之前列表总长度,用于确定加载更多的起始位置
                        mList.addAll(plInfo);
                        if (pullToRefresh)
                            mAdapter.notifyDataSetChanged();
                        else {
                            page++;
                            mAdapter.notifyItemRangeInserted(preEndIndex, plInfo.size());
                        }
                    }
                });
    }



    public void addBat(String match_id, String jc_id, String jc_pl_id, String jc_type, String wager) {
        String op = "addBet";
        Map<String, String> map = new HashMap<>();
        map.put("op", op);
        map.put("jc_id", jc_id);
        map.put("match_id", match_id);
        map.put("jc_pl_id", jc_pl_id);
        map.put("jc_type", jc_type);
        map.put("wager", wager);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView, mModel.addGuessBat(map))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseBean baseBean) {
                        if (baseBean != null)
                            mRootView.showMessage(baseBean.getMsg());
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
