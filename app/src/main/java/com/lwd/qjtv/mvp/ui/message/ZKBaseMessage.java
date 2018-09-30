package com.lwd.qjtv.mvp.ui.message;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import io.rong.common.ParcelUtils;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;

public abstract class ZKBaseMessage extends MessageContent {
    private String level;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    void readUserInfo(Parcel parcel) {
        this.setLevel(ParcelUtils.readFromParcel(parcel));
        this.setUserInfo(ParcelUtils.readFromParcel(parcel, UserInfo.class));
    }

    void writeUserInfo(Parcel parcel) {
        ParcelUtils.writeToParcel(parcel, this.getLevel());
        ParcelUtils.writeToParcel(parcel, this.getUserInfo());
    }

    void writeUserInfo(JSONObject jsonObj) {
        try {
            jsonObj.putOpt("level", level);
            if (getJSONUserInfo() != null) {
                jsonObj.putOpt("user", getJSONUserInfo());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void readUserInfo(JSONObject jsonObj) {
        if (jsonObj.has("level")) {
            setLevel(jsonObj.optString("level"));
        }
        if (jsonObj.has("user")) {
            setUserInfo(parseJsonToUserInfo(jsonObj.optJSONObject("user")));
        }
    }

}
