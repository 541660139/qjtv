package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by DELL on 2018/9/1.
 */

public class GameTabBean {

    /**
     * msg : success
     * status : 1
     * data : [{"id":"01","match_name":"马来西亚公开赛"},{"id":"02","match_name":"橄榄球NFL"},{"id":"03","match_name":"欧冠"},{"id":"04","match_name":"NBA夏季赛"},{"id":"05","match_name":"第三届中国斯诺克老运动员联谊活动规程　"},{"id":"06","match_name":"60kg自由搏击赛"},{"id":"07","match_name":"乒乓超级联赛"},{"id":"08","match_name":"美国职业棒球大联盟"},{"id":"09","match_name":"排球"}]
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
         * id : 01
         * match_name : 马来西亚公开赛
         */

        private String id;
        private String sport_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMatch_name() {
            return sport_name;
        }

        public void setMatch_name(String match_name) {
            this.sport_name = match_name;
        }
    }

    @Override
    public String toString() {
        return "GameTabBean{" +
                "msg='" + msg + '\'' +
                ", status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}
