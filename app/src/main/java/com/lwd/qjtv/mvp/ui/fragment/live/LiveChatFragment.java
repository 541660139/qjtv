package com.lwd.qjtv.mvp.ui.fragment.live;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.lwd.qjtv.R;
import com.lwd.qjtv.app.WEApplication;
import com.lwd.qjtv.app.utils.ChatRoomModel;
import com.lwd.qjtv.app.utils.ClickMoreUtils;
import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.app.utils.Contact;
import com.lwd.qjtv.app.utils.JavaScriptBridge;
import com.lwd.qjtv.app.utils.LiveDanmakuVideoPlayer;
import com.lwd.qjtv.app.utils.Preference;
import com.lwd.qjtv.app.utils.RongYunUtils;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.app.utils.SoftInputUtils;
import com.lwd.qjtv.app.utils.Utils;
import com.lwd.qjtv.di.component.DaggerLiveChatComponent;
import com.lwd.qjtv.di.module.LiveChatModule;
import com.lwd.qjtv.mvp.contract.LiveChatContract;
import com.lwd.qjtv.mvp.model.api.service.UserService;
import com.lwd.qjtv.mvp.model.entity.GetLevelBean;
import com.lwd.qjtv.mvp.model.entity.GiftBean;
import com.lwd.qjtv.mvp.presenter.LiveChatPresenter;
import com.lwd.qjtv.mvp.ui.activity.RechargeActivity;
import com.lwd.qjtv.mvp.ui.adapter.ChatMessageAdapter;
import com.lwd.qjtv.mvp.ui.adapter.GiftViewAdapter;
import com.lwd.qjtv.mvp.ui.message.GiftMessage;
import com.lwd.qjtv.view.EmojiBoard;
import com.lwd.qjtv.view.FengjinDialog;
import com.lwd.qjtv.view.GiftView;
import com.lwd.qjtv.view.gift.GiftLayout;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.jpush.im.android.api.ChatRoomManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.RequestCallback;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.api.BasicCallback;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.message.TextMessage;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.lwd.qjtv.R.style.AnimBottomInOut;

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
 * Created by ZhengQian on 2017/6/6.
 */

public class LiveChatFragment extends BaseFragment<LiveChatPresenter> implements LiveChatContract.View, GiftViewAdapter.OnRecyclerViewItemClickListener<GiftBean>, ChatMessageAdapter.ClickUserName {

    //    @BindView(R.id.chat_listview)
//    ListView chatRecyclerView;
    @BindView(R.id.fragment_live_chat_message_edt)
    EditText messageEdt;
    //    @BindView(R.id.input_gift_board)
    GiftView giftView;
    //    @BindView(R.id.send)
//    Button sendBtn;
    @BindView(R.id.fragment_live_chat_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_live_login_recyclerview)
    RecyclerView loginRecyclerView;
    @BindView(R.id.fragment_live_chat_recharge_iv)
    TextView rechargeIv;
    @BindView(R.id.fragment_live_chat_gift_iv)
    TextView giftTv;
    @BindView(R.id.input_emoji_board)
    EmojiBoard emojiBoard;
    @BindView(R.id.fragment_live_chat_emoji_iv)
    ImageView emojiIv;
    @BindView(R.id.giftView)
    GiftLayout giftLayout;
    //    @BindView(R.id.fragment_video_details_danmakuview)
//    DanmakuView danmakuView;
    @BindView(R.id.fragment_comment_message_bottom_ll)
    LinearLayout msgBottomLl;
    @BindView(R.id.fragment_live_chat_layout_rl)
    RelativeLayout chatRl;
    @BindView(R.id.fragment_live_chat_gonggao_tv)
    TextView gonggaoTv;
    @BindView(R.id.fragmetn_live_chat_webview)
    WebView webView;
    @BindView(R.id.fragment_live_chat_send_tv)
    TextView fragment_live_chat_send_tv;


