package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.alipay.PayUtils;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Base64;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.app.utils.ToastUtil;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.di.component.DaggerRechargeComponent;
import com.lwd.qjtv.di.module.RechargeModule;
import com.lwd.qjtv.mvp.contract.RechargeContract;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.AliH5PayBean;
import com.lwd.qjtv.mvp.model.entity.AliPayBean;
import com.lwd.qjtv.mvp.model.entity.BaseBean;
import com.lwd.qjtv.mvp.model.entity.WechatH5PayBean;
import com.lwd.qjtv.mvp.model.entity.WechatPayBean;
import com.lwd.qjtv.mvp.presenter.RechargePresenter;
import com.lwd.qjtv.mvp.ui.adapter.RechargeGridAdapter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import timber.log.Timber;

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
 * Created by ZhengQian on 2017/5/24.
 */

public class RechargeActivity extends BaseActivity<RechargePresenter> implements RechargeContract.View, RechargeGridAdapter.OnItemChooseCallBack {

    @BindView(R.id.activity_recharge_own_money_tv)
    TextView moneyTv;
    @BindView(R.id.activity_recharge_award_tv)
    TextView awardTv;
    @BindView(R.id.activity_recharge_exp_tv)
    TextView expTv;
    @BindView(R.id.recharge_window_wechat_ll)
    LinearLayout wechatLl;
    @BindView(R.id.recharge_window_alipay_ll)
    LinearLayout alipayLl;
    @BindView(R.id.recharge_wechat_rb)
    RadioButton wechatRb;
    @BindView(R.id.recharge_alipay_rb)
    RadioButton alipayRb;
    @BindView(R.id.activity_recharge_pay_tv)
    TextView payTv;
    @BindView(R.id.activity_recharge_money_lv)
    RecyclerView recyclerView;
    private RechargeGridAdapter rechargeGridAdapter;
    private List<String> strList = new ArrayList<>();

    private String defaultMoney = "50";

    private static int PAY_TYPE = 1;
    //支付宝 type
    private static final int ALIPAY_CODE = 0;
    //微信 type
    private static final int WECHATPAY_CODE = 1;
    //银联 type
    private static final int UNIONPAY_CODE = 3;


    //微信H5支付跳转浏览器
    private static final int WECHAT_PAY_H5_CODE = 4;

    //微信H5支付跳转浏览器
    private static final int ALI_PAY_H5_CODE = 5;

    private String token;


