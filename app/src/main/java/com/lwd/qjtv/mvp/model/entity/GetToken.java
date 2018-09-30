package com.lwd.qjtv.mvp.model.entity;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/15.
 */

public class GetToken {

    /**
     * code : 200
     * userId : 2
     * token : nYXs9/3mGm4Xj6WW7ZwqGH7hE3vanTrhKBXpyeTo7eEJmFV/B4mmdfWhayR3LQV2wGMuNxmfTUmkRQzXzB6X3Q==
     */

    private int code;
    private String userId;
    private String token;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
