package com.lwd.qjtv.mvp.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.App;
import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.ClickMoreUtils;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.GetLevelBean;
import com.lwd.qjtv.mvp.model.entity.GuessCenterBean;
import com.lwd.qjtv.mvp.ui.activity.CollectionActivity;
import com.lwd.qjtv.mvp.ui.activity.GuessCenterActivity;
import com.lwd.qjtv.mvp.ui.activity.LoginActivity;
import com.lwd.qjtv.mvp.ui.activity.MallActivity;
import com.lwd.qjtv.mvp.ui.activity.NBAGuessCenterActivity;
import com.lwd.qjtv.mvp.ui.activity.PersonalWarehouseActivity;
import com.lwd.qjtv.mvp.ui.activity.RechargeActivity;
import com.lwd.qjtv.mvp.ui.activity.RechargeCostActivity;
import com.lwd.qjtv.mvp.ui.activity.SettingActivity;
import com.lwd.qjtv.mvp.ui.activity.UserInfoActivity;
import com.lwd.qjtv.mvp.ui.activity.WatchRecordeActivity;
import com.lwd.qjtv.mvp.ui.activity.WebActivity;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

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
 * Created by ZhengQian on 2017/5/11.
 */

public class MineFragment extends com.jess.arms.base.BaseFragment implements IFragment {
    @BindView(R.id.fragment_mine_enter_mall_tv)
    ImageView mallTv;
    @BindView(R.id.fragment_mine_recharge_yue_iv)
    ImageView yueIv;
    @BindView(R.id.fragment_mine_recharge_use_iv)
    ImageView useIv;
    @BindView(R.id.fragment_mine_mybag_iv)
    ImageView bagIv;
    @BindView(R.id.fragment_mine_watch_history_iv)
    ImageView historyIv;
    @BindView(R.id.fragment_mine_guess_iv)
    ImageView guessIv;
    @BindView(R.id.fragment_mine_video_collection_iv)
    ImageView collectionIv;
    @BindView(R.id.fragment_mine_setting)
    ImageView ivSetting;

    @BindView(R.id.fragment_mine_my_setting_iv)
    ImageView settingIv;


    @BindView(R.id.fragment_mine_experience_pb)
    ProgressBar experiencePb;
    @BindView(R.id.fragment_mine_goto_login_rl)
    RelativeLayout gotoLoginRl;
    @BindView(R.id.fragment_mine_login_ll)
    LinearLayout loginLl;
    @BindView(R.id.fragment_mine_head_civ)
    CircleImageView headIv;
    @BindView(R.id.fragment_mine_name_tv)
    TextView nameTv;
    @BindView(R.id.fragment_mine_lv_iv)
    ImageView lvIv;
    @BindView(R.id.fragment_mine_recharge_yue_ll)
    LinearLayout yueLl;
    @BindView(R.id.fragment_mine_recharge_use_ll)
    LinearLayout useLl;
    @BindView(R.id.fragment_mine_recharge_warehouse_ll)
    LinearLayout warehouseLl;
    @BindView(R.id.fragment_mine_watch_history_ll)
    LinearLayout historyLl;
    @BindView(R.id.fragment_mine_guess_ll)
    LinearLayout guessLl;
    @BindView(R.id.fragment_mine_video_collection_ll)
    LinearLayout collectionLl;
    @BindView(R.id.fragment_mine_nba_guess_ll)
    LinearLayout nbaGuessLL;
    @BindView(R.id.fragment_mine_jiaqun_ll)
    LinearLayout jiaqunLl;
    @BindView(R.id.fragment_mine_my_setting_ll)
    LinearLayout settingLl;
    @BindView(R.id.fragment_mine_money_ll)
    LinearLayout moneyLl;
    @BindView(R.id.fragment_mine_lucky_ll)
    LinearLayout luckyLl;
    @BindView(R.id.fragment_mine_new_gift_iv)
    ImageView newGiftIv;
    @BindView(R.id.fragment_mine_guess_new_iv)
    ImageView guessNewIv;
    @BindView(R.id.fragment_mine_lucky_new_iv)
    ImageView luckyNewIv;
    @BindView(R.id.fragment_mine_red_point)
    ImageView redPoint;
    //    @BindView(R.id.fragment_mine_shop_enter_iv)
//    ImageView shopIv;
    @BindView(R.id.fragment_mine_exp_tv)
    TextView expTv;
    @BindView(R.id.fragment_mine_tuiguang_ll)
    LinearLayout tuiguangLl;
    private static MineFragment mineFragment;
    private boolean isLogin;
    private AppComponent appComponent;
    private ImageLoader imageLoader;
    private String keFuQQ = "541660139";

