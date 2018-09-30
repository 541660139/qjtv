package com.lwd.qjtv.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.AdapterViewPager;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.ClickMoreUtils;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.DateTools;
import com.lwd.qjtv.app.utils.FileUtils;
import com.lwd.qjtv.app.utils.NetworkUtils;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.SampleListener;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.app.utils.SoftInputUtils;
import com.lwd.qjtv.di.component.DaggerVideoDetailsComponent;
import com.lwd.qjtv.di.module.VideoDetailsModule;
import com.lwd.qjtv.mvp.contract.VideoDetailsContract;
import com.lwd.qjtv.mvp.model.entity.DianZanBean;
import com.lwd.qjtv.mvp.model.entity.PingFenBean;
import com.lwd.qjtv.mvp.model.entity.VideoDetailsNewBean;
import com.lwd.qjtv.mvp.model.entity.VideoParse;
import com.lwd.qjtv.mvp.model.entity.WatchHistoryBean;
import com.lwd.qjtv.mvp.presenter.VideoDetailsPresenter;
import com.lwd.qjtv.mvp.ui.adapter.CommentMessageAdapter;
import com.lwd.qjtv.mvp.ui.fragment.other.CommentMessageFragment;
import com.lwd.qjtv.mvp.ui.fragment.video.VideoChildDetailsFragment;
import com.lwd.qjtv.mvp.ui.holder.CommentMessageItemHolder;
import com.lwd.qjtv.view.EmojiBoard;
import com.lwd.qjtv.view.LoadingPageView;
import com.lwd.qjtv.view.RatingDialog;
import com.rrmj.zhongduomei.videoparsesdk.parseurl.IOnParseUrlListener;
import com.rrmj.zhongduomei.videoparsesdk.parseurl.ParseUrl;
import com.rrmj.zhongduomei.videoparsesdk.parseurl.bean.ParseEntityModle;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shuyu.gsyvideoplayer.GSYPreViewManager;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.model.GSYVideoModel;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.ListGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.usher.greendao_demo.greendao.gen.WatchHistoryBeanDao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
 * Created by ZhengQian on 2017/5/18.
 */

