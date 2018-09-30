package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/13.
 */

public class MatchTimeBean {


    /**
     * msg : success
     * status : 1
     * data : [{"id":"3","match_name":"半潗决赛","match_englishName":"finalGame1","bigMatch_id":"3"},{"id":"4","match_name":"潗决赛","match_englishName":"finalGame","bigMatch_id":"3"},{"id":"5","match_name":"决赛","match_englishName":"finalGame2","bigMatch_id":"3"},{"id":"6","match_name":"表演赛2","match_englishName":"semifinalGame","bigMatch_id":"3"}]
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
         * id : 3
         * match_name : 半潗决赛
         * match_englishName : finalGame1
         * bigMatch_id : 3
         */

        private String phaseMatch_id;
        private String match_name;
        private String match_englishName;
        private String bigMatch_id;

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

        public String getMatch_englishName() {
            return match_englishName;
        }

        public void setMatch_englishName(String match_englishName) {
            this.match_englishName = match_englishName;
        }

        public String getBigMatch_id() {
            return bigMatch_id;
        }

        public void setBigMatch_id(String bigMatch_id) {
            this.bigMatch_id = bigMatch_id;
        }
    }
}
