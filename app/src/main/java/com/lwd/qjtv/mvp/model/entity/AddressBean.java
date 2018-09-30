package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/10.
 */

public class AddressBean {

    /**
     * msg : success
     * status : 1
     * data : [{"id":"10","receiver":"俊辉","zone":"湖北省武汉市青山区","address":"青和居小区兴盛平价","mobile":"18271857105","is_default":"1"},{"id":"5","receiver":"俊辉","zone":"湖北省武汉市青山区","address":"青和居小区兴盛平价","mobile":"18271857105","is_default":"0"},{"id":"9","receiver":"俊辉","zone":"湖北省武汉市青山区","address":"青和居小区兴盛平价","mobile":"18271857105","is_default":"0"},{"id":"11","receiver":"俊辉","zone":"湖北省武汉市青山区","address":"青和居小区兴盛平价","mobile":"18271857105","is_default":"0"},{"id":"12","receiver":"俊辉","zone":"湖北省武汉市青山区","address":"青和居小区兴盛平价","mobile":"18271857105","is_default":"0"},{"id":"13","receiver":"俊辉","zone":"湖北省武汉市青山区","address":"青和居小区兴盛平价","mobile":"18271857105","is_default":"0"}]
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
         * id : 10
         * receiver : 俊辉
         * zone : 湖北省武汉市青山区
         * address : 青和居小区兴盛平价
         * mobile : 18271857105
         * is_default : 1
         */

        private String id;
        private String receiver;
        private String zone;
        private String address;
        private String mobile;
        private String is_default;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }
    }
}
