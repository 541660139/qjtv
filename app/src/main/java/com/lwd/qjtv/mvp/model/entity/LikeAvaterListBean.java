package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by ZhengQian on 2018/2/1.
 * E-mail : wwwiiivip@yeah.net
 * If anyelse things , mail to it . I'll be soon to reply .
 */

public class LikeAvaterListBean {


    /**
     * msg : ok
     * status : 1
     * data : [{"avater":"http://img.8gu.com/slk/user_profile/201801/201801311533461573.jpeg","username":"aaa"},{"avater":"http://img.8gu.com/slk/user_profile/avatars/5.jpg","username":"u107018"},{"avater":"http://img.8gu.com/slk/user_profile/201711/201711231612442522.jpeg","username":"u100000"}]
     */

    private String msg;
    private String status;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * avater : http://img.8gu.com/slk/user_profile/201801/201801311533461573.jpeg
         * username : aaa
         */

        private String avater;
        private String username;

        public String getAvater() {
            return avater;
        }

        public void setAvater(String avater) {
            this.avater = avater;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
