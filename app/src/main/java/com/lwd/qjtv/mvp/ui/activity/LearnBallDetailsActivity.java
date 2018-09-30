package com.lwd.qjtv.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.AdapterViewPager;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.delegate.IActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.rrmj.zhongduomei.videoparsesdk.parseurl.IOnParseUrlListener;
import com.rrmj.zhongduomei.videoparsesdk.parseurl.ParseUrl;
import com.rrmj.zhongduomei.videoparsesdk.parseurl.bean.ParseEntityModle;
import com.shuyu.gsyvideoplayer.GSYPreViewManager;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.model.GSYVideoModel;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.ListGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.usher.greendao_demo.greendao.gen.WatchHistoryBeanDao;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.DateTools;
import com.lwd.qjtv.app.utils.FileUtils;
import com.lwd.qjtv.app.utils.NetworkUtils;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.SampleListener;
import com.lwd.qjtv.app.utils.SoftInputUtils;
import com.lwd.qjtv.di.component.DaggerLearnBallDetailsComponent;
import com.lwd.qjtv.di.module.LearnBallDetailsModule;
import com.lwd.qjtv.mvp.contract.LearnBallDetailsContract;
import com.lwd.qjtv.mvp.model.entity.PingFenBean;
import com.lwd.qjtv.mvp.model.entity.VideoDetailsBean;
import com.lwd.qjtv.mvp.model.entity.VideoParse;
import com.lwd.qjtv.mvp.model.entity.WatchHistoryBean;
import com.lwd.qjtv.mvp.presenter.LearnBallDetailsPresenter;
import com.lwd.qjtv.mvp.ui.fragment.other.CommentMessageFragment;
import com.lwd.qjtv.mvp.ui.fragment.video.VideoChildDetailsFragment;
import com.lwd.qjtv.view.RatingDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
 * Created by ZhengQian on 2017/5/24.
 */

public class LearnBallDetailsActivity extends BaseActivity<LearnBallDetailsPresenter> implements LearnBallDetailsContract.View, IActivity {

    @BindView(R.id.danmaku_player)
    ListGSYVideoPlayer danmakuVideoPlayer;
    @BindView(R.id.activity_learnball_details_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.activity_learnball_details_viewpager)
    ViewPager viewPager;
    private AdapterViewPager adapterViewPager;
    private int position;
    private Map<String, String> mNeedreferer;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private String starId;
    private String type = "";

