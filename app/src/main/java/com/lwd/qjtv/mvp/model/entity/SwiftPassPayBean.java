package com.lwd.qjtv.mvp.model.entity;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/17.
 */

public class SwiftPassPayBean {

    /**
     * msg : success
     * status : 1
     * data : {"token_id":"12c23e3df5812a2fe9d07b530f63faa60","services":"pay.weixin.app"}
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
         * token_id : 12c23e3df5812a2fe9d07b530f63faa60
         * services : pay.weixin.app
         */

        private String token_id;
        private String services;

        public String getToken_id() {
            return token_id;
        }

        public void setToken_id(String token_id) {
            this.token_id = token_id;
        }

        public String getServices() {
            return services;
        }

        public void setServices(String services) {
            this.services = services;
        }
    }
}
