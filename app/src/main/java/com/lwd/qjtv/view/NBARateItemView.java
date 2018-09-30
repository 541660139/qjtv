package com.lwd.qjtv.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.BaseBean;
import com.lwd.qjtv.mvp.model.entity.WatchBallBean;
import com.lwd.qjtv.mvp.ui.activity.RechargeActivity;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/13.
 */

public class NBARateItemView extends LinearLayout {
    private TextView titleTv;
    private TextView dateTv;
    private CircleImageView leftCiv;
    private CircleImageView rightCiv;
    private TextView leftNameTv;
    private TextView rightNameTv;
    private TextView timeTv;
    private TextView leftTv;
    private TextView rightTv;
    private LinearLayout numLL;
    private TextView numTv;
    private Context context;
    private AppComponent appComponent;
    private ImageLoader imageLoader;


    public NBARateItemView(Context context) {
        this(context, null);
    }

    public NBARateItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View.inflate(context, R.layout.item_rate_view_layout, this);
        titleTv = (TextView) findViewById(R.id.item_rate_view_layout_title_tv);
        dateTv = (TextView) findViewById(R.id.item_rate_view_layout_date_tv);
        leftCiv = (CircleImageView) findViewById(R.id.item_rate_view_layout_left_civ);
        rightCiv = (CircleImageView) findViewById(R.id.item_rate_view_layout_right_civ);
        leftNameTv = (TextView) findViewById(R.id.item_rate_view_layout_left_name_tv);
        rightNameTv = (TextView) findViewById(R.id.item_rate_view_layout_right_name_tv);
        numTv = (TextView) findViewById(R.id.item_rate_view_num_tv);
        timeTv = (TextView) findViewById(R.id.item_rate_view_layout_time_tv);
        leftTv = (TextView) findViewById(R.id.item_rate_view_layout_left_tv);
        rightTv = (TextView) findViewById(R.id.item_rate_view_layout_right_tv);
        numLL = (LinearLayout) findViewById(R.id.item_rate_num_ll);
        appComponent = ((App) context.getApplicationContext()).getAppComponent();
        imageLoader = appComponent.imageLoader();

    }

    public void setData(WatchBallBean.DataBean.NbaListBean.DataListBeanX data) {
//        if (data == null) {
//            this.setVisibility(GONE);
//            return;
//        }
//        if ("1".equals(data.getIsLive())) {
//            leftTv.setVisibility(GONE);
//            numLL.setVisibility(VISIBLE);
//            rightTv.setText("直播");
//            rightTv.setBackground(getResources().getDrawable(R.drawable.dialog_circle_corner_stroke_origin_bg));
//            rightTv.setTextColor(getResources().getColor(R.color.colorOrigin));
//            Intent intent = new Intent(context, LiveActivity.class);
//            intent.putExtra("id", data.getId());
//            intent.putExtra("match_id", data.getId());
//            intent.putExtra("url", data.getPlayUrl());
//            intent.putExtra("title", data.getMatcheName());
//            intent.putExtra("moveText", data.getMoveText());
//            intent.putExtra("moveSpeed", data.getMoveSpeed());
//            intent.putExtra("erweimaUrl", data.getErweimaUrl());
//            intent.putExtra("origin", data.getOrigin());
//            intent.putExtra("gonggao", data.getGonggao());
//            intent.putExtra("isGuess", true);
//            intent.putExtra("isNBA", true);
//            leftTv.setOnClickListener(v -> {
//                if (Preference.getBoolean(Constant.IS_LOGIN))
//                    context.startActivity(intent);
//                else
//                    context.startActivity(new Intent(context, LoginActivity.class));
//            });
////            rightTv.setOnClickListener(view -> {
////                List<WatchBallBean.DataBean.LiveListBean.DataListBean.StarInfoBean> starInfo = data.getStarInfo();
////                WatchBallBean.DataBean.LiveListBean.DataListBean.StarInfoBean starInfoBean = starInfo.get(0);
////                WatchBallBean.DataBean.LiveListBean.DataListBean.StarInfoBean starInfoBean1 = starInfo.get(1);
////                long startTime = Long.parseLong(data.getStartTimestamp());
////                long time = startTime - System.currentTimeMillis();
////                AlarmService.addNotification((int) time, "预约提醒", data.getMatcheName(), starInfoBean.getName() + "VS" + starInfoBean1.getName());
////                UiUtils.makeText(context,"预约成功");
////                rightTv.setText("已预约");
////            });
//        } else {
//            numLL.setVisibility(INVISIBLE);
//            if (Preference.getBoolean(Constant.IS_GUESS)) {
//                leftTv.setVisibility(VISIBLE);
//            }
//            if (!Preference.getBoolean(Constant.IS_RELEASE)) {
//                rightTv.setText("待直播");
//            } else {
//                rightTv.setOnClickListener(view -> {
//                    if (Preference.getBoolean(Constant.IS_LOGIN, false))
//                        if (rightTv.getText().equals("预约")) {
//                            rightTv.setBackground(getResources().getDrawable(R.drawable.dialog_circle_corner_stroke_origin_bg));
//                            rightTv.setTextColor(getResources().getColor(R.color.colorOrigin));
//                            // TODO: 2017/11/17 屏蔽预约功能
//                            if (Preference.getBoolean(Constant.IS_SHOW, true)) {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                                AlertDialog alertDialog = builder.create();
//                                View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_tip_yuyue, null);
//                                dialogView.findViewById(R.id.dialog_change_name_cancel_tv).setOnClickListener(v -> alertDialog.dismiss());
//                                dialogView.findViewById(R.id.dialog_change_name_sure_tv).setOnClickListener(v -> {
//                                    yuyueLive(data.getId());
//                                    alertDialog.dismiss();
//                                });
//                                ((CheckBox) dialogView.findViewById(R.id.dialog_tip_yuyue_cb)).setOnCheckedChangeListener((buttonView, isChecked) -> Preference.putBoolean(Constant.IS_SHOW, !isChecked));
//                                alertDialog.setView(dialogView);
//                                alertDialog.show();
//                            } else
//                                yuyueLive(data.getId());
//                            //// TODO: 2017/11/17 屏蔽预约功能
////                        AlarmService.addNotification((int) time, "预约提醒", data.getMatcheName(), starInfoBean.getName() + "VS" + starInfoBean1.getName());
//                            UiUtils.makeText(context, "预约成功");
//                            rightTv.setText("已预约");
//                        } else {
//                            rightTv.setClickable(false);
////                        AlarmService.cleanAllNotification();
////                        UiUtils.makeText(context, "取消预约成功");
////                        rightTv.setText("预约");
//                        }
//                    else
//                        context.startActivity(new Intent(context, LoginActivity.class));
//                });
//            }
//            Intent intent = new Intent(context, GuessDetailsActivity.class);
//            intent.putExtra("id", data.getId());
//            leftTv.setOnClickListener(v -> {
//                if (Preference.getBoolean(Constant.IS_LOGIN))
//                    context.startActivity(intent);
//                else
//                    context.startActivity(new Intent(context, LoginActivity.class));
//            });
//        }
//        String startTime = data.getStartTime();
//        String[] arr = startTime.split("\\s");
//        dateTv.setText(arr[0]);
//        titleTv.setText(data.getMatcheName() == null ? "" : data.getMatcheName());
//        timeTv.setText(arr[1]);
//        List<WatchBallBean.DataBean.NbaListBean.DataListBeanX.StarInfoBeanX> starInfo = data.getStarInfo();
//        if (starInfo.size() < 1) return;
//        //左边信息
//        WatchBallBean.DataBean.NbaListBean.DataListBeanX.StarInfoBeanX starInfoBean = starInfo.get(0);
////        imageLoader.loadImage(appComponent.appManager().getCurrentActivity() == null
////                ? appComponent.Application() : appComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(starInfoBean.getThumb_img(), leftCiv));
////        GlideConfigGlobal.loadImageView(starInfoBean.getThumb_img(),leftCiv);
//        if (starInfoBean.getThumb_img() != null)
//            Glide.with(context).load(starInfoBean.getThumb_img()).into(leftCiv);
//        leftNameTv.setText(starInfoBean.getName());
//
//        if (starInfo.size() < 2) return;
//        //右边信息
//        WatchBallBean.DataBean.NbaListBean.DataListBeanX.StarInfoBeanX starInfoBean1 = starInfo.get(1);
////        imageLoader.loadImage(appComponent.appManager().getCurrentActivity() == null
////                ? appComponent.Application() : appComponent.appManager().getCurrentActivity(), GlideConfigGlobal.loadImageView(starInfoBean1.getThumb_img(), rightCiv));
////        GlideConfigGlobal.loadImageView(starInfoBean1.getThumb_img(),rightCiv);
//        Glide.with(context).load(starInfoBean1.getThumb_img()).into(rightCiv);
//        rightNameTv.setText(starInfoBean1.getName());
//        numTv.setText(data.getPeople_num() + "");
    }

    private void yuyueLive(String matchId) {
        if (Integer.parseInt(Preference.get(Constant.COIN, "0")) >= 50) {
            Map<String, String> map = new HashMap<>();
            map.put("match_id", matchId);
            map.put("appid", Constant.APPID);
            map.put("pt", Constant.PT);
            map.put("ver", WEApplication.getApp_ver());
            map.put("imei", WEApplication.getIMEI());
            map.put("uid", Preference.get(Constant.UID, "0"));
            map.put("t", System.currentTimeMillis() + "");
            map.put("sign", Utils.buildSign(map, Constant.key));
            ((App) WEApplication.getContext().getApplicationContext()).getAppComponent().repositoryManager().obtainRetrofitService(UserService.class)
                    .orderLive(map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ErrorHandleSubscriber<BaseBean>(((App) WEApplication.getContext().getApplicationContext()).getAppComponent().rxErrorHandler()) {
                        @Override
                        public void onNext(@NonNull BaseBean baseBean) {
                            if (baseBean != null && baseBean.getStatus().equals("1")) {
                                UiUtils.makeText(context, "已预约");
                                if ("1".equals(baseBean.getStatus()))
                                    rightTv.setText("已预约");
                            } else if (baseBean != null && baseBean.getStatus().equals("0")) {
                                UiUtils.makeText(context, "已经预约过该比赛了");
                                rightTv.setText("已预约");
                            } else
                                UiUtils.makeText(context, "预约失败");
                        }
                    });
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("提醒")
                    .setMessage("当前积分不足，是否前往充值?")
                    .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                    .setPositiveButton("前往充值", (dialog, which) -> context.startActivity(new Intent(context, RechargeActivity.class))).create().show();
        }

    }
}
