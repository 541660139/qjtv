package com.lwd.qjtv.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.jess.arms.base.AdapterViewPager;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.rrmj.zhongduomei.videoparsesdk.parseurl.IOnParseUrlListener;
import com.rrmj.zhongduomei.videoparsesdk.parseurl.bean.ParseEntityModle;
import com.shuyu.gsyvideoplayer.GSYPreViewManager;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.LiveDanmakuVideoPlayer;
import com.lwd.qjtv.app.utils.NetworkUtils;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.SampleListener;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.app.utils.SoftInputUtils;
import com.lwd.qjtv.di.component.DaggerLiveComponent;
import com.lwd.qjtv.di.module.LiveModule;
import com.lwd.qjtv.mvp.contract.LiveContract;
import com.lwd.qjtv.mvp.model.entity.LiveParserBean;
import com.lwd.qjtv.mvp.model.entity.VideoDetailsBean;
import com.lwd.qjtv.mvp.presenter.LivePresenter;
import com.lwd.qjtv.mvp.ui.fragment.live.LiveChatFragment;
import com.lwd.qjtv.mvp.ui.fragment.live.LiveGuessFragment;


import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.ChatRoomMessageEvent;

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

public class LiveActivity extends BaseActivity<LivePresenter> implements LiveContract.View, IOnParseUrlListener {
    TabLayout tabLayout;
    ViewPager viewPager;
    //    private TextView gonggaoTv;
    LiveDanmakuVideoPlayer danmakuVideoPlayer;

    private List<BaseFragment> fragments;
    //半高
    private String[] titles = {
            "聊天",
            "竞猜"
    };
    private AdapterViewPager adapterViewPager;
    private String type;
    private String moveSpeed;
    private String origin;
    private String gonggao;
    private String id;
    private String match_id;
    //直播聊天模块
    private LiveChatFragment videoChildDetailsFragment;
    //直播竞猜模块
    private LiveGuessFragment commentMessageFragment;
    private AlertDialog commonDialog;
    private String baseParseParams = "type=live&info-only=false";
    //处理网络
    private Handler netHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 22:
//                    showMessage("wifi网络");
                    break;
                case 33:
                    if (Preference.get(Constant.IS_REMIND_NET, true)) {
                        commonDialog = new AlertDialog.Builder(context).create();
                        commonDialog.setTitle("警告");
                        commonDialog.setMessage("当前网络非WIFI连接，是否继续？");
                        commonDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", ((dialog, which) -> {
                            dialog.cancel();
                            commonDialog = null;
                            finish();
                        }));
                        commonDialog.setButton(AlertDialog.BUTTON_POSITIVE, "继续", ((dialog, which) -> {
                            dialog.cancel();
                            commonDialog = null;
                        }));
                        commonDialog.show();
                    } else {
                        if (commonDialog != null) {
                            commonDialog.cancel();
                            commonDialog = null;
                        }
                    }
//                    showMessage("3g网络");
                    break;
            }
        }

    };


    private boolean isPlay;
    private boolean isPause;

    private OrientationUtils orientationUtils;
    private String mUrl;
    private String title;
    private Context context;
    private boolean isGuess;
    private String moveText;
    private String erweimaUrl;
    private boolean isNBA;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerLiveComponent
                .builder()
                .appComponent(appComponent)
                .liveModule(new LiveModule(this)) //请将VideoDetailsModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return R.layout.activity_live;
    }


    @Override
    public void initData() {
        context = this;
        findView();
        //获取传递数据
        getIntentData();
        //加入网络监听Handler处理回调
        NetworkUtils.addEventHandler(netHandler);
        //注册网络监听
        NetworkUtils.registerNetworkReceiver(this);
        //初始化ViewPager
        initViewPager();

        initGsyVideoPlayer();

        //注册一个接收的广播
        JMessageClient.registerEventReceiver(this);
    }


    // 接收聊天室消息
    public void onEventMainThread(ChatRoomMessageEvent event) {
        Log.e("wangfeng", "收到了聊天室消息");
        List<cn.jpush.im.android.api.model.Message> msgs = event.getMessages();
        videoChildDetailsFragment.updateUI(msgs);
    }


    private void findView() {
        tabLayout = (TabLayout) findViewById(R.id.activity_live_tablayout);
        viewPager = (ViewPager) findViewById(R.id.activity_live_viewpager);
//        gonggaoTv = (TextView) findViewById(R.id.activity_live_gonggao_tv);
        danmakuVideoPlayer = (LiveDanmakuVideoPlayer) findViewById(R.id.danmaku_player);
    }

