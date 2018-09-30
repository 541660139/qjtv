package com.lwd.qjtv.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.utils.*;
import com.shuyu.gsyvideoplayer.video.GSYBaseVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.lwd.qjtv.R;
import com.lwd.qjtv.mvp.ui.adapter.DanamakuAdapter;
import com.lwd.qjtv.view.AutoScrollTv;

import java.io.InputStream;
import java.util.HashMap;

import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * Created by guoshuyu on 2017/2/16.
 * <p>
 * 配置弹幕使用的播放器，目前使用的是本地模拟数据。
 * <p>
 * 模拟数据的弹幕时常比较短，后面的时长点是没有数据的。
 * <p>
 * 注意：这只是一个例子，演示如何集合弹幕，需要完善如弹出输入弹幕等的，可以自行完善。
 * 注意：b站的弹幕so只有v5 v7 x86、没有64，所以记得配置上ndk过滤。
 */

public class LiveDanmakuVideoPlayer extends StandardGSYVideoPlayer {

    private BaseDanmakuParser mParser;//解析器对象
    private IDanmakuView mDanmakuView;//弹幕view
    private DanmakuContext mDanmakuContext;
    private ImageView erWeiMaIv;
    private ImageView logoIv;
    private ImageView fullscreen;
    private AutoScrollTv advAstv;
    private CheckBox danmuCb;
    private TextView sendTv;
    private EditText inputEdt;
    private ImageView pauseIv;
    public ImageView backIv;
    private boolean isFullScreen;

    private TextView mSendDanmaku, mToogleDanmaku;

    private long mDanmakuStartSeekPosition = -1;

    private boolean mDanmaKuShow = true;

    public LiveDanmakuVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public LiveDanmakuVideoPlayer(Context context) {
        super(context);
    }

    public LiveDanmakuVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.live_danmaku_layout;
    }


    @Override
    protected void init(Context context) {
        super.init(context);

        mDanmakuView = (DanmakuView) findViewById(R.id.danmaku_view);
        mSendDanmaku = (TextView) findViewById(R.id.send_danmaku);
        mToogleDanmaku = (TextView) findViewById(R.id.toogle_danmaku);
        erWeiMaIv = (ImageView) findViewById(R.id.video_erweima);
        advAstv = (AutoScrollTv) findViewById(R.id.marjortornament_adv_astv);
        logoIv = (ImageView) findViewById(R.id.video_logo);
        fullscreen = (ImageView) findViewById(R.id.fullscreen);
        danmuCb = (CheckBox) findViewById(R.id.dan_mu_switch);
        sendTv = (TextView) findViewById(R.id.live_land_msg_send);
        pauseIv = (ImageView) findViewById(R.id.video_view_half_control_layout_pause_iv);
        inputEdt = (EditText) findViewById(R.id.live_land_msg_input);
        backIv = (ImageView) findViewById(R.id.back);
//        mDanmakuView.setVisibility(VISIBLE);
        //初始化弹幕显示
        initDanmaku();
        mSendDanmaku.setOnClickListener(this);
        mToogleDanmaku.setOnClickListener(this);
        getDanmakuView().show();
        setIsLive(true);
        danmuCb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mDanmaKuShow = !isChecked;
            resolveDanmakuShow();
        });
        if (!isFullScreen) {
            inputEdt.setVisibility(GONE);
        }
//        inputEdt.setOnClickListener(v -> {
//            if (isFullScreen)
//                fullscreen.callOnClick();
//        });
    }


    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //切换为竖屏
        if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_PORTRAIT) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) getResources().getDimension(R.dimen.x100), (int) getResources().getDimension(R.dimen.y75));
            logoIv.setLayoutParams(params);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) getResources().getDimension(R.dimen.x120), (int) getResources().getDimension(R.dimen.x120));
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            layoutParams.addRule(RelativeLayout.ABOVE, advAstv.getId());
//            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            erWeiMaIv.setLayoutParams(layoutParams);
            advAstv.run();
            isFullScreen = false;
            mDanmakuView.setVisibility(GONE);
        }
        //切换为横屏
        else if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_LANDSCAPE) {
            logoIv.setLayoutParams(new RelativeLayout.LayoutParams((int) getResources().getDimension(R.dimen.y180), (int) getResources().getDimension(R.dimen.x150)));
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) getResources().getDimension(R.dimen.x200), (int) getResources().getDimension(R.dimen.x200));
            erWeiMaIv.setLayoutParams(params);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            params.addRule(RelativeLayout.ABOVE, advAstv.getId());
