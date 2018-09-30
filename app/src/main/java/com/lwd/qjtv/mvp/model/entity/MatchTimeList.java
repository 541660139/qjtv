package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/13.
 */

public class MatchTimeList {


    /**
     * msg : success
     * status : 1
     * data : [{"id":"001","phaseMatch_id":"1","winner":"12","matchPeople":"12,11","startTime":"08月07日 14:25","isOver":"1","jc_start_time":"1502087100","jc_over_time":"1502099880","starInfo":[{"id":"11","name":"宾汉姆","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041456323767.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"12","name":"霍金斯","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041442183706.png?x-oss-process=image/resize,m_fill,h_150,w_150"}]},{"id":"002","phaseMatch_id":"1","winner":"6","matchPeople":"6,8","startTime":"08月07日 14:25","isOver":"1","jc_start_time":"1502086800","jc_over_time":"1502087400","starInfo":[{"id":"6","name":"傅家俊","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707051733416925.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"8","name":"罗伯逊","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041446118649.png?x-oss-process=image/resize,m_fill,h_150,w_150"}]},{"id":"003","phaseMatch_id":"1","winner":"5","matchPeople":"5,9","startTime":"08月07日 14:25","isOver":"1","jc_start_time":"1502086800","jc_over_time":"1502087400","starInfo":[{"id":"5","name":"特鲁姆普","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041403424328.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"9","name":"墨菲","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041453081127.png?x-oss-process=image/resize,m_fill,h_150,w_150"}]},{"id":"004","phaseMatch_id":"1","winner":"1","matchPeople":"3,1","startTime":"08月08日 21:10","isOver":"1","jc_start_time":"1502086800","jc_over_time":"1502287200","starInfo":[{"id":"1","name":"奥沙利文","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041524356303.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"3","name":"希金斯","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041031448646.png?x-oss-process=image/resize,m_fill,h_150,w_150"}]}]
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
         * id : 001
         * phaseMatch_id : 1
         * winner : 12
         * matchPeople : 12,11
         * startTime : 08月07日 14:25
         * isOver : 1
         * jc_start_time : 1502087100
         * jc_over_time : 1502099880
         * starInfo : [{"id":"11","name":"宾汉姆","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041456323767.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"12","name":"霍金斯","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041442183706.png?x-oss-process=image/resize,m_fill,h_150,w_150"}]
         */

        private String id;
        private String phaseMatch_id;
        private String winner;
        private String matchPeople;
        private String startTime;
        private String isOver;
        private String jc_start_time;
        private String jc_over_time;
        private String matchResult;

        public String getMatchResult() {
            return matchResult;
        }

        public void setMatchResult(String matchResult) {
            this.matchResult = matchResult;
        }

        private List<StarInfoBean> starInfo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhaseMatch_id() {
            return phaseMatch_id;
        }

        public void setPhaseMatch_id(String phaseMatch_id) {
            this.phaseMatch_id = phaseMatch_id;
        }

        public String getWinner() {
            return winner;
        }

        public void setWinner(String winner) {
            this.winner = winner;
        }

        public String getMatchPeople() {
            return matchPeople;
        }

        public void setMatchPeople(String matchPeople) {
            this.matchPeople = matchPeople;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getIsOver() {
            return isOver;
        }

        public void setIsOver(String isOver) {
            this.isOver = isOver;
        }

        public String getJc_start_time() {
            return jc_start_time;
        }

        public void setJc_start_time(String jc_start_time) {
            this.jc_start_time = jc_start_time;
        }

        public String getJc_over_time() {
            return jc_over_time;
        }

        public void setJc_over_time(String jc_over_time) {
            this.jc_over_time = jc_over_time;
        }

        public List<StarInfoBean> getStarInfo() {
            return starInfo;
        }

        public void setStarInfo(List<StarInfoBean> starInfo) {
            this.starInfo = starInfo;
        }

        public static class StarInfoBean {
            /**
             * id : 11
             * name : 宾汉姆
             * thumb_img : http://img.ttkhj.3z.cc/slk/images/201707/201707041456323767.png?x-oss-process=image/resize,m_fill,h_150,w_150
             */

            private String id;
            private String name;
            private String thumb_img;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getThumb_img() {
                return thumb_img;
            }

            public void setThumb_img(String thumb_img) {
                this.thumb_img = thumb_img;
            }
        }
    }
}
