package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/31.
 */

public class DetailsMoneyBean {

    /**
     * msg : success
     * status : 1
     * data : [{"id":"4","amount":"-20000.00","r_name":"竞猜","r_type":"1","ctime":"2017:07:28 11:42:45"},{"id":"5","amount":"-200.00","r_name":"竞猜","r_type":"1","ctime":"2017:07:28 11:44:10"}]
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
         * id : 4
         * amount : -20000.00
         * r_name : 竞猜
         * r_type : 1
         * ctime : 2017:07:28 11:42:45
         */

        private String id;
        private String amount;
        private String r_name;
        private String r_type;
        private String ctime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getR_name() {
            return r_name;
        }

        public void setR_name(String r_name) {
            this.r_name = r_name;
        }

        public String getR_type() {
            return r_type;
        }

        public void setR_type(String r_type) {
            this.r_type = r_type;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }
    }
}
