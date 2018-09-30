package com.lwd.qjtv.app.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.lwd.qjtv.mvp.ui.activity.LoginActivity;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.ChatRoomInfo;
import io.rong.imlib.model.MessageContent;
import io.rong.message.InformationNotificationMessage;

public class ChatRoomModel {

    private final String mRoomId;//房间id
    private final Handler mHandler;//消息回调
    private Context mContext;
    private int mType = 0;//默认是普通用户
    private RongIMClient.ConnectionStatusListener mListener = (connectionStatus) -> {
        switch (connectionStatus) {
            case CONNECTED:
                joinRoom();
                break;
            case KICKED_OFFLINE_BY_OTHER_CLIENT:


                Log.d("ChatRoomModel_登录","KICKED_OFFLINE_BY_OTHER_CLIENT");
                if (mContext != null) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("isMoreLogin", true);
                    mContext.startActivity(intent);
                }


                handleEvent("账号在其他设备登录！", RongYunUtils.MESSAGE_SYSTEM);
                break;
            case NETWORK_UNAVAILABLE:
            case DISCONNECTED:
                handleEvent("弹幕服务器断开连接！", RongYunUtils.MESSAGE_SYSTEM);
                break;
        }
    };

    public ChatRoomModel(Context context, String roomId, Handler handler) {
        this.mContext = context;
        this.mRoomId = roomId;
        this.mHandler = handler;
    }

    /**
     * 1.获取融云token
     * 2.连接融云
     * 3.加入聊天室
     * 4.注册消息监听handler
     * 5.获取在线人数
     * 6.发送上线消息
     */
    public void joinChatRoom() {
//        joinRoom();
        handleEvent("正在连接弹幕服务器...", RongYunUtils.MESSAGE_SYSTEM);
        RongYunUtils.setConnectionStatusListener(mListener);
        RongIMClient.ConnectionStatusListener.ConnectionStatus status =
                RongIMClient.getInstance().getCurrentConnectionStatus();
        if (status == RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED) {
            joinRoom();
        } else if (status == RongIMClient.ConnectionStatusListener.ConnectionStatus.DISCONNECTED
                || status == RongIMClient.ConnectionStatusListener.ConnectionStatus.TOKEN_INCORRECT
                || status == RongIMClient.ConnectionStatusListener.ConnectionStatus.SERVER_INVALID) {
            RongYunUtils.connect(Preference.get(Constant.API_TOKEN, ""));
        }
    }

    /**
     * 设置用户身份
     */
    public void setUserType(int type) {
        this.mType = type;
    }

    private void joinRoom() {
        RongYunUtils.joinChatRoom(mRoomId == null ? "0" : mRoomId, 0, new RongIMClient.OperationCallback() {
            @Override
            public void onSuccess() {
                RongYunUtils.addEventHandler(mHandler);
                sendLoginMessage(true);
                InformationNotificationMessage informationNotificationMessage = new InformationNotificationMessage("欢迎" + SaveUserInfo.getUserName() + "进入直播间！");
                RongYunUtils.sendMessage(informationNotificationMessage);
//                LogUtils.e(ChatRoomModel.this, "joinChatRoom,onSuccess");
                handleEvent("连接弹幕服务器成功！", RongYunUtils.MESSAGE_SYSTEM);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
//                LogUtils.e(ChatRoomModel.this, "errorCode:" + errorCode);
                handleEvent("连接弹幕服务器失败！", RongYunUtils.MESSAGE_SYSTEM);
                RongYunUtils.connect(Preference.get(Constant.API_TOKEN, ""));
                switch (errorCode) {
                    case RC_NET_CHANNEL_INVALID://socket不存在
                        break;
                }
            }
        });
    }

    /**
     * 发送上下线广播
     *
     * @param isLogin 是否为上线消息
     */
    private void sendLoginMessage(final boolean isLogin) {
        if (isLogin) {
            handleEvent("欢迎" + SaveUserInfo.getUserName() + "进入直播间", RongYunUtils.MESSAGE_LOGIN);
        } else {
            handleEvent(SaveUserInfo.getUserName() + "退出了房间", RongYunUtils.MESSAGE_LOGIN);
        }
    }

    private void quitRoom() {
//        RongYunUtils.quitChatRoom(new RongIMClient.OperationCallback() {
//            @Override
//            public void onSuccess() {
////                LogUtils.i(ChatRoomModel.this, "quitRoom,onSuccess");
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
////                LogUtils.i(ChatRoomModel.this, "quitRoom,onError");
//            }
//        });
        RongYunUtils.removeEventHandler(mHandler);
    }

    /**
     * 1.发送离线广播
     * 2.退出聊天室
     * 3.断开连接
     * 4.注销监听handler
     */
    public void quitChatRoom() {
        quitRoom();
        sendLoginMessage(false);
        RongYunUtils.setConnectionStatusListener(null);
    }


    private void handleEvent(Object obj, int what) {
        android.os.Message m = android.os.Message.obtain();
        m.what = what;
        m.obj = obj;
        mHandler.sendMessage(m);
    }

    //发送消息
    public void sendMessage(MessageContent textMessage) {
        RongYunUtils.sendMessage(textMessage);
    }

    //更新在线人数
    public void updateOnlineCount() {
        RongYunUtils.getRoomInfo(mRoomId, 1, ChatRoomInfo.ChatRoomMemberOrder.RC_CHAT_ROOM_MEMBER_ASC, new RongIMClient.ResultCallback<ChatRoomInfo>() {
            @Override
            public void onSuccess(ChatRoomInfo chatRoomInfo) {
                handleEvent(chatRoomInfo.getTotalMemberCount(), RongYunUtils.MESSAGE_ONLINE_COUNT);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }
}