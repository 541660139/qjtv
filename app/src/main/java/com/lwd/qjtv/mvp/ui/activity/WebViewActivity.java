package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.delegate.IActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.JavaScriptBridge;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.di.component.DaggerWebViewComponent;
import com.lwd.qjtv.di.module.WebViewModule;
import com.lwd.qjtv.mvp.contract.WebViewContract;
import com.lwd.qjtv.mvp.presenter.WebViewPresenter;
import com.lwd.qjtv.view.ZQRefreshLayout;


import java.util.HashMap;
import java.util.Map;

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

public class WebViewActivity extends BaseActivity<WebViewPresenter> implements WebViewContract.View, IActivity {


    private TextView closeTv;
    private ImageView backIv;
    private WebView webView;
    private ProgressBar progressBar;
    private ZQRefreshLayout zqRefreshLayout;
    private String url;
    private String id;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerWebViewComponent
                .builder()
                .appComponent(appComponent)
                .webViewModule(new WebViewModule(this)) //请将WebViewModule()第一个首字母改为小写
                .build()
                .inject(this);
    }


    @Override
    public int initView() {
        return R.layout.activity_web;
    }


    @Override
    public void initData() {
        initViews();
        getUrl();
        initWebView();
        initListener();
        initDatas();
    }

    private void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        // 从assets目录下面的加载html
        webView.addJavascriptInterface(new JavaScriptBridge(this), "android");
    }

    private void initViews() {
        backIv = (ImageView) findViewById(R.id.iv_back);
        webView = (WebView) findViewById(R.id.activity_web_webview);
        closeTv = (TextView) findViewById(R.id.activity_web_close_tv);
        progressBar = (ProgressBar) findViewById(R.id.activity_web_progressbar);
        zqRefreshLayout = (ZQRefreshLayout) findViewById(R.id.zq_refreshlayout);
    }

    private void initDatas() {
        if (url != null) {
            webView.loadUrl(url);
            zqRefreshLayout.setRefreshing(true);
        }
    }

    private void getUrl() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getExtras() != null && intent.hasExtra("id")) {
//                url = Constant.MALL_DETAILS_URL+intent.getStringExtra("id");
                Map<String, String> map = new HashMap<>();
                id = intent.getStringExtra("id");
                map.put("goods_id", id);
                map.put("appid", Constant.APPID);
                map.put("pt", Constant.PT);
                map.put("ver", WEApplication.getApp_ver());
                map.put("imei", WEApplication.getIMEI());
                map.put("uid", Preference.get(Constant.UID, "0"));
                map.put("t", System.currentTimeMillis() + "");
                map.put("sign", Utils.buildSign(map, Constant.key));
                if (intent.hasExtra("url")) {
                    url = Utils.getUrl(intent.getStringExtra("url"), map);

                } else {
                    url = Utils.getUrl(Constant.MALL_DETAILS_URL, map);

                }
            } else if (intent.getExtras() != null && intent.hasExtra("price")) {
                Map<String, String> map = new HashMap<>();
                String price = intent.getStringExtra("price");
                map.put("price", price);
                map.put("appid", Constant.APPID);
                map.put("pt", Constant.PT);
                map.put("ver", WEApplication.getApp_ver());
                map.put("imei", WEApplication.getIMEI());
                map.put("uid", Preference.get(Constant.UID, "0"));
                map.put("t", System.currentTimeMillis() + "");
                map.put("sign", Utils.buildSign(map, Constant.key));
                url = Utils.getUrl(Constant.ALI_PAY, map);
            } else if (intent.getExtras() != null && intent.hasExtra("pay_url")) {
                url = intent.getStringExtra("pay_url");
            }
        }
    }

    private void initListener() {
        closeTv.setOnClickListener(view -> finish());
        backIv.setOnClickListener(view -> {
            if (webView.canGoBack())
                webView.goBack();
            else
                finish();
        });
        zqRefreshLayout.setOnRefreshListener(() -> webView.reload());
        webView.setOnKeyListener((view, i, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                if (i == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {  //表示按返回键
                    webView.goBack();   //后退
                    //webview.goForward();//前进
                    return true;    //已处理
                }
            }
            return false;
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (newProgress == 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                    zqRefreshLayout.setRefreshing(false);
                } else {
                    if (View.INVISIBLE == progressBar.getVisibility()) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0x001) {
            String addressId = data.getStringExtra("addressId");
            Map<String, String> map = new HashMap<>();
            map.put("goods_id", id);
            map.put("add_id", addressId);
            map.put("appid", Constant.APPID);
            map.put("pt", Constant.PT);
            map.put("ver", WEApplication.getApp_ver());
            map.put("imei", WEApplication.getIMEI());
            map.put("uid", Preference.get(Constant.UID, "0"));
            map.put("t", System.currentTimeMillis() + "");
            map.put("sign", Utils.buildSign(map, Constant.key));
            url = Utils.getUrl("http://slk.3z.cc/index.php?tp=mobile/con_order", map);
            webView.clearHistory();
            webView.loadUrl(url);
        }
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
        UiUtils.makeText(this, message);
//        UiUtils.SnackbarText(message);
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

    }


//    @Override
//    public void OnClickCallback(String url) {
//        if (webView != null) {
//            webView.loadUrl(Utils.getUrl(url, MyHttpUtils.refreshMap()));
//        }
//    }
}


