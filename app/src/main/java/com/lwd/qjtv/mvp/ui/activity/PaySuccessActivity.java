package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.di.component.DaggerPaySuccessComponent;
import com.lwd.qjtv.di.module.PaySuccessModule;
import com.lwd.qjtv.mvp.contract.PaySuccessContract;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.GetLevelBean;
import com.lwd.qjtv.mvp.presenter.PaySuccessPresenter;


import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
 * Created by ZhengQian on 2017/7/17.
 */

public class PaySuccessActivity extends BaseActivity<PaySuccessPresenter> implements PaySuccessContract.View {

    @BindView(R.id.activity_pay_success_back_tv)
    TextView backTv;
    @BindView(R.id.activity_pay_success_enter_guess_tv)
    TextView guessTv;
    @BindView(R.id.activity_pay_success_coin_tv)
    TextView coinTv;
    @BindView(R.id.activity_pay_success_exp_tv)
    TextView expTv;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerPaySuccessComponent
                .builder()
                .appComponent(appComponent)
                .paySuccessModule(new PaySuccessModule(this)) //请将PaySuccessModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_pay_success;
    }

    @Override
    public void initData() {
        setTitle("下单成功");
        initListener();
        initDatas();
    }

    private void initDatas() {
        resetCoin();
        String recharge_money = Preference.get("recharge_money", "0");
        int money = Integer.parseInt(recharge_money);
        if (money == 0) {
            finish();
        }
        if (money >= 50 && money < 200) {
            coinTv.setText(money * (105) + "(+5%)");
        } else if (money >= 200 && money < 500) {
            coinTv.setText(money * (108) + "(+8%)");
        } else if (money >= 500) {
            coinTv.setText(money * (110) + "(+10%)");
        } else {
            coinTv.setText(money * (100) + "");
        }
        expTv.setText(money * 10 + "");
    }

    public void goGuess(){
        UiUtils.makeText(this,"点击了guessTv");
        startActivity(new Intent(this, GuessCenterActivity.class));
        finish();
    }

    public void goBack(){
        UiUtils.makeText(this,"点击了backTv");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void initListener() {
        backTv.setOnClickListener(view -> {
            UiUtils.makeText(this,"点击了backTv");
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
        guessTv.setOnClickListener(view -> {
            UiUtils.makeText(this,"点击了guessTv");
            startActivity(new Intent(this, GuessCenterActivity.class));
            finish();
        });
    }

    public void resetCoin(){
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
        ((App)WEApplication.getContext().getApplicationContext()).getAppComponent().repositoryManager().obtainRetrofitService(UserService.class)
                .getUserInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<GetLevelBean>( ((App)WEApplication.getContext().getApplicationContext()).getAppComponent().rxErrorHandler()) {
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull GetLevelBean getLevelBean) {
                        if (getLevelBean == null || !"1".equals(getLevelBean.getStatus()))
                            return;
                        SaveUserInfo.setExperienceMax(getLevelBean.getData().getExperienceValueMax());
                        SaveUserInfo.setExperienceValue(getLevelBean.getData().getExperienceValue());
                        SaveUserInfo.setCoin(getLevelBean.getData().getB_coin());
                    }
                });
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
        UiUtils.makeText(this,message);
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

    }


}
