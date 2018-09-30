package com.lwd.qjtv.mvp.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;

import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.LiveKit;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.mvp.ui.message.RCServerPushMessage;
import com.umeng.analytics.MobclickAgent;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.MessageContent;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/5/5.
 */

public class WelcomeActivity extends Activity implements Handler.Callback {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!Preference.getBoolean(Constant.IS_FIRST, true)) {
                startActivity(new Intent(WEApplication.getContext(), com.lwd.qjtv.mvp.ui.activity.MainActivity.class));
                finish();
            } else {
                startActivity(new Intent(WEApplication.getContext(), com.lwd.qjtv.mvp.ui.activity.MainActivity.class));
                finish();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);


    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getPermission();
//        TODO: 2018/8/22 暂时屏蔽检查版本
//        WEApplication.checkVersion(false, null);
//        handler.sendEmptyMessageDelayed(0, 3000);

//        LiveKit.addEventHandler(new Handler(this));
//        RongIMClient.setOnReceiveMessageListener(onReceiveMessageListener);
    }

    public void getPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
            } else {
                WEApplication.getPhoneState();
                handler.sendEmptyMessageDelayed(0, 3000);
            }
        } else {
            WEApplication.getPhoneState();
            handler.sendEmptyMessageDelayed(0, 3000);
        }
    }


    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case LiveKit.MESSAGE_ARRIVED: {
                MessageContent message = (MessageContent) msg.obj;
                if (message instanceof RCServerPushMessage) {
                    String status = ((RCServerPushMessage) message).getStatus();
                    UiUtils.makeText(this, status);
                }
                break;
            }
        }
        return false;
    }

    /**
     * 消息接收监听者
     */
    private static RongIMClient.OnReceiveMessageListener onReceiveMessageListener = new RongIMClient.OnReceiveMessageListener() {
        @Override
        public boolean onReceived(io.rong.imlib.model.Message message, int i) {
            MessageContent msg = message.getContent();
            if (msg instanceof RCServerPushMessage) {
                String status = ((RCServerPushMessage) msg).getStatus();
                UiUtils.SnackbarText(status);
            }
            return false;
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                WEApplication.getPhoneState();
                handler.sendEmptyMessageDelayed(0, 3000);
            } else {
                finish();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