public class VideoDetailsActivity extends BaseActivity<VideoDetailsPresenter> implements VideoDetailsContract.View, CommentMessageItemHolder.VideoCommentLike, OnRefreshListener, OnLoadmoreListener {
    //
//    @BindView(R.id.activity_video_details_tablayout)
//    TabLayout tabLayout;
//    @BindView(R.id.activity_video_details_viewpager)
//    ViewPager viewPager;
    @BindView(R.id.danmaku_player)
    ListGSYVideoPlayer danmakuVideoPlayer;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_comment_message_edt)
    EditText messageEdt;
    @BindView(R.id.swipeRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.fragment_comment_message_send_tv)
    TextView sendTv;
    @BindView(R.id.fragment_comment_message_emoj_iv)
    ImageView emojIv;
    @BindView(R.id.input_emoji_board)
    EmojiBoard emojiBoard;

    @BindView(R.id.fragment_comment_message_bottom_ll)
    LinearLayout msgBottomLl;
    @BindView(R.id.rl_video_detail)
    RelativeLayout rl_video_detail;


    @BindView(R.id.iv_video_zan)
    ImageView iv_video_zan;
    @BindView(R.id.tv_video_zan)
    TextView tv_video_zan;
    @BindView(R.id.tv_video_ping_lun)
    TextView tv_video_ping_lun;
    @BindView(R.id.iv_video_fx)
    ImageView iv_video_fx;
    @BindView(R.id.iv_sc)
    ImageView iv_sc;


    LoadingPageView loadingPageView;
    private boolean fristEnter = false;
    private String filePath;
    private String mUrl_name;
    private List<BaseFragment> fragments;
    private int position;
    private int tDuration;
    private List<String> fUrls = new ArrayList<>();
    private List<GSYVideoModel> mUrls = new ArrayList<>();
    private String baseParseParams = "type=vod&info-only=false";
    private int halfHeight;
    private boolean isPlay;
    private boolean isPause;
    private String sdCardPath;
    private String lastPlayUrl;
    private String mUrl;
    private String videoName = "";

    private OrientationUtils orientationUtils;
    private String[] titles = {
            "详情",
            "评论"
    };
    private AdapterViewPager adapterViewPager;
    private String type;

    private String id;
    private VideoChildDetailsFragment videoChildDetailsFragment;
    private CommentMessageFragment commentMessageFragment;
    private AlertDialog commonDialog;
    private Context context;

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
                    break;
            }
        }

    };
    private String matchPeople;
    private Map<String, String> mNeedreferer;
    private String pic;
    private WatchHistoryBeanDao watchHistoryDao;
    private WatchHistoryBean watchHistoryBean;
    private int time;
    private boolean isPingfen;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerVideoDetailsComponent
                .builder()
                .appComponent(appComponent)
                .videoDetailsModule(new VideoDetailsModule(this)) //请将VideoDetailsModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return R.layout.activity_video_details;
    }


    @Override
    public void initData() {
        if (!SaveUserInfo.getLogin()) {
            startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
            finish();
            return;
        }


        //获取传递数据
        getIntentData();
//        mPresenter.getPingFen(id);
        context = this;
        sdCardPath = FileUtils.getStoragePath(this, false);
        //加入网络监听Handler处理回调
        NetworkUtils.addEventHandler(netHandler);
        //注册网络监听
        NetworkUtils.registerNetworkReceiver(this);
        danmakuVideoPlayer.getTitleTextView().setVisibility(View.GONE);
        initGsyVideoPlayer();
        //播放器
//        replaceVideoFragment();
        //初始化ViewPager
//        initViewPager();
        initListener();
    }


    private void initListener() {

        iv_video_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.likeVideo(id);
            }
        });


        sendTv.setOnClickListener(view -> {
            String trim = messageEdt.getText().toString().trim();
            if (messageEdt.getText().toString() == null || trim.length() == 0 || "".equals(messageEdt.getText().toString())) {
                showMessage("评论不能为空~");
                return;
            }
            try {
                SoftInputUtils.hideSoftInput(getWindow().getDecorView(), VideoDetailsActivity.this);
                emojiBoard.setVisibility(View.GONE);
                String encode = URLEncoder.encode(trim, "utf-8");
//                发送评论
                mPresenter.addMessage(id, type, encode);
                messageEdt.setText("");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        messageEdt.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (ClickMoreUtils.isFastClick()) {
                return false;
            }
            if (i == EditorInfo.IME_ACTION_SEND || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                String trim = messageEdt.getText().toString().trim();
                if (messageEdt.getText().toString() == null || trim.length() == 0 || "".equals(messageEdt.getText().toString())) {
                    showMessage("评论不能为空~");
                    return false;
                }
                try {
                    String encode = URLEncoder.encode(trim, "utf-8");
                    SoftInputUtils.hideSoftInput(((Activity) VideoDetailsActivity.this).getWindow().getDecorView(), VideoDetailsActivity.this);
                    //                发送评论
                    mPresenter.addMessage(id, type, encode);
                    messageEdt.setText("");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return true;
            }
            return false;
        });
        emojIv.setOnClickListener(view -> {
            SoftInputUtils.hideSoftInput(((Activity) VideoDetailsActivity.this).getWindow().getDecorView(), VideoDetailsActivity.this);
            emojiBoard.setVisibility(View.VISIBLE);
//            handler.sendEmptyMessageDelayed(1, 80);
//            emojiBoard.setVisibility(emojiBoard.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
//            InputMethodManager imm = (InputMethodManager) VideoDetailsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
//            emojIv.setSelected(emojiBoard.getVisibility() == View.VISIBLE);
        });
        emojiBoard.setItemClickListener(code -> {
            if (code.equals("/DEL")) {
                messageEdt.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            } else {
                messageEdt.getText().insert(messageEdt.getSelectionStart(), code);
            }
        });
        emojiBoard.setOnFocusChangeListener((view, b) -> {
            if (b) {
//                emojiBoard.setVisibility(View.VISIBLE);
            } else {
                emojiBoard.setVisibility(View.GONE);
            }
        });
        rl_video_detail.setOnTouchListener((v, event) -> {
            if (emojiBoard != null)
                emojiBoard.setVisibility(View.GONE);
            SoftInputUtils.hideSoftInput(((Activity) VideoDetailsActivity.this).getWindow().getDecorView(), VideoDetailsActivity.this);
            return true;
        });


        iv_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setCollect(id);
            }
        });

    }

    private void initGsyVideoPlayer() {

        //使用自定义的全屏切换图片，!!!注意xml布局中也需要设置为一样的
        //必须在setUp之前设置
        danmakuVideoPlayer.setShrinkImageRes(R.drawable.custom_shrink);
        danmakuVideoPlayer.setEnlargeImageRes(R.drawable.custom_enlarge);

        //增加封面
        if (pic != null) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(this).load(pic).placeholder(R.mipmap.video_place_holder).into(imageView);
            danmakuVideoPlayer.setThumbImageView(imageView);
        }

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
                danmakuVideoPlayer.startWindowFullscreen(VideoDetailsActivity.this, true, true, false, "");
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
                     * 去除评分
                     * */
