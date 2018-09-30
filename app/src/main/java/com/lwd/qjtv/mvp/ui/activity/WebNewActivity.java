package com.lwd.qjtv.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.delegate.IActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.ClickMoreUtils;
import com.lwd.qjtv.app.utils.ConfigUtils;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.JavaScriptBridge;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.app.utils.SoftInputUtils;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.CommentResultBean;
import com.lwd.qjtv.view.EmojiBoard;
import com.lwd.qjtv.view.ZQRefreshLayout;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

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

public class WebNewActivity extends BaseActivity implements IActivity {

    private TextView closeTv;
    private ImageView backIv;
    private WebView webView;
    private ProgressBar progressBar;
    private ZQRefreshLayout zqRefreshLayout;
    private String url;
    @BindView(R.id.fragment_comment_message_bottom_ll)
    LinearLayout msgBottomLl;
    @BindView(R.id.input_emoji_board)
    EmojiBoard emojiBoard;
    @BindView(R.id.fragment_comment_message_emoj_iv)
    ImageView emojIv;
    @BindView(R.id.fragment_comment_message_edt)
    EditText messageEdt;
    @BindView(R.id.fragment_comment_message_send_tv)
    TextView sendTv;
    @BindView(R.id.activity_web_title_tv)
    TextView titleTv;
    @BindView(R.id.activity_web_my_bbs_tv)
    TextView myBbsTv;
    private boolean is_bbs_details;
    //    private String from_uid;
    private String mainId;
    private String card_id;
    private String comment_id;
    private String to_uid;
    private String username;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView() {
        return R.layout.activity_web;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null)
            webView.reload();
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            url = getIntent().getStringExtra("url");
            is_bbs_details = getIntent().hasExtra("is_bbs_details");
        }
        if (is_bbs_details) {
            msgBottomLl.setVisibility(View.VISIBLE);
//            from_uid = getIntent().getStringExtra("from_uid");
            mainId = getIntent().getStringExtra("main_id");
            card_id = getIntent().getStringExtra("card_id");
        }
        initViews();
        initWebView();
        initListener();
        if (is_bbs_details)
            initCommentListener();
        initDatas();
    }

    private void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        // 从assets目录下面的加载html
        webView.addJavascriptInterface(new JavaScriptBridge(this), "android");
        setWebSetting();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @SuppressWarnings("deprecation")
    public void setWebSetting() {
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        WebSettings webSettings = webView.getSettings();
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        webSettings.setDatabaseEnabled(true);
//        webSettings.setAppCacheEnabled(true);
//        webSettings.setAppCacheMaxSize(Integer.MAX_VALUE);
//        webSettings.setAllowFileAccess(true);
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setDefaultTextEncodingName("GBK");
//        webSettings.setUseWideViewPort(true);
//        if (Build.VERSION.SDK_INT >= 21) {
//            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
//        webSettings.setUseWideViewPort(true);
//        //新加
//        webSettings.setLoadsImagesAutomatically(true);//支持自动加载图片

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            webSettings.setAllowFileAccessFromFileURLs(true);
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            webSettings.setAllowUniversalAccessFromFileURLs(true);
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
        //新加
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);//支持缩放
        webSettings.setLoadsImagesAutomatically(true);//支持自动加载图片
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//禁止横向滑动
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setAllowFileAccess(true);

        webSettings.setDefaultTextEncodingName("UTF-8");//设置编码格式


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //####

        webSettings.setDefaultTextEncodingName("GBK");
