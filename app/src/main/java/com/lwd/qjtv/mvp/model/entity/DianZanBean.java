package com.lwd.qjtv.mvp.model.entity;

/**
 * Created by DELL on 2018/6/28.
 */

public class DianZanBean {

    /**
     * msg : 点赞成功
     * status : 1
     * data : {"video_zancount":"2"}
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
         * zan_number : 2
         */

        private String video_zancount;

        public String getZan_number() {
            return video_zancount;
        }

        public void setZan_number(String zan_number) {
            this.video_zancount = zan_number;
        }
    }
}