    private String videoId, v_type;
    private OrientationUtils orientationUtils;
    private int tDuration;
    private String filePath;
    private String videoName = "";
    private String mUrl;
    private List<String> fUrls = new ArrayList<>();
    private List<GSYVideoModel> mUrls = new ArrayList<>();
    private String[] titles = {
            "详情",
            "评论"
    };
    private VideoChildDetailsFragment starIntroduceFragment;
    private CommentMessageFragment wonderfulVideoFragment;
    private AppComponent appComponent;
    private ImageLoader imageLoader;
    private boolean isPlay;
    private boolean isPause;
    private Context context;
    private String sdCardPath;
    private AlertDialog commonDialog;
    private int time;
    private String baseParseParams = "type=vod&info-only=false";

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
                            commonDialog.cancel();
                            commonDialog = null;
                            finish();
                        }));
                        commonDialog.setButton(AlertDialog.BUTTON_POSITIVE, "继续", ((dialog, which) -> {
                            commonDialog.cancel();
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
    private String pic;
    private WatchHistoryBeanDao watchHistoryDao;
    private WatchHistoryBean watchHistoryBean;
    private boolean isPingfen;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerLearnBallDetailsComponent
                .builder()
                .appComponent(appComponent)
                .learnBallDetailsModule(new LearnBallDetailsModule(this)) //请将LearnBallDetailsModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_learnball_details;
    }


    @Override
    public void initData() {
        type = getIntent().getStringExtra("type");

        starId = getIntent().getStringExtra("starId");
        videoId = getIntent().getStringExtra("videoId");
        v_type = getIntent().getStringExtra("v_type");
        pic = getIntent().getStringExtra("pic");
        if (getIntent().hasExtra("time"))
            time = getIntent().getIntExtra("time", 0);
        mPresenter.getPingFen(videoId);
        context = this;
        sdCardPath = FileUtils.getStoragePath(this, false);
        //加入网络监听Handler处理回调
        NetworkUtils.addEventHandler(netHandler);
        //注册网络监听
        NetworkUtils.registerNetworkReceiver(this);

        initGsyVideoPlayer();
        initFragment();
        initListener();
        initViewPager();
        mPresenter.getLearnBallVideo(videoId, v_type);
    }

    //初始化播放器
    private void initGsyVideoPlayer() {

        //使用自定义的全屏切换图片，!!!注意xml布局中也需要设置为一样的
        //必须在setUp之前设置
        danmakuVideoPlayer.setShrinkImageRes(R.drawable.custom_shrink);
        danmakuVideoPlayer.setEnlargeImageRes(R.drawable.custom_enlarge);

        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setImageResource(R.mipmap.ic_launcher);
        Glide.with(this).load(pic).into(imageView);
        danmakuVideoPlayer.setThumbImageView(imageView);

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
        danmakuVideoPlayer.setNeedLockFull(true);
        danmakuVideoPlayer.getBackButton().setOnClickListener(v -> {
            finish();
        });
        danmakuVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoftInputUtils.hideSoftInput(getWindow().getDecorView(), context);
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                danmakuVideoPlayer.startWindowFullscreen(LearnBallDetailsActivity.this, true, true, false, "");
            }
        });

        danmakuVideoPlayer.setStandardVideoAllCallBack(new SampleListener() {
            private RatingDialog ratingDialog;

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
                if (!isPingfen) {

                    /**
                     *  去除评分
                     * */
//                    if (ratingDialog == null)
//                        ratingDialog = new RatingDialog(LearnBallDetailsActivity.this);
//                    ratingDialog.setCallBack(isPingfen1 -> isPingfen = isPingfen1);
//                    ratingDialog.setVideoId(videoId);
//                    ratingDialog.show();
                }
            }

            @Override
            public void onClickStartError(String url, Object... objects) {
                super.onClickStartError(url, objects);
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
                if (orientationUtils != null) {
                    orientationUtils.backToProtVideo();
                }
            }
        });

        danmakuVideoPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        });

    }

    private void initFragment() {
        //学球详情页传值
        Bundle starIntroduceBundle = new Bundle();
//        starIntroduceBundle.putString("starId", starId);
        starIntroduceBundle.putString("videoId", videoId);
        starIntroduceBundle.putString("v_type", v_type);
        starIntroduceFragment = VideoChildDetailsFragment.newInstance();
        starIntroduceFragment.setArguments(starIntroduceBundle);
        fragmentList.add(starIntroduceFragment);

        //视频列表页传值
//        wonderfulVideoBundle.putString("videoId", videoId);
//        wonderfulVideoBundle.putString("v_type", v_type);
//        wonderfulVideoBundle.putString("op", "wonderful");
//        wonderfulVideoBundle.putString("starId", starId);
        wonderfulVideoFragment = CommentMessageFragment.newInstance();

        fragmentList.add(wonderfulVideoFragment);
    }

    private void initViewPager() {
        tabLayout.setTabTextColors(getResources().getColor(R.color.color666666), getResources().getColor(R.color.colorOrigin));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorOrigin));
        adapterViewPager = new AdapterViewPager(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        adapterViewPager.bindData(fragmentList, titles);
        tabLayout.setupWithViewPager(viewPager);
        if (!TextUtils.isEmpty(type) && type.equals("type")) {
            viewPager.setCurrentItem(1);
        }
    }

    private void initListener() {
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
        if (o instanceof VideoDetailsBean.DataBean) {

            VideoDetailsBean.DataBean dataBean = (VideoDetailsBean.DataBean) o;
            Message message = new Message();
            message.what = 0;
            message.obj = dataBean;
            mUrl = dataBean.getUrl();
            videoName = dataBean.getName();
            insertDao(dataBean);
            String intVideo = null;
            //切割播放时长显示
            String video_length = dataBean.getVideo_length();
            if (video_length != null && !video_length.equals("")) {
                intVideo = video_length.replace("分钟", "");
                int length = video_length.length();
                if (length > 4)
                    intVideo = video_length.substring(0, length - 4);
            }
            videoName = dataBean.getName();
            if (intVideo != null && !intVideo.equals(""))
                tDuration = Integer.parseInt(intVideo);
            else
                tDuration = 200;
            //如果来源属于直连
            if ("2".equals(dataBean.getOrigin()))
                danmakuVideoPlayer.setUp(dataBean.getUrl(), false, "");

                //否则解析

            else if ("4".equals(dataBean.getOrigin())) {
                String params = baseParseParams + "&url=" + mUrl.trim();
                Log.d("getOrigin", "4:" + params);
                mPresenter.parseVideoWithParams(params, false);
            } else
//            danmakuVideoPlayer.setUp(dataBean.getUrl(), true, "");
                getRealUrl(dataBean.getUrl());
            wonderfulVideoFragment.setIDandType(dataBean.getId(), dataBean.getV_type());
            starIntroduceFragment.setData(message);
        } else if (o instanceof PingFenBean.DataBean) {
            PingFenBean.DataBean dataBean = (PingFenBean.DataBean) o;
            isPingfen = dataBean.isIsPingfen();
        }
    }

    @Override
    public boolean useFragment() {
        return true;
    }

    //插入播放历史数据库
    private void insertDao(VideoDetailsBean.DataBean dataBean) {
        watchHistoryDao = WEApplication.getWatchHistoryDao();
        watchHistoryBean = new WatchHistoryBean();
        watchHistoryBean.setId(Long.valueOf(dataBean.getId()));
        watchHistoryBean.setMatchPeople(dataBean.getMatchPeople());
        watchHistoryBean.setPic(dataBean.getPic_h());
        watchHistoryBean.setScore(dataBean.getScore());
        watchHistoryBean.setTitle(dataBean.getName());
        watchHistoryBean.setType(dataBean.getV_type());
        watchHistoryBean.setStarId(dataBean.getStarId() == null ? "" : dataBean.getStarId());
        watchHistoryBean.setTeach(true);
        watchHistoryBean.setIsSelect(false);
    }

    //插入最后播放时间
    private void insertTime(int time) {
        if (watchHistoryBean == null || time == 0)
            return;
        watchHistoryBean.setIntTime(time / 1000);
        watchHistoryBean.setTime(DateTools.secToTime(time / 1000));
        watchHistoryDao.insertOrReplace(watchHistoryBean);
    }

    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
        danmakuVideoPlayer.onVideoPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!Preference.getBoolean(Constant.IS_LOGIN))
            finish();
        isPause = false;
        danmakuVideoPlayer.onVideoResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (danmakuVideoPlayer != null) {
            int currentPositionWhenPlaying = danmakuVideoPlayer.getCurrentPositionWhenPlaying();
            insertTime(currentPositionWhenPlaying);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
        GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
        NetworkUtils.removeEventHandler(netHandler);
        NetworkUtils.unRegisterNetworkReceiver(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                if (!danmakuVideoPlayer.isIfCurrentIsFullscreen()) {
                    danmakuVideoPlayer.startWindowFullscreen(this, true, true, false, "");
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
        danmakuVideoPlayer.getTitleTextView().setVisibility(View.GONE);
        danmakuVideoPlayer.getTitleTextView().setText(videoName);
        danmakuVideoPlayer.getBackButton().setVisibility(View.GONE);
    }


    private void getRealUrl(final String url) {
        ParseUrl.parseUrl(this, url.trim(), "high", new IOnParseUrlListener() {
            @Override
            public void onLoadSuccess(List<ParseEntityModle> list, int i) {
                //                t++;
                //每次进来清空临时url集合
                fUrls.clear();
                mUrls.clear();
                ParseEntityModle parseEntityModle = list.get(0);
                if (null != parseEntityModle) {
                    String parseurl = parseEntityModle.getM3U8Model().getUrl();
                    mNeedreferer = parseEntityModle.getM3U8Model().getExtraHeader();

                    //解析了多个url 用|分割
                    if (parseurl.contains("|")) {
                        String[] cache = parseurl.split("\\|");
                        for (String s : cache) {
                            //分割的url封装到concat文件所需数据集合
                            fUrls.add(s);
                        }
                        filePath = sdCardPath + File.separator + "ymq" + File.separator + (videoName) + ".ffconcat";
                        //如果临时url集合有数据就写文件
                        writeFileForConcat(fUrls, filePath);
                        //并马上把concat文件路径当url封装到gsyvideomodel对象,添加到播放集合
                        mUrls.add(new GSYVideoModel(filePath, videoName));
                    } else {
                        //只解析出来了1个url
                        if (!parseurl.equals("")) {
                            //分割的url封装到播放集合
                            mUrls.add(new GSYVideoModel(parseurl, videoName));
                        } else {
                            //没有解析出来任何url
                            showMessage("没有解析出来任何url...");

//                            showMessage("加载中...");
                            if (danmakuVideoPlayer != null)
                                danmakuVideoPlayer.release();
                            return;
                        }
                    }
                    if (mNeedreferer.size() != 0) {

                        //有头 单链接
                        if (!parseurl.contains("|") && !parseurl.equals("")) {
                            danmakuVideoPlayer.setUp(mUrls, false, 0, null, mNeedreferer, "");
                        }
                        //有头多连接
                        else {
                            //                                List<GSYVideoModel> tvConcatList = mUrls.subList(position, mUrls.size());
                            danmakuVideoPlayer.setUp(mUrls, tDuration, false, 0, null, mNeedreferer, "");
                            //                            mVideoPlayer.setUp(mUrls,true,0,"","");
                        }
                    }
                    //无头
                    else {
                        //分割后 没头的情况 并且只有1个url
                        if (mUrls.size() == 1) {
                            if (!parseurl.contains("|") && !parseurl.equals("")) {
                                danmakuVideoPlayer.setUp(mUrls, false, time, null, "");
                            } else {
                                //分割后 没头的情况 并且有多个url
                                if (danmakuVideoPlayer != null)
                                    danmakuVideoPlayer.setUp(mUrls, tDuration, false, time, null, null, "");
                            }
                        }
                    }

                    playVideo();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startPostponedEnterTransition();
                    }
                }
            }


            @Override
            public void onLoadFailure(String s, int i, int i1, Exception e) {
//                String content = "加载中...";
                String content = "onLoadFailure...";
                danmakuVideoPlayer.release();
//                    videoPlayError();
                showMessage(content);
            }

            @Override
            public void onShowProgress() {
            }

            @Override
            public void onHideProgress() {
            }

        });


    }

    //写入concact文件
    private File writeFileForConcat(List<String> urls, String filePath) {
        File file = null;
        StringBuffer sb = new StringBuffer();
        File rootDir = null;
        if (null != urls && urls.size() > 0 && !TextUtils.isEmpty(filePath)) {
            file = new File(filePath);
            rootDir = file.getParentFile();
            try {
                if (null == rootDir || !rootDir.exists()) {
                    rootDir.mkdirs();
                }

                if ((null == file) || (!file.exists())) {
                    if (!file.createNewFile()) {
                        Log.e(TAG, "播放concat文件已存在!!!");
                    }

                } else {
                    Log.e(TAG, "播放concat文件不存在!!!");
                }
                sb.append("ffconcat version 1.0" + "\n");
                for (int i = 0; i < urls.size(); i++) {
                    //                                        sb.append("file " + urls.get(i) + "\n");
                    sb.append("file " + urls.get(i) + "\n" + "duration" + "\t" + tDuration * 60 / urls.size() + "\n");
                }
                Log.d(TAG, "writeFileForConcat: " + sb.toString());
                PrintWriter p = new PrintWriter(new FileOutputStream(file.getAbsolutePath()));
                p.write(sb.toString());
                p.close();
            } catch (IOException e) {
                file = null;
                e.printStackTrace();
            }
        }
        return file;
    }


    private void playVideo() {
        //增加封面


        //增加title
        if (danmakuVideoPlayer == null)
            return;
        danmakuVideoPlayer.getTitleTextView().setVisibility(View.GONE);
        danmakuVideoPlayer.getTitleTextView().setText(videoName);

        //设置返回键
        danmakuVideoPlayer.getBackButton().setVisibility(View.VISIBLE);

        //设置旋转
        orientationUtils = new OrientationUtils(this, danmakuVideoPlayer);

        //设置全屏按键功能
        danmakuVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoftInputUtils.hideSoftInput(getWindow().getDecorView(), context);
                orientationUtils.resolveByClick();
                danmakuVideoPlayer.startWindowFullscreen(context, true, true, false, "");
            }
        });
        orientationUtils.setEnable(false);
        //是否可以滑动调整
        danmakuVideoPlayer.setIsTouchWiget(true);
        //关闭自动旋转
        danmakuVideoPlayer.setRotateViewAuto(false);
        danmakuVideoPlayer.setLockLand(false);
        danmakuVideoPlayer.setShowFullAnimation(false);
        danmakuVideoPlayer.setNeedLockFull(true);
        //设置返回按键功能
        danmakuVideoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // 设置缓冲失败的弹框