    private String orderNUmber;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerRechargeComponent
                .builder()
                .appComponent(appComponent)
                .rechargeModule(new RechargeModule(this)) //请将RechargeModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!Preference.getBoolean(Constant.IS_LOGIN))
            finish();
        mPresenter.getUserCoin();
    }

    @Override
    public void initData() {
        if (!SaveUserInfo.getLogin()) {
            startActivity(new Intent(WEApplication.getContext(), LoginActivity.class));
            finish();
        }
        setTitle("充值中心");
        initList();
        rechargeGridAdapter = new RechargeGridAdapter(WEApplication.getContext(), this);
        UiUtils.configRecycleView(recyclerView, new GridLayoutManager(WEApplication.getContext(), 3));
        recyclerView.setAdapter(rechargeGridAdapter);
        rechargeGridAdapter.getList().clear();
        rechargeGridAdapter.getList().addAll(strList);
        rechargeGridAdapter.notifyDataSetChanged();
        initListener();
        wechatRb.setChecked(true);
        PAY_TYPE = WECHATPAY_CODE;
    }


    private void initListener() {
        alipayRb.setOnCheckedChangeListener((compoundButton, b) -> {
            alipayRb.setBackgroundResource(b ? R.mipmap.nike_select : R.mipmap.nike_unselect);
            checkRb(b ? ALIPAY_CODE : WECHATPAY_CODE);
        });
        wechatRb.setOnCheckedChangeListener((compoundButton, b) -> {
            wechatRb.setBackgroundResource(b ? R.mipmap.nike_select : R.mipmap.nike_unselect);
            checkRb(!b ? ALIPAY_CODE : WECHATPAY_CODE);
        });
        alipayLl.setOnClickListener(radioButtonClickListener);
        wechatLl.setOnClickListener(radioButtonClickListener);
        payTv.setOnClickListener(view -> {
            if (defaultMoney == null || defaultMoney.equals("") || defaultMoney.equals("0"))
                showMessage("充值金额不能为空");
            else if (Integer.parseInt(defaultMoney) < 50) {
                UiUtils.makeText(this, "充值金额不能小于50元！");
                return;
            } else if (PAY_TYPE == WECHATPAY_CODE)
//                  微信app支付
//                mPresenter.wechatPay(money);

//                  微信h5支付
                mPresenter.wechatH5Pay(defaultMoney);
//               mPresenter.getSwiftPassPay(money);

            else if (PAY_TYPE == ALIPAY_CODE) {


//                  支付宝app支付
//                mPresenter.aliPay(money);

//                ali h5支付
                mPresenter.aliH5Pay(defaultMoney);

//                  ali h5扫码支付
//                Intent intent = new Intent(this, WebViewActivity.class);
//                intent.putExtra("price", money);
//                startActivity(intent);
            }
        });
    }


    private View.OnClickListener radioButtonClickListener = view -> {
        switch (view.getId()) {
            case R.id.recharge_window_alipay_ll:
                PAY_TYPE = ALIPAY_CODE;
                break;
            case R.id.recharge_window_wechat_ll:
                PAY_TYPE = WECHATPAY_CODE;
                break;
            case R.id.recharge_union_rb:
                PAY_TYPE = UNIONPAY_CODE;
                break;
        }
        checkRb(PAY_TYPE);
    };

    /**
     * 弹窗的单选按钮方法
     *
     * @param k 传入选择项 （微信，支付宝，银联）
     */
    private void checkRb(int k) {
        alipayRb.setChecked(k == ALIPAY_CODE);
        wechatRb.setChecked(!alipayRb.isChecked());
        PAY_TYPE = k;
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
        finish();
    }

    @Override
    public void setData(Object json) {
        if (json == null) {
            moneyTv.setText(SaveUserInfo.getCoin());
            return;
        } else if (json instanceof Message) {
            Message message = (Message) json;
            int what = message.what;
            //微信支付
            if (what == 2) {
                WechatPayBean.DataBean wechatBean = (WechatPayBean.DataBean) message.obj;
                new PayUtils().payWechat(wechatBean);
                //支付宝支付
            } else if (what == 3) {
                AliPayBean.DataBean wechatBean = (AliPayBean.DataBean) message.obj;
                try {
                    String s = new String(Base64.decode(wechatBean.getOrderInfo()), "UTF-8");
                    new PayUtils().payMoney(s, this, true);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else if (what == 4) {
                //微信H5支付
                WechatH5PayBean.DataBean wechath5paybean = (WechatH5PayBean.DataBean) message.obj;
                orderNUmber = wechath5paybean.getPay_order_number();
                Timber.d(wechath5paybean.getPay_url());
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(wechath5paybean.getPay_url());
                intent.setData(content_url);
                startActivityForResult(intent, WECHAT_PAY_H5_CODE);

//                Intent intent = new Intent(RechargeActivity.this, WebViewActivity.class);
//                intent.putExtra("pay_url", wechath5paybean.getPay_url());
//
//                startActivityForResult(intent, PAY_H5_CODE);
            } else if (what == 5) {

                AliH5PayBean.DataBean alih5paybean = (AliH5PayBean.DataBean) message.obj;
                orderNUmber = alih5paybean.getPay_order_number();
                Timber.d(alih5paybean.getPay_url());
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(alih5paybean.getPay_url());
                intent.setData(content_url);
                startActivityForResult(intent, ALI_PAY_H5_CODE);

//                Intent intent = new Intent(RechargeActivity.this, WebViewActivity.class);
//                intent.putExtra("pay_url", alih5paybean.getPay_url());
//                startActivityForResult(intent, PAY_H5_CODE);


            }
//            Message message = (Message) json;
//            token = (String) message.obj;
//            RequestMsg msg = new RequestMsg();
//            msg.setTokenId(token);
//            msg.setTradeType(MainApplication.WX_APP_TYPE);
//            msg.setAppId(WEApplication.WX_APPID);
//            PayPlugin.unifiedAppPay(RechargeActivity.this, msg);
//            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0x001) {
            startActivity(new Intent(this, PaySuccessActivity.class));
        } else if (requestCode == ALI_PAY_H5_CODE) {
//          支付完成刷新积分数据
            mPresenter.getUserCoin();
            H5PayResult(orderNUmber);
        } else if (requestCode == WECHAT_PAY_H5_CODE) {
            mPresenter.getUserCoin();
            H5PayResult(orderNUmber);
        }
    }


    private void H5PayResult(String payOrderNumber) {
        AppComponent appComponent = ((App) getApplicationContext()).getAppComponent();
        Map<String, String> map = new HashMap<>();
        map.put("pay_order_number", payOrderNumber);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        if (appComponent == null) {
            return;
        }
        appComponent.repositoryManager().obtainRetrofitService(UserService.class).GetPayMsg(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<BaseBean>(appComponent.rxErrorHandler()) {
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull BaseBean baseBean) {
                        ToastUtil.showToast(RechargeActivity.this, baseBean.getMsg());


                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        orderNUmber = "";
                    }
                });
    }


    @Override
    public void chooseItem(int position, String awardStr) {
        int money = 0;
        if (awardStr == null || awardStr.equals("")) {
            awardTv.setText("");
            expTv.setText("");
        } else {
            if (awardStr.contains("(")) {
                String substring = awardStr.substring(0, awardStr.indexOf("("));
                money = Integer.parseInt(substring);
            } else {
                money = Integer.parseInt(awardStr);
            }

            String award = null;
            if (money >= 200 && money < 500) {
                award = money * (105) + "";
            } else if (money >= 500 && money < 2000) {
                award = money * (108) + "";
            } else if (money >= 2000) {
                award = money * (110) + "";
            } else {
                award = money * (100) + "";
            }
            awardTv.setText(award);

            String exp = null;
            if (money >= 200 && money < 500) {
                exp = money * (105) + "";
            } else if (money >= 500 && money < 2000) {
                exp = money * (108) + "";
            } else if (money >= 2000) {
                exp = money * (110) + "";
            } else {
                exp = money * (100) + "";
            }
            expTv.setText(exp + "0");
        }
        defaultMoney = money + "";

    }

    private void initList() {
        if (strList != null) {
            strList.add("50");
            strList.add("100");
            strList.add("200(+5%)");
            strList.add("500(+8%)");
            strList.add("2000(+10%)");
            strList.add("");
        }
    }
}

