package com.lwd.qjtv.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.ClickMoreUtils;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Contact;
import com.lwd.qjtv.app.utils.JavaScriptBridge;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.app.utils.SoftInputUtils;
import com.lwd.qjtv.di.component.DaggerCommuntiyDetailComponent;
import com.lwd.qjtv.di.module.CommuntiyDetailModule;
import com.lwd.qjtv.mvp.contract.CommuntiyDetailContract;
import com.lwd.qjtv.mvp.model.entity.BaseBean;
import com.lwd.qjtv.mvp.model.entity.CommunityHfBean;
import com.lwd.qjtv.mvp.presenter.CommuntiyDetailPresenter;
import com.lwd.qjtv.mvp.ui.adapter.CommunityPostDetailAdapter;
import com.lwd.qjtv.view.EmojiBoard;
import com.lwd.qjtv.view.LoadingPageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class CommuntiyDetailActivity extends BaseActivity<CommuntiyDetailPresenter> implements CommuntiyDetailContract.View, OnRefreshListener, OnLoadmoreListener {


    @BindView(R.id.ll_community_detail)
    LinearLayout ll_community_detail;

    @BindView(R.id.ll_webview)
    LinearLayout ll_webview;


    @BindView(R.id.tv_title_community_detail)
    TextView tv_title_community_detail;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.fragment_comment_message_edt)
    EditText messageEdt;
    @BindView(R.id.coments_smartrefreshlayout)
    SmartRefreshLayout coments_smartrefreshlayout;
    @BindView(R.id.fragment_comment_message_send_tv)
    TextView sendTv;
    @BindView(R.id.fragment_comment_message_emoj_iv)
    ImageView emojIv;
    @BindView(R.id.input_emoji_board)
    EmojiBoard emojiBoard;


    @BindView(R.id.loading_community_detail)
    LoadingPageView loading_community_detail;
    private String post_id;


    private int groupPosition;
    private int childPosition;

    private String replyContent;
    //用于发送评论
    private int replyType;
    private CommunityPostDetailAdapter communityPostDetailAdapter;
    private RelativeLayout.LayoutParams layoutParams;
    private WebView webView;
    private int webviewContentWidth;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCommuntiyDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .communtiyDetailModule(new CommuntiyDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_communtiy_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData() {
        post_id = getIntent().getStringExtra("post_id");
        mPresenter.getCommunityDetail(true, post_id);
        initListener();
        loading_community_detail.startLodingAnim();
        initWebView();
        initRecyclerVIew();
        initSmartrefreshlayout();
    }

    private void initWebView() {
        webView = new WebView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ll_webview.addView(webView, layoutParams);
        initWebViewSetting();
        loadWe();
    }

    private void loadWe() {
        HashMap<String, String> map = new HashMap<>();
        map.put("post_id", post_id);
        map.put("uid", Preference.get(Constant.UID, "0"));
        String webUrl = new Contact(this).getWebUrl("mobile/postcard_detail", map);
        Log.d("webUrl", webUrl);
        webView.loadUrl(webUrl);
    }




    private void initWebViewSetting() {
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        // 从assets目录下面的加载html
        webView.addJavascriptInterface(new JavaScriptBridge(this), "android");

        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        //新加
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setSupportZoom(true);//支持缩放
        webView.getSettings().setLoadsImagesAutomatically(true);//支持自动加载图片
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//禁止横向滑动
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setAllowFileAccess(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webView.getSettings().setAllowFileAccessFromFileURLs(true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.getSettings().setDefaultTextEncodingName("UTF-8");//设置编码格式
        
    }


    private void initSmartrefreshlayout() {
        coments_smartrefreshlayout.setOnRefreshListener(this);
        coments_smartrefreshlayout.setOnLoadmoreListener(this);
    }

    private void initRecyclerVIew() {


        recyclerview.setOnTouchListener(getOnTouchListener());
        UiUtils.configRecycleView(recyclerview, new LinearLayoutManager(CommuntiyDetailActivity.this));
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        coments_smartrefreshlayout.finishRefresh();

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.SnackbarText(message);
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


    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;


            case R.id.fragment_comment_message_emoj_iv:
                SoftInputUtils.hideSoftInput(((Activity) CommuntiyDetailActivity.this).getWindow().getDecorView(), CommuntiyDetailActivity.this);
                emojiBoard.setVisibility(View.VISIBLE);
                break;


            default:
        }

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.getCommunityDetail(true, post_id);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.getCommunityDetail(false, post_id);
    }

    private void initListener() {
        sendTv.setOnClickListener(view -> {
            String trim = messageEdt.getText().toString().trim();
            if (trim.length() == 0 || "".equals(messageEdt.getText().toString())) {
                showMessage("评论不能为空~");
                return;
            }
            try {
                SoftInputUtils.hideSoftInput(getWindow().getDecorView(), CommuntiyDetailActivity.this);
                emojiBoard.setVisibility(View.GONE);
                String encode = URLEncoder.encode(trim, "utf-8");
                if (replyType == 0) {
                    mPresenter.sendCommunitycontent(trim, post_id);
                } else if (replyType == 1) {
                    //回复楼主评论
                    if (communityPostDetailAdapter.get(groupPosition).getReview_id() != null && communityPostDetailAdapter.get(groupPosition).getOne_id() != null) {
                        mPresenter.sendCommunitycontent(trim, post_id, communityPostDetailAdapter.get(groupPosition).getReview_id(), communityPostDetailAdapter.get(groupPosition).getOne_id(), replyType);
                    }
                } else if (replyType == 2) {
                    //回复子评论
                    if (communityPostDetailAdapter.get(groupPosition).getReview_id() != null && communityPostDetailAdapter.get(groupPosition).getReview_reply_list().get(childPosition).getUid() != null) {
                        mPresenter.sendCommunitycontent(trim, post_id, communityPostDetailAdapter.get(groupPosition).getReview_id(), communityPostDetailAdapter.get(groupPosition).getReview_reply_list().get(childPosition).getUid(), replyType);
                    }
                }
                messageEdt.setText("");
                messageEdt.setHint("说点什么吧~");
                replyType = 0;

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
                    showMessage("评论不能为空~");
                    return false;
                }
                try {
                    String encode = URLEncoder.encode(trim, "utf-8");
                    SoftInputUtils.hideSoftInput(((Activity) CommuntiyDetailActivity.this).getWindow().getDecorView(), CommuntiyDetailActivity.this);

                    messageEdt.setText("");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return true;
            }
            return false;
        });
        emojIv.setOnClickListener(view -> {
            SoftInputUtils.hideSoftInput(((Activity) CommuntiyDetailActivity.this).getWindow().getDecorView(), CommuntiyDetailActivity.this);
            emojiBoard.setVisibility(View.VISIBLE);

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
        ll_community_detail.setOnTouchListener((v, event) -> {
            if (emojiBoard != null)
                emojiBoard.setVisibility(View.GONE);
            SoftInputUtils.hideSoftInput(((Activity) CommuntiyDetailActivity.this).getWindow().getDecorView(), CommuntiyDetailActivity.this);
            return true;
        });


    }

    @Override
    public void startLoadMore() {

    }

    @Override
    public void endLoadMore() {
        coments_smartrefreshlayout.finishLoadmore();

    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {

    }

    /**
     * 若软键盘或表情键盘弹起，点击上端空白处应该隐藏输入法键盘
     *
     * @return 会隐藏输入法键盘的触摸事件监听器
     */
    private View.OnTouchListener getOnTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SoftInputUtils.hideSoftInput(((Activity) CommuntiyDetailActivity.this).getWindow().getDecorView(), CommuntiyDetailActivity.this);
                return false;
            }
        };
    }


    @Override
    public void setData(Object o) {
        loading_community_detail.checkData(o);


    }

    @Override
    public void setSendContenData(CommunityHfBean baseBean) {

        communityPostDetailAdapter.get(groupPosition).setReview_reply_list(baseBean.getData());
        //        communityPostDetailAdapter.notifyItemChanged(groupPosition);
        communityPostDetailAdapter.notifyDataSetChanged();

    }

    @Override
    public void setDianZan(BaseBean baseBean) {

    }


    @Override
    public void setAdapter(CommunityPostDetailAdapter adapter) {
        recyclerview.setAdapter(adapter);
        communityPostDetailAdapter = adapter;
        adapter.setmOnItemClickListener(new CommunityPostDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemLikeClickListener(int position) {//评论点赞
                groupPosition = position;
                if (!SaveUserInfo.getLogin()) {
                    UiUtils.SnackbarText("请登录后再操作");
                    return;
                }
//                mPresenter.getLikeClickRequest(uid, communityPostDetailAdapter.get(position).getId(), 2);
            }

            @Override
            public void onItemReplyClickListener(int position) {//主评论回复
                if (!SaveUserInfo.getLogin()) {
                    UiUtils.SnackbarText("请登录后再操作");
                    return;
                }
                showReplySoftInput(position, 0, 1);
            }

            @Override
            public void onChildItemReplyClickListener(int position, int childPosition) {//子评论回复
                if (!SaveUserInfo.getLogin()) {
                    UiUtils.SnackbarText("请登录后再操作");
                    return;
                }
                showReplySoftInput(position, childPosition, 2);
            }

            @Override
            public void onItemWatchClickListener(int position) {
                communityPostDetailAdapter.get(position).setShowAll(true);
                communityPostDetailAdapter.notifyItemChanged(position);
                if (layoutParams == null)
                    layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                recyclerview.setLayoutParams(layoutParams);

            }
        });

    }


    private void showReplySoftInput(int position, int secondPosition, int commentType) {
        groupPosition = position;
        childPosition = secondPosition;
        replyType = commentType;

        messageEdt.setFocusable(true);
        messageEdt.setFocusableInTouchMode(true);
        messageEdt.requestFocus();
        InputMethodManager imm = (InputMethodManager) CommuntiyDetailActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(messageEdt, 0);

        if (replyType == 1) {//回复楼主评论
            messageEdt.setHint("回复 " + communityPostDetailAdapter.get(groupPosition).getUsername() + " 的评论:");
        } else if (replyType == 2) {//回复子评论
            messageEdt.setHint("回复 " + communityPostDetailAdapter.get(groupPosition).getReview_reply_list().get(childPosition).getReply_username() + " 的评论:");
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.WB_TO_LOGIN) {

            HashMap<String, String> map = new HashMap<>();
            map.put("post_id", post_id);
            map.put("uid", Preference.get(Constant.UID, "0"));
            String webUrl = new Contact(this).getWebUrl("mobile/postcard_detail", map);
            webView.clearHistory();
            webView.loadUrl(webUrl);
        }
    }
}
