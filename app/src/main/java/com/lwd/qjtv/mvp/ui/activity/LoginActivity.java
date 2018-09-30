package com.lwd.qjtv.mvp.ui.activity;

import com.jess.arms.di.component.AppComponent;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.RongYunUtils;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.di.component.DaggerLoginComponent;
import com.lwd.qjtv.di.module.LoginModule;
import com.lwd.qjtv.mvp.contract.LoginContract;
import com.lwd.qjtv.mvp.presenter.LoginPresenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.utils.UiUtils;


import butterknife.BindView;
import top.wefor.circularanim.CircularAnim;

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

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    //注册按钮
    @BindView(R.id.activity_login_register_tv)
    TextView registerTv;
    //登陆按钮
    @BindView(R.id.activity_login_login_tv)
    TextView loginTv;
    //忘记密码按钮
    @BindView(R.id.activity_login_forget_tv)
    TextView forgetTv;
    //手机编辑框
    @BindView(R.id.activity_login_phone_et)
    EditText phoneEdt;
    //密码编辑框
    @BindView(R.id.activity_login_pwd_et)
    EditText pwdEdt;
    //退出按钮
    @BindView(R.id.activity_login_quit_iv)
    ImageView quitIv;

    //密码长度,手机长度
    private int pwdLenth, phoneLenth;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this)) //请将LoginModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_login;
    }


    @Override
    public void initData() {
        setTitle("登陆");
        if (getIntent() != null && !getIntent().hasExtra("isMoreLogin")) {
            String account = getIntent().getStringExtra("account");
            String password = getIntent().getStringExtra("password");
            if (account != null && password != null) {
                mPresenter.login(account, password);
            }

        } else if (getIntent() != null && getIntent().hasExtra("isMoreLogin")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog alertDialog = builder
                    .setTitle("提醒")
                    .setMessage("您的账号在其他设备登陆!")
                    .setPositiveButton("重登", (dialog, which) -> {
                        RongYunUtils.connect(SaveUserInfo.getToken());
                        dialog.dismiss();
                        finish();
                    })
                    .setNegativeButton("取消", (dialog, which) -> {
                        SaveUserInfo.logout();
                        dialog.dismiss();
                    }).create();
            alertDialog.show();
        }
        initListener();
    }


    private void initListener() {
        quitIv.setOnClickListener(view -> {
            if (Preference.getBoolean(Constant.IS_LOGIN))
                SaveUserInfo.logout();
            finish();
        });
        registerTv.setOnClickListener(view -> {
            Intent intent = new Intent(WEApplication.getContext(), RegisterActivity.class);
            intent.putExtra("type", 1);
            startActivity(intent);
            finish();
        });
        forgetTv.setOnClickListener(view -> {
            Intent intent = new Intent(WEApplication.getContext(), RegisterActivity.class);
            intent.putExtra("type", 0);
            startActivity(intent);
        });
        phoneEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                phoneLenth = charSequence.toString().trim().length();
                changeRegisterTvBg();
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
        loginTv.setOnClickListener(view -> CircularAnim.fullActivity(LoginActivity.this, view)
                .colorOrImageRes(R.color.colorOrigin)
                .go(() -> {
                    String phone = phoneEdt.getText().toString().trim();
                    String pwd = pwdEdt.getText().toString().trim();
                    mPresenter.login(phone, pwd);
                }));
    }

    private void changeRegisterTvBg() {
        //改变按钮背景颜色
        if (pwdLenth >= 6 && phoneLenth >= 11) {
            loginTv.setBackground(getResources().getDrawable(R.drawable.dialog_circle_corner_login_enable_bg));
            loginTv.setEnabled(true);
        } else {
            loginTv.setBackground(getResources().getDrawable(R.drawable.dialog_circle_corner_login_bg));
            loginTv.setEnabled(false);
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
        setResult(Constant.LOGIN_TO_WB);
        finish();
    }

    @Override
    public void setData(Object o) {


//        mPresenter.getToken();

//        /**=================     调用SDk登陆接口    =================*/
//
//        String account = phoneEdt.getText().toString().trim();
//
//        JMessageClient.login(account, "123456", new BasicCallback() {
//            @Override
//            public void gotResult(int responseCode, String LoginDesc) {
//                if (responseCode == 0) {
//
//                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
//                    Log.i("MainActivity", "JMessageClient.login" + ", responseCode = " + responseCode + " ; LoginDesc = " + LoginDesc);
//                    finish();
//                } else {
//
//                    Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
//                    Log.i("MainActivity", "JMessageClient.login" + ", responseCode = " + responseCode + " ; LoginDesc = " + LoginDesc + "--- phone :" + account);
//                }
//            }
//        });


    }
}