//    public void setGonggaoTv(String gonggao) {
//        gonggaoTv.setVisibility((gonggao == null || gonggao.equals("")) ? View.GONE : View.VISIBLE);
//        gonggaoTv.setText(gonggao == null ? "" : gonggao);
//    }

    public void liveOver() {
        showMessage("直播已结束");
    }


    public void setUrl(String url, String origin) {
        mUrl = url;
        this.origin = origin;
        initGsyVideoPlayer();
    }

    //初始化播放器
    private void initGsyVideoPlayer() {


        danmakuVideoPlayer.backIv.setOnClickListener(v -> finish());

        danmakuVideoPlayer.setClickBack(v -> finish());
        //使用自定义的全屏切换图片，!!!注意xml布局中也需要设置为一样的
        //必须在setUp之前设置
        danmakuVideoPlayer.setShrinkImageRes(R.drawable.custom_shrink);
        danmakuVideoPlayer.setEnlargeImageRes(R.drawable.custom_enlarge);

        danmakuVideoPlayer.setIsTouchWiget(true);
        if ("4".equals(origin)) {
            String params = baseParseParams + "&url=" + mUrl.trim();
            Log.d(TAG, "onAutoComplete: " + params);
            new Thread(() -> mPresenter.parseVideoWithParams(params, false)).start();
        } else if ("0".equals(origin)) {
            danmakuVideoPlayer.setUp(mUrl, false, null, title);
        } else {
            danmakuVideoPlayer.setUp(mUrl, false, null, title);
        }
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        danmakuVideoPlayer.setThumbImageView(imageView);

        danmakuVideoPlayer.clearThumbImageView();
        resolveNormalVideoUI();


        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, danmakuVideoPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        danmakuVideoPlayer.setIsTouchWiget(true);
        //关闭自动旋转
        danmakuVideoPlayer.setRotateViewAuto(false);
        danmakuVideoPlayer.setLockLand(false);
        danmakuVideoPlayer.setShowFullAnimation(false);
//        danmakuVideoPlayer.setNeedLockFull(true);


//        if (!SaveUserInfo.getUserType()) {
//            /**
//             * 当用户非充值用户时进入直播间为全屏
//             *
//             * */
//            orientationUtils.resolveByClick();
//            danmakuVideoPlayer.startWindowFullscreen(context, true, true, false, "");
//            danmakuVideoPlayer.setListener(new GSYBaseVideoPlayer.BackButtonClickListen() {
//                @Override
//                public void onBackButtonClickListen() {
//                    finish();
//                }
//            });
//
//            Toast.makeText(context, "充值任意金额后开启聊天和弹幕功能", LENGTH_SHORT).show();
//        }

        danmakuVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoftInputUtils.hideSoftInput(getWindow().getDecorView(), context);
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                danmakuVideoPlayer.startWindowFullscreen(context, true, true, false, "");
            }
        });


        danmakuVideoPlayer.setStandardVideoAllCallBack(new SampleListener() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                //开始播放了才能旋转和全屏
                orientationUtils.setEnable(true);
                isPlay = true;
            }

            @Override
            public void onAutoComplete(String url, Object... objects) {
                super.onAutoComplete(url, objects);
                Log.d(TAG, "onAutoComplete: " + mUrl);
                if ("4".equals(origin)) {
                    String params = baseParseParams + "&url=" + mUrl.trim();
                    Log.d(TAG, "onAutoComplete: " + params);
                    new Thread(() -> mPresenter.parseVideoWithParams(params, false)).start();
                } else if ("0".equals(origin)) {
                    danmakuVideoPlayer.setUp(mUrl, false, null, title);
                } else {
                    danmakuVideoPlayer.setUp(mUrl, false, null, title);
                }
            }

            @Override
            public void onClickStartError(String url, Object... objects) {
                super.onClickStartError(url, objects);
                if ("4".equals(origin)) {

                    String params = baseParseParams + "&url=" + mUrl.trim();
                    Log.d(TAG, "onAutoComplete: " + params);
                    new Thread(() -> mPresenter.parseVideoWithParams(params, false)).start();
                } else if ("0".equals(origin)) {
                    danmakuVideoPlayer.setUp(mUrl, false, null, title);
                } else {
                    danmakuVideoPlayer.setUp(mUrl, false, null, title);
                }
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
                if (orientationUtils != null) {
                    orientationUtils.backToProtVideo();
                }
            }
        });

        danmakuVideoPlayer.setLockClickListener((view, b) -> {
            if (orientationUtils != null) {
                //配合下方的onConfigurationChanged
                orientationUtils.setEnable(!b);
            }
        });

        danmakuVideoPlayer.startPlayLogic();
    }


    private void getIntentData() {
        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
            match_id = getIntent().getStringExtra("match_id");
            mUrl = getIntent().getStringExtra("url");
            title = getIntent().getStringExtra("title");
            isGuess = getIntent().getBooleanExtra("isGuess", false);
            moveText = getIntent().getStringExtra("moveText");
            moveSpeed = getIntent().getStringExtra("moveSpeed");
            origin = getIntent().getStringExtra("origin");
            gonggao = getIntent().getStringExtra("gonggao");
            erweimaUrl = getIntent().getStringExtra("erweimaUrl");
            isNBA = getIntent().getBooleanExtra("isNBA", false);
            if (erweimaUrl != null && !"".equals(erweimaUrl))
                danmakuVideoPlayer.setErweima(erweimaUrl);
            if (moveSpeed != null && !"".equals(moveSpeed))
                danmakuVideoPlayer.setAdvAstv(moveText, (int) Float.parseFloat(moveSpeed) * 100);
            mPresenter.liveUser(true, id);
//            setGonggaoTv(gonggao);
//            RRSdkUtils.parseUrl(this, url, this);
//            RRSdkUtils.parseUrl(this, "http://v.youku.com/v_show/id_XMjgxNTYwNzA5Ng==.html", this);
//            mPresenter.getVideoDetails(id, type);
        }
    }


    private void initViewPager() {

        tabLayout.setTabTextColors(getResources().getColor(R.color.color666666), getResources().getColor(R.color.colorOrigin));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorOrigin));
        tabLayout.post(() -> AdapterViewPager.setIndicator(getApplicationContext(), tabLayout, 60, 60));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                if (position == 0)
