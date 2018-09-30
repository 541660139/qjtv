package com.lwd.qjtv.mvp.ui.message;

import android.graphics.Color;

/**
 * 聊天信息
 * Created by admin on 2016/9/29.
 */

public class ChatMessage implements IChatMessage {

    public static String MESSAGE_TEXT_SEP = "###";//隔断符
    public static String MESSAGE_COLOR_SEP = "\\|\\|\\|";//隔断符
    private String time;//时间戳
    private String source;//来源
    private int userType;//类型
    private int msgType;//命令

    private String userCount;//用户数量
    private String userLevel;//用户等级
    private String roomId;//房间id
    private long userId;//用户名
    private String userName;//id
    private String userAvatar;//头像
    private String giftId;//礼物id
    private String giftCount;//礼物数量

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getGiftCount() {
        return giftCount;
    }

    public void setGiftCount(String giftCount) {
        this.giftCount = giftCount;
    }

    private String contentText;
    private int textColor;//Android系统颜色格式

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        try {
            this.textColor = Color.parseColor("#" + textColor.substring(2, 8));
        } catch (Exception e) {
            this.textColor = Color.parseColor("#ffffff");
        }
    }

    public static final int CHAT_MESSAGE_USER_TEXT = 0x0000;//消息
    public static final int CHAT_MESSAGE_USER_LOGIN = 0x0001;//上线

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int cmd) {
        this.msgType = cmd;
    }

    public String getUserCount() {
        return userCount;
    }

    public void setUserCount(String userCount) {
        this.userCount = userCount;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    @Override
    public String getContent() {
        return contentText == null ? "" : contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    @Override
    public int getType() {
        return msgType;
    }
}