//        webSettings.setUseWideViewPort(true);
        // TODO: 2017/11/13
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);// web内容强制满屏

        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setUseWideViewPort(true);

        webView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String title = (webView == null || webView.getTitle() == null) ? "" : webView.getTitle().contains(".com") ? "网页无法打开" : webView.getTitle().toString();
                if (titleTv != null)
                    titleTv.setText(title);
                if (title.equals("讨论区"))
                    myBbsTv.setVisibility(View.VISIBLE);
            }
        });
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
//                    progressBar.setVisibility(View.INVISIBLE);


                    zqRefreshLayout.setRefreshing(false);
                } else {
                    if (View.INVISIBLE == progressBar.getVisibility()) {
//                        progressBar.setVisibility(View.VISIBLE);
                    }
//                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        myBbsTv.setOnClickListener(v -> startActivity(new Intent(this, MyCommunityActivity.class)));
    }

    private void initCommentListener() {
        sendTv.setOnClickListener(view -> {
            String trim = messageEdt.getText().toString().trim();
            if (messageEdt.getText().toString() == null || trim.length() == 0 || "".equals(messageEdt.getText().toString())) {
                UiUtils.SnackbarText("评论不能为空~");
                return;
            }
            if (!SaveUserInfo.getLogin()) {
                Intent intent = new Intent(WebNewActivity.this, LoginActivity.class);
                startActivity(intent);
                return;
            }
            try {
                SoftInputUtils.hideSoftInput(getWindow().getDecorView(), this);
                emojiBoard.setVisibility(View.GONE);
                String encode = URLEncoder.encode(trim, "utf-8");
                sendComment(encode);
                messageEdt.setText("");

                messageEdt.setHint("说点什么吧~");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        messageEdt.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (ClickMoreUtils.isFastClick()) {
                return false;
            }
            if (i == EditorInfo.IME_ACTION_SEND || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                String trim = messageEdt.getText().toString().trim();
                if (messageEdt.getText().toString() == null || trim.length() == 0 || "".equals(messageEdt.getText().toString())) {
                    UiUtils.SnackbarText("评论不能为空~");
                    return false;
                }
                if (!SaveUserInfo.getLogin()) {
                    Intent intent = new Intent(WebNewActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return false;
                }
                try {
                    String encode = URLEncoder.encode(trim, "utf-8");
                    SoftInputUtils.hideSoftInput(getWindow().getDecorView(), this);
                    // TODO: 2018/2/3
                    sendComment(encode);
//                    mPresenter.addMessage(videoId, videoType, encode);
                    messageEdt.setText("");
                    messageEdt.setHint("说点什么吧~");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return true;
            }
            return false;
        });
        emojIv.setOnClickListener(view -> {
            SoftInputUtils.hideSoftInput(getWindow().getDecorView(), this);
            emojiBoard.setVisibility(View.VISIBLE);
//            handler.sendEmptyMessageDelayed(1, 80);
//            emojiBoard.setVisibility(emojiBoard.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
//            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
//            emojIv.setSelected(emojiBoard.getVisibility() == View.VISIBLE);
        });
        emojiBoard.setItemClickListener(code -> {
            if (code.equals("/DEL")) {
                messageEdt.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            } else {
                messageEdt.getText().insert(messageEdt.getSelectionStart(), code);
            }
        });
        emojiBoard.setOnFocusChangeListener((view, b) -> {
            if (b) {
//                emojiBoard.setVisibility(View.VISIBLE);
            } else {
                emojiBoard.setVisibility(View.GONE);
            }
        });
//        webView.setOnTouchListener((v, event) -> {
//            emojiBoard.setVisibility(View.GONE);
//            SoftInputUtils.hideSoftInput(getWindow().getDecorView(), this);
//            return true;
//        });
    }

    public void sendComment(String msg) {
        Map<String, String> map = new HashMap<>();
        map.put("from_uid", SaveUserInfo.getUid());
        map.put("main_id", mainId);
        map.put("card_id", card_id);
        if (comment_id != null)
            map.put("comment_id", comment_id);
        if (to_uid != null)
            map.put("to_uid", to_uid);
        map.put("content", msg);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));


        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        ((App) getApplicationContext()).getAppComponent().repositoryManager().obtainRetrofitService(UserService.class)
                .commentBBSDetails(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<CommentResultBean>(((App) getApplicationContext()).getAppComponent().rxErrorHandler()) {
                    @Override
                    public void onNext(@NonNull CommentResultBean baseBean) {
                        UiUtils.SnackbarText(baseBean.getMsg());
                        if (webView != null) {
                            webView.reload();
                            comment_id = null;
                            to_uid = null;

                        }
                    }
                });
    }

    public void setCopy(String comment_id, String to_uid, String username) {
        this.comment_id = comment_id;
        this.to_uid = to_uid;
        this.username = username;
        messageEdt.setHint("回复 " + username + ":");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.WB_TO_LOGIN && resultCode == Constant.LOGIN_TO_WB) {
            String url = webView.getUrl();
            if (url.contains("http://a.langweida.cn/index.php?tp=mobile/bbs_card_info")) {
                String replace = url.replace("http://a.langweida.cn/index.php?tp=mobile/bbs_card_info", "");
                Log.d("onActivityResult", "url: " + url);
                Map<String, String> stringStringMap = ConfigUtils.splitHomeWorkItemParams(replace);
                stringStringMap.put("from_uid", SaveUserInfo.getUid());
                stringStringMap.replace("uid", SaveUserInfo.getUid());

                Log.d("onActivityResult", "stringStringMap: " + stringStringMap.toString());
                String url1 = ConfigUtils.getUrl(stringStringMap);
                Log.d("onActivityResult", "url1: " + url1);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         *要执行的操作
                         */
                        webView.loadUrl(url1);

                    }
                }, 100);

            }
        }
    }
}

