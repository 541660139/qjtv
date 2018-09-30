package com.lwd.qjtv.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZhengQian on 2017/12/9.
 * E-mail : wwwiiivip@yeah.net
 * If anyelse things , mail to it . I'll be soon to reply .
 */

public class WechatPayBean {

    /**
     * code : 1
     * msg : success
     * data : {"openID":"wxe87d9d69af0a59ae","partnerId":"1460339802","prepayId":"wx20171209135904b5cd204cf30854011388","nonceStr":"kvagAxsAhS90Soif","sign":"381DC4154B733BE6280F0BCAF8011156","package":"Sign=WXPay","OrderNum":"201712091359047311f0dvgxSLK"}
     */

    private String code;
    private String msg;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * openID : wxe87d9d69af0a59ae
         * partnerId : 1460339802
         * prepayId : wx20171209135904b5cd204cf30854011388
         * nonceStr : kvagAxsAhS90Soif
         * sign : 381DC4154B733BE6280F0BCAF8011156
         * package : Sign=WXPay
         * OrderNum : 201712091359047311f0dvgxSLK
         */

        private String openID;
        private String partnerId;
        private String prepayId;
        private String nonceStr;
        private String sign;
        @SerializedName("package")
        private String packageX;
        private String OrderNum;

        public String getOpenID() {
            return openID;
        }

        public void setOpenID(String openID) {
            this.openID = openID;
        }

        public String getPartnerId() {
            return partnerId;
        }

        public void setPartnerId(String partnerId) {
            this.partnerId = partnerId;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getOrderNum() {
            return OrderNum;
        }

        public void setOrderNum(String OrderNum) {
            this.OrderNum = OrderNum;
        }
    }
}
