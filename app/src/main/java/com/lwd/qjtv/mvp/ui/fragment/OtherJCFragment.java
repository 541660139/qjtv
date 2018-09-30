package com.lwd.qjtv.mvp.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jess.arms.base.App;
import com.jess.arms.di.component.AppComponent;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Contact;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.ToastUtil;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.GuessCenterBean;
import com.lwd.qjtv.mvp.ui.activity.MyGuessActivity;
import com.lwd.qjtv.mvp.ui.activity.RankActivity;
import com.lwd.qjtv.mvp.ui.activity.WebNewActivity;
import com.lwd.qjtv.mvp.ui.adapter.GuessCenterAdapter;
import com.lwd.qjtv.mvp.ui.holder.GuessCenterItemHolder;
import com.lwd.qjtv.view.LoadingPageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Created by Administrator on 2018/3/29.
 */

public class OtherJCFragment extends com.jess.arms.base.BaseFragment implements OnRefreshListener, OnLoadmoreListener {
    private View view;
    private LinearLayout activity_guess_center_rank_ll;
    private LinearLayout activity_guess_center_fuhaobang_ll;
    private LinearLayout activity_guess_center_my_guess_ll;
    private SmartRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LoadingPageView loading_framelayout;
    private int page = 1;
    private boolean loadFinish = true;
    private List<GuessCenterBean.DataBean> mList = new ArrayList<>();
    private GuessCenterAdapter mAdapter;
    private Contact contact;

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_snookerjc, null);

        initView();
        initListener();
        loading_framelayout.startLodingAnim();
        initGuessCenterData();
        return view;
    }

    private void initView() {
        activity_guess_center_rank_ll = (LinearLayout) view.findViewById(R.id.activity_guess_center_rank_ll);
        activity_guess_center_fuhaobang_ll = (LinearLayout) view.findViewById(R.id.activity_guess_center_fuhaobang_ll);
        activity_guess_center_my_guess_ll = (LinearLayout) view.findViewById(R.id.activity_guess_center_my_guess_ll);
        View listview = view.findViewById(R.id.recyclerView_layout);
        mSwipeRefreshLayout = (SmartRefreshLayout) listview.findViewById(R.id.zq_refreshlayout);
        mRecyclerView = (RecyclerView) listview.findViewById(R.id.recyclerView);
        loading_framelayout = (LoadingPageView) view.findViewById(R.id.loading_framelayout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GuessCenterAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);//设置Adapter


    }

    private void initListener() {

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setOnLoadmoreListener(this);
        activity_guess_center_fuhaobang_ll.setOnClickListener(v -> {
            Intent intent = new Intent(WEApplication.getContext(), RankActivity.class);
            intent.putExtra(Constant.IS_GUESS_RANK, false);
            intent.putExtra(Constant.IS_NBA, false);
            startActivity(intent);
        });
        activity_guess_center_rank_ll.setOnClickListener(view -> {
            Intent intent = new Intent(WEApplication.getContext(), RankActivity.class);
            intent.putExtra(Constant.IS_GUESS_RANK, true);
            intent.putExtra(Constant.IS_NBA, false);
            startActivity(intent);
        });
        activity_guess_center_my_guess_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WEApplication.getContext(), MyGuessActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });


    }

    @Override
    public void initData() {

    }

    @Override
    public void setData(Object data) {

    }


    private void initGuessCenterData() {
        AppComponent appComponent = ((App) getContext().getApplicationContext()).getAppComponent();
        Map<String, String> map = new TreeMap<>();
        map.put("page", page + "");
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        if (appComponent == null) {
            return;
        }
        appComponent.repositoryManager().obtainRetrofitService(UserService.class).getNBAGuessCenter(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<GuessCenterBean>(appComponent.rxErrorHandler()) {
                    @Override
                    public void onNext(@NonNull GuessCenterBean guesscenterbean) {
                        mSwipeRefreshLayout.finishRefresh();
                        mSwipeRefreshLayout.finishLoadmore();
                        loadFinish = true;
                        if (guesscenterbean == null)
                            return;
                        loading_framelayout.checkData(guesscenterbean.getData());
                        if (!guesscenterbean.getStatus().equals("1")) {
                            ToastUtil.showToast(getContext(), guesscenterbean.getMsg());
                        } else {
                            if (guesscenterbean.getData() == null) {
                                return;
                            }

                        }

                        mList.addAll(guesscenterbean.getData());
                        Log.d("GlobalConfiguration", mList.size() + "");

                        mAdapter.notifyDataSetChanged();
                        mAdapter.setGuessCallBack(new GuessCenterItemHolder.GuessClickCallBack() {
                            @Override
                            public void clickGuess(GuessCenterBean.DataBean data, int position) {
//                                Intent intent = new Intent(getContext(), GuessDetailsActivity.class);
//                                intent.putExtra("id", data.getMatch_id());
//                                startActivity(intent);


                                HashMap<String, String> map = new HashMap<>();
                                map.put("match_id", data.getMatch_id());
                                if (contact == null)
                                    contact = new Contact(getContext());
                                String webUrl = contact.getWebUrl("mobile/qitaGuess", map);
                                Intent intent = new Intent(getContext(), WebNewActivity.class);
                                intent.putExtra("url", webUrl);
                                startActivity(intent);
                            }
                        });

                    }
                });
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        mList.clear();
        initGuessCenterData();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        if (loadFinish) {
            page++;
            initGuessCenterData();
            loadFinish = false;
        }

    }
}
