package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/13.
 */

public class PersonalWareHouseBean {

    /**
     * msg : success
     * status : 0
     * data : [{"count":"3","gift_id":"4"},{"count":"2","gift_id":"5"},{"count":"4","gift_id":"6"}]
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
         * count : 3
         * gift_id : 4
         */

        private String count;
        private String gift_id;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getGift_id() {
            return gift_id;
        }

        public void setGift_id(String gift_id) {
            this.gift_id = gift_id;
        }
    }
}