    public static MineFragment newInstance() {
        if (mineFragment == null)
            mineFragment = new MineFragment();
        return mineFragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this, view);
        initListener();
        initView();
        initData();
        return view;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        isLogin = Preference.get(Constant.IS_LOGIN, false);
        if (gotoLoginRl != null)
            gotoLoginRl.setVisibility(isLogin ? View.GONE : View.VISIBLE);
        if (loginLl != null)
            loginLl.setVisibility(isLogin ? View.VISIBLE : View.GONE);
        if (isLogin)
            setUserInfo();
        initGiftRedPoint();
        initGuessCenterData();


        if (!Preference.getBoolean(Constant.IS_GUESS) || !Preference.getBoolean(Constant.ON_OFF)) {
            guessLl.setVisibility(View.GONE);
        } else {
            guessLl.setVisibility(View.VISIBLE);
        }
        if (!Preference.getBoolean(Constant.IS_RELEASE) || !Preference.getBoolean(Constant.ON_OFF)) {
            moneyLl.setVisibility(View.GONE);
        } else {
            moneyLl.setVisibility(View.VISIBLE);
        }
        if (!Preference.getBoolean(Constant.IS_RELEASE)) {
            jiaqunLl.setVisibility(View.GONE);
        } else {
            jiaqunLl.setVisibility(View.GONE);
        }
        if (SaveUserInfo.isTopLevelUser() && Preference.getBoolean(Constant.ON_OFF)) {
            mallTv.setVisibility(View.VISIBLE);
        } else {
            mallTv.setVisibility(View.GONE);
        }

        setLevel();
        if (!Preference.getBoolean(Constant.IS_NEW_GUESS, true))
            guessNewIv.setVisibility(View.GONE);
        if (!Preference.getBoolean(Constant.IS_NEW_LUCKY, true))
            luckyNewIv.setVisibility(View.GONE);
        redPoint.setVisibility(Preference.get(Constant.HAS_ROTATE, true) ? View.VISIBLE : View.GONE);
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            if (Preference.getBoolean(Constant.IS_FIRST_ENTER_MINE, true)) {
                UiUtils.makeText(getContext(), "告诉你个秘密，只有在充值中心才能查看余额。");
                Preference.putBoolean(Constant.IS_FIRST_ENTER_MINE, false);
            }
            setLevel();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isLogin = Preference.get(Constant.IS_LOGIN, false);
        if (gotoLoginRl != null)
            gotoLoginRl.setVisibility(isLogin ? View.GONE : View.VISIBLE);
        if (loginLl != null)
            loginLl.setVisibility(isLogin ? View.VISIBLE : View.GONE);
        if (isLogin)
            setUserInfo();
        initGiftRedPoint();
        initGuessCenterData();

        if (!Preference.getBoolean(Constant.IS_GUESS) || !Preference.getBoolean(Constant.ON_OFF)) {
            guessLl.setVisibility(View.GONE);
        } else {
            guessLl.setVisibility(View.VISIBLE);
        }

        if (!Preference.getBoolean(Constant.IS_RELEASE) || !Preference.getBoolean(Constant.ON_OFF)) {
            moneyLl.setVisibility(View.GONE);
        }
        if (SaveUserInfo.isTopLevelUser() && Preference.getBoolean(Constant.ON_OFF)) {
            mallTv.setVisibility(View.VISIBLE);
        } else {
            mallTv.setVisibility(View.GONE);
        }
        setLevel();
        if (!Preference.getBoolean(Constant.IS_NEW_GUESS, true))
            guessNewIv.setVisibility(View.GONE);

