package com.lwd.qjtv.mvp.ui.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.shuyu.gsyvideoplayer.model.GSYVideoModel;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.video.ListGSYVideoPlayer;
import com.usher.greendao_demo.greendao.gen.WatchHistoryBeanDao;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.FileUtils;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.SampleListener;
import com.lwd.qjtv.mvp.model.entity.ChoiceDataBean;
import com.lwd.qjtv.mvp.model.entity.VideoParse;
import com.lwd.qjtv.mvp.model.entity.WatchHistoryBean;
import com.lwd.qjtv.view.RatingDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * Created by DELL on 2018/6/11.
 */

public class ChoiceAdapter extends DefaultAdapter<ChoiceDataBean.DataBean> {

    private ChoiceItemHolder choiceItemHolder;
    private ClickMoreCallback clickMoreCallback;
    private WatchHistoryBeanDao watchHistoryDao;
    private WatchHistoryBean watchHistoryBean;

    public ChoiceAdapter(List<ChoiceDataBean.DataBean> mChoiceList) {
        super(mChoiceList);
    }

    @Override
    public BaseHolder<ChoiceDataBean.DataBean> getHolder(View v, int viewType) {
        choiceItemHolder = new ChoiceItemHolder(v);
        return choiceItemHolder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_choice;
    }

    public void setClickMoreCallback(ClickMoreCallback clickMoreCallback) {
        this.clickMoreCallback = clickMoreCallback;

    }


    public interface ClickMoreCallback {
//        void clickpinlun(View view, ChoiceDataBean.DataBean data);

        void clickdainzan(View view, ChoiceDataBean.DataBean data, View view1);
    }


    public void setListGSYVideoPlayerState(int state) {
        if (choiceItemHolder != null) {
            if (choiceItemHolder.danmaku_player != null) {
                if (state == 2) {
                    choiceItemHolder.danmaku_player.onVideoPause();
                }

            }
        }

    }


//    public void setDianZan(BaseBean baseBean, ChoiceDataBean.DataBean data) {
//        if (choiceItemHolder.tv_dianzan != null && choiceItemHolder.iv_dianzan != null) {
//            if (baseBean.getStatus().equals("1")) {
//                /**
//                 * 取消点赞
//                 * */
//
//                String zan_number = data.getZan_number();
//                int i = Integer.parseInt(zan_number);
//                if (i > 0) {
//                    choiceItemHolder.tv_dianzan.setText((i - 1) + "");
//                } else {
//                    choiceItemHolder.tv_dianzan.setText(0 + "");
//                }
//                choiceItemHolder.iv_dianzan.setImageResource(R.mipmap.dainzan);
//
//
//            }
//            if (baseBean.getStatus().equals("2")) {
//                String zan_number = data.getZan_number();
//                int i = Integer.parseInt(zan_number);
//
//                choiceItemHolder.tv_dianzan.setText((i + 1) + "");
//                choiceItemHolder.iv_dianzan.setImageResource(R.mipmap.dianzanselect);
//            }
//
//        }
//
//    }

    private void insertDao(ChoiceDataBean.DataBean dataBean) {
        watchHistoryDao = WEApplication.getWatchHistoryDao();
        watchHistoryBean = new WatchHistoryBean();
        watchHistoryBean.setId(Long.valueOf(dataBean.getId()));
        watchHistoryBean.setPic(dataBean.getBitmap());

        watchHistoryBean.setTitle(dataBean.getVideo_title() + " ");
        watchHistoryBean.setType(dataBean.getAnalysis_type());
        watchHistoryBean.setStarId(dataBean.getId() == null ? "" : dataBean.getId());
        watchHistoryBean.setTeach(false);
        watchHistoryBean.setIsSelect(false);
    }

    public class ChoiceItemHolder extends BaseHolder<ChoiceDataBean.DataBean> {

        @BindView(R.id.danmaku_player)
        ListGSYVideoPlayer danmaku_player;


        @BindView(R.id.tv_pinglun)
        TextView tv_pinglun;

        @BindView(R.id.tv_dianzan)
        TextView tv_dianzan;

