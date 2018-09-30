package com.lwd.qjtv.mvp.model.entity;

/**
 * Created by ZhengQian on 2017/12/16.
 * E-mail : wwwiiivip@yeah.net
 * If anyelse things , mail to it . I'll be soon to reply .
 */

public class FengJinBean {

    /**
     * msg : 禁言成功
     * status : 1
     * data : {"name":"哈哈哈哈哈哈哈","j_time":"1分钟"}
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
         * name : 哈哈哈哈哈哈哈
         * j_time : 1分钟
         */

        private String name;
        private String j_time;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getJ_time() {
            return j_time;
        }

        public void setJ_time(String j_time) {
            this.j_time = j_time;
        }
    }
}
