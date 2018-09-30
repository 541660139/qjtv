package com.lwd.qjtv.mvp.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.delegate.IActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.DataCleanManager;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.app.utils.ToastUtil;
import com.lwd.qjtv.app.utils.UpdateManager;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.CheckVersionBean;
import com.lwd.qjtv.mvp.ui.common.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/11.
 */

public class SettingActivity extends BaseActivity implements IActivity {

    @BindView(R.id.activity_setting_preference_tv)
    TextView clearTv;
    @BindView(R.id.activity_setting_about_us_iv)
    ImageView aboutIv;
    @BindView(R.id.activity_setting_feedback_iv)
    ImageView feedBackIv;
    @BindView(R.id.activity_setting_update_iv)
    ImageView updateIv;
    @BindView(R.id.activity_setting_switch)
    Switch aSwitch;
    @BindView(R.id.activity_setting_clear_ll)
    LinearLayout clearLl;
    @BindView(R.id.activity_setting_feedback_ll)
    LinearLayout feedbackLl;
    @BindView(R.id.activity_setting_update_ll)
    LinearLayout updateLl;
    @BindView(R.id.activity_setting_about_us_ll)
    LinearLayout aboutUsLl;
    private static Context context;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    public int initView() {
        return R.layout.activity_setting;
    }

    @Override
    public void initData() {
        setTitle("我的设置");
        initListener();
        setDataTv(false);
        aSwitch.setChecked(Preference.getBoolean(Constant.IS_REMIND_NET, true));
        context = this;
    }


    @Override
    public boolean useFragment() {
        return false;
    }


    protected void initListener() {
        clearLl.setOnClickListener(view -> {
            DataCleanManager.clearAllCache(this);
            setDataTv(true);
        });
        aboutUsLl.setOnClickListener(view -> startActivity(new Intent(WEApplication.getContext(), AboutUsActivity.class)));
        feedbackLl.setOnClickListener(view -> {
            if (!SaveUserInfo.getLogin())
                startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
            else
                startActivityForResult(new Intent(WEApplication.getContext(), FeedBackActivity.class), 0x001);
        });
        updateLl.setOnClickListener(view -> checkVersion());
        aSwitch.setOnCheckedChangeListener((compoundButton, b) -> Preference.put(Constant.IS_REMIND_NET, b));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x001 && resultCode == 0x002) {
            UiUtils.makeText(this, "反馈成功!");
        }
    }

    private void setDataTv(boolean flag) {
        if (flag) {
            try {
                String cacheSize = DataCleanManager.getTotalCacheSize(this);
                if (cacheSize.equals(clearTv.getText().toString()))
                    ToastUtil.showToast(SettingActivity.this, "已经清理到最低");
                else
                    ToastUtil.showToast(SettingActivity.this, "清理成功");
                clearTv.setText(cacheSize);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            String cacheSize = null;
            try {
                cacheSize = DataCleanManager.getTotalCacheSize(this);
                clearTv.setText(cacheSize);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void checkVersion() {
        Map<String, String> map = new HashMap<>();
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        ((App) WEApplication.getContext().getApplicationContext()).
                getAppComponent().repositoryManager().obtainRetrofitService(UserService.class)
                .checkVersion(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 5))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<CheckVersionBean>(((App) WEApplication.getContext().getApplicationContext()).getAppComponent().rxErrorHandler()) {
                    @Override
                    public void onNext(@NonNull CheckVersionBean checkVersionBean) {
                        if ("1".equals(checkVersionBean.getStatus())) {
                            CheckVersionBean.DataBean data = checkVersionBean.getData();
                            Preference.putBoolean(Constant.IS_RELEASE, checkVersionBean.getData().getIs_realease().equals("0"));
                            Preference.putBoolean(Constant.IS_GUESS, checkVersionBean.getData().getIs_guessNotOpen().equals("2"));
                            Preference.putBoolean(Constant.ON_OFF, checkVersionBean.getData().getOn_off().equals("1"));
                            if ("0".equals(data.getUpdate_type())) {
                                UiUtils.makeText(context, "当前已是最新版本！");
                                return;
                            }
                            UpdateManager updateManager = new UpdateManager(context);
                            updateManager.showNoticeDialog(data.getVersion(), data.getUpdate_info(), "2".equals(data.getUpdate_type()), data.getUrl(), false);
                        } else {

                        }
                    }
                });

    }


}
