package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/21.
 */

public class RechargeCostBean {

    /**
     * msg : success
     * status : 1
     * data : [{"id":"72","ordernum":"2017071809391075294548o8SLK","money":"0.01","ontime":"1500341950"},{"id":"68","ordernum":"201707141016180606q4q4inSLK","money":"0.01","ontime":"1499998578"},{"id":"67","ordernum":"201707141012428244b8ots4SLK","money":"0.01","ontime":"1499998362"}]
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
         * id : 72
         * ordernum : 2017071809391075294548o8SLK
         * money : 0.01
         * ontime : 1500341950
         */

        private String id;
        private String ordernum;
        private String money;
        private String ontime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(String ordernum) {
            this.ordernum = ordernum;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getOntime() {
            return ontime;
        }

        public void setOntime(String ontime) {
            this.ontime = ontime;
        }
    }
}
