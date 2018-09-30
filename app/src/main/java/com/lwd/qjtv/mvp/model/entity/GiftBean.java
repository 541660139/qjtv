package com.lwd.qjtv.mvp.model.entity;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/20.
 */

public class GiftBean {

    private String id;
    private String coin;
    private Integer idRes;
    private String giftName;

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public Integer getIdRes() {
        return idRes;
    }

    public void setIdRes(Integer idRes) {
        this.idRes = idRes;
    }
}
