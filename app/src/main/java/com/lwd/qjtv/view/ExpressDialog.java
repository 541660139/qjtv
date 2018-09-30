package com.lwd.qjtv.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.base.App;
import com.jess.arms.mvp.IView;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.OrderExpressBean;
import com.lwd.qjtv.mvp.ui.adapter.LogisticsAdapter;

import java.util.HashMap;
import java.util.List;
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

public class ExpressDialog extends View implements IView
{

    private RecyclerView recyclerView;
    private AlertDialog alertDialog;
    private TextView tipTv;
    private Context context;
    private String express_sn;

    public ExpressDialog(Context context) {
        super(context);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_logistics_layout, null);
        init(view);
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setView(view);
    }


    public void show() {
        alertDialog.show();
    }

    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.dialog_logistics_layout_recyclerview);
        tipTv = (TextView) view.findViewById(R.id.dialog_logistics_tip_tv);

    }

    private void requestExpressList() {
        Map<String, String> map = new HashMap<>();
        String op = "express";
        map.put("op", op);
        map.put("express_sn", express_sn);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));

        ((App) WEApplication.getContext()).getAppComponent().repositoryManager().obtainRetrofitService(UserService.class)
                .getOrderExpress(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 5))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<OrderExpressBean>(((App) WEApplication.getContext()).getAppComponent().rxErrorHandler()) {
                    @Override
                    public void onNext(@NonNull OrderExpressBean baseBean) {
                        if (baseBean == null || baseBean.getData() == null || !"1".equals(baseBean.getStatus())) {
                            showMessage("暂无物流信息，稍后再试。");
                            return;
                        }
                        List<OrderExpressBean.DataBeanX.DataBean> data = baseBean.getData().getData();
                        if (data != null && data.size() != 0)
                            tipTv.setVisibility(GONE);
                        LogisticsAdapter logisticsAdapter = new LogisticsAdapter(data);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(logisticsAdapter);
                    }
                });
    }

    public void setExpressNum(String express_sn) {
        this.express_sn = express_sn;
        requestExpressList();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void setData(Object o) {

    }
}
