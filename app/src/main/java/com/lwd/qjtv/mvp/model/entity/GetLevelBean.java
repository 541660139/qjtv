package com.lwd.qjtv.mvp.model.entity;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/29.
 */

public class GetLevelBean {


    /**
     * msg : success
     * status : 1
     * data : {"id":"3","memberLevel":"2","experienceValue":"7350","b_coin":"96000","experienceValueMax":"100000000","gift_is_new":0}
     */

    private String msg;
    private String status;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3
         * memberLevel : 2
         * experienceValue : 7350
         * b_coin : 96000
         * experienceValueMax : 100000000
         * gift_is_new : 0
         */

        private String id;
        private String memberLevel;
        private String experienceValue;
        private String b_coin;
        private String experienceValueMax;
        private int user_type;
        private String yq_link;
        private int gift_is_new;
        private String on_off;

        public String getOn_off() {
            return on_off;
        }

        public void setOn_off(String on_off) {
            this.on_off = on_off;
        }

        private String kf_qq;
        public String getKf_qq() {
            return kf_qq;
        }

        public void setKf_qq(String kf_qq) {
            this.kf_qq = kf_qq;
        }

        public int getUser_type() {
            return user_type;
        }

        public void setUser_type(int user_type) {
            this.user_type = user_type;
        }

        public String getYq_link() {
            return yq_link;
        }

        public void setYq_link(String yq_link) {
            this.yq_link = yq_link;
        }



        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMemberLevel() {
            return memberLevel;
        }

        public void setMemberLevel(String memberLevel) {
            this.memberLevel = memberLevel;
        }

        public String getExperienceValue() {
            return experienceValue;
        }

        public void setExperienceValue(String experienceValue) {
            this.experienceValue = experienceValue;
        }

        public String getB_coin() {
            return b_coin;
        }

        public void setB_coin(String b_coin) {
            this.b_coin = b_coin;
        }

        public String getExperienceValueMax() {
            return experienceValueMax;
        }

        public void setExperienceValueMax(String experienceValueMax) {
            this.experienceValueMax = experienceValueMax;
        }

        public int getGift_is_new() {
            return gift_is_new;
        }

        public void setGift_is_new(int gift_is_new) {
            this.gift_is_new = gift_is_new;
        }
    }
}
