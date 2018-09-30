package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.EditCheckUtil;
import com.lwd.qjtv.app.utils.MyHttpUtils;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.di.component.DaggerRegisterComponent;
import com.lwd.qjtv.di.module.RegisterModule;
import com.lwd.qjtv.mvp.contract.RegisterContract;
import com.lwd.qjtv.mvp.presenter.RegisterPresenter;


import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

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

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.activity_register_send_code_tv)
    TextView sendcode_tv;
    @BindView(R.id.activity_register_register_tv)
    TextView registerTv;
    @BindView(R.id.activity_register_phone_edt)
    EditText phoneEdt;
    @BindView(R.id.activity_register_code_edt)
    EditText codeEdt;
    @BindView(R.id.activity_register_password_edt)
    EditText pwdEdt;
    @BindView(R.id.activity_register_yaoqing_edt)
    EditText yaoqingEdt;
    @BindView(R.id.activity_register_intro_ll)
    LinearLayout introLl;
    @BindView(R.id.activity_register_yaoqing_ll)
    LinearLayout yaoqingLl;
//    @BindView(R.id.activity_register_confirm_password_edt)
//    EditText confirmPwdEdt;

    private int pwdLenth;
    //    private int confirmPwdLenth;
    private int codeLenth;
    private TimeCount time;
    private boolean isRegister;
    private String phone;
    private String pwd;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerRegisterComponent
                .builder()
                .appComponent(appComponent)
                .registerModule(new RegisterModule(this)) //请将RegisterModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_register;
    }


    @Override
    public void initData() {
        initTitle();
        time = new TimeCount(60000, 1000);
        initListener();
    }

    private void initTitle() {
        int type = getIntent().getIntExtra("type", 0);
        isRegister = type == 1;
        setTitle(isRegister ? "注册" : "找回密码");
        introLl.setVisibility(isRegister ? View.VISIBLE : View.GONE);
        registerTv.setText(isRegister ? "注册" : "找回密码");
        yaoqingLl.setVisibility(isRegister ? View.VISIBLE : View.GONE);
    }

    private void initListener() {
        sendcode_tv.setOnClickListener(view -> {
            String trim = phoneEdt.getText().toString().trim();
            boolean isMobile = EditCheckUtil.isMobileNO(trim);
            if (isMobile) {
                //发送短信
                mPresenter.sendMsg(isRegister ? "reg" : "findpwd", trim);
                startTime();
            } else {
                showMessage("请输入正确的手机号");
            }
        });

        pwdEdt.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pwdLenth = charSequence.toString().trim().length();
                changeRegisterTvBg();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

//        confirmPwdEdt.addTextChangedListener(new_pic TextWatcher() {
//
//
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                confirmPwdLenth = charSequence.toString().trim().length();
//                changeRegisterTvBg();
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

        codeEdt.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                codeLenth = charSequence.toString().trim().length();
                changeRegisterTvBg();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        registerTv.setOnClickListener(view -> {
            phone = phoneEdt.getText().toString().trim();
            pwd = pwdEdt.getText().toString().trim();
            mPresenter.register(isRegister ? "phone_reg" : "findpwd", phone, codeEdt.getText().toString().trim(), pwd, MyHttpUtils.getChannel(RegisterActivity.this, "com.vid.snooker"), yaoqingEdt.getText().toString());

        });
    }

    private void changeRegisterTvBg() {
        if (pwdLenth >= 6 && codeLenth >= 6) {
            registerTv.setBackground(getResources().getDrawable(R.drawable.dialog_circle_corner_login_enable_bg));
            registerTv.setEnabled(true);
        } else {
            registerTv.setBackground(getResources().getDrawable(R.drawable.dialog_circle_corner_login_bg));
            registerTv.setEnabled(false);
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
        /**=================     调用SDK注册接口    =================*/
        if (o instanceof String) {
            String op = (String) o;
            if (op.equals("phone_reg")) {
                phone = phoneEdt.getText().toString().trim();
                JMessageClient.register(phone, "123456", new BasicCallback() {
                    @Override
                    public void gotResult(int responseCode, String registerDesc) {
                        if (responseCode == 0) {

                            Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "JMessageClient.register " + "注册成功, responseCode = " + responseCode + " ; registerDesc = " + registerDesc);
                            Preference.put(Constant.IS_REGISTER, true);
//                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                            intent.putExtra("password", pwd);
//                            intent.putExtra("account", phone);
//                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "JMessageClient.register " + "注册失败, responseCode = " + responseCode + " ; registerDesc = " + registerDesc);
                        }
                    }
                });

            } else {
                Preference.put(Constant.IS_REGISTER, true);
//                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                intent.putExtra("password", pwd);
//                intent.putExtra("account", phone);
//                startActivity(intent);
                finish();
            }
        }


    }


    public void startTime() {
        time.start();
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            if (sendcode_tv == null)
                return;
            sendcode_tv.setClickable(true);
            sendcode_tv.setText("重新发送");
            sendcode_tv.setTextColor(UiUtils.getColor(WEApplication.getContext(), R.color.colorOrigin));
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            if (sendcode_tv == null)
                return;
            sendcode_tv.setClickable(false);
            sendcode_tv.setText("重新发送(" + millisUntilFinished / 1000 + ")秒");
            sendcode_tv.setTextColor(UiUtils.getColor(WEApplication.getContext(), R.color.color999999));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        time.onFinish();
        time.cancel();
        time = null;
    }

}