        if (!Preference.getBoolean(Constant.IS_NEW_LUCKY, true))
            luckyNewIv.setVisibility(View.GONE);
        redPoint.setVisibility(Preference.get(Constant.HAS_ROTATE, true) ? View.VISIBLE : View.GONE);
    }

    private void initGiftRedPoint() {
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
        if (appComponent == null) {
            newGiftIv.setVisibility(View.GONE);
            return;
        }
        appComponent.repositoryManager().obtainRetrofitService(UserService.class).getUserInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<GetLevelBean>(appComponent.rxErrorHandler()) {
                    @Override
                    public void onNext(@NonNull GetLevelBean getLevelBean) {
                        if (getLevelBean == null || !"1".equals(getLevelBean.getStatus()))
                            return;
                        SaveUserInfo.setExperienceMax(getLevelBean.getData().getExperienceValueMax());
                        SaveUserInfo.setExperienceValue(getLevelBean.getData().getExperienceValue());
                        SaveUserInfo.setCoin(getLevelBean.getData().getB_coin());
                        Preference.putBoolean(Constant.ON_OFF, getLevelBean.getData().getOn_off().equals("1"));
                        keFuQQ = getLevelBean.getData().getKf_qq();

                        if (!Preference.getBoolean(Constant.IS_RELEASE) || !getLevelBean.getData().getOn_off().equals("1")) {
                            moneyLl.setVisibility(View.GONE);
                        } else {
                            moneyLl.setVisibility(View.VISIBLE);
                        }
                        if (SaveUserInfo.isTopLevelUser() && getLevelBean.getData().getOn_off().equals("1")) {
                            mallTv.setVisibility(View.VISIBLE);
                        } else {
                            mallTv.setVisibility(View.GONE);
                        }

                        if (!Preference.getBoolean(Constant.IS_RELEASE) || !getLevelBean.getData().getOn_off().equals("1")) {
                            moneyLl.setVisibility(View.GONE);
                        } else {
                            moneyLl.setVisibility(View.VISIBLE);
                        }

                        if (getLevelBean.getData().getGift_is_new() != 0)
                            newGiftIv.setVisibility(View.VISIBLE);
                        else
                            newGiftIv.setVisibility(View.GONE);
                    }
                });


    }


    private void initGuessCenterData() {

        AppComponent appComponent = ((App) getContext().getApplicationContext()).getAppComponent();
        Map<String, String> map = new TreeMap<>();
        map.put("page", "1");
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        if (appComponent == null) {
            return;
        }
        appComponent.repositoryManager().obtainRetrofitService(UserService.class).getNBAGuessCenter(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<GuessCenterBean>(appComponent.rxErrorHandler()) {
                    @Override
                    public void onNext(@NonNull GuessCenterBean guesscenterbean) {

                        if (guesscenterbean == null)
                            return;

                        if (guesscenterbean.getStatus().equals("1")) {
                            if (guesscenterbean.getData() != null && guesscenterbean.getData().size() > 0) {
                                SaveUserInfo.setHasOtherCai(true);
                            } else {
                                SaveUserInfo.setHasOtherCai(false);
                            }

                        }


                    }
                });
    }

    private void setUserInfo() {
        nameTv.setText(SaveUserInfo.getUserName());
//        GlideConfigGlobal.loadImageView(SaveUserInfo.getAvater(),headIv);

        setLevel();
        appComponent = ((App) getContext().getApplicationContext()).getAppComponent();
        imageLoader = appComponent.imageLoader();
        String avater = SaveUserInfo.getAvater();
        if (avater != null && !"".equals(avater))
            Glide.with(WEApplication.getContext()).load(SaveUserInfo.getAvater()).into(headIv);
    }

    private void setLevel() {
        if (SaveUserInfo.getLogin()) {
            int i = Integer.parseInt(SaveUserInfo.getLevel());
            if (i <= 0)
                i = 1;
            else if (i > 10)
                i = 10;
            lvIv.setBackground(getResources().getDrawable(getDrawableResource("level_" + i)));
            experiencePb.setMax(Integer.parseInt(SaveUserInfo.getExperienceMax()));
            experiencePb.setProgress(Integer.parseInt(SaveUserInfo.getExperienceValue()));
            expTv.setText(Preference.get(Constant.EX_VALUE, "0") + "/" + Preference.get(Constant.EX_MAX, "0"));
        }
    }

    public int getDrawableResource(String resIdName) {
        try {
            Field field = R.mipmap.class.getField(resIdName);
            int i = field.getInt(new R.drawable());
            return i;
        } catch (Exception e) {
            return R.mipmap.video_place_holder;
        }
    }

    private void initView() {
//        luckyLl.setVisibility(Preference.getBoolean(Constant.CAN_ROTATE, false) ? View.VISIBLE : View.GONE);
        isLogin = Preference.get(Constant.IS_LOGIN, false);
        gotoLoginRl.setVisibility(isLogin ? View.GONE : View.VISIBLE);
        loginLl.setVisibility(isLogin ? View.VISIBLE : View.GONE);
    }

    public void initListener() {
        luckyLl.setOnClickListener(v -> {
            Preference.put(Constant.IS_NEW_LUCKY, false);
            luckyNewIv.setVisibility(View.GONE);
            if (!SaveUserInfo.getLogin())
                startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
            else if (!ClickMoreUtils.isFastClick())
                WEApplication.showLuckyPan(getContext());
        });
        //登陆
        gotoLoginRl.setOnClickListener(view -> startActivity(new Intent(WEApplication.getContext(), LoginActivity.class)));
        //用户中心
        headIv.setOnClickListener(view -> startActivityForResult(new Intent(WEApplication.getContext(), UserInfoActivity.class),0));
        //商城
        mallTv.setOnClickListener(view -> startActivity(new Intent(WEApplication.getContext(), MallActivity.class)));
        //余额
        yueLl.setOnClickListener(view -> {
            if (!SaveUserInfo.getLogin())
                startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
            else
                startActivity(new Intent(WEApplication.getContext(), RechargeActivity.class));
        });
        //充值消费
        useLl.setOnClickListener(view -> {
            if (!SaveUserInfo.getLogin())
                startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
            else
                startActivity(new Intent(WEApplication.getContext(), RechargeCostActivity.class));
        });
        //个人仓库
        warehouseLl.setOnClickListener(view -> {
            if (!SaveUserInfo.getLogin())
                startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
            else
                startActivity(new Intent(WEApplication.getContext(), PersonalWarehouseActivity.class));
        });
        //观看历史
        historyLl.setOnClickListener(view -> {
            if (!SaveUserInfo.getLogin())
                startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
            else
                startActivity(new Intent(WEApplication.getContext(), WatchRecordeActivity.class));
        });
        //竟猜中心
        guessLl.setOnClickListener(view -> {
            Preference.put(Constant.IS_NEW_GUESS, false);
            guessNewIv.setVisibility(View.GONE);
            if (!SaveUserInfo.getLogin())
                startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
            else
                startActivity(new Intent(WEApplication.getContext(), GuessCenterActivity.class));
        });
        //nba竟猜中心
        nbaGuessLL.setOnClickListener(view -> {
            if (!SaveUserInfo.getLogin())
                startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
            else
                startActivity(new Intent(WEApplication.getContext(), NBAGuessCenterActivity.class));
        });

        //视频收藏
        collectionLl.setOnClickListener(view -> {
            if (!SaveUserInfo.getLogin())
                startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
            else
                startActivity(new Intent(WEApplication.getContext(), CollectionActivity.class));
        });
        //我的设置
        ivSetting.setOnClickListener(view -> startActivity(new Intent(WEApplication.getContext(), SettingActivity.class)));
//        settingLl.setOnClickListener(view -> startActivity(new Intent(WEApplication.getContext(), SettingActivity.class)));
        //一键加群
        jiaqunLl.setOnClickListener(view -> {
            if (SaveUserInfo.getLogin()) {
//                joinQQGroup();
                final String qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=" + keFuQQ + "&version=1";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));

            } else {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }

        });
        //推广
        tuiguangLl.setOnClickListener(view -> {
            if (!SaveUserInfo.getLogin())
                startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
            else {
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("url", SaveUserInfo.getYqLink());
                startActivity(intent);
            }
        });
    }


    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Override
    public void initData() {
        isLogin = Preference.get(Constant.IS_LOGIN, false);
        if (gotoLoginRl != null)
            gotoLoginRl.setVisibility(isLogin ? View.GONE : View.VISIBLE);
        if (loginLl != null)
            loginLl.setVisibility(isLogin ? View.VISIBLE : View.GONE);
        if (isLogin)
            setUserInfo();
        initGiftRedPoint();

        initGuessCenterData();
        if (!Preference.getBoolean(Constant.IS_GUESS) || !Preference.getBoolean(Constant.ON_OFF)) {
            guessLl.setVisibility(View.GONE);
        } else {
            guessLl.setVisibility(View.VISIBLE);
        }
        if (!Preference.getBoolean(Constant.IS_RELEASE)) {
            guessLl.setVisibility(View.GONE);
        }

        if (!Preference.getBoolean(Constant.IS_RELEASE) || !Preference.getBoolean(Constant.ON_OFF)) {
            moneyLl.setVisibility(View.GONE);

        }
        setLevel();
        if (!Preference.getBoolean(Constant.IS_NEW_GUESS, true))
            guessNewIv.setVisibility(View.GONE);

        if (!Preference.getBoolean(Constant.IS_NEW_LUCKY, true))
            luckyNewIv.setVisibility(View.GONE);
        redPoint.setVisibility(Preference.get(Constant.HAS_ROTATE, true) ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        imageLoader.clear(appComponent.Application(), GlideImageConfig.builder()
//                .imageViews(headIv)
//                .build());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /****************
     *
     * 发起添加群流程。群号：斯诺克(335114508) 的 key 为： EPu0PdglYLjCRDPeLhndfFot0uxjHCwg
     * 调用 joinQQGroup(EPu0PdglYLjCRDPeLhndfFot0uxjHCwg) 即可发起手Q客户端申请加群 斯诺克(335114508)
     *
     *   由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public boolean joinQQGroup() {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + "EPu0PdglYLjCRDPeLhndfFot0uxjHCwg"));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }

}