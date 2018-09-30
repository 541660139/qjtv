package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/17.
 */

public class MyOrderBean {

    /**
     * msg : success
     * status : 1
     * data : [{"order_id":"32","order_sn":"SLK2017060817152747221","pay_status":"1","goods_id":"2","goods_name":"皇牌台球桌2","goods_number":"1","subtotal":"288.00","goods_brief":"皇牌台球桌 标准桌球美式黑8 家用乒乓球二合一高档台球","goods_img":"http://img.ttkhj.3z.cc/slk/images/tq.png"},{"order_id":"36","order_sn":"SLK2017061210430951560","pay_status":"1","goods_id":"9","goods_name":"皇牌台球桌8","goods_number":"1","subtotal":"888.00","goods_brief":"皇牌台球桌 标准桌球美式黑8 家用乒乓球二合一高档台球","goods_img":"http://img.ttkhj.3z.cc/slk/images/tq.png"},{"order_id":"33","order_sn":"SLK2017061014531239897","pay_status":"1","goods_id":"2","goods_name":"皇牌台球桌2","goods_number":"1","subtotal":"288.00","goods_brief":"皇牌台球桌 标准桌球美式黑8 家用乒乓球二合一高档台球","goods_img":"http://img.ttkhj.3z.cc/slk/images/tq.png"}]
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
         * order_id : 32
         * order_sn : SLK2017060817152747221
         * pay_status : 1
         * goods_id : 2
         * goods_name : 皇牌台球桌2
         * goods_number : 1
         * subtotal : 288.00
         * goods_brief : 皇牌台球桌 标准桌球美式黑8 家用乒乓球二合一高档台球
         * goods_img : http://img.ttkhj.3z.cc/slk/images/tq.png
         */

        private String order_id;
        private String order_sn;
        private String pay_status;
        private String goods_id;
        private String goods_name;
        private String goods_number;
        private String subtotal;
        private String goods_brief;
        private String goods_img;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_number() {
            return goods_number;
        }

        public void setGoods_number(String goods_number) {
            this.goods_number = goods_number;
        }

        public String getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(String subtotal) {
            this.subtotal = subtotal;
        }

        public String getGoods_brief() {
            return goods_brief;
        }

        public void setGoods_brief(String goods_brief) {
            this.goods_brief = goods_brief;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }
    }
}
