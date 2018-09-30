package com.lwd.qjtv.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.FengJinBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/29.
 */

public class FengjinDialog extends View {

    private AlertDialog alertDialog;
    private Context context;
    private TextView nameTv;
    private EditText timeEdt;
    private View sureTv;
    private View cancelTv;
    private String name;
    private String uid;
    private String room_id;
    private String min;
    private FengJinCallback fengJinCallback;

    public FengjinDialog(Context context) {
        super(context);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_fengjin_layout, null);
        init(view);
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setView(view);
    }

    public void setData(String room_id, String name, String uid) {
        this.name = name;
        this.uid = uid;
        this.room_id = room_id;
        nameTv.setText(name == null ? "" : name);
    }

    public void show() {
        if (alertDialog != null)
            alertDialog.show();
    }

    private void init(View view) {
        nameTv = (TextView) view.findViewById(R.id.dialog_fengjin_name_tv);
        timeEdt = (EditText) view.findViewById(R.id.dialog_fengjin_time_edt);
        sureTv = view.findViewById(R.id.dialog_fengjin_sure_tv);
        cancelTv = view.findViewById(R.id.dialog_fengjin_cancel_tv);
        initListener();
    }

    private void initListener() {
        sureTv.setOnClickListener(v -> fengjinUser());
        cancelTv.setOnClickListener(v -> {
            if (alertDialog != null)
                alertDialog.dismiss();
            alertDialog = null;
        });
    }

    public void setCallBack(FengJinCallback fengJinCallback) {
        this.fengJinCallback = fengJinCallback;
    }

    private void fengjinUser() {
        min = timeEdt.getText().toString();
        if (min == null || min.equals("")) {
            UiUtils.SnackbarText("封禁时间不能为空");
            return;
        } else if (room_id == null || room_id.equals("") || uid == null || uid.equals("")) {
            UiUtils.SnackbarText("封禁失败");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("room_id", room_id);
        map.put("min", min);
        map.put("j_uid", uid);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));

        ((App) WEApplication.getContext()).getAppComponent().repositoryManager().obtainRetrofitService(UserService.class)
                .fengjinUser(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 5))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<FengJinBean>(((App) WEApplication.getContext()).getAppComponent().rxErrorHandler()) {
                    @Override
                    public void onNext(@NonNull FengJinBean fengJinBean) {
                        UiUtils.SnackbarText(fengJinBean.getMsg());
                        if (fengJinCallback != null)
                            fengJinCallback.fengJin((fengJinBean.getData().getName() == null ? "" : fengJinBean.getData().getName()) + "违反规则，被管理员封禁" + fengJinBean.getData().getJ_time());
                        if (alertDialog != null)
                            alertDialog.dismiss();
                    }
                });
    }

    public interface FengJinCallback {
        void fengJin(String message);
    }
}