    //    private LiveVideoFragment liveVideoFragment;
    private ChatMessageAdapter chatListAdapter;
    private ChatMessageAdapter loginAdapter;
    private ChatRoomModel chatRoomModel;
    private String id;
    private DanmakuContext mContext;
    private GiftBean giftBean;
    private LiveDanmakuVideoPlayer danmakuVideoPlayer;
    private GiftMessage giftMessage;
    private View view;
    private int height;
    private int measuredHeight;
    private int measuredWidth;
    private EmojiBoard emojiBoard1;
    int i = 0;
    //聊天室id
    private static long roomId;


    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (webView != null)
                webView.reload();
            /**
             * 轮询的方式，每一秒刷新一次
             * */

            mhandler.removeMessages(1);
            Message message = new Message();
            message.what = 1;
            mhandler.sendMessageDelayed(message, 1000);
        }
    };
    //    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            if (webView != null)
//                webView.reload();
//            switch (msg.what) {
//                //接收消息
//                case RongYunUtils.MESSAGE_ARRIVED: {
//                    Log.d(TAG, "handleMessage: MESSAGE_ARRIVED ");
//                    MessageContent message = (MessageContent) msg.obj;
//                    io.rong.imlib.model.UserInfo userInfo = message.getUserInfo();
//                    if (message instanceof TextMessage) {
//                        final TextMessage content = (TextMessage) message;
//                        chatListAdapter.addTextMsgItem(userInfo, content);
//                        recyclerView.smoothScrollToPosition(chatListAdapter.getItemCount() - 1);
//                        danmakuVideoPlayer.addDanmaku(true, content.getContent(), ((TextMessage) message).getExtra());
//                    } else if (message instanceof GiftMessage) {
//                        Log.d(TAG, "handleMessage: MESSAGE_ARRIVEDGiFt ");
//                        GiftMessage message1 = (GiftMessage) message;
//                        if ("1".equals(message1.getIsOpen())) {
//                            giftLayout.addGift(message1);
//
//                        } else if (SaveUserInfo.getUserName().equals(message1.getGoalUserName())) {
//                            giftLayout.addGift(message1);
//                            showMessage(message1.getUserInfo().getName() + "送给了" + message1.getGoalUserName() + message1.getGiftNumber() + "个" + message1.getGiftName());
//                        }
//                    } else if (message instanceof InformationNotificationMessage) {
//                        InformationNotificationMessage message1 = (InformationNotificationMessage) message;
//                        String name = message1.getMessage();
//                        loginAdapter.addLoginMsgItem(name);
//                        loginRecyclerView.scrollToPosition(loginAdapter.getItemCount() - 1);
//                    }
//                    break;
//                }
//                // 发送消息
//                case RongYunUtils.MESSAGE_SENT: {
//                    if (msg.obj instanceof TextMessage) {
//                        Log.d(TAG, "handleMessage: MESSAGE_SENT ");
//                        TextMessage content = (TextMessage) msg.obj;
//                        content.setExtra(SaveUserInfo.getLevel());
//                        content.setUserInfo(new UserInfo(SaveUserInfo.getUid(), SaveUserInfo.getUserName(), null));
//                        chatListAdapter.addTextMsgItem(SaveUserInfo.getUserInfo(), content);
//                        chatListAdapter.notifyDataSetChanged();
//                        recyclerView.smoothScrollToPosition(chatListAdapter.getItemCount() - 1);
//                        danmakuVideoPlayer.addDanmaku(true, content.getContent(), SaveUserInfo.getLevel());
//                    } else if (msg.obj instanceof GiftMessage) {
//                        //添加礼物
//                        Log.d(TAG, "handleMessage: MESSAGE_SENTGift ");
//                        giftLayout.addGift((GiftMessage) msg.obj);
//                        giftLayout.start();
//                    }
//                    break;
//                }
//                case LiveKit.MESSAGE_SEND_ERROR: {
//                    Log.d(TAG, "MESSAGE_SEND_ERROR ");
//
//                    break;
//                }
//                case MESSAGE_SYSTEM:
//                    Log.d(TAG, "handleMessage: MESSAGE_SYSTEM ");
//
//                    loginAdapter.addSystemMsgItem((String) msg.obj);
//                    loginRecyclerView.scrollToPosition(loginAdapter.getItemCount() - 1);
//                    break;
//                case MESSAGE_LOGIN:
//                    Log.d(TAG, "handleMessage: MESSAGE_LOGIN ");
//                    loginAdapter.addLoginMsgItem((String) msg.obj);
//                    loginRecyclerView.scrollToPosition(loginAdapter.getItemCount() - 1);
//                    break;
//                default:
//            }
////        chatListAdapter.notifyDataSetChanged();
//
//        }
//    };
    private Conversation conv;


    /**
     * 更新消息UI
     */
    public void updateUI(List<cn.jpush.im.android.api.model.Message> messages) {

        for (cn.jpush.im.android.api.model.Message msg : messages) {
            ContentType type = msg.getContentType();

            if (type.equals(ContentType.custom)) {
                CustomContent customContent = (CustomContent) msg.getContent();
                String content = customContent.getStringValue("content");
                String msgType = customContent.getStringValue("messageType");

                Log.e("wangfeng", "消息：" + content);
                if (msgType.equals("1")) {//进入直播间消息
                    loginAdapter.addLoginMsgItem(content);
                    loginAdapter.notifyDataSetChanged();
                    loginRecyclerView.smoothScrollToPosition(loginAdapter.getItemCount() - 1);
                } else if (msgType.equals("2")) {
                    chatListAdapter.addTextMsgItem(msg.getFromUser(), new TextContent(content));
                    chatListAdapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(chatListAdapter.getItemCount() - 1);
                }
            }

        }
    }

    public void sendChatMessage(long roomID) {
        // 发送聊天室消息
        Conversation conv = JMessageClient.getChatRoomConversation(roomID);
        if (null == conv) {
            conv = Conversation.createChatRoomConversation(roomID);
        }
        ChatRoomManager.enterChatRoom(roomID, new RequestCallback<Conversation>() {
            @Override
            public void gotResult(int i, String s, Conversation conversation) {

            }
        });

        String text = messageEdt.getText().toString();
        Map<String, String> customMessageMap = new HashMap<>();
        customMessageMap.put("content", text);
        customMessageMap.put("messageType", "2");
        final cn.jpush.im.android.api.model.Message msg = conv.createSendCustomMessage(customMessageMap);//实际聊天室可以支持所有类型的消息发送
        msg.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (0 == responseCode) {
                    Log.e("wangfeng", responseCode + "," + responseMessage);
                    //清空编辑框内容并隐藏键盘
                    messageEdt.setText("");
                    chatListAdapter.addTextMsgItem(SaveUserInfo.getUserInfo(), new TextContent(text));
                    chatListAdapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(chatListAdapter.getItemCount() - 1);
                } else {
                    Log.e("wangfeng", responseCode + "," + responseMessage + ",消息发送失败");
                }
            }
        });
        JMessageClient.sendMessage(msg);
    }


    public static LiveChatFragment newInstance() {
        LiveChatFragment fragment = new LiveChatFragment();

        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerLiveChatComponent
                .builder()
                .appComponent(appComponent)
                .liveChatModule(new LiveChatModule(this))//请将LiveChatModule()第一个首字母改为小写
                .build()
                .inject(this);
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_live_chat_layout, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initEnterChatRoom();
    }

    @Override
    public void onPause() {
        super.onPause();
        initExitChatRoom(roomId);

    }

    private void initExitChatRoom(long roomId) {
        // 离开聊天室
        cn.jpush.im.android.api.model.UserInfo userInfo = JMessageClient.getMyInfo();
        if (null != userInfo) {
            String userName = userInfo.getUserName();
            if (!TextUtils.isEmpty(userName)) {
                String msg = userName + "退出了房间";
//        loginAdapter.addLoginMsgItem(msg);
//        loginRecyclerView.scrollToPosition(loginAdapter.getItemCount() - 1);
                // 发送进入聊天室消息通知他人
                sendEnterOrExitRoomMessage(roomId, msg);
                ChatRoomManager.leaveChatRoom(roomId, new BasicCallback() {
                    @Override
                    public void gotResult(int responseCode, String responseMessage) {
                        Log.e("wangfeng", "退出房间" + responseMessage);
                    }
                });
            }
        }
    }

    @Override
    public void initData() {

        getParseArguments();
        initWebView();
        if (!Preference.getBoolean(Constant.IS_RELEASE)) {
            giftTv.setVisibility(View.GONE);
            rechargeIv.setVisibility(View.GONE);
        }
        if (!Preference.getBoolean(Constant.ON_OFF)) {
            giftTv.setVisibility(View.GONE);
            rechargeIv.setVisibility(View.GONE);
        }
        danmakuVideoPlayer.setIsTouchWiget(false);

        loginAdapter = new ChatMessageAdapter(getContext());
        loginRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loginRecyclerView.setAdapter(loginAdapter);

        chatListAdapter = new ChatMessageAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(chatListAdapter);
        chatListAdapter.setOnClickNameCallback(this);
        initListener();

//        LiveKit.addEventHandler(handler);
//        充值用户才初始化(去掉限制)
//        if (SaveUserInfo.getUserType()) {
//            startLiveShow();
//        }
//        startLiveShow();


        height = msgBottomLl.getHeight();
    }


    private void initEnterChatRoom() {
        //极光聊天室登陆
        //如果已经登录
        if (SaveUserInfo.getLogin()) {
            JMessageClient.login(SaveUserInfo.getPhone(), "123456", new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    if (i == 0) {
                        Log.e("wangfeng", "登陆成功");
                        ChatRoomManager.enterChatRoom(roomId, new RequestCallback<Conversation>() {
                            @Override
                            public void gotResult(int i, String s, Conversation conversation) {

                                //进入聊天室
                                String msg = "欢迎" + JMessageClient.getMyInfo().getUserName() + "进入直播间";
                                Log.e("wangfeng", msg);
                                loginAdapter.addLoginMsgItem(msg);
                                loginRecyclerView.scrollToPosition(loginAdapter.getItemCount() - 1);

                                // 发送进入聊天室消息通知他人
                                sendEnterOrExitRoomMessage(roomId, msg);


                            }
                        });
                        //更新极光对应用户头像

                    }
                }
            });
        } else {//未登录,以游客方式进入
            // TODO: 2018/8/27
            Log.e("wangfeng", "登陆失败");
        }

    }


    public void sendEnterOrExitRoomMessage(long roomID, String message) {
        // 发送聊天室消息
        Conversation conv = JMessageClient.getChatRoomConversation(roomID);
        if (null == conv) {
            conv = Conversation.createChatRoomConversation(roomID);
        }
        Map<String, String> customMessageMap = new HashMap<>();
        customMessageMap.put("content", message);
        customMessageMap.put("messageType", "1");
        final cn.jpush.im.android.api.model.Message msg = conv.createSendCustomMessage(customMessageMap);//实际聊天室可以支持所有类型的消息发送
        msg.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (0 == responseCode) {
                    Log.e("xiaoyu", responseCode + "," + responseMessage);
                } else {
                    Log.e("xiaoyu", responseCode + "," + responseMessage + ",消息发送失败");
                }
            }
        });
        JMessageClient.sendMessage(msg);
    }


    private void initWebView() {
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // 从assets目录下面的加载html
        webView.addJavascriptInterface(new JavaScriptBridge(getContext()), "android");
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCacheMaxSize(Integer.MAX_VALUE);
        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("GBK");
        webSettings.setUseWideViewPort(true);
        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setUseWideViewPort(true);
        //新加
        webSettings.setLoadsImagesAutomatically(true);//支持自动加载图片

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("match_id", id);
        String webUrl = new Contact(getContext()).getWebUrl(Constant.GUESS_JUJIAN, map);
        Log.d(TAG, "initWebView: webUrl" + webUrl);
//        webView.loadUrl("http://slk.3z.cc/index.php?tp=mobile/bbs_card&appid=0&imei=990007170693706&pt=2&sign=6948aa99950a136f68106c8844ff3fdc&t=1495677100605&uid=1037&ver=1.0.0&id=1&p=1");
        webView.loadUrl(webUrl);
        Message message = new Message();
        message.what = 1;
        mhandler.sendMessageDelayed(message, 1000);
    }

    private void getParseArguments() {
        if (getArguments() != null) {
            id = getArguments().getString("id");
        }

        //暂时测试写死roomId
        roomId = 15017394;
    }


    private void initListener() {
        chatRl.setOnClickListener(view1 -> {
            if (emojiBoard.getVisibility() == View.VISIBLE) {
                emojiBoard.setVisibility(View.GONE);
            }
        });
        messageEdt.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (ClickMoreUtils.isFastClick()) {
                return false;
            }
//            if (!SaveUserInfo.getUserType()) {
//                UiUtils.SnackbarText("请成为充值会员后使用该功能");
//                return false;
//            }
            if (i == EditorInfo.IME_ACTION_SEND || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                String trim = messageEdt.getText().toString().trim();
                if (messageEdt.getText().toString() == null || trim.length() == 0 || "".equals(messageEdt.getText().toString())) {
                    showMessage("消息不能为空~");
                    return false;
                }
//                TextMessage textMessage = new TextMessage(trim);
//                String level = SaveUserInfo.getLevel();
//                textMessage.setExtra(level);
//                chatRoomModel.sendMessage(TextMessage.obtain(trim));
//                LiveKit.sendMessage(TextMessage.obtain(messageEdt.getText().toString().trim()));
//                recyclerView.scrollToPosition(chatListAdapter.getItemCount() - 1);
//                messageEdt.setText("");


                return true;
            }
            return false;
        });

        messageEdt.setOnClickListener(v -> {
            if (messageEdt != null)
                messageEdt.requestFocus();
        });


        messageEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //TODO:
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //TODO:

                String trim = messageEdt.getText().toString().trim();
                if (!TextUtils.isEmpty(trim)) {
                    rechargeIv.setVisibility(View.GONE);
                    giftTv.setVisibility(View.GONE);
                    fragment_live_chat_send_tv.setVisibility(View.VISIBLE);
                } else {
                    rechargeIv.setVisibility(View.VISIBLE);
                    giftTv.setVisibility(View.VISIBLE);
                    fragment_live_chat_send_tv.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //TODO:
            }
        });
        fragment_live_chat_send_tv.setOnClickListener(view -> {
            if (ClickMoreUtils.isFastClick()) {
                return;
            }

//            if (!SaveUserInfo.getUserType()) {
//                UiUtils.SnackbarText("请成为充值会员后使用该功能");
//                return;
//            }
//
//            String trim = messageEdt.getText().toString().trim();
//            if (trim.length() == 0 || "".equals(messageEdt.getText().toString())) {
//                showMessage("消息不能为空~");
//                return;
//            }
//            TextMessage textMessage = new TextMessage(trim);
//            String level = SaveUserInfo.getLevel();
//            textMessage.setExtra(level);
//            chatRoomModel.sendMessage(TextMessage.obtain(trim));
////                LiveKit.sendMessage(TextMessage.obtain(messageEdt.getText().toString().trim()));
////            recyclerView.scrollToPosition(chatListAdapter.getItemCount() - 1);
//            messageEdt.setText("");

            sendChatMessage(roomId);

        });


        emojiBoard.setItemClickListener(code -> {
            if (code.equals("/DEL")) {
                messageEdt.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            } else {
                messageEdt.getText().insert(messageEdt.getSelectionStart(), code);
            }
        });

        rechargeIv.setOnClickListener(view -> startActivity(new Intent(getContext(), RechargeActivity.class)));

        giftTv.setOnClickListener(view -> showGifts());

        emojiIv.setOnClickListener(view -> {
            SoftInputUtils.hideSoftInput(((Activity) getContext()).getWindow().getDecorView(), getContext());
            emojiBoard.setVisibility(View.VISIBLE);
//            emojiHandler.sendEmptyMessageDelayed(1, 80);
        });


        msgBottomLl.setOnClickListener(view -> emojiBoard.setVisibility(View.GONE));
