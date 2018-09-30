package com.lwd.qjtv.mvp.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.delegate.IActivity;
import com.jess.arms.di.component.AppComponent;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.JavaScriptBridge;
import com.lwd.qjtv.app.utils.MyHttpUtils;
import com.lwd.qjtv.app.utils.Utils;
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

public class WebActivity extends BaseActivity implements IActivity {

    private TextView closeTv;
    private ImageView backIv;
    private WebView webView;
    private ProgressBar progressBar;
    private ZQRefreshLayout zqRefreshLayout;
    private String url;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

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

        webView.setWebViewClient(new MyWebViewClient());
    }

//    private void initSwipeRefresh() {
//        zqRefreshLayout.setBottomColor(R.color.home_title, R.color.end_color, R.color.colorAccent, R.color.textColor_alert_button_destructive);
//        zqRefreshLayout.setTopColor(R.color.home_title, R.color.end_color, R.color.colorAccent, R.color.textColor_alert_button_destructive);
//        zqRefreshLayout.setColor(R.color.home_title, R.color.end_color, R.color.colorAccent, R.color.textColor_alert_button_destructive);
//    }


    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            imgReset();//重置webview中img标签的图片大小


        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
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
            if (url.endsWith(".jpg") || url.endsWith(".png")) {

                webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//适应内容大小

            }


            webView.loadUrl(url);

            zqRefreshLayout.setRefreshing(true);
        }
    }

    private void getUrl() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getExtras() != null) {
//                url = Constant.MALL_DETAILS_URL+intent.getStringExtra("id");
                if (getIntent().hasExtra("type")) {
                    Map<String, String> map = new HashMap<>();
                    map.put("type", "1");
                    url = Utils.getUrl(intent.getStringExtra("url"), MyHttpUtils.refreshMap(map));
                } else {
                    String url1 = intent.getStringExtra("url");
                    if (url1.endsWith(".jpg") || url1.endsWith(".png")) {
                        url = url1;
                    } else {
                        url = Utils.getUrl(intent.getStringExtra("url"), MyHttpUtils.refreshMap());
                    }
                    Log.d("line_url", "" + url);
                }
            }
        }
    }

    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    private void imgReset() {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()");
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
}
