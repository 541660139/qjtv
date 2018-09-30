package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by ZhengQian on 2017/11/23.
 * E-mail : wwwiiivip@yeah.net
 * If anyelse things , mail to it . I'll be soon to reply .
 */

public class SearchCollectionBean {

    /**
     * msg : success
     * status : 1
     * data : [{"id":"813","match_num":"38009","player_a_name":"丁俊晖","player_b_name":"汉密尔顿","player_a":"2","player_b":"25","startTime":"11月23日 15:40","a_win_num":"2","b_win_num":"4","player_a_avater":"http://img.8gu.com/slk/images/201707/201707041421082709.png?x-oss-process=image/resize,m_fill,h_150,w_150","player_b_avater":"http://img.8gu.com/slk/images/201707/201707051741561757.png?x-oss-process=image/resize,m_fill,h_150,w_150","video_id":null,"v_type":null},{"id":"815","match_num":"38010","player_a_name":"傅家俊","player_b_name":"怀特","player_a":"6","player_b":"26","startTime":"11月23日 15:40","a_win_num":"3","b_win_num":"4","player_a_avater":"http://img.8gu.com/slk/images/201707/201707051733416925.png?x-oss-process=image/resize,m_fill,h_150,w_150","player_b_avater":"http://img.8gu.com/slk/images/201707/201707051742249903.png?x-oss-process=image/resize,m_fill,h_150,w_150","video_id":null,"v_type":null}]
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
         * id : 813
         * match_num : 38009
         * player_a_name : 丁俊晖
         * player_b_name : 汉密尔顿
         * player_a : 2
         * player_b : 25
         * startTime : 11月23日 15:40
         * a_win_num : 2
         * b_win_num : 4
         * player_a_avater : http://img.8gu.com/slk/images/201707/201707041421082709.png?x-oss-process=image/resize,m_fill,h_150,w_150
         * player_b_avater : http://img.8gu.com/slk/images/201707/201707051741561757.png?x-oss-process=image/resize,m_fill,h_150,w_150
         * video_id : null
         * v_type : null
         */

        private String id;
        private String match_num;
        private String player_a_name;
        private String player_b_name;
        private String player_a;
        private String player_b;
        private String startTime;
        private String a_win_num;
        private String b_win_num;
        private String player_a_avater;
        private String player_b_avater;
        private String video_id;
        private String v_type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMatch_num() {
            return match_num;
        }

        public void setMatch_num(String match_num) {
            this.match_num = match_num;
        }

        public String getPlayer_a_name() {
            return player_a_name;
        }

        public void setPlayer_a_name(String player_a_name) {
            this.player_a_name = player_a_name;
        }

        public String getPlayer_b_name() {
            return player_b_name;
        }

        public void setPlayer_b_name(String player_b_name) {
            this.player_b_name = player_b_name;
        }

        public String getPlayer_a() {
            return player_a;
        }

        public void setPlayer_a(String player_a) {
            this.player_a = player_a;
        }

        public String getPlayer_b() {
            return player_b;
        }

        public void setPlayer_b(String player_b) {
            this.player_b = player_b;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getA_win_num() {
            return a_win_num;
        }

        public void setA_win_num(String a_win_num) {
            this.a_win_num = a_win_num;
        }

        public String getB_win_num() {
            return b_win_num;
        }

        public void setB_win_num(String b_win_num) {
            this.b_win_num = b_win_num;
        }

        public String getPlayer_a_avater() {
            return player_a_avater;
        }

        public void setPlayer_a_avater(String player_a_avater) {
            this.player_a_avater = player_a_avater;
        }

        public String getPlayer_b_avater() {
            return player_b_avater;
        }

        public void setPlayer_b_avater(String player_b_avater) {
            this.player_b_avater = player_b_avater;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getV_type() {
            return v_type;
        }

        public void setV_type(String v_type) {
            this.v_type = v_type;
        }
    }
}
