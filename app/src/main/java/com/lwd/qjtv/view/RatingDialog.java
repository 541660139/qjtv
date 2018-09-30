package com.lwd.qjtv.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.PingFenBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Created by ZhengQian on 2017/12/22.
 * E-mail : wwwiiivip@yeah.net
 * If anyelse things , mail to it . I'll be soon to reply .
 */

public class RatingDialog extends View {

    private AlertDialog alertDialog;
    private Context context;
    private RatingBar ratingBar;
    private TextView cancelTv;
    private TextView pingfenTv;
    private TextView sureTv;
    private String videoId;
    private PingFenCallBack pingFenCallBack;

    public RatingDialog(Context context) {
        super(context);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_rating_layout, null);
        init(view);
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setView(view);
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    private void init(View view) {
        ratingBar = (RatingBar) view.findViewById(R.id.dialog_rating_ratingbar);
        cancelTv = (TextView) view.findViewById(R.id.dialog_change_name_cancel_tv);
        pingfenTv = (TextView) view.findViewById(R.id.dialog_rating_pingfen_tv);
        sureTv = (TextView) view.findViewById(R.id.dialog_change_name_sure_tv);
        initListener();
    }

    public void show() {
        if (alertDialog != null)
            alertDialog.show();
    }

    private void initListener() {
        cancelTv.setOnClickListener(v -> {
            if (alertDialog != null)
                alertDialog.dismiss();
        });
        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            pingfenTv.setText(rating * 2 + "");
        });
        sureTv.setOnClickListener(v -> pingfen());
    }

    private void pingfen() {
        float rating = ratingBar.getRating();
        String op = "add";
        Map<String, String> map = new HashMap<>();
        map.put("op", op);
        map.put("pingfen", rating + "");
        map.put("video_id", videoId);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        ((App) WEApplication.getContext()).getAppComponent().repositoryManager().obtainRetrofitService(UserService.class)
                .addPingFen(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<PingFenBean>(((App) WEApplication.getContext()).getAppComponent().rxErrorHandler()) {
                    @Override
                    public void onNext(@NonNull PingFenBean pingFenBean) {
                        UiUtils.SnackbarText(pingFenBean.getMsg());
                        if (pingFenCallBack != null && "1".equals(pingFenBean.getStatus())) {
                            pingFenCallBack.isPingfen(true);
                            UiUtils.SnackbarText("评分成功");
                        }
                        if (alertDialog != null) {
                            alertDialog.dismiss();
                        }
                    }
                });
    }

    public void setCallBack(PingFenCallBack pingFenCallBack) {
        this.pingFenCallBack = pingFenCallBack;
    }

    public interface PingFenCallBack {
        void isPingfen(boolean isPingfen);
    }

}
