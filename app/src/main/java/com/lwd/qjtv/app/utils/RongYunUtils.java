package com.lwd.qjtv.app.utils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;


import com.lwd.qjtv.mvp.ui.message.BaseMsgView;
import com.lwd.qjtv.mvp.ui.message.GiftMessage;
import com.lwd.qjtv.mvp.ui.message.InfoMsgView;
import com.lwd.qjtv.mvp.ui.message.RCServerPushMessage;
import com.lwd.qjtv.mvp.ui.message.TextMsgView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import io.rong.imlib.AnnotationNotFoundException;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.ChatRoomInfo;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.InformationNotificationMessage;
import io.rong.message.TextMessage;

/**
 * LiveKit是融云直播聊天室Demo对IMLib库的接口封装类。目的是在IMLib库众多通用接口中，提炼出与直播聊天室应用相关的常用接口，
 * 方便开发者了解IMLib库的调用流程，降低学习成本。同时也开发者可以此为基础扩展，并快速移植到自己的应用中去。
 * <p/>
 * <strong>注意：</strong>此种封装并不是集成IMLib库的唯一方法，开发者可以根据自身需求添加修改，或者直接使用IMLib接口。
 */

public class RongYunUtils {

    /**
     * 事件代码
     */
    public static final int MESSAGE_ARRIVED = 5;
//    public static final int MESSAGE_ARRIVED = 0;
    public static final int MESSAGE_SENT = 1;
    public static final int MESSAGE_SYSTEM = 2;
    public static final int MESSAGE_ONLINE_COUNT = 3;//在线人数
    public static final int MESSAGE_LOGIN = 4;//在线人数

    /**
     * 事件错误代码
     */
    public static final int MESSAGE_SEND_ERROR = -1;

    /**
     * 事件监听者列表
     */
    private static ArrayList<Handler> eventHandlerList = new ArrayList<>();

    /**
     * 当前登录用户id
     */
    private static UserInfo currentUser;

    /**
     * 当前登录用户等级
     */
    private static int currentUserLevel;

    /**
     * 当前聊天室房间id
     */
    private static String currentRoomId;

    /**
     * 消息接收监听者
     */
    private static RongIMClient.OnReceiveMessageListener onReceiveMessageListener = new RongIMClient.OnReceiveMessageListener() {
        @Override
        public boolean onReceived(Message message, int i) {
            handleEvent(MESSAGE_ARRIVED, message.getContent());
            return false;
        }
    };

    /**
     * 初始化方法，在整个应用程序全局只需要调用一次，建议在Application 继承类中调用。
     * <p/>
     * <strong>注意：</strong>其余方法都需要在这之后调用。
     *
     * @param context Application类的Context
     * @param appKey  融云注册应用的AppKey
     */
    public static void init(Context context, String appKey) {
        RongIMClient.init(context, appKey);
        EmojiManager.init(context);
        RongIMClient.setOnReceiveMessageListener(onReceiveMessageListener);
        registerMessageType(GiftMessage.class);
        registerMessageType(RCServerPushMessage.class);
        registerMessageView(TextMessage.class, TextMsgView.class);
        registerMessageView(InformationNotificationMessage.class, InfoMsgView.class);
    }