//                    gonggaoTv.setVisibility(View.VISIBLE);
//                else
//                    gonggaoTv.setVisibility(View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOffscreenPageLimit(2);
        fragments = new ArrayList<>();
        //直播聊天fragment
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        videoChildDetailsFragment = LiveChatFragment.newInstance();
        videoChildDetailsFragment.setArguments(bundle);
        //竞猜详情fragment
        Bundle bundle2 = new Bundle();
        bundle2.putString("match_id", match_id);
        bundle2.putBoolean("isNBA", isNBA);
        commentMessageFragment = LiveGuessFragment.newInstance();
        commentMessageFragment.setArguments(bundle2);
        videoChildDetailsFragment.setData(danmakuVideoPlayer);
        fragments.add(videoChildDetailsFragment);
        /**
         *  屏蔽竞猜
         * */
        if (Preference.getBoolean(Constant.IS_GUESS) && Preference.getBoolean(Constant.ON_OFF)) {
            fragments.add(commentMessageFragment);
        } else {
            tabLayout.setSelectedTabIndicatorHeight(0);
        }

        adapterViewPager = new AdapterViewPager(getSupportFragmentManager());
        AdapterViewPager.setIndicator(this, tabLayout, 50, 50);
        viewPager.setAdapter(adapterViewPager);
        adapterViewPager.bindData(fragments, titles);
        tabLayout.setupWithViewPager(viewPager);
        if (getIntent().hasExtra("isGuess")) {
            viewPager.setCurrentItem(1);
        }
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
//        UiUtils.SnackbarText(message);
        UiUtils.makeText(this, message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void setData(Object o) {
        VideoDetailsBean.DataBean dataBean = (VideoDetailsBean.DataBean) o;
        videoChildDetailsFragment.setData(dataBean);
    }

    @Override
    public void onBackPressed() {

        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }

        if (StandardGSYVideoPlayer.backFromWindowFull(this) && SaveUserInfo.getUserType()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCurPlay().onVideoResume();
        isPause = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        getCurPlay().onVideoPause();
        isPause = true;
    }


    @Override
    protected void onStart() {
        super.onStart();

//        if (!Preference.getBoolean(Constant.IS_LOGIN))
//            finish();


        isPause = false;
        initGsyVideoPlayer();
//        danmakuVideoPlayer.onVideoResume();
    }


    @Override
    protected void onDestroy() {
        mPresenter.liveUser(false, id);
        super.onDestroy();
        if (isPlay) {
            getCurPlay().release();
        }
        GSYVideoPlayer.releaseAllVideos();
        GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
        NetworkUtils.removeEventHandler(netHandler);
        NetworkUtils.unRegisterNetworkReceiver(this);
        JMessageClient.unRegisterEventReceiver(this);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                if (!danmakuVideoPlayer.isIfCurrentIsFullscreen()) {
                    danmakuVideoPlayer.startWindowFullscreen(context, true, true, false, "");
                    SoftInputUtils.hideSoftInput(getWindow().getDecorView(), context);
                }
            } else {
                //新版本isIfCurrentIsFullscreen的标志位内部提前设置了，所以不会和手动点击冲突
                if (danmakuVideoPlayer.isIfCurrentIsFullscreen()) {
                    StandardGSYVideoPlayer.backFromWindowFull(this);
                }
                if (orientationUtils != null) {
                    orientationUtils.setEnable(true);
                }
            }
        }
    }


    private void resolveNormalVideoUI() {
        //增加title
        danmakuVideoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        danmakuVideoPlayer.getTitleTextView().setText(title);
        danmakuVideoPlayer.getBackButton().setVisibility(View.VISIBLE);
    }


    @Override
    public void onLoadSuccess(List<ParseEntityModle> list, int i) {
        ParseEntityModle parseEntityModle = list.get(0);
        String url = parseEntityModle.getM3U8Model().getUrl();
        Log.d(TAG, "onLoadSuccess: " + url);
        danmakuVideoPlayer.setUp(url, true, null, title);
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
    public void videoParseFinish(LiveParserBean videoParse) {
        createPlayUrl(videoParse.getData().getStreams().get(0));
    }

    @Override
    public void videoDownloadParseFinish(LiveParserBean videoParse) {
        createPlayUrl(videoParse.getData().getStreams().get(0));
    }

    private void createPlayUrl(LiveParserBean.DataBean.StreamsBean streamsBean) {
        String url = streamsBean.getUrl();
        danmakuVideoPlayer.setUp(url, false, null, title);
        Log.d(TAG, "createPlayUrl: " + url);
        danmakuVideoPlayer.getStartButton().callOnClick();
    }

    private GSYVideoPlayer getCurPlay() {
        if (danmakuVideoPlayer.getFullWindowPlayer() != null) {
            return danmakuVideoPlayer.getFullWindowPlayer();
        }
        return danmakuVideoPlayer;
    }




}
