package com.lwd.qjtv.mvp.model.entity;

/**
 * Created by Administrator on 2018/3/20.
 */

public class WechatH5PayBean {

    /**
     * msg : success
     * status : 1
     * data : {"pay_order_number":"201803201447091939izfrf3SLK","pay_url":"http://slk.3z.cc/i/cA1LVQ12779SLK"}
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
         * pay_order_number : 201803201447091939izfrf3SLK
         * pay_url : http://slk.3z.cc/i/cA1LVQ12779SLK
         */

        private String pay_order_number;
        private String pay_url;

        public String getPay_order_number() {
            return pay_order_number;
        }

        public void setPay_order_number(String pay_order_number) {
            this.pay_order_number = pay_order_number;
        }

        public String getPay_url() {
            return pay_url;
        }

        public void setPay_url(String pay_url) {
            this.pay_url = pay_url;
        }
    }
}