//            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            advAstv.run();
            initDanmaku();
            isFullScreen = true;
            resolveDanmakuShow();
            mDanmakuView.show();
            mDanmakuView.isShown();
            mDanmakuView.setVisibility(VISIBLE);
        }
    }


    @Override
    public void onPrepared() {
        super.onPrepared();
        onPrepareDanmaku(this);
    }

    @Override
    public void onVideoPause() {
        super.onVideoPause();
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    @Override
    public void onVideoResume() {
        super.onVideoResume();
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }


    @Override
    public void onCompletion() {
        releaseDanmaku(this);
    }


    @Override
    public void onSeekComplete() {
        super.onSeekComplete();
        int time = mProgressBar.getProgress() * getDuration() / 100;
        //如果已经初始化过的，直接seek到对于位置
        if (mHadPlay && getDanmakuView() != null && getDanmakuView().isPrepared()) {
            resolveDanmakuSeek(this, time);
        } else if (mHadPlay && getDanmakuView() != null && !getDanmakuView().isPrepared()) {
            //如果没有初始化过的，记录位置等待
            setDanmakuStartSeekPosition(time);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.send_danmaku:
//                addDanmaku(true);
                break;
            case R.id.toogle_danmaku:
                mDanmaKuShow = !mDanmaKuShow;
                resolveDanmakuShow();
                break;
        }
    }

    @Override
    public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar, boolean isMember, String type) {
        GSYBaseVideoPlayer gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar, isMember, type);
        if (gsyBaseVideoPlayer != null) {
            LiveDanmakuVideoPlayer gsyVideoPlayer = (LiveDanmakuVideoPlayer) gsyBaseVideoPlayer;
            //对弹幕设置偏移记录
            gsyVideoPlayer.setDanmakuStartSeekPosition(getCurrentPositionWhenPlaying());
            gsyVideoPlayer.setDanmaKuShow(getDanmaKuShow());
            gsyVideoPlayer.setDanmaKuShow(true);
            gsyVideoPlayer.getTitleTextView().setText(mTitleTextView.getText().toString());
            onPrepareDanmaku(gsyVideoPlayer);
        }

        return gsyBaseVideoPlayer;
    }