//        danmakuVideoPlayer.setPlayErrorDialog(mVideoPlayErrorDialogFragment);
        danmakuVideoPlayer.setSupportFragmentManager(getSupportFragmentManager());

        danmakuVideoPlayer.setStandardVideoAllCallBack(new SampleListener() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                //开始播放了才能旋转和全屏
                orientationUtils.setEnable(true);
                isPlay = true;
                orientationUtils.resolveByClick();

            }


            @Override
            public void onAutoComplete(String url, Object... objects) {
                if (mUrls != null) {
                    if (position < (mUrls.size() - 1)) {

                        ParseUrl.parseUrl(getApplicationContext(), mUrl, "high", new IOnParseUrlListener() {
                            @Override
                            public void onLoadSuccess(List<ParseEntityModle> list, int i) {
                                //每次进来清空临时url集合
                                fUrls.clear();
                                ParseEntityModle parseEntityModle = list.get(0);
                                if (null != parseEntityModle) {
                                    String parseurl = parseEntityModle.getM3U8Model().getUrl();
                                    mNeedreferer = parseEntityModle.getM3U8Model().getExtraHeader();

                                    //解析了多个url 用|分割
                                    if (parseurl.contains("|")) {
                                        String[] cache = parseurl.split("\\|");
                                        for (String s : cache) {
                                            //分割的url封装到concat文件所需数据集合
                                            fUrls.add(s);
                                        }
                                        filePath = sdCardPath + File.separator + "ymq" + File.separator + (videoName) + ".ffconcat";
                                        //如果临时url集合有数据就写文件
                                        writeFileForConcat(fUrls, filePath);
                                        //并马上把concat文件路径当url封装到gsyvideomodel对象,添加到播放集合
                                        mUrls.add(new GSYVideoModel(filePath, videoName));

                                    } else {
                                        //只解析出来了1个url
                                        if (!parseurl.equals("")) {
                                            //分割的url封装到播放集合
                                            mUrls.add(new GSYVideoModel(parseurl, videoName));
                                        } else {
                                            //没有解析出来任何url
//                                            String content = "加载中...";
                                            String content = "没有解析出来任何url...";
                                            danmakuVideoPlayer.release();

                                            showMessage(content);
                                            return;
                                        }
                                    }
                                    if (mNeedreferer.size() != 0) {
                                        //有头 单链接
                                        if (!parseurl.contains("|") && !parseurl.equals("")) {
                                            danmakuVideoPlayer.setUp(mUrls, false, time, null, mNeedreferer, "");
                                        }
                                        //有头多连接
                                        else {
                                            //                                List<GSYVideoModel> tvConcatList = mUrls.subList(position, mUrls.size());
                                            danmakuVideoPlayer.setUp(mUrls, tDuration, false, time, null, mNeedreferer, "");
                                            //                            mVideoPlayer.setUp(mUrls,true,0,"","");
                                        }
                                    }
                                    //无头
                                    else {
                                        //分割后 没头的情况 并且只有1个url
                                        if (fUrls.size() == 1) {
                                            danmakuVideoPlayer.setUp(mUrls, false, time, null, "");
                                        } else {
                                            //分割后 没头的情况 并且有多个url
                                            danmakuVideoPlayer.setUp(mUrls, tDuration, false, time, null, null, "");
                                        }
                                    }
                                    playVideo();
                                    danmakuVideoPlayer.startPlayLogic();
                                }
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
                        });
                    }
                } else {
                    finish();
                }
            }


            @Override
            public void onClickStartError(String url, Object... objects) {
                super.onClickStartError(url, objects);
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
                if (orientationUtils != null) {
                    orientationUtils.backToProtVideo();
                }
            }

        });


    }

    @Override
    public void videoParseFinish(VideoParse videoParse) {
        createPlayUrl(videoParse.getData().getStreams().get(0));
    }

    @Override
    public void videoDownloadParseFinish(VideoParse videoParse) {
        createPlayUrl(videoParse.getData().getStreams().get(0));
    }

    private void createPlayUrl(VideoParse.DataBean.StreamsBean streamsBean) {
        List<VideoParse.DataBean.StreamsBean.SegsBean> segs = streamsBean.getSegs();
        if (segs.size() > 1) {  // 多段 考虑有头无头
            fUrls.clear();
            tDuration = 0;  // 直连服务器给时长
            for (int i = 0; i < segs.size(); i++) {
                fUrls.add(segs.get(i).getUrl());
                tDuration += segs.get(i).getDuration();
            }
            tDuration /= 60;  // 分钟 // 如果是会员资源，可能只有六分钟  此时可对比data下的总时长做取舍
            filePath = sdCardPath + File.separator + "ymq" + File.separator + (videoName) + ".ffconcat";
            //如果临时url集合有数据就写文件
            writeFileForConcat(fUrls, filePath);
            //并马上把concat文件路径当url封装到gsyvideomodel对象,添加到播放集合
            mUrls.add(new GSYVideoModel(filePath, videoName));

            if (streamsBean.getHeaders() != null) { // 有头
                Map<String, String> headers = mPresenter.getHeaders(streamsBean);
                danmakuVideoPlayer.setUp(mUrls, tDuration, false, 0, null, headers, "");
            } else { // 无头
                danmakuVideoPlayer.setUp(mUrls, tDuration, false, 0, null, null, "");  // 时长==每一段相加
            }
        } else {
            mUrls.add(new GSYVideoModel(segs.get(0).getUrl(), videoName));
            // todo 一段  考虑有头无头
            if (streamsBean.getHeaders() != null) {
                danmakuVideoPlayer.setUp(mUrls, false, 0, null, mPresenter.getHeaders(streamsBean), ""); // 有头单链接
            } else {
                danmakuVideoPlayer.setUp(mUrls, false, 0, null, "");
            }
        }
    }
}
