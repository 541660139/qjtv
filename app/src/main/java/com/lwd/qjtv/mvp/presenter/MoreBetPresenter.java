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

import com.usher.greendao_demo.greendao.gen.BetModelBeanDao;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.RxUtils;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.contract.MoreBetContract;
import com.lwd.qjtv.mvp.model.entity.BaseBean;
import com.lwd.qjtv.mvp.model.entity.BetModelBean;
import com.lwd.qjtv.mvp.ui.adapter.MoreBetAdapter;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/13.
 */

@ActivityScope
public class MoreBetPresenter extends BasePresenter<MoreBetContract.Model, MoreBetContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;
    private List<BetModelBean> mList = new ArrayList<>();
    private DefaultAdapter mAdapter;
    private int page = 1;
    private boolean isFirst = true;
    private int preEndIndex;
    private BetModelBeanDao betModelBeanDao;

    @Inject
    public MoreBetPresenter(MoreBetContract.Model model, MoreBetContract.View rootView, RxErrorHandler handler
            , AppManager appManager, Application application) {
        super(model, rootView);
        this.mApplication = application;
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
    }

    public void requestMoreBetList(final boolean pullToRefresh) {
        if (mAdapter == null) {
            mAdapter = new MoreBetAdapter(mList);
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
        mList.clear();
        List<BetModelBean> betModelBeen = WEApplication.getWinDao().loadAll();
        List<BetModelBean> betModelBeen1 = WEApplication.getThirdModelBeanDao().loadAll();
        mList.addAll(betModelBeen1);
        mList.addAll(betModelBeen);
        mAdapter.notifyDataSetChanged();
        mRootView.setData(mList);
    }

    public void addMoreBet(String many_jc,String wager){
        String op = "manyChooseBet";
        Map<String, String> map = new HashMap<>();
        map.put("many_jc", many_jc);
        map.put("wager", wager);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        RxUtils.applyNormalSchedulers(mRootView,mModel.addMoreBet(map))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseBean baseBean) {
                        if (baseBean != null )
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
