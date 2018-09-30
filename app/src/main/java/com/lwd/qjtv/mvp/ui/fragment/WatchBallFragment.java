package com.lwd.qjtv.mvp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.paginate.Paginate;
import com.rrmj.zhongduomei.videoparsesdk.parseurl.IOnParseUrlListener;
import com.rrmj.zhongduomei.videoparsesdk.parseurl.bean.ParseEntityModle;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;


import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.ClickMoreUtils;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Contact;
import com.lwd.qjtv.app.utils.GlideConfigGlobal;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.di.component.DaggerWatchBallComponent;
import com.lwd.qjtv.di.module.WatchBallModule;
import com.lwd.qjtv.mvp.contract.WatchBallContract;
import com.lwd.qjtv.mvp.model.entity.Auto;
import com.lwd.qjtv.mvp.model.entity.WatchBallBean;
import com.lwd.qjtv.mvp.presenter.WatchBallPresenter;
import com.lwd.qjtv.mvp.ui.activity.GuessCenterActivity;
import com.lwd.qjtv.mvp.ui.activity.GuessChampionActivity;
import com.lwd.qjtv.mvp.ui.activity.LearnBallMoreActivity;
import com.lwd.qjtv.mvp.ui.activity.LiveActivity;
import com.lwd.qjtv.mvp.ui.activity.LoginActivity;
import com.lwd.qjtv.mvp.ui.activity.MatchCollectionMoreActivity;
import com.lwd.qjtv.mvp.ui.activity.MatchTimeActivity;
import com.lwd.qjtv.mvp.ui.activity.MoreBetActivity;
import com.lwd.qjtv.mvp.ui.activity.MoreVideoActivity;
import com.lwd.qjtv.mvp.ui.activity.SearchActivity;
import com.lwd.qjtv.mvp.ui.activity.StarDetailsActivity;
import com.lwd.qjtv.mvp.ui.activity.VideoDetailsActivity;
import com.lwd.qjtv.mvp.ui.activity.WatchRecordeActivity;
import com.lwd.qjtv.mvp.ui.activity.WebActivity;
import com.lwd.qjtv.mvp.ui.activity.WebViewActivity;
import com.lwd.qjtv.mvp.ui.adapter.FragmentWatchBallPagerAdapter;
import com.lwd.qjtv.mvp.ui.adapter.NBAFragmentWatchBallPagerAdapter;
import com.lwd.qjtv.mvp.ui.adapter.OtherGuessAdapter;
import com.lwd.qjtv.mvp.ui.fragment.other.RateSmallFragment;
import com.lwd.qjtv.view.AutoRollLayout;
import com.lwd.qjtv.view.CustomLoadingListItemCreator;
import com.lwd.qjtv.view.LoadingPageView;
import com.lwd.qjtv.view.WatchBallItemViewTwo;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class WatchBallFragment extends BaseFragment<WatchBallPresenter> implements IFragment, WatchBallContract.View, OnRefreshListener, DefaultAdapter.OnRecyclerViewItemClickListener, IOnParseUrlListener {

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
    private static WatchBallFragment watchBallFragment;
    private SmartRefreshLayout mSwipeRefreshLayout;

    //轮播图item
    private List<AutoRollLayout.IShowItem> items = new ArrayList<>();
    private boolean isFirst;
    private boolean isNBAFirst;

    //比赛滑动块
    @BindView(R.id.fragment_watch_ball_viewpager)
    ViewPager viewPager;

    //nba比赛滑动块
    @BindView(R.id.fragment_watch_nba_viewpager)
    ViewPager nbaViewPager;
    @BindView(R.id.fragment_watch_nba_match_live_iv)
    ImageView nbaLiveIv;
    @BindView(R.id.fragment_watch_nba_match_live_tv)
    TextView nbaLiveTv;
    @BindView(R.id.fragment_watch_nba_match_live_more_tv)
    TextView nbaLiveMoreTv;
    @BindView(R.id.nba_linearlayout)
    LinearLayout nbaLl;


    private FragmentWatchBallPagerAdapter fragmentWatchBallPagerAdapter;
    private NBAFragmentWatchBallPagerAdapter fragmentWatchNBAPagerAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    private List<Fragment> nbaFragments = new ArrayList<>();

    //轮播图控件
    @BindView(R.id.fragment_watch_ball_banner_auto)
    AutoRollLayout autoRollLayout;

    @BindView(R.id.fragment_watch_ball_message_iv)
    ImageView messageIv;

    @BindView(R.id.fragment_watch_ball_lucky_iv)
    ImageView luckyIv;

    @BindView(R.id.fragment_watch_ball_lucky_red_iv)
    ImageView redPoint;

    @BindView(R.id.linearlayout)
    LinearLayout linearLayout;

    @BindView(R.id.fragment_watch_ball_live_rl)
    AutoRelativeLayout liveRl;


    //搜索
    @BindView(R.id.fragment_watch_ball_search_iv)
    ImageView searchIv;

    //比赛直播图标
    @BindView(R.id.fragment_watch_ball_match_live_iv)
    ImageView liveIv;
    //比赛直播标题
    @BindView(R.id.fragment_watch_ball_match_live_tv)
    TextView liveTv;
    //比赛直播更多
    @BindView(R.id.fragment_watch_ball_match_live_more_tv)
    TextView liveMoreTv;

    //比赛记录模块
    @BindView(R.id.fragment_watch_ball_match_recorde_title_tv)
    TextView recordeTitleTv;
    @BindView(R.id.fragment_watch_ball_match_recorde_iv)
    ImageView recordeIv;
    @BindView(R.id.fragment_watch_ball_match_recorde_more_tv)
    TextView recordeMoreTv;
    @BindView(R.id.fragment_watch_ball_match_recorde_wbiv_one)
    WatchBallItemViewTwo recordeWbivOne;
    @BindView(R.id.fragment_watch_ball_match_recorde_wbiv_two)
    WatchBallItemViewTwo recordeWbivTwo;
    @BindView(R.id.fragment_watch_ball_match_recorde_wbiv_three)
    WatchBallItemViewTwo recordeWbivThree;
    @BindView(R.id.fragment_watch_ball_match_recorde_wbiv_four)
    WatchBallItemViewTwo recordeWbivFour;

    //精彩集锦模块
    @BindView(R.id.fragment_watch_ball_highlight_iv)
    ImageView highlightIv;
    @BindView(R.id.fragment_watch_ball_highlight_tittle_tv)
    TextView highlightTitleTv;
    @BindView(R.id.fragment_watch_ball_highlight_more_tv)
    TextView highlightMoreTv;
    @BindView(R.id.fragment_watch_ball_wonderfulset_wbiv_one)
    WatchBallItemViewTwo wonderfulWbivOne;
    @BindView(R.id.fragment_watch_ball_wonderfulset_wbiv_two)
    WatchBallItemViewTwo wonderfulWbivTwo;
    @BindView(R.id.fragment_watch_ball_wonderfulset_wbiv_three)
    WatchBallItemViewTwo wonderfulWbivThree;
    @BindView(R.id.fragment_watch_ball_wonderfulset_wbiv_four)
    WatchBallItemViewTwo wonderfulWbivFour;

    //学球视频模块
    @BindView(R.id.fragment_watch_ball_learn_iv)
    ImageView learnIv;
    @BindView(R.id.fragment_watch_ball_learn_tittle_tv)
    TextView learnTitleTv;
    @BindView(R.id.fragment_watch_ball_learn_more_tv)
    TextView learnMoreTv;
    @BindView(R.id.fragment_watch_ball_learn_wbiv_one)
    WatchBallItemViewTwo learnWbivOne;
    @BindView(R.id.fragment_watch_ball_learn_wbiv_two)
    WatchBallItemViewTwo learnWbivTwo;
    @BindView(R.id.fragment_watch_ball_learn_wbiv_three)
    WatchBallItemViewTwo learnWbivThree;
    @BindView(R.id.fragment_watch_ball_learn_wbiv_four)
    WatchBallItemViewTwo learnWbivFour;


    //明星列表
    @BindView(R.id.fragment_watch_ball_star_recyclerview)
    RecyclerView starRecyclerview;
    //明星标题
    @BindView(R.id.fragment_watch_ball_star_title_tv)
    TextView titleTv;
    //明星图标
    @BindView(R.id.fragment_watch_ball_star_iv)
    ImageView starIv;
    //明星更多
    @BindView(R.id.fragment_watch_ball_star_more_tv)
    TextView starMoreTv;

    //其他竞猜
    @BindView(R.id.arl_other_cai)
    AutoRelativeLayout arl_other_cai;

    @BindView(R.id.fragment_watch_ball_other_guess_iv)
    ImageView otherGuessIv;
    @BindView(R.id.fragment_watch_ball_other_guess_title_tv)
    TextView otherGuessTitleTv;
    @BindView(R.id.fragment_watch_ball_match_other_guess_tv)
    TextView otherGuessMoreTv;
    @BindView(R.id.fragment_base_ll_other_guess_rv)
    RecyclerView otherGuessRv;


    @BindView(R.id.loading_framelayout)
    LoadingPageView loadingPageView;

    private WatchBallBean.DataBean data = null;
    private int size;
    private int nbaSize;
    private AppComponent appComponent;
    private ImageLoader imageLoader;
    private String mId;
    private String nbaMid;

    private List<WatchBallBean.DataBean.BannerListBean> advList = new ArrayList<>();
    private List<WatchBallBean.DataBean.LiveListBean.DataListBean> leftList = new ArrayList<>();
    private List<WatchBallBean.DataBean.LiveListBean.DataListBean> rightList = new ArrayList<>();
    private List<WatchBallBean.DataBean.NbaListBean.DataListBeanX> nbaLeftList = new ArrayList<>();
    private List<WatchBallBean.DataBean.NbaListBean.DataListBeanX> nbaRightList = new ArrayList<>();
    private FragmentManager fragmentManager;
    private Contact contact;

    public static WatchBallFragment newInstance() {
        if (watchBallFragment == null)
            watchBallFragment = new WatchBallFragment();
        return watchBallFragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerWatchBallComponent
                .builder()
                .appComponent(appComponent)
                .watchBallModule(new WatchBallModule(this))//请将WatchBallModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_watch_ball, container, false);
        ButterKnife.bind(this, view);
        mSwipeRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        initViewPager();
        return view;
    }


    private void initViewPager() {
        loadingPageView.startLodingAnim();
        autoRollLayout.setItems(items);
        fragmentManager = getFragmentManager();
        fragmentWatchBallPagerAdapter = new FragmentWatchBallPagerAdapter(fragmentManager);
        fragmentWatchNBAPagerAdapter = new NBAFragmentWatchBallPagerAdapter(fragmentManager);
        viewPager.setOffscreenPageLimit(2);
        nbaViewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(fragmentWatchBallPagerAdapter);
        nbaViewPager.setAdapter(fragmentWatchNBAPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ctrlIndicator(position, linearLayout, size);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        nbaViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ctrlIndicator(position, nbaLl, nbaSize);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void refreshData() {
        mPresenter.requestWatchBallList(true);
    }

    @Override
    public void initData() {

        initListener();

    }

    private void initListener() {
        mSwipeRefreshLayout.setEnableLoadmore(false);
        loadingPageView.setClickReload(() -> mPresenter.requestWatchBallList(true));
        searchIv.setOnClickListener(view -> startActivity(new Intent(WEApplication.getContext(), SearchActivity.class)));
        messageIv.setOnClickListener(view -> startActivity(new Intent(WEApplication.getContext(), WatchRecordeActivity.class)));
        luckyIv.setOnClickListener(v -> {
            if (!SaveUserInfo.getLogin())
                startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
            else if (!ClickMoreUtils.isFastClick()) {
                WEApplication.showLuckyPan(getContext());
            }
        });
        liveMoreTv.setOnClickListener(view -> {
            boolean login = SaveUserInfo.getLogin();
            if (!login) {
                startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                return;
            }
            if (data == null)
                return;
            Intent intent = new Intent(WEApplication.getContext(), MatchTimeActivity.class);
            if (data.getLiveList().getDataList() == null || mId == null) {
                showMessage("当前无赛程信息");
            } else {
                intent.putExtra("id", mId);
                startActivity(intent);
            }
        });
        otherGuessMoreTv.setOnClickListener(v -> {
            if (contact == null)
                contact = new Contact(getContext());
            contact.startWebActivity(Constant.OTHER_GUESS_MORE);
        });
        nbaLiveMoreTv.setOnClickListener(v -> {
//            if (data == null)
//                return;
//            Intent intent = new Intent(WEApplication.getContext(), MatchTimeActivity.class);
//            if (data.getNbaList().getDataList() == null || data.getNbaList().getDataList().size() == 0) {
//                showMessage("当前无赛程信息");
//            } else {
//                intent.putExtra("id", mId);
//                startActivity(intent);
//            }
        });
        autoRollLayout.setOnItemClickListener(index -> jumpBanner(index % data.getBannerList().size()));
        recordeMoreTv.setOnClickListener(view -> {
            Intent intent = new Intent(WEApplication.getContext(), MatchCollectionMoreActivity.class);
            startActivity(intent);
        });
        highlightMoreTv.setOnClickListener(view -> {
            Intent intent = new Intent(WEApplication.getContext(), MoreVideoActivity.class);
            intent.putExtra("type", "2");
            intent.putExtra("title", "精彩集锦");
            startActivity(intent);
        });
        learnMoreTv.setOnClickListener(view -> {
            Intent intent = new Intent(WEApplication.getContext(), LearnBallMoreActivity.class);
            startActivity(intent);
        });
//        ViewUtils.clickLaunchActivity(searchIv,getActivity(), SearchActivity.class);
    }


    @Override
    public void onResume() {
        super.onResume();

        mPresenter.requestWatchBallList(true);//打开app时自动加载列表
//        if (data != null)
//            //设置赛事直播
//            setDataToLiveList(data);
        luckyIv.setVisibility(Preference.getBoolean(Constant.CAN_ROTATE, false) ? View.VISIBLE : View.GONE);
        if (Preference.getBoolean(Constant.CAN_ROTATE, false))
            redPoint.setVisibility(Preference.get(Constant.HAS_ROTATE, true) ? View.VISIBLE : View.GONE);
        else
            redPoint.setVisibility(View.GONE);
    }

    private void jumpBanner(int index) {
        if (data == null || data.getBannerList() == null || data.getBannerList().size() == 0)
            return;
        List<WatchBallBean.DataBean.BannerListBean> bannerList = data.getBannerList();
        WatchBallBean.DataBean.BannerListBean bannerListBean = bannerList.get(index);
        switch (bannerListBean.getLink_type()) {
            case "1":
                //赛程页
                if (!SaveUserInfo.getLogin()) {
                    getContext().startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                    return;
                }
                Intent intent = new Intent(getContext(), MatchTimeActivity.class);
                intent.putExtra("id", bannerListBean.getLink());
                startActivity(intent);

                break;
            case "2":
                //视频详情页
                if (!SaveUserInfo.getLogin()) {
                    getContext().startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                    return;
                }
                Intent intent1 = new Intent(getContext(), VideoDetailsActivity.class);
                intent1.putExtra("id", bannerListBean.getLink());
                intent1.putExtra("pic", bannerListBean.getPicture());
                intent1.putExtra("type", bannerListBean.getV_type());
                startActivity(intent1);
                break;
            case "3":
                //跳转商城
                Intent intent2 = new Intent(WEApplication.getContext(), WebViewActivity.class);
                intent2.putExtra("id", bannerListBean.getLink());
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                break;
            case "4":
                //竟猜中心
                if (!SaveUserInfo.getLogin()) {
                    getContext().startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getContext(), GuessCenterActivity.class));
                break;
            case "5":
                //冠军竞猜
                if (!SaveUserInfo.getLogin()) {
                    getContext().startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                    return;
                }
                Intent intent3 = new Intent(getContext(), GuessChampionActivity.class);
                intent3.putExtra("id", bannerListBean.getLink());
                startActivity(intent3);
                break;
            case "6":
                //多选竞猜
                if (!SaveUserInfo.getLogin()) {
                    getContext().startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getContext(), MoreBetActivity.class));
                break;
            case "7":
                //活动详情页
                Intent intent4 = new Intent(getContext(), WebActivity.class);
                intent4.putExtra("url", bannerListBean.getLink_url());
                startActivity(intent4);
                break;
            case "8":
                Intent intent6 = new Intent();
                intent6.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(bannerListBean.getLink_url());
                intent6.setData(content_url);
                startActivity(intent6);
                break;
            case "9":
                //直播间
                if (!SaveUserInfo.getLogin()) {
                    getContext().startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                    return;
                }
                Intent intent5 = new Intent(getContext(), LiveActivity.class);
                intent5.putExtra("id", bannerListBean.getId());
                intent5.putExtra("match_id", bannerListBean.getId());
                intent5.putExtra("url", bannerListBean.getLink_url());
                intent5.putExtra("title", bannerListBean.getTitle());
                intent5.putExtra("moveText", "");
                intent5.putExtra("moveSpeed", "");
                intent5.putExtra("origin", bannerListBean.getOrigin());
                intent5.putExtra("erweimaUrl", "");
                intent5.putExtra("isNBA", false);
                startActivity(intent5);
                break;
            case "10":
                final String qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=" + bannerListBean.getLink() + "&version=1";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));
                break;
        }
    }

    @Override
    public void setData(Object data) {

    }


    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        UiUtils.configRecycleView(starRecyclerview, new LinearLayoutManager(WEApplication.getContext(), LinearLayoutManager.HORIZONTAL, false));
    }


    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.finishRefresh();
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
    public void setAdapter(DefaultAdapter adapter) {
        starRecyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        initRecycleView();
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
    public void setData(Message message) {
        Log.d("onHiddenChanged", "setData");
        loadingPageView.checkData(message);
        data = (WatchBallBean.DataBean) message.obj;
        //设置banner图
        setDataToBanner(data);
        //设置赛事直播
        setDataToLiveList(data);
        //设置NBA赛事直播
//        setDataToNBALiveList(data);
        //设置比赛回放
        setDataToReplayGame(data);
        //设置学球视频
        setDataToLearnBallVideo(data);
        //设置精彩集锦
        setDataToWonderfulSet(data);
        //设置明星视频
        setDataToStarList(data);
        //设置其他竞猜
        setDataToOhterGuess(data);
    }

    private void setDataToOhterGuess(WatchBallBean.DataBean data) {
        WatchBallBean.DataBean.NbaListBean nbaList = data.getNbaList();

//        opne   1:开启  2:屏蔽
//        if (nbaList.getOpne().equals("2")) {
//            otherGuessRv.setVisibility(View.VISIBLE);
//        }

        /**
         *   屏蔽 和 开启
         * */
        Log.d("setDataToOhterGuess", "屏蔽 和 开启");
        if (data.getOn_off().equals("1")) {
            arl_other_cai.setVisibility(View.VISIBLE);
            otherGuessRv.setVisibility(View.VISIBLE);
        } else {
            arl_other_cai.setVisibility(View.GONE);
            otherGuessRv.setVisibility(View.GONE);
        }

        appComponent = ((App) getContext().getApplicationContext()).getAppComponent();
        imageLoader = appComponent.imageLoader();
        imageLoader.loadImage(appComponent.appManager().getCurrentActivity() == null
                ? appComponent.Application() : appComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(nbaList.getIcon(), otherGuessIv));
        otherGuessTitleTv.setText(nbaList.getColumn_name() == null ? "" : nbaList.getColumn_name());
        otherGuessRv.setLayoutManager(new LinearLayoutManager(getContext()));

        List<WatchBallBean.DataBean.NbaListBean.DataListBeanX> dataList = data.getNbaList().getDataList();


        if (dataList.size() == 0) {
            SaveUserInfo.setHasOtherCai(false);
            arl_other_cai.setVisibility(View.GONE);
            otherGuessRv.setVisibility(View.GONE);
        } else {
            SaveUserInfo.setHasOtherCai(true);
        }

        OtherGuessAdapter adapter = new OtherGuessAdapter(dataList);
        otherGuessRv.setAdapter(adapter);
        adapter.setOnItemClickListener((view, viewType, data1, position) -> {
            if (contact == null)
                contact = new Contact(getContext());
            HashMap<String, String> map = new HashMap<>();
            map.put("id", ((WatchBallBean.DataBean.NbaListBean.DataListBeanX) data1).getId() + "");
            contact.startWebActivity(Constant.OTHER_GUESS_DETAILS, map);
        });
    }

    //设置数据学球视频
    private void setDataToLearnBallVideo(WatchBallBean.DataBean data) {
        WatchBallBean.DataBean.SinglePoleBean wonderfulSet = data.getSinglePole();
//        GlideConfigGlobal.loadImageView(wonderfulSet.getIcon(),highlightIv);
        appComponent = ((App) getContext().getApplicationContext()).getAppComponent();
        imageLoader = appComponent.imageLoader();
        imageLoader.loadImage(appComponent.appManager().getCurrentActivity() == null
                ? appComponent.Application() : appComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(wonderfulSet.getIcon(), learnIv));
//        Glide.with(WEApplication.getContext()).load(wonderfulSet.getIcon()).into(highlightIv);
        highlightTitleTv.setText(wonderfulSet.getColumn_name());
        List<WatchBallBean.DataBean.SinglePoleBean.DataListBeanXXXXXX> dataList = wonderfulSet.getDataList();
        if (dataList.size() > 0)
            learnWbivOne.setData(dataList.get(0));
        else
            learnWbivOne.setVisibility(View.GONE);
        if (dataList.size() > 1)
            learnWbivTwo.setData(dataList.get(1));
        else {
            learnWbivTwo.setVisibility(View.GONE);
            if (dataList.size() == 1)
                learnWbivTwo.setVisibility(View.INVISIBLE);
        }
        if (dataList.size() > 2)
            learnWbivThree.setData(dataList.get(2));
        else
            learnWbivThree.setVisibility(View.GONE);
        if (dataList.size() > 3)
            learnWbivFour.setData(dataList.get(3));
        else {
            learnWbivFour.setVisibility(View.GONE);
            if (dataList.size() == 3)
                learnWbivFour.setVisibility(View.INVISIBLE);
        }
    }

    //设置数据明星列表
    private void setDataToStarList(WatchBallBean.DataBean data) {
        titleTv.setText(data.getStarList().getColumn_name());
//        GlideConfigGlobal.loadImageView(data.getStarList().getIcon(),starIv);
        appComponent = ((App) getContext().getApplicationContext()).getAppComponent();
        imageLoader = appComponent.imageLoader();
        imageLoader.loadImage(appComponent.appManager().getCurrentActivity() == null
                ? appComponent.Application() : appComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(data.getStarList().getIcon(), starIv));
//        Glide.with(WEApplication.getContext()).load(data.getStarList().getIcon()).into(starIv);
    }

    //设置数据精彩集锦
    private void setDataToWonderfulSet(WatchBallBean.DataBean data) {
        WatchBallBean.DataBean.WonderfulSetBean wonderfulSet = data.getWonderfulSet();
//        GlideConfigGlobal.loadImageView(wonderfulSet.getIcon(),highlightIv);
        appComponent = ((App) getContext().getApplicationContext()).getAppComponent();
        imageLoader = appComponent.imageLoader();
        imageLoader.loadImage(appComponent.appManager().getCurrentActivity() == null
                ? appComponent.Application() : appComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(wonderfulSet.getIcon(), highlightIv));
//        Glide.with(WEApplication.getContext()).load(wonderfulSet.getIcon()).into(highlightIv);
        highlightTitleTv.setText(wonderfulSet.getColumn_name());
        List<WatchBallBean.DataBean.WonderfulSetBean.DataListBeanXXXX> dataList = wonderfulSet.getDataList();
        if (dataList.size() > 0)
            wonderfulWbivOne.setData(dataList.get(0));
        else
            wonderfulWbivOne.setVisibility(View.GONE);
        if (dataList.size() > 1)
            wonderfulWbivTwo.setData(dataList.get(1));
        else {
            wonderfulWbivTwo.setVisibility(View.GONE);
            if (dataList.size() == 1)
                wonderfulWbivTwo.setVisibility(View.INVISIBLE);
        }
        if (dataList.size() > 2)
            wonderfulWbivThree.setData(dataList.get(2));
        else
            wonderfulWbivThree.setVisibility(View.GONE);
        if (dataList.size() > 3)
            wonderfulWbivFour.setData(dataList.get(3));
        else {
            wonderfulWbivFour.setVisibility(View.GONE);
            if (dataList.size() == 3)
                wonderfulWbivFour.setVisibility(View.INVISIBLE);
        }
    }

    //设置数据比赛回放
    private void setDataToReplayGame(WatchBallBean.DataBean data) {
        WatchBallBean.DataBean.GameCollectionBean replayGame = data.getGameCollection();
//        GlideConfigGlobal.loadImageView(replayGame.getIcon(),recordeIv);
        appComponent = ((App) getContext().getApplicationContext()).getAppComponent();
        imageLoader = appComponent.imageLoader();
        imageLoader.loadImage(appComponent.appManager().getCurrentActivity() == null
                ? appComponent.Application() : appComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(replayGame.getIcon(), recordeIv));
//        Glide.with(WEApplication.getContext()).load(replayGame.getIcon()).into(recordeIv);
        recordeTitleTv.setText(replayGame.getColumn_name());
        List<WatchBallBean.DataBean.GameCollectionBean.DataListBeanXX> dataList = data.getGameCollection().getDataList();
        if (dataList.size() > 0)
            recordeWbivOne.setData(dataList.get(0));
        else
            recordeWbivOne.setVisibility(View.GONE);
        if (dataList.size() > 1)
            recordeWbivTwo.setData(dataList.get(1));
        else {
            recordeWbivTwo.setVisibility(View.GONE);
            if (dataList.size() == 1)
                recordeWbivTwo.setVisibility(View.INVISIBLE);
        }
        if (dataList.size() > 2)
            recordeWbivThree.setData(dataList.get(2));
        else
            recordeWbivThree.setVisibility(View.GONE);
        if (dataList.size() > 3)
            recordeWbivFour.setData(dataList.get(3));
        else {
            recordeWbivFour.setVisibility(View.GONE);
            if (dataList.size() == 3)
                recordeWbivTwo.setVisibility(View.INVISIBLE);
        }

    }

    //设置数据 直播
    private void setDataToLiveList(WatchBallBean.DataBean data) {
//        if (fragments != null && fragments.size() != 0) {
//            for (int i = 0; i < fragments.size(); i++) {
//                fragmentManager.beginTransaction().remove(fragments.get(i));
//            }
//        }
        leftList.clear();
        rightList.clear();
        fragments.clear();
        WatchBallBean.DataBean.LiveListBean liveList = data.getLiveList();

        liveTv.setText(liveList.getColumn_name());
//        GlideConfigGlobal.loadImageView(liveList.getIcon(),liveIv);
        appComponent = ((App) getContext().getApplicationContext()).getAppComponent();
        imageLoader = appComponent.imageLoader();
        imageLoader.loadImage(appComponent.appManager().getCurrentActivity() == null
                ? appComponent.Application() : appComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(liveList.getIcon(), liveIv));
//        Glide.with(WEApplication.getContext()).load(liveList.getIcon()).into(liveIv);
        List<WatchBallBean.DataBean.LiveListBean.DataListBean> dataList = liveList.getDataList();
        if (dataList == null || dataList.size() == 0) {
            viewPager.setVisibility(View.GONE);
        } else {
            viewPager.setVisibility(View.VISIBLE);
        }
        if (data.getLiveList().getDataList().size() > 0) {
            mId = data.getLiveList().getDataList().get(0).getBigMatch_id();
        }
        //分开奇偶数据，装进两个List
        for (int i = 0; i < dataList.size(); i++) {
            if (i % 2 == 0)
                //左边比赛模块List
                leftList.add(dataList.get(i));
            else
                //右边比赛模块List
                rightList.add(dataList.get(i));
        }
        size = leftList.size();

        //动态创建比赛模块Fragment
        for (int i = 0; i < size; i++) {

            WatchBallBean.DataBean.LiveListBean.DataListBean dataListBean = null;
            //如果rightList数量不比leftList少
            if (leftList.size() == rightList.size() || size - 1 != i)
                dataListBean = rightList.get(i);
            else
                dataListBean = null;
            //将数据传入fragment
//            RateSmallFragment rateSmallFragment = new RateSmallFragment();
            RateSmallFragment rateSmallFragment = RateSmallFragment.newInstance(leftList.get(i), dataListBean);
            rateSmallFragment.setOnRateSmallFragmentClick(new RateSmallFragment.OnRateSmallFragmentClick() {
                @Override
                public void onRateSmallFragmentClick() {
                    /**
                     * 点击赛事就刷新
                     * */
                    mPresenter.requestWatchBallList(true);

                }
            });

            fragments.add(rateSmallFragment);
        }
        //viewpager适配器绑定fragment
        fragmentWatchBallPagerAdapter.bindData(fragments);
        fragmentWatchBallPagerAdapter.updateData(leftList, rightList);
        if (!isFirst) {
            initIndicator(size, getContext(), linearLayout);
            isFirst = !isFirst;
        }
        //Indicator控制为0
        ctrlIndicator(0, linearLayout, size);
    }

//    //设置数据 直播
//    private void setDataToNBALiveList(WatchBallBean.DataBean data) {
////        if (fragments != null && fragments.size() != 0) {
////            for (int i = 0; i < fragments.size(); i++) {
////                fragmentManager.beginTransaction().remove(fragments.get(i));
////            }
////        }
//        nbaLeftList.clear();
//        nbaRightList.clear();
//        nbaFragments.clear();
//        WatchBallBean.DataBean.NbaListBean liveList = data.getNbaList();
//
//        nbaLiveTv.setText(liveList.getColumn_name());
////        GlideConfigGlobal.loadImageView(liveList.getIcon(),liveIv);
//        appComponent = ((App) getContext().getApplicationContext()).getAppComponent();
//        imageLoader = appComponent.imageLoader();
//        imageLoader.loadImage(appComponent.appManager().getCurrentActivity() == null
//                ? appComponent.Application() : appComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(liveList.getIcon(), nbaLiveIv));
////        Glide.with(WEApplication.getContext()).load(liveList.getIcon()).into(liveIv);
//        List<WatchBallBean.DataBean.NbaListBean.DataListBeanX> dataList = liveList.getDataList();
//        if (dataList == null || dataList.size() == 0) {
//            nbaViewPager.setVisibility(View.GONE);
//        } else {
//            nbaViewPager.setVisibility(View.VISIBLE);
//        }
//        if (dataList.size() > 0)
//            nbaMid = dataList.get(0).getBigMatch_id();
//        //分开奇偶数据，装进两个List
//        for (int i = 0; i < dataList.size(); i++) {
//            if (i % 2 == 0)
//                //左边比赛模块List
//                nbaLeftList.add(dataList.get(i));
//            else
//                //右边比赛模块List
//                nbaRightList.add(dataList.get(i));
//        }
//        nbaSize = nbaLeftList.size();
//
//        //动态创建比赛模块Fragment
//        for (int i = 0; i < nbaSize; i++) {
//
//            WatchBallBean.DataBean.NbaListBean.DataListBeanX dataListBean = null;
//            //如果rightList数量不比leftList少
//            if (nbaLeftList.size() == nbaRightList.size() || nbaSize - 1 != i)
//                dataListBean = nbaRightList.get(i);
//            else
//                dataListBean = null;
//            //将数据传入fragment
////            RateSmallFragment rateSmallFragment = new RateSmallFragment();
//            NBARateSmallFragment rateSmallFragment = NBARateSmallFragment.newInstance(nbaLeftList.get(i), dataListBean);
//            nbaFragments.add(rateSmallFragment);
//        }
//        //viewpager适配器绑定fragment
//        fragmentWatchNBAPagerAdapter.bindData(nbaFragments);
//        fragmentWatchNBAPagerAdapter.updateData(nbaLeftList, nbaRightList);
//        if (!isNBAFirst) {
//            initIndicator(nbaSize, getContext(), nbaLl);
//            isNBAFirst = !isNBAFirst;
//        }
//        //Indicator控制为0
//        ctrlIndicator(0, nbaLl, nbaSize);
//    }

    //设置数据轮播图
    private void setDataToBanner(WatchBallBean.DataBean data) {
        List<WatchBallBean.DataBean.BannerListBean> bannerList = data.getBannerList();
        items.clear();
        advList.clear();
        for (int i = 0; i < (bannerList == null ? 0 : bannerList.size()); i++) {
            WatchBallBean.DataBean.BannerListBean bannerListBean = bannerList.get(i);
            Auto auto = new Auto(bannerListBean.getPicture(), bannerListBean.getTitle());
            items.add(auto);
            advList.add(bannerListBean);
        }
        autoRollLayout.getItemList().addAll(items);
        autoRollLayout.notifyPage();
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
                    mPresenter.requestWatchBallList(false);
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

            mPaginate = Paginate.with(starRecyclerview, callbacks)
                    .setLoadingTriggerThreshold(10).setLoadingListItemCreator(new CustomLoadingListItemCreator())
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    @Override
    public void onDestroy() {
        DefaultAdapter.releaseAllHolder(starRecyclerview);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
        this.mRxPermissions = null;
        this.mPaginate = null;
//        imageLoader.clear(appComponent.Application(), GlideImageConfig.builder()
//                .imageViews(starIv,recordeIv,highlightIv)
//                .build());
    }


    /**
     * 控制indicator的切换
     *
     * @param index        索引为当前viewpager的currentitemPosition 注意求余
     * @param linearLayout
     */
    private static void ctrlIndicator(int index, LinearLayout linearLayout, int size) {
        if (size == 0) {
            return;
        }
        ImageView imageView = (ImageView) linearLayout.getChildAt((index) % size);
        for (int i = 0; i < size; i++) {
            ImageView imageView1 = (ImageView) linearLayout.getChildAt((i) % size);
            if (imageView1 != null)
                imageView1.setEnabled(false);
        }
        if (imageView != null)
            imageView.setEnabled(true);
    }

    /**
     * 初始化indicator
     *
     * @param size
     * @param mContext
     */
    private void initIndicator(int size, Context mContext, LinearLayout linearLayout) {
        int dx = (int) mContext.getResources().getDimension(R.dimen.x12);
        int dy = (int) mContext.getResources().getDimension(R.dimen.x12);
        for (int i = 0; i < size; i++) {
            ImageView bt = new ImageView(mContext);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(dy, dx, dy, dx);
            bt.setLayoutParams(layoutParams);
            bt.setBackgroundResource(R.drawable.selector_home_pager_indicator);
            linearLayout.addView(bt);
        }
    }

    @Override
    public void onItemClick(View view, int viewType, Object data, int position) {
        if (!SaveUserInfo.getLogin()) {
            getContext().startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
            return;
        }
        Intent intent = new Intent(getContext(), StarDetailsActivity.class);
        WatchBallBean.DataBean.StarListBean.DataListBeanXXXXX dataBean = (WatchBallBean.DataBean.StarListBean.DataListBeanXXXXX) data;
        intent.putExtra("starId", dataBean.getId() + "");
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }

    @Override
    public void onLoadSuccess(List<ParseEntityModle> list, int i) {

    }

    @Override
    public void onLoadFailure(String s, int i, int i1, Exception e) {

    }

    @Override
    public void onShowProgress() {

    }

    @Override
    public void onHideProgress() {

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.requestWatchBallList(true);
    }
}
