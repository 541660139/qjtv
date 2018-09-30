package com.lwd.qjtv.mvp.ui.message;

import android.os.Parcel;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.imlib.MessageTag;

@MessageTag(value = "SELFRC:PushMsg", flag = MessageTag.STATUS)
public class RCServerPushMessage extends ZKBaseMessage {

    private String status;
    private String playUrl;
    private String origin;
    private String gonggao;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public String getGonggao() {
        return gonggao;
    }

    public void setGonggao(String gonggao) {
        this.gonggao = gonggao;
    }

    public RCServerPushMessage(String status, String playUrl, String origin,String gonggao) {
        this.status = status;
        this.playUrl = playUrl;
        this.origin = origin;
        this.gonggao = gonggao;
    }

    public RCServerPushMessage(byte[] data) {
        String jsonStr = null;
        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            if (jsonObj.has("status"))
                status = jsonObj.optString("status");
            if (jsonObj.has("playUrl"))
                playUrl = jsonObj.optString("playUrl");
            if (jsonObj.has("origin"))
                origin = jsonObj.optString("origin");
            if (jsonObj.has("gonggao"))
                gonggao = jsonObj.optString("gonggao");
            readUserInfo(jsonObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {
            if (!TextUtils.isEmpty(status)) {
                jsonObj.putOpt("status", status);
            }
            if (!TextUtils.isEmpty(playUrl)) {
                jsonObj.putOpt("playUrl", playUrl);
            }
            if (!TextUtils.isEmpty(origin)) {
                jsonObj.putOpt("origin", origin);
            }
            if (!TextUtils.isEmpty(gonggao)) {
                jsonObj.putOpt("gonggao", gonggao);
            }
            writeUserInfo(jsonObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        writeUserInfo(dest);
        dest.writeString(status);
        dest.writeString(playUrl);
        dest.writeString(origin);
        dest.writeString(gonggao);
    }

    private RCServerPushMessage(Parcel in) {
        readUserInfo(in);
        status = in.readString();
        playUrl = in.readString();
        origin = in.readString();
        gonggao = in.readString();
    }

    public static final Creator<RCServerPushMessage> CREATOR = new Creator<RCServerPushMessage>() {
        @Override
        public RCServerPushMessage createFromParcel(Parcel source) {
            return new RCServerPushMessage(source);
        }

        @Override
        public RCServerPushMessage[] newArray(int size) {
            return new RCServerPushMessage[size];
        }
    };


}