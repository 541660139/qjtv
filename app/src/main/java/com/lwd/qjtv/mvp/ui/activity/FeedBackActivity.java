package com.lwd.qjtv.mvp.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.di.component.DaggerFeedBackComponent;
import com.lwd.qjtv.di.module.FeedBackModule;
import com.lwd.qjtv.mvp.contract.FeedBackContract;
import com.lwd.qjtv.mvp.presenter.FeedBackPresenter;


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
 * Created by ZhengQian on 2017/5/11.
 */

public class FeedBackActivity extends BaseActivity<FeedBackPresenter> implements FeedBackContract.View {

    @BindView(R.id.activity_feedback_feedback_edt)
    EditText feedbackEdt;
    @BindView(R.id.activity_feedback_contact_edt)
    EditText contactEdt;
    @BindView(R.id.activity_feedback_commit_tv)
    TextView commitTv;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerFeedBackComponent
                .builder()
                .appComponent(appComponent)
                .feedBackModule(new FeedBackModule(this)) //请将FeedBackModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!Preference.getBoolean(Constant.IS_LOGIN))
            finish();
    }

    @Override
    public void initData() {
        setTitle("反馈");
        initListener();
    }


    private void initListener() {
        commitTv.setOnClickListener(view -> {
            String content = feedbackEdt.getText().toString().trim();
            String contact = contactEdt.getText().toString().trim();
            if (content == null || content.equals("")) {
                showMessage("内容不能为空");
                return;
            }
            if (contact == null || contact.equals("")) {
                showMessage("联系方式不能为空");
                return;
            }
            feedbackEdt.setText("");
            contactEdt.setText("");
            mPresenter.feedBack(content, contact);
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
        setResult(0x002);
        finish();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("提示");
        alertDialog.setMessage("反馈成功！");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", (dialog, which) -> {
            dialog.dismiss();
            finish();
        });
    }


}
