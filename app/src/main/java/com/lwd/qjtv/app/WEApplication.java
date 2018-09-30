package com.lwd.qjtv.app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.multidex.MultiDex;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jess.arms.base.App;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.app.utils.EmojiManager;
import com.mob.MobSDK;
import com.rrmj.zhongduomei.videoparsesdk.parseurl.ParseUrl;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.usher.greendao_demo.greendao.gen.BetModelBeanDao;
import com.usher.greendao_demo.greendao.gen.DaoMaster;
import com.usher.greendao_demo.greendao.gen.DaoSession;
import com.usher.greendao_demo.greendao.gen.WatchHistoryBeanDao;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Core;
import com.lwd.qjtv.app.utils.FileUtils;
import com.lwd.qjtv.app.utils.MyHttpUtils;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.SPUtils;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.app.utils.ToastUtil;
import com.lwd.qjtv.app.utils.UpdateManager;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.CheckVersionBean;
import com.lwd.qjtv.mvp.model.entity.GetLevelBean;
import com.lwd.qjtv.mvp.model.entity.LuckyListBean;
import com.lwd.qjtv.mvp.model.entity.ParseModule;
import com.lwd.qjtv.mvp.model.entity.ShareBean;
import com.lwd.qjtv.mvp.ui.activity.GuessCenterActivity;
import com.lwd.qjtv.mvp.ui.activity.GuessChampionActivity;
import com.lwd.qjtv.mvp.ui.activity.LoginActivity;
import com.lwd.qjtv.mvp.ui.activity.MatchTimeActivity;
import com.lwd.qjtv.mvp.ui.activity.MoreBetActivity;
import com.lwd.qjtv.mvp.ui.activity.RechargeActivity;
import com.lwd.qjtv.mvp.ui.activity.VideoDetailsActivity;
import com.lwd.qjtv.mvp.ui.activity.WebActivity;
import com.lwd.qjtv.mvp.ui.activity.WebViewActivity;
import com.lwd.qjtv.view.luckypan.LuckPanLayout;
import com.lwd.qjtv.view.luckypan.NormalLuckPanLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import cn.dolit.siteparser.Module;
//import cn.jpush.android.api.JPushInterface;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.sharesdk.onekeyshare.OnekeyShare;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.rong.imlib.RongIMClient;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.lwd.qjtv.mvp.model.api.Api.parseUrl;


/**
 * Created by jess on 8/5/16 11:07
 * contact with jess.yan.effort@gmail.com
 */
public class WEApplication extends BaseApplication {
    //app版本
    public static String app_ver;
    //imei序列号
    public static String IMEI;
    private static WEApplication mApplication;
    //微信appid
    public static final String WX_APPID = "wxe87d9d69af0a59ae";

    public static String parseKey = "ce410bbf63bf917f9f9444080898d61c";

    public static String parseScriptPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "script.spp";
    public static IWXAPI api;
    //投注输赢 数据库
    private static BetModelBeanDao betModelBeanDao;
    //投注局数 数据库
    private static BetModelBeanDao thirdModelBeanDao;
    //观看历史 数据库
    private static WatchHistoryBeanDao watchHistoryDao;

    private String parseHash;

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AlertDialog obj = (AlertDialog) msg.obj;
            obj.dismiss();
        }
    };

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.white, android.R.color.black);//全局设置主题颜色
                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
    }

    public static final Module parseModule = new Module();

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        app_ver = getVersion();


        api = WXAPIFactory.createWXAPI(this, null);
        api.registerApp(WX_APPID);
        MobclickAgent.setDebugMode(true);
        MyHttpUtils.init();
        initThirdDbHelp();
        initWinDbHelp();
        initWatchRecordeDbHelp();
        initParseModule();
        EmojiManager.init(getContext());
        initShare();
