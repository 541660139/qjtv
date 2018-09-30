package com.lwd.qjtv.mvp.model.entity;

/**
 * Created by ZhengQian on 2017/12/16.
 * E-mail : wwwiiivip@yeah.net
 * If anyelse things , mail to it . I'll be soon to reply .
 */

public class PingFenBean {

    /**
     * msg : success
     * status : 1
     * data : {"pingfen":7.05,"isPingfen":true}
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
         * pingfen : 7.05
         * isPingfen : true
         */

        private double pingfen;
        private boolean isPingfen;

        public double getPingfen() {
            return pingfen;
        }

        public void setPingfen(double pingfen) {
            this.pingfen = pingfen;
        }

        public boolean isIsPingfen() {
            return isPingfen;
        }

        public void setIsPingfen(boolean isPingfen) {
            this.isPingfen = isPingfen;
        }
    }
}