//    /**
//     * 处理播放器在全屏切换时，弹幕显示的逻辑
//     * 需要格外注意的是，因为全屏和小屏，是切换了播放器，所以需要同步之间的弹幕状态
//     */
//    @Override
//    public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar) {
//        GSYBaseVideoPlayer gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar);
//        if (gsyBaseVideoPlayer != null) {
//            DanmakuVideoPlayer gsyVideoPlayer = (DanmakuVideoPlayer) gsyBaseVideoPlayer;
//            //对弹幕设置偏移记录
//            gsyVideoPlayer.setDanmakuStartSeekPosition(getCurrentPositionWhenPlaying());
//            gsyVideoPlayer.setDanmaKuShow(getDanmaKuShow());
//            onPrepareDanmaku(gsyVideoPlayer);
//        }
//        return gsyBaseVideoPlayer;
//    }


    /**
     * 处理播放器在退出全屏时，弹幕显示的逻辑
     * 需要格外注意的是，因为全屏和小屏，是切换了播放器，所以需要同步之间的弹幕状态
     */
    @Override
    protected void resolveNormalVideoShow(View oldF, ViewGroup vp, GSYVideoPlayer gsyVideoPlayer) {

        /**
         * 当用户非充值用户时进入直播间为全屏,点击退出全屏按钮，退出直播间
         *
         */

        if (!SaveUserInfo.getUserType()) {
            ( (Activity)getContext()).finish();
        }

        super.resolveNormalVideoShow(oldF, vp, gsyVideoPlayer);
        if (gsyVideoPlayer != null) {
            LiveDanmakuVideoPlayer gsyDanmaVideoPlayer = (LiveDanmakuVideoPlayer) gsyVideoPlayer;
            setDanmaKuShow(gsyDanmaVideoPlayer.getDanmaKuShow());
            if (gsyDanmaVideoPlayer.getDanmakuView() != null &&
                    gsyDanmaVideoPlayer.getDanmakuView().isPrepared()) {

//                之前是注释的
//                resolveDanmakuSeek(this, gsyDanmaVideoPlayer.getCurrentPositionWhenPlaying());

                resolveDanmakuShow();
                releaseDanmaku(gsyDanmaVideoPlayer);
            }
        }
    }


    private void initDanmaku() {
        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<Integer, Integer>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5); // 滚动弹幕最大显示5行
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<Integer, Boolean>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);

        DanamakuAdapter danamakuAdapter = new DanamakuAdapter(mDanmakuView);
        mDanmakuContext = DanmakuContext.create();
        mDanmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3).setDuplicateMergingEnabled(false).setScrollSpeedFactor(1.2f).setScaleTextSize(1.2f)
                .setCacheStuffer(new SpannedCacheStuffer(), danamakuAdapter) // 图文混排使用SpannedCacheStuffer
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair);
        if (mDanmakuView != null) {
            //todo 替换成你的数据流
            mParser = createParser(this.getResources().openRawResource(R.raw.comments));
            mDanmakuView.setCallback(new master.flame.danmaku.controller.DrawHandler.Callback() {
                @Override
                public void updateTimer(DanmakuTimer timer) {

                }

                @Override
                public void drawingFinished() {

                }

                @Override
                public void danmakuShown(BaseDanmaku danmaku) {
                }

                @Override
                public void prepared() {
                    if (getDanmakuView() != null) {
                        getDanmakuView().start();
                        if (getDanmakuStartSeekPosition() != -1) {
                            //                之前是注释的
//                            resolveDanmakuSeek(LiveDanmakuVideoPlayer.this, getDanmakuStartSeekPosition());
                            setDanmakuStartSeekPosition(-1);
                        }
                        resolveDanmakuShow();
                    }
                }
            });
            mDanmakuView.enableDanmakuDrawingCache(true);
        }
    }

    /**
     * 弹幕的显示与关闭
     */
    private void resolveDanmakuShow() {
        post(new Runnable() {
            @Override
            public void run() {
                if (mDanmaKuShow) {
                    if (!getDanmakuView().isShown())
                        getDanmakuView().show();
                    mToogleDanmaku.setText("弹幕关");
                } else {
                    if (getDanmakuView().isShown()) {
                        getDanmakuView().hide();
                    }
                    mToogleDanmaku.setText("弹幕开");
                }
            }
        });
    }

    /**
     * 开始播放弹幕
     */
    private void onPrepareDanmaku(LiveDanmakuVideoPlayer gsyVideoPlayer) {
        if (gsyVideoPlayer.getDanmakuView() != null && !gsyVideoPlayer.getDanmakuView().isPrepared()) {
            gsyVideoPlayer.getDanmakuView().prepare(gsyVideoPlayer.getParser(),
                    gsyVideoPlayer.getDanmakuContext());
        }
    }

    /**
     * 弹幕偏移
     */
    private void resolveDanmakuSeek(LiveDanmakuVideoPlayer gsyVideoPlayer, long time) {
        if (GSYVideoManager.instance().getMediaPlayer() != null && mHadPlay
                && gsyVideoPlayer.getDanmakuView() != null && gsyVideoPlayer.getDanmakuView().isPrepared()) {
            gsyVideoPlayer.getDanmakuView().seekTo(time);
        }
    }

    /**
     * 创建解析器对象，解析输入流
     *
     * @param stream
     * @return
     */
    private BaseDanmakuParser createParser(InputStream stream) {

        if (stream == null) {
            return new BaseDanmakuParser() {

                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };
        }

        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);

        try {
            loader.load(stream);
        } catch (IllegalDataException e) {
            e.printStackTrace();
        }
        BaseDanmakuParser parser = new BiliDanmukuParser();
        IDataSource<?> dataSource = loader.getDataSource();
        parser.load(dataSource);
        return parser;

    }

    /**
     * 释放弹幕控件
     */
    private void releaseDanmaku(LiveDanmakuVideoPlayer danmakuVideoPlayer) {
        if (danmakuVideoPlayer != null && danmakuVideoPlayer.getDanmakuView() != null) {
            Debuger.printfError("release Danmaku!");
            danmakuVideoPlayer.getDanmakuView().release();
        }
    }

    public BaseDanmakuParser getParser() {
        return mParser;
    }

    public DanmakuContext getDanmakuContext() {
        return mDanmakuContext;
    }

    public IDanmakuView getDanmakuView() {
        return mDanmakuView;
    }

    public long getDanmakuStartSeekPosition() {
        return mDanmakuStartSeekPosition;
    }

    public void setDanmakuStartSeekPosition(long danmakuStartSeekPosition) {
        this.mDanmakuStartSeekPosition = danmakuStartSeekPosition;
    }

    public void setDanmaKuShow(boolean danmaKuShow) {
        mDanmaKuShow = danmaKuShow;
    }

    public boolean getDanmaKuShow() {
        return mDanmaKuShow;
    }

    /**
     * 模拟添加弹幕数据
     */
    public void addDanmaku(boolean islive, String content, String level) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null || mDanmakuView == null) {
            return;
        }
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.priority = 8;  // 可能会被各种过滤器过滤并隐藏显示，所以提高等级
        danmaku.isLive = islive;
        danmaku.setTime(mDanmakuView.getCurrentTime() + 500);
        danmaku.textSize = 25f * (mParser.getDisplayer().getDensity() - 0.6f);
        if (level == null || level.equals("") || "1".equals(level))
            danmaku.textColor = Color.WHITE;
        else if ("2".equals(level)) {
            danmaku.textColor = 0x45e4fc;
        } else if ("3".equals(level)) {
            danmaku.textColor = 0x5995f9;
        } else if ("4".equals(level)) {
            danmaku.textColor = 0xfbd70f;
        } else if ("5".equals(level)) {
            danmaku.textColor = 0xfb9f0f;
        } else if ("6".equals(level)) {
            danmaku.textColor = 0xfa3b44;
        } else if ("7".equals(level)) {
            danmaku.textColor = 0xf35bf5;
        } else if ("8".equals(level)) {
            danmaku.textColor = 0xc478ff;
        } else if ("9".equals(level)) {
            danmaku.textColor = 0xff755e;
        } else if ("10".equals(level)) {
            danmaku.textColor = 0xa8e74d;
        } else {
            danmaku.textColor = Color.WHITE;
        }