//                    if (ratingDialog == null)
//                        ratingDialog = new RatingDialog(VideoDetailsActivity.this);
//                    ratingDialog.setVideoId(id);
//                    ratingDialog.setCallBack(isPingfen1 -> isPingfen = isPingfen1);
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

    private void getIntentData() {
        if (getIntent() != null) {
            type = getIntent().getStringExtra("type");
            id = getIntent().getStringExtra("id");
            if (getIntent().hasExtra("pic"))
                pic = getIntent().getStringExtra("pic");
            if (getIntent().hasExtra("time")) {
                time = getIntent().getIntExtra("time", 0);
                danmakuVideoPlayer.setSeekOnStart(time * 1000);
            }

            setTitle(getIntent().getStringExtra("title"));
            mPresenter.getVideoDetails(id, type, true);
        }
    }

//    //切换占位控件
//    private void replaceVideoFragment() {
//
//    }
//
//
//    private void initViewPager() {
//
//        tabLayout.setTabTextColors(getResources().getColor(R.color.color666666), getResources().getColor(R.color.colorOrigin));
//        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorOrigin));
//        tabLayout.post(() -> AdapterViewPager.setIndicator(getApplicationContext(), tabLayout, 60, 60));
//        fragments = new ArrayList<>();
//        //视频详情fragment
//        Bundle bundle = new Bundle();
//        bundle.putString("video_id", id);
//        bundle.putString("v_type", type);
//        videoChildDetailsFragment = VideoChildDetailsFragment.newInstance();
//        videoChildDetailsFragment.setArguments(bundle);
//        //聊天消息fragment
//        commentMessageFragment = CommentMessageFragment.newInstance();
//        fragments.add(videoChildDetailsFragment);
//        fragments.add(commentMessageFragment);
//        adapterViewPager = new AdapterViewPager(getSupportFragmentManager());
//        viewPager.setAdapter(adapterViewPager);
//        adapterViewPager.bindData(fragments, titles);
//        tabLayout.setupWithViewPager(viewPager);
//
//        if (getIntent().hasExtra("state")) {
//            viewPager.setCurrentItem(1);
//        }
//    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        smartRefreshLayout.finishRefresh();
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
        if (o instanceof VideoDetailsNewBean.DataBean) {
            VideoDetailsNewBean.DataBean dataBean = (VideoDetailsNewBean.DataBean) o;
            Message message = new Message();
            message.what = 0;
            message.obj = dataBean;
            insertDao(dataBean);
            if (pic == null) {
                String pic_h = dataBean.getVideo().getBitmap();
                ImageView imageView = new ImageView(this);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(this).load(pic_h).placeholder(R.mipmap.video_place_holder).into(imageView);
                danmakuVideoPlayer.setThumbImageView(imageView);
            }

            iv_video_zan.setImageResource(dataBean.getVideo().getIs_zan() == 1 ? R.mipmap.dianzanselect : R.mipmap.dainzan);
            tv_video_zan.setText(dataBean.getVideo().getVideo_zancount());

            tv_video_ping_lun.setText(dataBean.getVideo().getVideo_reviews());

            iv_sc.setImageResource(dataBean.getVideo().getIs_collect() == 0 ? R.mipmap.nav_icon_shoucang_n : R.mipmap.nav_icon_shoucang_s);
            matchPeople = dataBean.getVideo().getVideo_title();
            mUrl = dataBean.getVideo().getVideo_url();
            String video_length = dataBean.getVideo().getVideo_length();
            String intVideo = null;
            if (video_length != null && !video_length.equals("")) {
                intVideo = video_length.replace("分钟", "");
                int length = video_length.length();
                if (length > 4)
                    intVideo = video_length.substring(0, length - 4);
            }
            videoName = dataBean.getVideo().getVideo_title() == null ? "" : dataBean.getVideo().getVideo_title();
            if (intVideo != null && !intVideo.equals(""))
                tDuration = Integer.parseInt(intVideo);
            else
                tDuration = 200;
            if ("0".equals(dataBean.getVideo().getAnalysis_type()))
//              danmakuVideoPlayer.setUp("http://cj.ttkhj.3z.cc/Client/apiget.php?v=youku&m3u8&url=http://v.youku.com/v_show/id_XMzAxOTc3MzczNg==.html", true, "");
                danmakuVideoPlayer.setUp(dataBean.getVideo().getVideo_url(), false, "");
            else if ("1".equals(dataBean.getVideo().getAnalysis_type())) {
                String params = baseParseParams + "&url=" + mUrl.trim();
                Log.d("getOrigin", "4:" + params);
                new Thread(() -> mPresenter.parseVideoWithParams(params, false)).start();
            }
//            videoChildDetailsFragment.setData(message);
//            setIDandType(id, type);
        } else if (o instanceof PingFenBean.DataBean) {
            PingFenBean.DataBean dataBean = (PingFenBean.DataBean) o;
            isPingfen = dataBean.isIsPingfen();
        }
    }


    private void insertDao(VideoDetailsNewBean.DataBean dataBean) {
        watchHistoryDao = WEApplication.getWatchHistoryDao();
        watchHistoryBean = new WatchHistoryBean();
        watchHistoryBean.setId(Long.valueOf(id));
        watchHistoryBean.setMatchPeople(dataBean.getVideo().getVideo_title());
        watchHistoryBean.setPic(dataBean.getVideo().getBitmap());
        watchHistoryBean.setTitle(dataBean.getVideo().getVideo_title() + " ");
        watchHistoryBean.setType(dataBean.getVideo().getAnalysis_type());
        watchHistoryBean.setTeach(false);
        watchHistoryBean.setIsSelect(false);
    }

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
        if (danmakuVideoPlayer != null) {
            int currentPositionWhenPlaying = danmakuVideoPlayer.getCurrentPositionWhenPlaying();
            insertTime(currentPositionWhenPlaying);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!Preference.getBoolean(Constant.IS_LOGIN))
            finish();
        isPause = false;
        if (danmakuVideoPlayer != null)
            danmakuVideoPlayer.onVideoResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ParseUrl.destroyed();
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
        danmakuVideoPlayer.getTitleTextView().setText(matchPeople + " ");
        danmakuVideoPlayer.getBackButton().setVisibility(View.VISIBLE);
    }