        @BindView(R.id.iv_dianzan)
        ImageView iv_dianzan;


        @BindView(R.id.iv_pinglun)
        ImageView iv_pinglun;

        @BindView(R.id.ll_dianzhan)
        LinearLayout ll_dianzhan;


        @BindView(R.id.ll_pinglun)
        LinearLayout ll_pinglun;

        @BindView(R.id.tv_choice_title)
        TextView tv_choice_title;


        private String pic;

        private AppComponent mAppComponent;
        private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架


        private int index = 0;

        private String baseParseParams = "type=vod&info-only=false";


        private List<String> fUrls = new ArrayList<>();

        private int tDuration = 200;
        private String sdCardPath;

        private ChoiceDataBean.DataBean data;
        private String filePath;

        private List<GSYVideoModel> mUrls = new ArrayList<>();

        public ChoiceItemHolder(View itemView) {
            super(itemView);
            //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
            mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
            mImageLoader = mAppComponent.imageLoader();

        }

        @Override
        public void setData(ChoiceDataBean.DataBean data, int position) {
            initGsyVideoPlayer(data, position);
            initView(data);
            initLister();
            index = position;
            this.data = data;
        }

        private void initLister() {
            if (ll_dianzhan != null) {
//                ll_pinglun.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        clickMoreCallback.clickpinlun(view, data);
//                    }
//                });

                ll_dianzhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        clickMoreCallback.clickdainzan(iv_dianzan, data, tv_dianzan);
                    }
                });
            }
        }


        private void initView(ChoiceDataBean.DataBean data) {


            choiceItemHolder.tv_pinglun.setText(data.getVideo_reviews() == null ? "0" : data.getVideo_reviews());


            choiceItemHolder.tv_dianzan.setText(data.getVideo_zancount() == null ? "0" : data.getVideo_zancount());
            if (Preference.get(Constant.UID, "0").equals("0")) {
                choiceItemHolder.iv_dianzan.setImageResource(R.mipmap.dainzan);
            } else {
                choiceItemHolder.iv_dianzan.setImageResource(data.getIs_zan().equals("0") ? R.mipmap.dainzan : R.mipmap.dianzanselect);
            }
            choiceItemHolder.tv_choice_title.setText(data.getVideo_title());
        }


        @Override
        protected void onRelease() {
//        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
//                .imageViews(null)
//                .build());
        }


        private void initGsyVideoPlayer(ChoiceDataBean.DataBean data, int position) {

            GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_16_9);

            danmaku_player.getTitleTextView().setVisibility(View.GONE);
            //使用自定义的全屏切换图片，!!!注意xml布局中也需要设置为一样的
            //必须在setUp之前设置
            danmaku_player.setShrinkImageRes(R.drawable.custom_shrink);
            danmaku_player.setEnlargeImageRes(R.drawable.custom_enlarge);

            ImageView imageView = new ImageView(mAppComponent.Application());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (data.getBitmap() != null && !TextUtils.isEmpty(data.getBitmap()))
                Glide.with(mAppComponent.Application()).load(data.getBitmap()).placeholder(R.mipmap.video_place_holder).into(imageView);
            danmaku_player.setThumbImageView(imageView);


            danmaku_player.getBackButton().setVisibility(View.GONE);
            danmaku_player.getFullscreenButton().setVisibility(View.GONE);

            danmaku_player.setIsTouchWiget(true);
            //关闭自动旋转
            danmaku_player.setRotateViewAuto(false);
            danmaku_player.setLockLand(false);
            danmaku_player.setShowFullAnimation(false);
            danmaku_player.setNeedLockFull(true);


            danmaku_player.setStandardVideoAllCallBack(new SampleListener() {
                private RatingDialog ratingDialog;


                @Override
                public void onClickStartIcon(String url, Object... objects) {
                    super.onClickStartIcon(url, objects);

                    //开始点击了开始播放按钮记录下来
                    insertDao(data);
                }

                @Override
                public void onPrepared(String url, Object... objects) {
                    super.onPrepared(url, objects);
                    //开始播放了才能旋转和全屏


                }

                @Override
                public void onAutoComplete(String url, Object... objects) {
                    super.onAutoComplete(url, objects);

                }

                @Override
                public void onClickStartError(String url, Object... objects) {
                    super.onClickStartError(url, objects);
//点击加载失败时重新请求
                    String params = baseParseParams + "&url=" + data.getVideo_url().trim();

                    new Thread(() -> parseVideoWithParams(params, false)).start();
                }

                @Override
                public void onQuitFullscreen(String url, Object... objects) {
                    super.onQuitFullscreen(url, objects);

                }
            });


            String params = baseParseParams + "&url=" + data.getVideo_url().trim();

            new Thread(() -> parseVideoWithParams(params, false)).start();


        }


        public void parseVideoWithParams(String jsonParams, final boolean isDownloladParse) {
            Observable.just(WEApplication.getParseModule().parse(jsonParams))
                    .subscribeOn(Schedulers.io())
                    .retryWhen(new RetryWithDelay(3, 2))
                    .observeOn(Schedulers.io())
                    .flatMap(new Function<String, ObservableSource<VideoParse>>() {
                        @Override
                        public ObservableSource<VideoParse> apply(@NonNull String resultStr) throws Exception {
                            Log.d("getOrigin", "4:" + resultStr);
                            VideoParse mVideoParse = new Gson().fromJson(resultStr, VideoParse.class);
                            return Observable.just(mVideoParse);
                        }
                    }).subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) throws Exception {
//                        mRootView.showLoading();
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnTerminate(new Action() {
                        @Override
                        public void run() throws Exception {
//                        mRootView.hideLoading();
                        }
                    })
                    .subscribe(videoParse -> {
                        if (videoParse == null || videoParse.getCode() != 0) {
                            Toast.makeText(mAppComponent.appManager().getCurrentActivity(), "加载中...", Toast.LENGTH_SHORT).show();

                            return;
                        }
                        // 回调v中的方法，解析完成

                        createPlayUrl(videoParse.getData().getStreams().get(0));


                    });
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
                sdCardPath = FileUtils.getStoragePath(mAppComponent.appManager().getCurrentActivity(), false);
                filePath = sdCardPath + File.separator + "ymq" + File.separator + (data.getVideo_title()) + ".ffconcat";
                //如果临时url集合有数据就写文件
                writeFileForConcat(fUrls, filePath);
                //并马上把concat文件路径当url封装到gsyvideomodel对象,添加到播放集合
                mUrls.add(new GSYVideoModel(filePath, data.getVideo_title()));

                if (streamsBean.getHeaders() != null) { // 有头
                    Map<String, String> headers = getHeaders(streamsBean);
                    danmaku_player.setUp(mUrls, tDuration, false, 0, null, headers, "");
                } else { // 无头
                    danmaku_player.setUp(mUrls, tDuration, false, 0, null, null, "");  // 时长==每一段相加
                }
            } else {
                mUrls.add(new GSYVideoModel(segs.get(0).getUrl(), data.getVideo_title()));
                // todo 一段  考虑有头无头
                if (streamsBean.getHeaders() != null) {
                    danmaku_player.setUp(mUrls, false, 0, null, getHeaders(streamsBean), ""); // 有头单链接
                } else {
                    danmaku_player.setUp(mUrls, false, 0, null, "");
                }
            }
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

        public Map<String, String> getHeaders(VideoParse.DataBean.StreamsBean streamsBean) {
            HashMap<String, String> headersMap = new HashMap<>();
            if (streamsBean.getHeaders() != null && streamsBean.getHeaders().size() != 0) {
                for (String header : streamsBean.getHeaders()) {
                    if (header != null && header.contains("Referer")) {
                        headersMap.put("Referer", (header.substring("Referer".length() + 2)).trim());
                    } else if (header != null && header.contains("User-Agent")) {
                        headersMap.put("User-Agent", (header.substring("User-Agent".length() + 2)).trim());
                    }
                }
            }
            return headersMap;
        }
    }
}