//        sendBtn.setOnClickListener(view -> sendGift());
    }


//    private void startLiveShow() {
//        LiveKit.setCurrentUser(SaveUserInfo.getUserInfo());
//        chatRoomModel = new ChatRoomModel(getContext(), id, handler);
//        chatRoomModel.joinChatRoom();
//    }

    private void setPopConfig(PopupWindow popConfig) {
//        this.setContentView(mDataView);//设置要显示的视图
        popConfig.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popConfig.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出窗体可点击
        popConfig.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xcc000000);
        popConfig.setBackgroundDrawable(dw);
        popConfig.setOutsideTouchable(true);// 设置外部触摸会关闭窗口

        //获取自身的长宽高
        popConfig.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        measuredHeight = popConfig.getContentView().getMeasuredHeight();
        measuredWidth = popConfig.getContentView().getMeasuredWidth();
    }

    public void showUp(PopupWindow popupWindow, View v) {
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        //在控件上方显示
        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, (location[0]) - measuredWidth / 2, location[1] - measuredHeight);
    }


    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onActivityCreated
     * 还没执行,setData里调用presenter的方法时,是会报空的,因为dagger注入是在onActivityCreated
     * 方法中执行的,如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     * @param data
     */

    @Override
    public void setData(Object data) {
        if (data instanceof Integer) {
            if ((int) data == 1) {
                RongYunUtils.sendMessage(giftMessage);
                giftView.setCoin();
            }
        } else
            danmakuVideoPlayer = (LiveDanmakuVideoPlayer) data;
        resetCoin();
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

        UiUtils.makeText(getContext(), message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }
//
//    @Override
//    public boolean handleMessage(android.os.Message msg) {
//        switch (msg.what) {
//            //接收消息
//            case RongYunUtils.MESSAGE_ARRIVED: {
//
//                Log.d(TAG, "handleMessage: MESSAGE_ARRIVED ");
//                MessageContent message = (MessageContent) msg.obj;
//                io.rong.imlib.model.UserInfo userInfo = message.getUserInfo();
//                if (message instanceof TextMessage) {
//                    final TextMessage content = (TextMessage) message;
//                    chatListAdapter.addTextMsgItem(userInfo, content);
//                    recyclerView.smoothScrollToPosition(chatListAdapter.getItemCount() - 1);
//                    danmakuVideoPlayer.addDanmaku(true, content.getContent(), ((TextMessage) message).getExtra());
//                } else if (message instanceof GiftMessage) {
//                    GiftMessage message1 = (GiftMessage) message;
//                    if ("1".equals(message1.getIsOpen())) {
//                        giftLayout.addGift(message1);
//                    } else if (SaveUserInfo.getUserName().equals(message1.getGoalUserName())) {
//                        giftLayout.addGift(message1);
//                        showMessage(message1.getUserInfo().getName() + "送给了" + message1.getGoalUserName() + message1.getGiftNumber() + "个" + message1.getGiftName());
//                    }
//                } else if (message instanceof InformationNotificationMessage) {
//                    InformationNotificationMessage message1 = (InformationNotificationMessage) message;
//                    String name = message1.getMessage();
//                    loginAdapter.addLoginMsgItem(name);
//                    loginRecyclerView.scrollToPosition(loginAdapter.getItemCount() - 1);
//                }
//                break;
//            }
//            // 发送消息
//            case RongYunUtils.MESSAGE_SENT: {
//                if (msg.obj instanceof TextMessage) {
//                    Log.d(TAG, "handleMessage: MESSAGE_SENT ");
//                    TextMessage content = (TextMessage) msg.obj;
//                    content.setExtra(SaveUserInfo.getLevel());
//                    content.setUserInfo(new UserInfo(SaveUserInfo.getUid(), SaveUserInfo.getUserName(), null));
//                    chatListAdapter.addTextMsgItem(SaveUserInfo.getUserInfo(), content);
//                    chatListAdapter.notifyDataSetChanged();
//                    recyclerView.smoothScrollToPosition(chatListAdapter.getItemCount() - 1);
//                    danmakuVideoPlayer.addDanmaku(true, content.getContent(), SaveUserInfo.getLevel());
//                } else if (msg.obj instanceof GiftMessage) {
//                    //添加礼物
//                    giftLayout.addGift((GiftMessage) msg.obj);
//                }
//                break;
//            }
//            case LiveKit.MESSAGE_SEND_ERROR: {
//                Log.d(TAG, "MESSAGE_SEND_ERROR ");
//
//                break;
//            }
//            case MESSAGE_SYSTEM:
//
//
//                loginAdapter.addSystemMsgItem((String) msg.obj);
//                loginRecyclerView.scrollToPosition(loginAdapter.getItemCount() - 1);
//                break;
//            case MESSAGE_LOGIN:
//                Log.d(TAG, "handleMessage: MESSAGE_LOGIN ");
//                loginAdapter.addLoginMsgItem((String) msg.obj);
//                loginRecyclerView.scrollToPosition(loginAdapter.getItemCount() - 1);
//                break;
//            default:
//        }
////        chatListAdapter.notifyDataSetChanged();
//        return false;
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (chatRoomModel != null)
            chatRoomModel.quitChatRoom();
    }


    //显示礼物界面
    private void showGifts() {

        PopupWindow popupWindow = new PopupWindow(getContext());
        giftView = new GiftView(getContext(), null);
        popupWindow.setContentView(giftView);
        popupWindow.setAnimationStyle(AnimBottomInOut);
        setPopConfig(popupWindow);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        giftView.setOnSendClickListener(view -> sendGift());
        giftView.setItemClickListener(this);
        giftView.setCloseListener(view -> {
            giftView.setVisibility(View.GONE);
            msgBottomLl.setVisibility(View.VISIBLE);
            popupWindow.dismiss();
        });

    }


    public void sendGift() {

//        if (!SaveUserInfo.getUserType()) {
//            showMessage("请充值成为会员才能送礼");
//            return;
//        }

        if (giftBean == null) {
            showMessage("当前未选择礼物");
            return;
        }
        String num = giftView.getNum();
        String name = giftView.getName();
        if (num == null || "".equals(num) || "0".equals(num) || "00".equals(num)) {
            num = "1";
            giftView.setNumTv("1");
        }
        if (Integer.parseInt(giftBean.getCoin()) > Integer.parseInt(Preference.get(Constant.COIN, "0"))) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("提醒")
                    .setMessage("当前积分不足，是否前往充值?")
                    .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                    .setPositiveButton("前往充值", (dialog, which) -> getContext().startActivity(new Intent(getContext(), RechargeActivity.class))).create().show();
            return;
        }
        mPresenter.sendGift(giftBean.getId(), name, num);
        giftMessage = new GiftMessage(num, giftBean.getGiftName(), name, giftView.getIsOpen());

    }

    public void resetCoin() {
        String op = "getMemberLevel";
        Map<String, String> map = new HashMap<>();
        map.put("op", op);
        map.put("appid", Constant.APPID);
        map.put("pt", Constant.PT);
        map.put("ver", WEApplication.getApp_ver());
        map.put("imei", WEApplication.getIMEI());
        map.put("uid", Preference.get(Constant.UID, "0"));
        map.put("t", System.currentTimeMillis() + "");
        map.put("sign", Utils.buildSign(map, Constant.key));
        ((App) WEApplication.getContext().getApplicationContext()).getAppComponent().repositoryManager().obtainRetrofitService(UserService.class)
                .getUserInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<GetLevelBean>(((App) WEApplication.getContext().getApplicationContext()).getAppComponent().rxErrorHandler()) {
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull GetLevelBean getLevelBean) {
                        if (getLevelBean == null || !"1".equals(getLevelBean.getStatus()))
                            return;
                        SaveUserInfo.setExperienceMax(getLevelBean.getData().getExperienceValueMax());
                        SaveUserInfo.setExperienceValue(getLevelBean.getData().getExperienceValue());
                        SaveUserInfo.setCoin(getLevelBean.getData().getB_coin());
                    }
                });
    }


    @Override
    public void onItemClick(View view, GiftBean data, int position) {
        giftBean = data;
    }

    @Override
    public void clickName(String name, long uid) {
        //超管
        if (SaveUserInfo.getLevel().equals("20")) {
            showFengJinDialog(name, String.valueOf(uid));
        }
        //普通用户
        else {
            showGifts();
            if (giftView != null)
                giftView.setNameEdt(name);
        }

    }

    //封禁用户弹框
    private void showFengJinDialog(String name, String uid) {
        FengjinDialog fengjinDialog = new FengjinDialog(getContext());
        fengjinDialog.setData(id, name, uid);
        fengjinDialog.setCallBack(message -> {
            TextMessage textMessage = new TextMessage(message);
            String level = SaveUserInfo.getLevel();
            textMessage.setExtra(level);
            chatRoomModel.sendMessage(TextMessage.obtain(message));
            recyclerView.scrollToPosition(chatListAdapter.getItemCount() - 1);
        });
        fengjinDialog.show();
    }


}