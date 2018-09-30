package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/20.
 */

public class GuessRankBean {

    /**
     * msg : success
     * status : 1
     * data : [{"user_id":"2","all_wager":"1400","username":"186*****526","avater":""},{"user_id":"5","all_wager":"400","username":"好咯取消","avater":""},{"user_id":"3","all_wager":"100","username":"sunrain","avater":""},{"user_id":"4","all_wager":"100","username":"songjk","avater":""}]
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
         * user_id : 2
         * all_wager : 1400
         * username : 186*****526
         * avater :
         */

        private String user_id;
        private String all_wager;
        private String username;
        private String avater;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getAll_wager() {
            return all_wager;
        }

        public void setAll_wager(String all_wager) {
            this.all_wager = all_wager;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAvater() {
            return avater;
        }

        public void setAvater(String avater) {
            this.avater = avater;
        }
    }
}
