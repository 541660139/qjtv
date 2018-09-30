package com.lwd.qjtv.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.mvp.model.entity.WechatPayBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 重要说明:
 * <p>
 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
 */
public class PayUtils {


    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private Activity context;
    private Context mContext;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 判断resultStatus 为9000则代表支付成功
                        context.finish();
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        //// TODO: 2017/4/11  进行同步通知服务器
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(context,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(context,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };
    private String pay_param;
    private boolean isFlag;


    public void payMoney(String pay_param, Activity context, boolean isFlag) {
        mContext = context;
        final String orderInfo = pay_param;
        this.isFlag = isFlag;
        this.context = context;
        final Activity acontext = context;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(acontext);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public void payWechat(JSONObject jsonObject) {
        try {
            PayReq req = new PayReq();
            req.appId = jsonObject.getString("appid");
            req.partnerId = jsonObject.getString("partnerid");
            req.prepayId = jsonObject.getString("prepayid");
            req.packageValue = jsonObject.getString("package");
            req.nonceStr = jsonObject.getString("noncestr");
            req.timeStamp = jsonObject.getLong("timestamp") + "";
            req.sign = jsonObject.getString("sign");
            WEApplication.api.sendReq(req);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void payWechat(WechatPayBean.DataBean dataBean) {
        String time = WXMethods.genTimeStamp() + "";
        PayReq req = new PayReq();
        req.appId = dataBean.getOpenID();
        req.partnerId = dataBean.getPartnerId();
        req.prepayId = dataBean.getPrepayId();
        req.packageValue = dataBean.getPackageX();
        req.nonceStr = dataBean.getNonceStr();
        req.timeStamp = time;
        req.sign = getWechatSign(dataBean, time);
//        req.sign = dataBean.getSign();
        WEApplication.api.sendReq(req);
    }

    public String getWechatSign(WechatPayBean.DataBean dataBean, String time) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", dataBean.getOpenID());
        map.put("partnerid", dataBean.getPartnerId());
        map.put("prepayid", dataBean.getPrepayId());
        map.put("package", dataBean.getPackageX());
        map.put("noncestr", dataBean.getNonceStr());
        map.put("timestamp", time);
        String s = WXMethods.genPackageSign(map);
        return s;
    }


}
