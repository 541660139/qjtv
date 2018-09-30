package com.lwd.qjtv.mvp.ui.fragment.live;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.di.component.DaggerLiveGuessComponent;
import com.lwd.qjtv.di.module.LiveGuessModule;
import com.lwd.qjtv.mvp.contract.LiveGuessContract;
import com.lwd.qjtv.mvp.model.entity.GuessCenterBean;
import com.lwd.qjtv.mvp.presenter.LiveGuessPresenter;
import com.lwd.qjtv.mvp.ui.activity.GuessDetailsActivity;
import com.lwd.qjtv.mvp.ui.activity.MyGuessActivity;
import com.lwd.qjtv.mvp.ui.activity.RankActivity;
import com.lwd.qjtv.mvp.ui.adapter.GuessCenterAdapter;
import com.lwd.qjtv.mvp.ui.holder.GuessCenterItemHolder;
import com.lwd.qjtv.view.CustomLoadingListItemCreator;
import com.lwd.qjtv.view.LoadingPageView;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/6.
 */

public class LiveGuessFragment extends BaseFragment<LiveGuessPresenter> implements LiveGuessContract.View, OnRefreshListener, OnLoadmoreListener, GuessCenterItemHolder.GuessClickCallBack, DefaultAdapter.OnRecyclerViewItemClickListener<GuessCenterBean.DataBean> {
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.activity_guess_center_rank_ll)
    LinearLayout rankLl;
    @BindView(R.id.activity_guess_center_my_guess_ll)
    LinearLayout guessLl;
    @BindView(R.id.activity_guess_center_fuhaobang_ll)
    LinearLayout fhbLl;

    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;
    private View view;
    private boolean isNBA;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(WEApplication.getContext(), RankActivity.class);
            intent.putExtra(Constant.IS_GUESS_RANK, true);
            intent.putExtra(Constant.IS_NBA, isNBA);
            startActivity(intent);
        }
    };

    public static LiveGuessFragment newInstance() {
        LiveGuessFragment fragment = new LiveGuessFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerLiveGuessComponent
                .builder()
                .appComponent(appComponent)
                .liveGuessModule(new LiveGuessModule(this))//请将LiveGuessModule()第一个首字母改为小写
                .build()
                .inject(this);
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_live_guess_layout, container, false);
        return view;
    }


    @Override
    public void initData() {
        if (getArguments() != null) {
            isNBA = getArguments().getBoolean("isNBA");
        }
        initListener();
        initRecyclerview();
        mPresenter.requestGuessCenterList(true);//打开app时自动加载列表
        loadingPageView.startLodingAnim();
    }

    private void initRecyclerview() {
        View listview = view.findViewById(R.id.recyclerView_layout);
        mSwipeRefreshLayout = (SmartRefreshLayout) listview.findViewById(R.id.zq_refreshlayout);
        mRecyclerView = (RecyclerView) listview.findViewById(R.id.recyclerView);
    }

    private void initListener() {
        loadingPageView.setClickReload(() -> mPresenter.requestGuessCenterList(true));
        rankLl.setOnClickListener(view -> handler.sendEmptyMessage(1));
        fhbLl.setOnClickListener(v -> {
            Intent intent = new Intent(WEApplication.getContext(), RankActivity.class);
            intent.putExtra(Constant.IS_GUESS_RANK, false);
            intent.putExtra(Constant.IS_NBA, false);
            startActivity(intent);
        });
        guessLl.setOnClickListener(view -> startActivity(new Intent(WEApplication.getContext(), MyGuessActivity.class)));
    }

    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setOnLoadmoreListener(this);
        UiUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(getContext()));
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.finishLoadmore();
    }

    @Override
    public void showMessage(String message) {
//        UiUtils.SnackbarText(message);

        UiUtils.makeText(getContext(), message);
    }

    @Override
    public void launchActivity(Intent intent) {
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }


    @Override
    public void setData(Object o) {
        loadingPageView.checkData(o);
    }


    @Override
    public void setAdapter(DefaultAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        GuessCenterAdapter guessCenterAdapter = (GuessCenterAdapter) adapter;
        guessCenterAdapter.setGuessCallBack(this);
        initRecycleView();
//        initPaginate();
    }

    /**
     * 开始加载更多
     */
    @Override
    public void startLoadMore() {
        isLoadingMore = true;
    }

    /**
     * 结束加载更多
     */
    @Override
    public void endLoadMore() {
        isLoadingMore = false;
        mSwipeRefreshLayout.finishLoadmore();
    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    /**
     * 初始化Paginate,用于加载更多
     */
    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {

                }

                @Override
                public boolean isLoading() {
                    return isLoadingMore;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return false;
                }
            };

            mPaginate = Paginate.with(mRecyclerView, callbacks)
                    .setLoadingTriggerThreshold(10).setLoadingListItemCreator(new CustomLoadingListItemCreator())
                    .build();
//            mPaginate.setHasMoreDataToLoad(true);
        }
    }

    @Override
    public void clickGuess(GuessCenterBean.DataBean data, int position) {
        Intent intent = new Intent(getContext(), GuessDetailsActivity.class);
        intent.putExtra("id", data.getMatch_id());
        startActivity(intent);
    }

    @Override
    public void onItemClick(View view, int viewType, GuessCenterBean.DataBean data, int position) {

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.requestGuessCenterList(false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.requestGuessCenterList(true);
    }
}