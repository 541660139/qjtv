package com.lwd.qjtv.mvp.model.entity;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/14.
 */

public class SendGiftBean {

    /**
     * msg : 礼物赠送成功
     * status : 1
     * data : {"b_coin":5200}
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
         * b_coin : 5200
         */

        private int b_coin;

        public int getB_coin() {
            return b_coin;
        }

        public void setB_coin(int b_coin) {
            this.b_coin = b_coin;
        }
    }
}