//        initMob();
//        initRrSDK();
//        initRotatePan();
        initJPush();


        /**屏蔽融云
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIMClient 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
//        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
//            RongYunUtils.init(this, "ik1qhw09ipabp");
////            RongYunUtils.init(this, "8w7jv4qb8awfy");
//
//            Log.d("rongyun", "appkey: " + "z3v5yqkbz47i0");
//        }
//        RongIMClient.setOnReceiveMessageListener(new RongYunMessageListener());
//        RongYunUtils.setConnectionStatusListener(mListener);
//        if (SaveUserInfo.getLogin()) {
//            RongYunUtils.connect(SaveUserInfo.getToken());
////            RongYunUtils.connect("BFV0JCmgGdaiNXno8uRBcnA+UvCT5kYYzboKQfrCSdK8sV0wpWsOu+Ci5K5xPldXWAWTxdDum5M=");
//
//        }


        /**
         *屏蔽融云
         *
         * */

    }

    public static void getPhoneState() {
        IMEI = new Core(getContext()).getIMEI();
    }


    private void initJPush() {


        //注册全局事件监听类
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
            //设置开启日志,发布时请关闭日志
            JPushInterface.setDebugMode(true);
            //初始化
            JPushInterface.init(this);

            //设置开启日志,发布时请关闭日志
            JMessageClient.setDebugMode(true);
            JMessageClient.init(this);


            //初始化
            JAnalyticsInterface.init(this);
            //设置调试模式：参数为 true 表示打开调试模式，可看到 sdk 的日志。
            JAnalyticsInterface.setDebugMode(true);
        }

    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void initParseModule() {
        ((App) WEApplication.getContext()).getAppComponent().repositoryManager().obtainRetrofitService(UserService.class)
                .getParseFile(parseUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<ParseModule>(mApplication.getAppComponent().rxErrorHandler()) {
                    @Override
                    public void onNext(@NonNull ParseModule parseModule) {
                        parseHash = parseModule.getHash();
                        // 从本地取出上一版本的hash，对比，如果不一致，则需重新下载，一致，则直接初始化
                        String hashStr = SPUtils.getString(Constant.APP_NAME, mApplication, Constant.PARSE_MODULE_HASH, "");
                        if (hashStr != null && hashStr.length() != 0) { // 判断文件是否存在
                            if (parseHash.equals(hashStr)) {
                                if (FileUtils.isFileExist(parseScriptPath)) {
                                    // 直接初始化
                                    initParse();
                                } else {
                                    // 文件被删除，重新下载
                                    // 重新下载
                                    String url = parseModule.getUrl();
                                    downloadParseFile(url);
                                }
                            } else {
                                // 重新下载
                                String url = parseModule.getUrl();
                                downloadParseFile(url);
                            }
                        } else { // 第一次下载规则文件 ，直接下载
                            // 重新下载
                            String url = parseModule.getUrl();
                            downloadParseFile(url);
                        }
                    }
                });
    }

    private void initRotatePan() {
        Map<String, String> map = new HashMap<>();
        String op = "getAward";
        map.put("op", op);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        ((App) WEApplication.getContext()).getAppComponent().repositoryManager().obtainRetrofitService(UserService.class)
                .getLuckyList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<LuckyListBean>(mApplication.getAppComponent().rxErrorHandler()) {
                    @Override
                    public void onNext(@NonNull LuckyListBean luckyListBean) {
                        if (luckyListBean == null)
                            return;
                        if (luckyListBean.getStatus().equals("0"))
                            return;
                        Preference.put(Constant.HAS_ROTATE, luckyListBean.getData().getAward_num() > 0);
                    }
                });
    }

    private void initShare() {
        MobSDK.init(this);
    }

    public static Module getParseModule() {
        return parseModule;
    }

    public static void resetCoin() {
        String op = "getMemberLevel";
        Map<String, String> map = new HashMap<>();
        map.put("op", op);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        ((App) WEApplication.getContext().getApplicationContext()).getAppComponent().repositoryManager().obtainRetrofitService(UserService.class)
                .getUserInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<GetLevelBean>(mApplication.getAppComponent().rxErrorHandler()) {
                    @Override
                    public void onNext(@NonNull GetLevelBean getLevelBean) {
                        if (getLevelBean == null || !"1".equals(getLevelBean.getStatus()))
                            return;
                        SaveUserInfo.setExperienceMax(getLevelBean.getData().getExperienceValueMax());
                        SaveUserInfo.setExperienceValue(getLevelBean.getData().getExperienceValue());
                        SaveUserInfo.setCoin(getLevelBean.getData().getB_coin());
                        SaveUserInfo.setUserType(getLevelBean.getData().getUser_type() + "");
                        SaveUserInfo.setYqLink(getLevelBean.getData().getYq_link());
                    }
                });
    }


    public static void checkVersion(boolean isUpdate, Context context) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        mApplication.getAppComponent().repositoryManager().obtainRetrofitService(UserService.class)
                .checkVersion(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 5))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<CheckVersionBean>(mApplication.getAppComponent().rxErrorHandler()) {
                    @Override
                    public void onNext(@NonNull CheckVersionBean checkVersionBean) {
                        if ("1".equals(checkVersionBean.getStatus())) {
                            CheckVersionBean.DataBean data = checkVersionBean.getData();
//                            Preference.putBoolean(Constant.IS_RELEASE, checkVersionBean.getData().getIs_realease().equals("0"));
                            // TODO: 2018/8/20  暂时写死
                            Preference.putBoolean(Constant.IS_RELEASE, true);

                            Preference.putBoolean(Constant.IS_GUESS, checkVersionBean.getData().getIs_guessNotOpen().equals("2"));
                            Preference.putBoolean(Constant.CAN_ROTATE, checkVersionBean.getData().getDstatus_turntable().equals("2"));
                            Preference.putBoolean(Constant.CAN_USE_COIN_ROTATE, checkVersionBean.getData().getDstatus_b_coin().equals("2"));
                            if (checkVersionBean.getData().getOn_off() != null) {
                                Preference.putBoolean(Constant.ON_OFF, checkVersionBean.getData().getOn_off().equals("1"));
                            }


                            if ("0".equals(data.getUpdate_type())) {
                                if (context != null && checkVersionBean.getData().getKdInfo() != null)
                                    showAdv(checkVersionBean, context);
                                return;
                            }
                            if (isUpdate) {
                                UpdateManager updateManager = new UpdateManager(context == null ? getContext() : context);
                                updateManager.showNoticeDialog(data.getVersion(), data.getUpdate_info(), "2".equals(data.getUpdate_type()), data.getUrl(), false);
                                if (checkVersionBean.getData().getKdInfo() != null && context != null) {
                                    showAdv(checkVersionBean, context);
                                }
                            }
                        }
                    }
                });

    }

    private static void showAdv(@NonNull CheckVersionBean checkVersionBean, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog alertDialog = builder.create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_adv_layout, null);
        alertDialog.setView(view);
        ImageView advIv = (ImageView) view.findViewById(R.id.dialog_adv_iv);
        if (checkVersionBean.getData().getKdInfo().getPicture() != null) {
            Glide.with(context).load(checkVersionBean.getData().getKdInfo().getPicture()).into(advIv);
        }
        advIv.setOnClickListener(v -> jumpAdv(checkVersionBean, context));
        alertDialog.show();
    }

    private static void jumpAdv(@NonNull CheckVersionBean data, Context context) {
        if (data.getData() == null || data.getData().getKdInfo() == null)
            return;
        CheckVersionBean.DataBean.KdInfoBean kdInfo = data.getData().getKdInfo();
        switch (kdInfo.getLink_type()) {
            case "1":
                //赛程页
                if (!SaveUserInfo.getLogin()) {
                    getContext().startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                    return;
                }
                Intent intent = new Intent(getContext(), MatchTimeActivity.class);
                intent.putExtra("id", kdInfo.getId());
                context.startActivity(intent);
                break;
            case "2":
//                视频详情页
                if (!SaveUserInfo.getLogin()) {
                    getContext().startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                    return;
                }
                Intent intent1 = new Intent(getContext(), VideoDetailsActivity.class);
                intent1.putExtra("id", kdInfo.getId());
                intent1.putExtra("pic", kdInfo.getPicture());
                intent1.putExtra("type", kdInfo.getV_type());
                context.startActivity(intent1);
                break;
            case "3":
                //跳转商城
                Intent intent2 = new Intent(WEApplication.getContext(), WebViewActivity.class);
                intent2.putExtra("id", kdInfo.getLink());
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
                break;
            case "4":
                //竟猜中心
                if (!SaveUserInfo.getLogin()) {
                    getContext().startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                    return;
                }
                context.startActivity(new Intent(getContext(), GuessCenterActivity.class));
                break;
            case "5":
                //冠军竞猜
                if (!SaveUserInfo.getLogin()) {
                    getContext().startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                    return;
                }
                Intent intent3 = new Intent(getContext(), GuessChampionActivity.class);
                intent3.putExtra("id", kdInfo.getLink());
                context.startActivity(intent3);
                break;
            case "6":
                //多选竞猜
                if (!SaveUserInfo.getLogin()) {
                    getContext().startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
                    return;
                }
                context.startActivity(new Intent(getContext(), MoreBetActivity.class));
                break;
            case "7":
                //活动详情页
                Intent intent4 = new Intent(getContext(), WebActivity.class);
                intent4.putExtra("url", kdInfo.getLink_url());
                context.startActivity(intent4);
                break;
            case "9":
                //直播间
//                if (!SaveUserInfo.getLogin()) {
//                    getContext().startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
//                    return;
//                }
//                Intent intent5 = new Intent(getContext(), LiveActivity.class);
//                intent5.putExtra("id", kdInfo.getId());
//                intent5.putExtra("match_id", kdInfo.getId());
//                intent5.putExtra("url", kdInfo.getLink_url());
//                intent5.putExtra("title", kdInfo.getTitle());
//                intent5.putExtra("moveText", "");
//                intent5.putExtra("moveSpeed", "");
//                intent5.putExtra("origin", kdInfo.getOrigin());
//                intent5.putExtra("erweimaUrl", "");
//                intent5.putExtra("isNBA", false);
//                 context.startActivity(intent5);
                break;
        }
    }

    public static void showShare() {
        Map<String, String> map = new HashMap<>();
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        mApplication.getAppComponent().repositoryManager().obtainRetrofitService(UserService.class)
                .getShareInfo(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 5))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<ShareBean>(mApplication.getAppComponent().rxErrorHandler()) {
                    @Override
                    public void onNext(@NonNull ShareBean shareBean) {
                        if (shareBean != null && shareBean.getStatus().equals("1")) {
                            ShareBean.DataBean data = shareBean.getData();
                            final OnekeyShare oks = new OnekeyShare();
                            oks.setSilent(true);
                            //关闭sso授权
                            oks.disableSSOWhenAuthorize();
                            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
                            oks.setTitle(data.getTitle());
                            // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
                            oks.setTitleUrl(data.getUrl());
                            // text是分享文本，所有平台都需要这个字段
                            oks.setText(data.getDesc());
                            //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
                            if (!TextUtils.isEmpty(data.getIcon())) {
                                oks.setImageUrl(data.getIcon());
                            }
                            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                            //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                            // url仅在微信（包括好友和朋友圈）中使用
                            oks.setUrl(data.getUrl());
                            // site是分享此内容的网站名称，仅在QQ空间使用
                            oks.setSite(mApplication.getString(R.string.app_name));
                            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                            oks.setSiteUrl(data.getUrl());
                            //启动分享
                            oks.show(WEApplication.getContext());
                        }
                    }
                });
    }

    /*初始化胜负数据库*/
    private void initWinDbHelp() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(WEApplication.getContext(), "win-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        betModelBeanDao = daoSession.getBetModelBeanDao();
    }

    /*初始化局数数据库*/
    private void initThirdDbHelp() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(WEApplication.getContext(), "third-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        thirdModelBeanDao = daoSession.getBetModelBeanDao();
    }

    /*初始化局数数据库*/
    private void initWatchRecordeDbHelp() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(WEApplication.getContext(), "watch_history-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        watchHistoryDao = daoSession.getWatchHistoryBeanDao();
    }

    public static BetModelBeanDao getWinDao() {
        return betModelBeanDao;
    }

    public static BetModelBeanDao getThirdModelBeanDao() {
        return thirdModelBeanDao;
    }

    public static WatchHistoryBeanDao getWatchHistoryDao() {
        return watchHistoryDao;
    }


    //人人美剧SDK
    private void initRrSDK() {
        String version = "0";
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            version = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        ParseUrl.init(this, "sdk_android_dianzhi", version, "7c967bfd6e814802bd05ecf2bcb5ba99", "http://open.rr.tv");
    }

    //初始化友盟统计
    private void initMob() {
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    @Override
    protected String getBaseUrl() {
        return Constant.IP;
    }

    public static Context getContext() {
        return mApplication;
    }

    public static String getApp_ver() {
        return app_ver;
    }

    public static String getIMEI() {
        return IMEI;
    }

    public static String getTime() {
        return System.currentTimeMillis() + "";
    }

    public void initParse() {
        parseModule.setLogLevel(Module.PSL_DEBUG); // 解析视频资源的文件日志打印在d中
        int ret = parseModule.init2(parseScriptPath, parseKey);
        if (ret != 0) { // 0成功
            Toast.makeText(getBaseContext(), "初始化失败，返回值：" + ret, Toast.LENGTH_SHORT).show();
        }
    }

    public String getVersion() {
        PackageManager manager;
        PackageInfo info;
        manager = getApplicationContext().getPackageManager();
        try {
            info = manager.getPackageInfo(getApplicationContext().getPackageName(), 0);
            return info.versionName + "";
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public void downloadParseFile(String url) {
        ((App) WEApplication.getContext()).getAppComponent().repositoryManager().obtainRetrofitService(UserService.class)
                .downloadParseFile(url)
                .retryWhen(new RetryWithDelay(3, 2))
                .observeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                    }
                })
                .subscribe(new ErrorHandleSubscriber<Response<ResponseBody>>(mApplication.getAppComponent().rxErrorHandler()) {
                    @Override
                    public void onNext(@NonNull Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse != null) {
                            InputStream inputStream = responseBodyResponse.body().byteStream();
                            parseFile(inputStream);
                        } else {
                            parseFile(null);
                        }
                    }
                });
    }

    public void parseFile(InputStream inputStream) {
        if (inputStream == null) {
            UiUtils.makeText(this, "解析文件下载失败");
        } else {
            writeParseFile(inputStream);
            parseModule.uninit();
            initParse();
            SPUtils.put(Constant.APP_NAME, this, Constant.PARSE_MODULE_HASH, parseHash);
        }
    }

    public void writeParseFile(InputStream is) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(parseScriptPath));
            byte[] buffer = new byte[1024];
            while (true) {
                int len = is.read(buffer);
                if (len == -1) {
                    break;
                }
                fos.write(buffer, 0, len);
            }
            fos.flush();
            is.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    public static void showLuckyPan(Context context) {
        boolean aBoolean = Preference.getBoolean(Constant.IS_FIRST_SHOW_LUCKY_PAN, true);
        if (aBoolean) {
            ToastUtil.showToast(context, "用户每日第一次登录可免费抽奖一次，每日首次充值可再次获得一次抽奖机会，奖品多多，一般人我不告诉他。");
            Preference.put(Constant.IS_FIRST_SHOW_LUCKY_PAN, false);
        }
        Map<String, String> map = new HashMap<>();
        String op = "getAward";
        map.put("op", op);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        ((App) WEApplication.getContext()).getAppComponent().repositoryManager().obtainRetrofitService(UserService.class)
                .getLuckyList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<LuckyListBean>(mApplication.getAppComponent().rxErrorHandler()) {
                    @Override
                    public void onNext(@NonNull LuckyListBean baseBean) {
                        {
                            if (baseBean == null)
                                return;
                            if (baseBean.getStatus().equals("0"))
                                return;
                            Preference.put("award_num", baseBean.getData().getAward_num());
                            Preference.put(Constant.HAS_ROTATE, baseBean.getData().getAward_num() > 0);
                            Preference.put(Constant.AWARD_ID, baseBean.getData().getAwrad_id());
                            if (baseBean.getData().getUser_type() == 2) {
                                Preference.put(Constant.COIN, baseBean.getData().getB_coin() + "");
                                Preference.put(Constant.LEVEL, baseBean.getData().getMemberLevel() + "");
                            }
                            //增加判断，是否是充值用户，保存起来
                            if (baseBean.getData().getAward_num() <= 0) {
                                if (baseBean.getData().getUser_type() == 1) {
                                    UiUtils.makeText(context, "您当前的抽奖次数不足哦，单日首充可赠送一次抽奖");
//                            return;
                                } else {
                                    String s = Preference.get(Constant.LEVEL, "1");
                                    if (Integer.parseInt(s) >= 3) {
                                        if (Integer.parseInt(Preference.get(Constant.COIN, "0")) < 50) {
                                            UiUtils.makeText(context, "当前余额不足,");
                                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                            builder.setTitle("提醒")
                                                    .setMessage("当前抽奖需要50积分，积分不足，是否前往充值?")
                                                    .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                                                    .setPositiveButton("前往充值", (dialog, which) -> context.startActivity(new Intent(context, RechargeActivity.class))).create().show();
                                            return;
                                        }
                                        Preference.put("drawType", "2");
                                    } else {
                                        UiUtils.makeText(context, "您当前的抽奖次数不足,您的等级达到3级即可用积分抽奖啦！");
                                        return;
                                    }
                                }
                            } else
                                Preference.put("drawType", "1");

                            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Dialog_Fullscreen);
                            AlertDialog alertDialog = builder.create();
                            Message msg = new Message();
                            msg.obj = alertDialog;
                            msg.what = 1;
                            handler.sendMessageDelayed(msg, 60000);
                            View view = null;
                            if (baseBean.getData().getUser_type() == 2 && baseBean.getData().getMemberLevel() >= 3 && Preference.getBoolean(Constant.CAN_USE_COIN_ROTATE, false)) {
                                view = LayoutInflater.from(context).inflate(R.layout.dialog_luckpan, null);
                                TextView awardPoolTv = (TextView) view.findViewById(R.id.dialog_luckpan_award_pool);
                                TextView luckyTv = (TextView) view.findViewById(R.id.lucky_text);
                                awardPoolTv.setText(baseBean.getData().getAward_pool() + "");
                                if (baseBean.getData().getAward_num() > 0) {
                                    luckyTv.setText(baseBean.getData().getAward_num() + "次");
                                }
                                LuckPanLayout luckPanLayout = (LuckPanLayout) view.findViewById(R.id.luckpan_layout);
                                view.findViewById(R.id.go).setOnClickListener(v -> {
                                    //抽奖次数
                                    if (Preference.get("award_num", 0) > 0) {
                                        luckyTv.setText(Preference.get("award_num", 0) + "次");
                                        ratateLuckPan(baseBean, alertDialog, luckPanLayout);
                                        Preference.put("drawType", "1");
                                    }
                                    //花费积分抽奖
                                    else if (Integer.parseInt(Preference.get(Constant.LEVEL, "1")) >= 3) {
                                        Preference.put("drawType", "2");
                                        luckyTv.setText("50积分");
                                        if (Integer.parseInt(Preference.get(Constant.COIN, "0")) < 50) {
                                            UiUtils.makeText(context, "当前余额不足");
                                            AlertDialog.Builder builderb = new AlertDialog.Builder(context);
                                            builderb.setTitle("提醒")
                                                    .setMessage("当前抽奖需要50积分，积分不足，是否前往充值?")
                                                    .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                                                    .setPositiveButton("前往充值", (dialog, which) -> context.startActivity(new Intent(context, RechargeActivity.class))).create().show();
                                            return;
                                        } else if (Preference.getBoolean(Constant.AWARD_IS_SHOW, true)) {
                                            AlertDialog.Builder buildera = new AlertDialog.Builder(context);
                                            AlertDialog alertDialoga = buildera.create();
                                            View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_tip_yuyue, null);
                                            ((TextView) dialogView.findViewById(R.id.dialog_tip_tv)).setText("抽奖需要扣除50积分，是否抽奖？");
                                            ((TextView) dialogView.findViewById(R.id.dialog_change_name_sure_tv)).setText("抽奖");
                                            dialogView.findViewById(R.id.dialog_change_name_cancel_tv).setOnClickListener(v1 -> alertDialoga.dismiss());
                                            dialogView.findViewById(R.id.dialog_change_name_sure_tv).setOnClickListener(v1 -> {
                                                ratateLuckPan(baseBean, alertDialog, luckPanLayout);
                                                alertDialoga.dismiss();
                                            });
                                            ((CheckBox) dialogView.findViewById(R.id.dialog_tip_yuyue_cb)).setOnCheckedChangeListener((buttonView, isChecked) -> Preference.putBoolean(Constant.AWARD_IS_SHOW, !isChecked));
                                            alertDialoga.setView(dialogView);
                                            alertDialoga.show();
                                        } else {
                                            ratateLuckPan(baseBean, alertDialog, luckPanLayout);
                                        }
                                    } else {
                                        UiUtils.makeText(context, "您当前的抽奖次数不足,您的等级达到3级即可用积分抽奖啦！");
                                        return;
                                    }
                                });
                                luckPanLayout.setAnimationEndListener(position1 -> {
                                    Map<String, String> map1 = new HashMap<>();
                                    String op1 = "userWinning";
                                    map1.put("op", op1);
                                    map1.put("drawType", Preference.get("drawType", "1"));
                                    map1.put("awrad_id", Preference.get(Constant.AWARD_ID, 0) + "");
                                    map1.put("appid", Constant.APPID);
                                    map1.put("pt", Constant.PT);
                                    map1.put("ver", WEApplication.getApp_ver());
                                    map1.put("imei", WEApplication.getIMEI());
                                    map1.put("uid", Preference.get(Constant.UID, "0"));
                                    map1.put("t", System.currentTimeMillis() + "");
                                    map1.put("sign", Utils.buildSign(map1, Constant.key));
                                    ((App) WEApplication.getContext()).getAppComponent().repositoryManager().obtainRetrofitService(UserService.class)
                                            .getAward(map1)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(bean -> {
                                                if (bean != null && bean.getData() != null) {
                                                    awardPoolTv.setText(bean.getData().getAward_pool() + "");
                                                    if (baseBean.getData().getUser_type() == 2) {
                                                        Preference.put(Constant.COIN, bean.getData().getB_coin() + "");
                                                        Preference.put(Constant.LEVEL, bean.getData().getMemberLevel() + "");
                                                    }
                                                }
                                                Preference.put(Constant.HAS_ROTATE, bean.getData().getAward_num() > 0);
                                                if (Preference.getBoolean(Constant.HAS_ROTATE)) {
                                                    luckyTv.setText(bean.getData().getAward_num() + "次");
                                                } else {
                                                    luckyTv.setText("50积分");
                                                }
                                                if (bean != null && bean.getData() != null && bean.getStatus().equals("1")) {
                                                    int awrad_id = bean.getData().getAwrad_id();
                                                    Preference.put("award_num", bean.getData().getAward_num());
                                                    Preference.put(Constant.AWARD_ID, awrad_id);
                                                    String[] names = context.getResources().getStringArray(R.array.names);
                                                    UiUtils.makeText(context, "恭喜您，抽中" + names[position1]);
                                                    return;
                                                } else if (bean != null && bean.getStatus().equals("2")) {
                                                    UiUtils.makeText(context, bean.getMsg());
                                                    Preference.put(Constant.AWARD_ID, 0);
                                                    return;
                                                } else if (bean != null && bean.getStatus().equals("3")) {
                                                    UiUtils.makeText(context, "奖品不存在");
                                                    return;
                                                } else if (bean != null && bean.getStatus().equals("4")) {
                                                    UiUtils.makeText(context, "积分不足");
                                                    AlertDialog.Builder builderb = new AlertDialog.Builder(context);
                                                    builderb.setTitle("提醒")
                                                            .setMessage("当前抽奖需要50积分，积分不足，是否前往充值?")
                                                            .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                                                            .setPositiveButton("前往充值", (dialog, which) -> context.startActivity(new Intent(context, RechargeActivity.class))).create().show();
                                                    return;
                                                }

                                            });
                                });
                            } else {
                                view = LayoutInflater.from(context).inflate(R.layout.dialog_normal_luckpan, null);
                                TextView awardPoolTv = (TextView) view.findViewById(R.id.dialog_luckpan_award_pool);
                                TextView luckyTv = (TextView) view.findViewById(R.id.lucky_text);
                                awardPoolTv.setText(baseBean.getData().getAward_pool() + "");
                                NormalLuckPanLayout luckPanLayout = (NormalLuckPanLayout) view.findViewById(R.id.luckpan_layout);
                                luckyTv.setText(baseBean.getData().getAward_num() + "次");
                                view.findViewById(R.id.go).setOnClickListener(v -> {
                                    //抽奖次数
                                    if (Preference.get("award_num", 0) > 0) {
                                        ratateLuckPan(baseBean, alertDialog, luckPanLayout);
                                        Preference.put("drawType", "1");
                                        luckyTv.setText(Preference.get("award_num", 0) + "次");
                                    }
//                            //花费积分抽奖
//                            else if (Integer.parseInt(Preference.get(Constant.LEVEL, "1")) >= 3) {
//                                Preference.put("drawType", "2");
//                                luckyTv.setText("50积分");
//                                if (Integer.parseInt(Preference.get(Constant.COIN, "0")) < 50) {
//                                    UiUtils.makeText(context, "当前余额不足");
//                                    AlertDialog.Builder builderb = new AlertDialog.Builder(context);
//                                    builderb.setTitle("提醒")
//                                            .setMessage("当前抽奖需要50积分，积分不足，是否前往充值?")
//                                            .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
//                                            .setPositiveButton("前往充值", (dialog, which) -> context.startActivity(new Intent(context, RechargeActivity.class))).create().show();
//                                    return;
//                                } else if (Preference.getBoolean(Constant.AWARD_IS_SHOW, true)) {
//                                    AlertDialog.Builder buildera = new AlertDialog.Builder(context);
//                                    AlertDialog alertDialoga = buildera.create();
//                                    View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_tip_yuyue, null);
//                                    ((TextView) dialogView.findViewById(R.id.dialog_tip_tv)).setText("抽奖需要扣除50积分，是否抽奖？");
//                                    ((TextView) dialogView.findViewById(R.id.dialog_change_name_sure_tv)).setText("抽奖");
//                                    dialogView.findViewById(R.id.dialog_change_name_cancel_tv).setOnClickListener(v1 -> alertDialoga.dismiss());
//                                    dialogView.findViewById(R.id.dialog_change_name_sure_tv).setOnClickListener(v1 -> {
//                                        ratateLuckPan(baseBean, alertDialog, luckPanLayout);
//                                        alertDialoga.dismiss();
//                                    });
//                                    ((CheckBox) dialogView.findViewById(R.id.dialog_tip_yuyue_cb)).setOnCheckedChangeListener((buttonView, isChecked) -> Preference.putBoolean(Constant.AWARD_IS_SHOW, !isChecked));
//                                    alertDialoga.setView(dialogView);
//                                    alertDialoga.show();
//                                } else {
//                                    ratateLuckPan(baseBean, alertDialog, luckPanLayout);
//                                }
//                            }
                                    else {
                                        UiUtils.makeText(context, "您当前的抽奖次数不足,您充值后等级达到3级即可用积分抽奖啦！");
                                        return;
                                    }
                                });
                                luckPanLayout.setAnimationEndListener(position1 -> {
                                    Map<String, String> map1 = new HashMap<>();
                                    String op1 = "userWinning";
                                    map1.put("op", op1);
                                    map1.put("drawType", Preference.get("drawType", "1"));
                                    map1.put("awrad_id", Preference.get(Constant.AWARD_ID, 0) + "");
                                    map1.put("appid", Constant.APPID);
                                    map1.put("pt", Constant.PT);
                                    map1.put("ver", WEApplication.getApp_ver());
                                    map1.put("imei", WEApplication.getIMEI());
                                    map1.put("uid", Preference.get(Constant.UID, "0"));
                                    map1.put("t", System.currentTimeMillis() + "");
                                    map1.put("sign", Utils.buildSign(map1, Constant.key));
                                    ((App) WEApplication.getContext()).getAppComponent().repositoryManager().obtainRetrofitService(UserService.class)
                                            .getAward(map1)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(bean -> {
                                                if (bean != null && bean.getData() != null) {
                                                    awardPoolTv.setText(bean.getData().getAward_pool() + "");
                                                    if (baseBean.getData().getUser_type() == 2) {
                                                        Preference.put(Constant.COIN, bean.getData().getB_coin() + "");
                                                        Preference.put(Constant.LEVEL, bean.getData().getMemberLevel() + "");
                                                    }
                                                }
                                                Preference.put(Constant.HAS_ROTATE, bean.getData().getAward_num() > 0);
                                                luckyTv.setText(bean.getData().getAward_num() + "次");
                                                if (bean != null && bean.getData() != null && bean.getStatus().equals("1")) {
                                                    int awrad_id = bean.getData().getAwrad_id();
                                                    Preference.put("award_num", bean.getData().getAward_num());
                                                    Preference.put(Constant.AWARD_ID, awrad_id);
                                                    String[] names = context.getResources().getStringArray(R.array.normal_names);
                                                    UiUtils.makeText(context, "恭喜您，抽中" + names[position1]);
                                                    return;
                                                } else if (bean != null && bean.getStatus().equals("2")) {
                                                    UiUtils.makeText(context, bean.getMsg());
                                                    Preference.put(Constant.AWARD_ID, 0);
                                                    return;
                                                } else if (bean != null && bean.getStatus().equals("3")) {
                                                    UiUtils.makeText(context, "奖品不存在");
                                                    return;
                                                }
//                                        else if (bean != null && bean.getStatus().equals("4")) {
//                                            UiUtils.makeText(context, "积分不足");
//                                            AlertDialog.Builder builderb = new AlertDialog.Builder(context);
//                                            builderb.setTitle("提醒")
//                                                    .setMessage("当前抽奖需要50积分，积分不足，是否前往充值?")
//                                                    .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
//                                                    .setPositiveButton("前往充值", (dialog, which) -> context.startActivity(new Intent(context, RechargeActivity.class))).create().show();
//                                            return;
//                                        }
                                            });
                                });
                            }
                            view.findViewById(R.id.dialog_luckpan_finish_iv).setOnClickListener(v -> alertDialog.dismiss());
                            alertDialog.setView(view);
                            alertDialog.show();
                        }
                    }
                });
    }

    private static void ratateLuckPan(LuckyListBean baseBean, AlertDialog alertDialog, LuckPanLayout luckPanLayout) {
        if (Preference.get(Constant.AWARD_ID, 0) == 0)
            luckPanLayout.rotate(baseBean.getData().getAwrad_id() - 1, 100);
        else
            luckPanLayout.rotate(Preference.get(Constant.AWARD_ID, baseBean.getData().getAwrad_id()) - 1, 100);
        Message message = new Message();
        message.obj = alertDialog;
        message.what = 1;
        handler.removeMessages(1);
        handler.sendMessageDelayed(message, 60000);
    }

    private static void ratateLuckPan(LuckyListBean baseBean, AlertDialog alertDialog, NormalLuckPanLayout luckPanLayout) {
        if (Preference.get(Constant.AWARD_ID, 0) == 0)
            luckPanLayout.rotate(baseBean.getData().getAwrad_id() - 1, 100);
        else
            luckPanLayout.rotate(Preference.get(Constant.AWARD_ID, baseBean.getData().getAwrad_id()) - 1, 100);
        Message message = new Message();
        message.obj = alertDialog;
        message.what = 1;
        handler.removeMessages(1);
        handler.sendMessageDelayed(message, 60000);
    }

    private RongIMClient.ConnectionStatusListener mListener = (connectionStatus) -> {
        switch (connectionStatus) {
            case KICKED_OFFLINE_BY_OTHER_CLIENT:
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("isMoreLogin", true);
                startActivity(intent);
                break;
        }
    };
}