    /**
     * 注册自定义消息。
     *
     * @param msgType 自定义消息类型
     */
    public static void registerMessageType(Class<? extends MessageContent> msgType) {
        try {
            RongIMClient.registerMessageType(msgType);
        } catch (AnnotationNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置当前登录用户，通常由注册生成，通过应用服务器来返回。
     *
     * @param context context
     */
    public static void setCurrentUser(Context context) {
//        String id = String.valueOf(UserHelper.getUserId(context));
//        if (!TextUtils.isEmpty(id)) {
//            currentUser = new_pic UserInfo(id, UserHelper.getUserName(context),
//                    Uri.parse(UserHelper.getUserAvatar(context)));
//        }
//        currentUserLevel = UserHelper.getUserLevel(context);
    }

    /**
     * 设置当前登录用户，通常由注册生成，通过应用服务器来返回。
     *
     * @param level level
     */
    public static void setCurrentUserLevel(int level) {
        currentUserLevel = level;
    }

    /**
     * 获得当前登录用户。
     *
     * @return
     */
    public static UserInfo getCurrentUser() {
        return currentUser;
    }

    /**
     * 连接融云服务器，在整个应用程序全局，只需要调用一次，需在 {@link #init(Context, String)} 之后调用。
     * </p>
     * 如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。
     *
     * @param token    从服务端获取的用户身份令牌（Token）
     * @param callback 连接回调
     */
    public static void connect(String token, final RongIMClient.ConnectCallback callback) {
        RongIMClient.connect(token, callback);
    }

    /**
     * 断开与融云服务器的连接，并且不再接收 Push 消息。
     */
    public static void logout() {
        RongIMClient.getInstance().logout();
    }

    /**
     * 加入聊天室。如果聊天室不存在，sdk 会创建聊天室并加入，如果已存在，则直接加入。加入聊天室时，可以选择拉取聊天室消息数目。
     *
     * @param roomId          聊天室 Id
     * @param defMessageCount 默认开始时拉取的历史记录条数
     * @param callback        状态回调
     */
    public static void joinChatRoom(String roomId, int defMessageCount, final RongIMClient.OperationCallback callback) {
        currentRoomId = roomId;
        RongIMClient.getInstance().joinChatRoom(currentRoomId, defMessageCount, callback);
    }

    /**
     * 退出聊天室，不在接收其消息。
     */
    public static void quitChatRoom(final RongIMClient.OperationCallback callback) {
        RongIMClient.getInstance().quitChatRoom(currentRoomId, callback);
    }

    /**
     * 向当前聊天室发送消息。
     * </p>
     * <strong>注意：</strong>此函数为异步函数，发送结果将通过handler事件返回。
     *
     * @param msgContent 消息对象
     */
    public static void sendMessage(final MessageContent msgContent) {
//        currentUser = SaveUserInfo.getUserInfo();
//        if (currentUser == null) {
//            currentUser = SaveUserInfo.getUserInfo();
//            throw new RuntimeException("currentUser should not be null.");
//        }
//        if (msgContent instanceof ZKBaseMessage) {
        if (false) {
//            ((ZKBaseMessage) msgContent).setLevel(String.valueOf(currentUserLevel));
        } else if (msgContent instanceof TextMessage) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.putOpt("level", currentUserLevel);
                ((TextMessage) msgContent).setExtra(SaveUserInfo.getLevel() + "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        msgContent.setUserInfo(currentUser);
        Message msg = Message.obtain(currentRoomId, Conversation.ConversationType.CHATROOM, msgContent);
        RongIMClient.getInstance().sendMessage(msg, null, null, new RongIMClient.SendMessageCallback() {
            @Override
            public void onSuccess(Integer integer) {
                handleEvent(MESSAGE_SENT, msgContent);
                Log.d("LiveChatFragment", "RongYunUtilssendonSuccess ");
            }

            @Override
            public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {
                handleEvent(MESSAGE_SEND_ERROR, errorCode.getValue(), 0, msgContent);
                Log.d("LiveChatFragment", "sendonError ");
            }
        }, new RongIMClient.ResultCallback<Message>() {
            @Override
            public void onSuccess(Message message) {
                Log.d("LiveChatFragment", "RongYunUtilsResultonSuccess ");
            }

            @Override
            public void onError(RongIMClient.ErrorCode e) {
                Log.d("LiveChatFragment", "ResultonError ");
            }
        });
    }

    /**
     * 添加IMLib 事件接收者。
     *
     * @param handler
     */
    public static void addEventHandler(Handler handler) {
        if (!eventHandlerList.contains(handler)) {
            eventHandlerList.add(handler);
        }
    }

    /**
     * 移除IMLib 事件接收者。
     *
     * @param handler
     */
    public static void removeEventHandler(Handler handler) {
        eventHandlerList.remove(handler);
    }

    private static void handleEvent(int what) {
        for (Handler handler : eventHandlerList) {
            android.os.Message m = android.os.Message.obtain();
            m.what = what;
            handler.sendMessage(m);
        }
    }

    public static void handleEvent(int what, Object obj) {
        for (Handler handler : eventHandlerList) {
            android.os.Message m = android.os.Message.obtain();
            m.what = what;
            m.obj = obj;
            handler.sendMessage(m);
        }
    }

    private static void handleEvent(int what, int arg1, int arg2, Object obj) {
        for (Handler handler : eventHandlerList) {
            android.os.Message m = android.os.Message.obtain();
            m.what = what;
            m.arg1 = arg1;
            m.arg2 = arg2;
            m.obj = obj;
            handler.sendMessage(m);
        }
    }

    /**
     * 连接融云
     *
     * @param apiToken 业务服务器ApiToken
     */
    public static void connect(String apiToken) {
        RongYunUtils.connect(apiToken, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                //token失效，重新获取
                Log.d("rongyun", "onTokenIncorrect: " + apiToken);

            }

            @Override
            public void onSuccess(String s) {
                Log.d("rongyun", "onSuccess: " + apiToken);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.d("rongyun", "onError: " + errorCode.getMessage() + "    " + errorCode.getValue());
            }
        });
    }

    public static void setConnectionStatusListener(RongIMClient.ConnectionStatusListener listener) {
        RongIMClient.setConnectionStatusListener(listener);
    }

    public static void getRoomInfo(final String chatRoomId, final int defMemberCount, final ChatRoomInfo.ChatRoomMemberOrder order, RongIMClient.ResultCallback<ChatRoomInfo> callback) {
        RongIMClient.getInstance().getChatRoomInfo(chatRoomId, defMemberCount, order, callback);
    }

    /**
     * 注册消息展示界面类。
     *
     * @param msgContent 消息类型
     * @param msgView    对应的界面展示类
     */
    public static void registerMessageView(Class<? extends MessageContent> msgContent, Class<? extends BaseMsgView> msgView) {
        msgViewMap.put(msgContent, msgView);
    }


    /**
     * 消息类与消息UI展示类对应表
     */
    private static HashMap<Class<? extends MessageContent>, Class<? extends BaseMsgView>> msgViewMap = new HashMap<>();

    /**
     * 获取注册消息对应的UI展示类。
     *
     * @param msgContent 注册的消息类型
     * @return 对应的UI展示类
     */
    public static Class<? extends BaseMsgView> getRegisterMessageView(Class<? extends MessageContent> msgContent) {
        return msgViewMap.get(msgContent);
    }
}
