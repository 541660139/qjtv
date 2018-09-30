package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.di.component.DaggerRankComponent;
import com.lwd.qjtv.di.module.RankModule;
import com.lwd.qjtv.mvp.contract.RankContract;
import com.lwd.qjtv.mvp.model.entity.GuessRankBean;
import com.lwd.qjtv.mvp.presenter.RankPresenter;
import com.lwd.qjtv.view.LoadingPageView;
import com.lwd.qjtv.view.VerticalSwipeRefreshLayout;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class RankActivity extends BaseActivity<RankPresenter> implements RankContract.View, SwipeRefreshLayout.OnRefreshListener {
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
    @BindView(R.id.activity_rank_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_rank_swiperefreshlayout)
    VerticalSwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;
    @BindView(R.id.activity_rank_one_civ)
    CircleImageView twoCiv;
    @BindView(R.id.activity_rank_two_civ)
    CircleImageView oneCiv;
    @BindView(R.id.activity_rank_three_civ)
    CircleImageView threeCiv;
    @BindView(R.id.activity_rank_one_name_tv)
    TextView twoNameTv;
    @BindView(R.id.activity_rank_two_name_tv)
    TextView oneNameTv;
    @BindView(R.id.activity_rank_three_name_tv)
    TextView threeNameTv;
    @BindView(R.id.activity_rank_one_number_tv)
    TextView twoNumTv;
    @BindView(R.id.activity_rank_two_number_tv)
    TextView oneNumTv;
    @BindView(R.id.activity_rank_three_number_tv)
    TextView threeNumTv;
    @BindView(R.id.tv_bottom_tishi)
    TextView tv_bottom_tishi;


    private boolean isGuessRank = true;
    private boolean isNBA;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        this.mRxPermissions = new RxPermissions(this);
        DaggerRankComponent
                .builder()
                .appComponent(appComponent)
                .rankModule(new RankModule(this))  //首字母改成小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
//        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_rank, null);
//        mSwipeRefreshLayout = (VerticalSwipeRefreshLayout) view.findViewById(R.id.zq_refreshlayout);
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        return R.layout.activity_rank;
    }


    @Override
    public void initData() {
        loadingPageView.startLodingAnim();
        if (getIntent() != null) {
            isGuessRank = getIntent().getBooleanExtra(Constant.IS_GUESS_RANK, true);
            isNBA = getIntent().getBooleanExtra(Constant.IS_NBA, false);
        }
        if (isGuessRank) {
            setTitle("竞猜排行榜");
            tv_bottom_tishi.setVisibility(View.VISIBLE);
        } else {
            setTitle("富豪排行榜");
        }
        initListener();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        if (isNBA)
            mPresenter.requestNBARankList(true, isGuessRank);//打开app时自动加载列表
        else
            mPresenter.requestRankList(true, isGuessRank);//打开app时自动加载列表
    }

    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        UiUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(this));
    }


    private void initListener() {
        loadingPageView.setClickReload(() -> onRefresh());
    }

    @Override
    public void showLoading() {
        Timber.tag(TAG).w("showLoading");
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> mSwipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void hideLoading() {
        Timber.tag(TAG).w("hideLoading");
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(String message) {
//        UiUtils.SnackbarText(message);
        UiUtils.makeText(this, message);
    }

    @Override
    public void launchActivity(Intent intent) {
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void setData(Object o) {
        loadingPageView.checkData(o);
        if (o != null) {
            List<GuessRankBean.DataBean> dataBeen = (List<GuessRankBean.DataBean>) o;
            for (int i = 0; i < dataBeen.size(); i++) {
                GuessRankBean.DataBean dataBean = dataBeen.get(i);
                if (i == 1) {
                    twoNameTv.setText(dataBean.getUsername());
                    twoNumTv.setText(dataBean.getAll_wager());
                    Glide.with(this).load(dataBean.getAvater()).placeholder(R.mipmap.video_place_holder).error(R.mipmap.video_place_holder).into(twoCiv);
                } else if (i == 2) {
                    threeNameTv.setText(dataBean.getUsername());
                    threeNumTv.setText(dataBean.getAll_wager());
                    Glide.with(this).load(dataBean.getAvater()).placeholder(R.mipmap.video_place_holder).error(R.mipmap.video_place_holder).into(threeCiv);
                } else if (i == 0) {
                    oneNameTv.setText(dataBean.getUsername());
                    oneNumTv.setText(dataBean.getAll_wager());
                    Glide.with(this).load(dataBean.getAvater()).placeholder(R.mipmap.video_place_holder).error(R.mipmap.video_place_holder).into(oneCiv);
                }
            }
        }
    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
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
    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }


    @Override
    protected void onDestroy() {
        DefaultAdapter.releaseAllHolder(mRecyclerView);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
        this.mRxPermissions = null;
        this.mPaginate = null;
    }
}
