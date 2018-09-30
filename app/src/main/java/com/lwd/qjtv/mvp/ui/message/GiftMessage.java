package com.lwd.qjtv.mvp.ui.message;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.imlib.MessageTag;

@MessageTag(value = "RC:GiftMsg", flag = MessageTag.STATUS)
public class GiftMessage extends ZKBaseMessage {

    //    private String gift_id;
    private String giftNumber;
    //    private Integer gift_img;
    private String giftName;
    private String goalUserName;
    private String isOpen;
//    private String sendId;


    public GiftMessage(String giftNumber, String giftName, String goalUserName, String isOpen) {
        this.giftNumber = giftNumber;
        this.giftName = giftName;
        this.goalUserName = goalUserName;
        this.isOpen = isOpen;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getGiftNumber() {
        return giftNumber;
    }

    public void setGiftNumber(String giftNumber) {
        this.giftNumber = giftNumber;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getGoalUserName() {
        return goalUserName;
    }

    public void setGoalUserName(String goalUserName) {
        this.goalUserName = goalUserName;
    }

    public GiftMessage(byte[] data) {
        String jsonStr = null;
        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            if (jsonObj.has("giftNumber"))
                giftNumber = jsonObj.optString("giftNumber");
            if (jsonObj.has("giftName"))
                giftName = jsonObj.optString("giftName");
            if (jsonObj.has("goalUserName"))
                goalUserName = jsonObj.optString("goalUserName");
            if (jsonObj.has("isOpen"))
                isOpen = jsonObj.optString("isOpen");
//            if (jsonObj.has("gift_img"))
//                gift_img = jsonObj.optInt("gift_img");
//            if (jsonObj.has("gift_name"))
//                gift_name = jsonObj.optString("gift_name");
            readUserInfo(jsonObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {
            if (!TextUtils.isEmpty(giftNumber)) {
                jsonObj.putOpt("giftNumber", giftNumber);
            }
            if (!TextUtils.isEmpty(giftName)) {
                jsonObj.putOpt("giftName", giftName);
            }
            if (!TextUtils.isEmpty(goalUserName)) {
                jsonObj.putOpt("goalUserName", goalUserName);
            }
            if (!TextUtils.isEmpty(isOpen)) {
                jsonObj.putOpt("isOpen", isOpen);
            }
//            if (gift_img != 0) {
//                jsonObj.putOpt("gift_img", gift_img);
//            }
//            if (!TextUtils.isEmpty(gift_name)) {
//                jsonObj.putOpt("gift_name", gift_name);
//            }
//            if (!TextUtils.isEmpty(sendId)) {
//                jsonObj.putOpt("sendId", sendId);
//            }
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
        dest.writeString(giftNumber);
        dest.writeString(giftName);
        dest.writeString(goalUserName);
        dest.writeString(isOpen);
//        dest.writeString(gift_name);
//        dest.writeString(sendId);
    }

    private GiftMessage(Parcel in) {
        readUserInfo(in);
        giftNumber = in.readString();
        giftName = in.readString();
//        gift_img = in.readInt();
        goalUserName = in.readString();
        isOpen = in.readString();
//        sendId = in.readString();
    }

    public static final Parcelable.Creator<GiftMessage> CREATOR = new Parcelable.Creator<GiftMessage>() {
        @Override
        public GiftMessage createFromParcel(Parcel source) {
            return new GiftMessage(source);
        }

        @Override
        public GiftMessage[] newArray(int size) {
            return new GiftMessage[size];
        }
    };


}