//        danmaku.textShadowColor = Color.WHITE;
//        danmaku.borderColor = Color.GREEN;
        mDanmakuView.addDanmaku(danmaku);
    }

    public void setClickBack(OnClickListener clickBack) {
        backIv.setOnClickListener(clickBack);
    }

    public void setErweima(String url) {
        if (url != null)
            Glide.with(getContext()).load(url).into(erWeiMaIv);
    }

    public void setAdvAstv(String content, int speed) {
        advAstv.setText(content + "");
        advAstv.setMarqueeVelocity(speed);
        advAstv.startFor0();
    }

    /**
     * 获取全屏播放器对象
     *
     * @return GSYVideoPlayer 如果没有则返回空。
     */
    @SuppressWarnings("ResourceType")
    public GSYVideoPlayer getFullWindowPlayer() {
        ViewGroup vp = (ViewGroup) (com.shuyu.gsyvideoplayer.utils.CommonUtil.scanForActivity(getContext())).findViewById(Window.ID_ANDROID_CONTENT);
        final View full = vp.findViewById(FULLSCREEN_ID);
        GSYVideoPlayer gsyVideoPlayer = null;
        if (full != null) {
            gsyVideoPlayer = (GSYVideoPlayer) full;
        }
        return gsyVideoPlayer;
    }
}
