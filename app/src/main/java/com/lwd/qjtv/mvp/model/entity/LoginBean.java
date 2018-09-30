package com.lwd.qjtv.mvp.model.entity;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/8.
 */

public class LoginBean {


    /**
     * msg : 登录成功
     * status : 1
     * data : {"uid":"3","username":"sunrain","phone":"18271857105","avatar":"http://img.ttkhj.3z.cc/slk/user_profile/201707/201707220108579595.jpeg","sex":"1","b_coin":"10284","memberLevel":"5","totalLevel":"10","experienceValue":"244621","experienceValueMax":"500000"}
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
         * uid : 3
         * username : sunrain
         * phone : 18271857105
         * avatar : http://img.ttkhj.3z.cc/slk/user_profile/201707/201707220108579595.jpeg
         * sex : 1
         * b_coin : 10284
         * memberLevel : 5
         * totalLevel : 10
         * experienceValue : 244621
         * experienceValueMax : 500000
         * on_off 开关
         */
        private String unamekey;



        private String uid;
        private String username;
        private String phone;
        private String avatar;
        private String sex;
        private String b_coin;
        private String memberLevel;
        private String totalLevel;
        private String experienceValue;
        private String experienceValueMax;
        private String user_type;
        private String yq_link;
        private String on_off;

        public String getOn_off() {
            return on_off;
        }

        public void setOn_off(String on_off) {
            this.on_off = on_off;
        }

        public String getYq_link() {
            return yq_link;
        }

        public void setYq_link(String yq_link) {
            this.yq_link = yq_link;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getB_coin() {
            return b_coin;
        }

        public void setB_coin(String b_coin) {
            this.b_coin = b_coin;
        }

        public String getMemberLevel() {
            return memberLevel;
        }

        public void setMemberLevel(String memberLevel) {
            this.memberLevel = memberLevel;
        }

        public String getTotalLevel() {
            return totalLevel;
        }

        public void setTotalLevel(String totalLevel) {
            this.totalLevel = totalLevel;
        }

        public String getExperienceValue() {
            return experienceValue;
        }

        public void setExperienceValue(String experienceValue) {
            this.experienceValue = experienceValue;
        }

        public String getExperienceValueMax() {
            return experienceValueMax;
        }

        public void setExperienceValueMax(String experienceValueMax) {
            this.experienceValueMax = experienceValueMax;
        }

        public String getUnamekey() {
            return unamekey;
        }

        public void setUnamekey(String unamekey) {
            this.unamekey = unamekey;
        }
    }
}