//
//    private void getRealUrl(final String url) {
//        ParseUrl.parseUrl(this, url.trim(), "high", new IOnParseUrlListener() {
//            @Override
//            public void onLoadSuccess(List<ParseEntityModle> list, int i) {
//                //                t++;
//                //每次进来清空临时url集合
//                fUrls.clear();
//                mUrls.clear();
//                ParseEntityModle parseEntityModle = list.get(0);
//                if (null != parseEntityModle) {
//                    String parseurl = parseEntityModle.getM3U8Model().getUrl();
//                    mNeedreferer = parseEntityModle.getM3U8Model().getExtraHeader();
//
//                    //解析了多个url 用|分割
//                    if (parseurl.contains("|")) {
//                        String[] cache = parseurl.split("\\|");
//                        for (String s : cache) {
//                            //分割的url封装到concat文件所需数据集合
//                            fUrls.add(s);
//                        }
//                        filePath = sdCardPath + File.separator + "SNOOKER" + File.separator + (videoName) + ".ffconcat";
//                        //如果临时url集合有数据就写文件
//                        writeFileForConcat(fUrls, filePath);
//                        //并马上把concat文件路径当url封装到gsyvideomodel对象,添加到播放集合
//                        mUrls.add(new GSYVideoModel(filePath, videoName));
//                    } else {
//                        //只解析出来了1个url
//                        if (!parseurl.equals("")) {
//                            //分割的url封装到播放集合
//                            mUrls.add(new GSYVideoModel(parseurl, videoName));
//                        } else {
//                            //没有解析出来任何url
//                            showMessage("加载中...");
//                            danmakuVideoPlayer.release();
//                            return;
//                        }
//                    }
//
//
//                    if (mNeedreferer.size() != 0) {
//
//                        //有头 单链接
//                        if (!parseurl.contains("|") && !parseurl.equals("")) {
////
//                            danmakuVideoPlayer.setUp(mUrls, false, time, null, mNeedreferer, "");
//                        }
//                        //有头多连接
//                        else {
//                            //                                List<GSYVideoModel> tvConcatList = mUrls.subList(position, mUrls.size());
//                            danmakuVideoPlayer.setUp(mUrls, tDuration, false, time, null, mNeedreferer, "");
//                            //                            mVideoPlayer.setUp(mUrls,true,0,"","");
//                        }
//                    }
//                    //无头
//                    else {
//                        //分割后 没头的情况 并且只有1个url
//                        if (mUrls.size() == 1) {
//                            if (!parseurl.contains("|") && !parseurl.equals("")) {
//                                danmakuVideoPlayer.setUp(mUrls, false, time, null, "");
//                            } else {
//                                //分割后 没头的情况 并且有多个url
//                                danmakuVideoPlayer.setUp(mUrls, tDuration, false, time, null, null, "");
//                            }
//                        }
//                    }
//
//                    playVideo();
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        startPostponedEnterTransition();
//                    }
//                }
//            }
//
//
//            @Override
//            public void onLoadFailure(String s, int i, int i1, Exception e) {
//                String content = "加载中...";
//                danmakuVideoPlayer.release();
////                    videoPlayError();
//                showMessage(content);
//            }
//
//            @Override
//            public void onShowProgress() {
//            }
//
//            @Override
//            public void onHideProgress() {
//            }
//
//        });
//
//
//    }

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
//        mImageView = new_pic ImageView(PlayActivity.this);
//        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        SimpleTarget<Bitmap> simpleTarget = new_pic SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                mImageView.setImageBitmap(resource);
//            }
//        };
//        Glide.with(this)
//                .load(mPic_h)
//                .asBitmap()
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .into(simpleTarget);
//        mVideoPlayer.setThumbImageView(mImageView);
//        //        resolveNormalVideoUI();

        danmakuVideoPlayer.setLockLand(false);
        //增加title
        danmakuVideoPlayer.getTitleTextView().setVisibility(View.GONE);
        danmakuVideoPlayer.getTitleTextView().setText(videoName);


        //设置返回键
        danmakuVideoPlayer.getBackButton().setVisibility(View.VISIBLE);

        //设置旋转
        orientationUtils = new OrientationUtils(this, danmakuVideoPlayer);
        orientationUtils.setEnable(false);
        //设置全屏按键功能
        danmakuVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
                danmakuVideoPlayer.startWindowFullscreen(context, true, true, false, "");
                SoftInputUtils.hideSoftInput(getWindow().getDecorView(), context);
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
        danmakuVideoPlayer.setSeekOnStart(time * 1000);
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
                lastPlayUrl = url;
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
                                            String content = "加载中...";
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
                                            danmakuVideoPlayer.setUp(mUrls, false, 0, null, "");
                                        } else {
                                            //分割后 没头的情况 并且有多个url
                                            danmakuVideoPlayer.setUp(mUrls, tDuration, false, 0, null, null, "");
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


    @Override
    public void setAdapter(DefaultAdapter adapter) {

        CommentMessageAdapter commentMessageAdapter = (CommentMessageAdapter) adapter;
        mRecyclerView.setAdapter(commentMessageAdapter);
        commentMessageAdapter.setLikeCallback(this);
        initRecycleView();
    }


    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
        UiUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(WEApplication.getContext()));
    }

    @Override
    public void startLoadMore() {

    }

    @Override
    public void endLoadMore() {
        smartRefreshLayout.finishLoadmore();

    }

    @Override
    public void setScState(String state) {
        iv_sc.setImageResource(state.equals("0") ? R.mipmap.nav_icon_shoucang_n : R.mipmap.nav_icon_shoucang_s);

    }

    @Override
    public void setVideoZan(DianZanBean dianZanBean) {
        if (dianZanBean.getMsg().equals("点赞成功")) {
            iv_video_zan.setImageResource(R.mipmap.dianzanselect);
        } else {
            iv_video_zan.setImageResource(R.mipmap.dainzan);
        }
        tv_video_zan.setText(dianZanBean.getData().getZan_number());

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
            mUrls.add(new GSYVideoModel(filePath, mUrl_name));

            if (streamsBean.getHeaders() != null) { // 有头
                Map<String, String> headers = mPresenter.getHeaders(streamsBean);
                danmakuVideoPlayer.setUp(mUrls, tDuration, false, 0, null, headers, "");
            } else { // 无头
                danmakuVideoPlayer.setUp(mUrls, tDuration, false, 0, null, null, "");  // 时长==每一段相加
            }
        } else {
            mUrls.add(new GSYVideoModel(segs.get(0).getUrl(), mUrl_name));
            // todo 一段  考虑有头无头
            if (streamsBean.getHeaders() != null) {
                danmakuVideoPlayer.setUp(mUrls, false, 0, null, mPresenter.getHeaders(streamsBean), ""); // 有头单链接
            } else {
                danmakuVideoPlayer.setUp(mUrls, false, 0, null, "");
            }
        }
    }

    @Override
    public void likeVideoComment(VideoDetailsNewBean.DataBean.VideoReviewBean data) {
        //点赞
        mPresenter.likeComment(data.getVideo_id(), type, data.getReview_id());
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.getVideoDetails(id, type, true);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.getVideoDetails(id, type, false);
    }
}


