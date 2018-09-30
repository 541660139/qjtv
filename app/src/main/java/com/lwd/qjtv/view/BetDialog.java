package com.lwd.qjtv.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.base.App;
import com.jess.arms.mvp.IView;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.CalculatePeilv;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.BaseBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/29.
 */

public class BetDialog extends View implements IView {

    private TextView titleTv;
    private TextView seletorTv;
    private TextView peilvTv;
    private EditText numEdt;
    private TextView guessMoneyTv;
    private TextView earnMoneyTv;
    private TextView cancelTv;
    private TextView sureTv;
    private String match_id, jc_id, jc_pl_id, jc_type, wager, peilv;
    private AlertDialog alertDialog;
    private Context context;
    private AlertDialog alertDialog1;
    private TextView user_all_money;

    public BetDialog(Context context) {
        super(context);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_bet, null);
        init(view);
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setView(view);
    }


    public void show() {


        alertDialog.show();
    }

    private void init(View view) {
        titleTv = (TextView) view.findViewById(R.id.dialog_add_bet_tittle_tv);
        seletorTv = (TextView) view.findViewById(R.id.dialog_add_bet_seletor_tv);
        peilvTv = (TextView) view.findViewById(R.id.dialog_add_bet_peilv_tv);
        numEdt = (EditText) view.findViewById(R.id.dialog_add_bet_guess_num_edt);
        guessMoneyTv = (TextView) view.findViewById(R.id.dialog_add_bet_guess_money_tv);
        earnMoneyTv = (TextView) view.findViewById(R.id.dialog_add_bet_earn_money_tv);
        cancelTv = (TextView) view.findViewById(R.id.dialog_add_bet_cancel_tv);
        sureTv = (TextView) view.findViewById(R.id.dialog_add_bet_sure_tv);
        user_all_money = (TextView) view.findViewById(R.id.user_all_money);
        initListener();
    }

    private void initListener() {
        cancelTv.setOnClickListener(view -> alertDialog.dismiss());
        sureTv.setOnClickListener(view -> showSureDialog());

        String coin = SaveUserInfo.getCoin();

        try {
            int a = Integer.parseInt(coin);
            Log.d("SaveUserInfocoin",a+"");
            int i = a / 100;
            if (a > 100) {
                numEdt.setHint("可投注1~" + i);
            } else {
                numEdt.setHint("可投注0");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }





        numEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
                if (s.indexOf('0') == 0) {
                    showMessage("不能为0");
                    return;
                }
                if (s.length() == 0 || s.trim().equals("")) {
                    guessMoneyTv.setText("0");
                    earnMoneyTv.setText("0");
                    return;
                }
                guessMoneyTv.setText(s + "00");
                earnMoneyTv.setText(CalculatePeilv.calculate(charSequence + "", peilv));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void showSureDialog() {
        if (alertDialog1 == null)
            alertDialog1 = new AlertDialog.Builder(context).create();
        alertDialog1.setTitle("提示");
        alertDialog1.setMessage("温馨提示：您是否确认投注，投注后将无法更改！！！");
        alertDialog1.setButton(DialogInterface.BUTTON_POSITIVE, "确定", (dialog, which) -> requestAddBet());
        alertDialog1.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", (dialog, which) -> dialog.cancel());
        alertDialog1.show();
    }


    private void requestAddBet() {
        wager = numEdt.getText().toString().trim();
        Map<String, String> map = new HashMap<>();
        String op = "addBet";
        map.put("match_id", match_id);
        map.put("jc_id", jc_id);
        map.put("op", op);
        map.put("jc_pl_id", jc_pl_id);
        map.put("jc_type", jc_type);
        map.put("wager", wager);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        ((App) WEApplication.getContext()).getAppComponent().repositoryManager().obtainRetrofitService(UserService.class)
                .addGuessBat(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<BaseBean>(((App) WEApplication.getContext()).getAppComponent().rxErrorHandler()) {
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull BaseBean baseBean) {
                        showMessage(baseBean.getMsg());
                        if ("1".equals(baseBean.getStatus()))
                            alertDialog.dismiss();
                        WEApplication.resetCoin();
                    }
                });
    }

    public void setApiParam(String match_id, String jc_id, String jc_pl_id, String jc_type) {
        this.match_id = match_id;
        this.jc_id = jc_id;
        this.jc_pl_id = jc_pl_id;
        this.jc_type = jc_type;
    }

    public void setData(String title, String seletor, String peilv) {
        this.peilv = peilv;
        titleTv.setText(title);
        seletorTv.setText(seletor);
        peilvTv.setText(peilv);
        user_all_money.setText(SaveUserInfo.getCoin());
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
