package com.lwd.qjtv.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.mvp.ui.activity.PaySuccessActivity;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WEApplication.api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        WEApplication.api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        int errCode = resp.errCode;
        switch (errCode) {
            case 0:
                Preference.putBoolean("isPaySuccess", true);
                Log.d("WXPayEntryActivity", "onResp:  0 ");
                Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, PaySuccessActivity.class);
                startActivity(intent);
                finish();
                break;
            case -1:
                Preference.putBoolean("isPaySuccess", false);
                Log.d("WXPayEntryActivity", "onResp:  -1 ");
                Toast.makeText(this, "支付失败，请稍后再试", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case -2:
                Preference.putBoolean("isPaySuccess", false);
                finish();
                Log.d("WXPayEntryActivity", "onResp:  -2 ");
                Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
                break;
            default:
                finish();
                break;
        }
    }
}