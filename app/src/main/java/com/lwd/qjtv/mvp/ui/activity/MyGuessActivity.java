package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.TextView;

import com.jess.arms.base.AdapterViewPager;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.di.component.DaggerMyGuessComponent;
import com.lwd.qjtv.di.module.MyGuessModule;
import com.lwd.qjtv.mvp.contract.MyGuessContract;
import com.lwd.qjtv.mvp.model.entity.GuessMyBean;
import com.lwd.qjtv.mvp.presenter.MyGuessPresenter;
import com.lwd.qjtv.mvp.ui.fragment.guess.GuessMyFragment;
import com.lwd.qjtv.view.LoadingPageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/17.
 */

public class MyGuessActivity extends BaseActivity<MyGuessPresenter> implements MyGuessContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.activity_my_guess_all_time_tv)
    TextView allTimeTv;
    @BindView(R.id.activity_my_guess_win_tv)
    TextView winTv;
    @BindView(R.id.activity_my_guess_fail_tv)
    TextView failTv;
    @BindView(R.id.activity_my_guess_win_rate_tv)
    TextView winRateTv;
    @BindView(R.id.activity_my_guess_money_tv)
    TextView moneyTv;
    @BindView(R.id.activity_my_guess_single_tv)
    TextView singleTv;
    @BindView(R.id.activity_my_guess_champion_tv)
    TextView championTv;
    @BindView(R.id.activity_my_guess_more_tv)
    TextView moreTv;
    @BindView(R.id.activiy_my_guess_viewpager)
    ViewPager viewpager;
    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
    private AdapterViewPager adapterViewPager;
    private List<BaseFragment> fragments = new ArrayList<>();
    private int type;
    //    private RecyclerView mRecyclerView;
//    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        this.mRxPermissions = new RxPermissions(this);
        DaggerMyGuessComponent
                .builder()
                .appComponent(appComponent)
                .myGuessModule(new MyGuessModule(this))  //首字母改成小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_my_guess;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!Preference.getBoolean(Constant.IS_LOGIN))
            finish();
    }

    @Override
    public void initData() {
        type = getIntent().getIntExtra("type", 0);

        loadingPageView.startLodingAnim();
        setTitle("我的竞猜");
        initViewpager();
        mPresenter.requestMyGuessList(true, type);//打开app时自动加载列表
        initListener();
    }

    private void initViewpager() {
        initFragments();
        adapterViewPager = new AdapterViewPager(getSupportFragmentManager());
        adapterViewPager.bindData(fragments);
        viewpager.setAdapter(adapterViewPager);
        viewpager.setOffscreenPageLimit(1);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        singleTv.setBackground(getResources().getDrawable(R.color.colorf6f5f5));
                        championTv.setBackground(getResources().getDrawable(R.color.white));
                        moreTv.setBackground(getResources().getDrawable(R.color.white));
                        break;
                    case 1:
                        championTv.setBackground(getResources().getDrawable(R.color.colorf6f5f5));
                        singleTv.setBackground(getResources().getDrawable(R.color.white));
                        moreTv.setBackground(getResources().getDrawable(R.color.white));
                        break;
                    case 2:
                        moreTv.setBackground(getResources().getDrawable(R.color.colorf6f5f5));
                        singleTv.setBackground(getResources().getDrawable(R.color.white));
                        championTv.setBackground(getResources().getDrawable(R.color.white));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initFragments() {
        fragments.add(GuessMyFragment.newInstance(type));
//        fragments.add(ChampionGuessFragment.newInstance());
//        fragments.add(GuessMoreFragment.newInstance());
    }

    private void initListener() {
        loadingPageView.setClickReload(() -> onRefresh());
        singleTv.setOnClickListener(v -> viewpager.setCurrentItem(0));
        championTv.setOnClickListener(v -> viewpager.setCurrentItem(1));
        moreTv.setOnClickListener(v -> viewpager.setCurrentItem(2));
    }


    @Override
    public void onRefresh() {
        mPresenter.requestMyGuessList(true, type);
    }


    @Override
    public void showLoading() {
//        Timber.tag(TAG).w("showLoading");
//        Observable.just(1)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(integer -> mSwipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void hideLoading() {
//        Timber.tag(TAG).w("hideLoading");
//        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(String message) {
        UiUtils.makeText(this, message);
//            UiUtils.SnackbarText(message);
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
        GuessMyBean.DataBean dataBean = (GuessMyBean.DataBean) o;
        GuessMyBean.DataBean.BetRecordBean bet_record = dataBean.getBet_record();
        allTimeTv.setText(bet_record.getAll_total() + "");
        winTv.setText(bet_record.getWin_total() + "");
        failTv.setText(bet_record.getLoss_total() + "");
        winRateTv.setText(bet_record.getWin_odds() + "");
        moneyTv.setText(bet_record.getProfit_loss() + "");
    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {

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

        super.onDestroy();
        this.mRxPermissions = null;
        this.mPaginate = null;
    }
}
