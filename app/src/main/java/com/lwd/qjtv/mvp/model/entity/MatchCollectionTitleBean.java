package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by ZhengQian on 2017/11/22.
 * E-mail : wwwiiivip@yeah.net
 * If anyelse things , mail to it . I'll be soon to reply .
 */

public class MatchCollectionTitleBean {

    /**
     * msg : success
     * status : 1
     * data : [{"phaseMatch_id":"104","match_name":"决赛"},{"phaseMatch_id":"103","match_name":"半决赛"},{"phaseMatch_id":"102","match_name":"1/4决赛"},{"phaseMatch_id":"101","match_name":"第3轮"},{"phaseMatch_id":"100","match_name":"第2轮"},{"phaseMatch_id":"99","match_name":"第1轮"},{"phaseMatch_id":"98","match_name":"延期资格赛"}]
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
         * phaseMatch_id : 104
         * match_name : 决赛
         */

        private String phaseMatch_id;
        private String match_name;

        public String getPhaseMatch_id() {
            return phaseMatch_id;
        }

        public void setPhaseMatch_id(String phaseMatch_id) {
            this.phaseMatch_id = phaseMatch_id;
        }

        public String getMatch_name() {
            return match_name;
        }

        public void setMatch_name(String match_name) {
            this.match_name = match_name;
        }
    }
}
