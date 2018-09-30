package com.lwd.qjtv.mvp.ui.message;

import android.os.Parcel;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.imlib.MessageTag;

@MessageTag(value = "RC:LoginMsg", flag = MessageTag.STATUS)
public class LoginMessage extends ZKBaseMessage {
    private String userName;


    public LoginMessage(String userName) {
        this.userName = userName;
    }

    public LoginMessage(byte[] data) {
        String jsonStr = null;
        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            if (jsonObj.has("userName"))
                userName = jsonObj.optString("userName");
            readUserInfo(jsonObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {
            if (!TextUtils.isEmpty(userName)) {
                jsonObj.putOpt("userName", userName);
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
        dest.writeString(userName);
    }

    private LoginMessage(Parcel in) {
        readUserInfo(in);
        userName = in.readString();
    }

    public static final Creator<LoginMessage> CREATOR = new Creator<LoginMessage>() {
        @Override
        public LoginMessage createFromParcel(Parcel source) {
            return new LoginMessage(source);
        }

        @Override
        public LoginMessage[] newArray(int size) {
            return new LoginMessage[size];
        }
    };


}