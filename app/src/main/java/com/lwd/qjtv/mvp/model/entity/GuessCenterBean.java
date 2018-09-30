package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/23.
 */

public class GuessCenterBean {


    /**
     * msg : success
     * status : 1
     * data : [{"id":"14","match_id":"22","start_time":"08月11日 10:15","over_time":"1502778600","starInfo":[{"id":"5","name":"特鲁姆普","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041403424328.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"3","name":"希金斯","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041031448646.png?x-oss-process=image/resize,m_fill,h_150,w_150"}]}]
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
         * id : 14
         * match_id : 22
         * start_time : 08月11日 10:15
         * over_time : 1502778600
         * starInfo : [{"id":"5","name":"特鲁姆普","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041403424328.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"3","name":"希金斯","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041031448646.png?x-oss-process=image/resize,m_fill,h_150,w_150"}]
         */

        private String id;
        private String match_id;
        private String start_time;
        private String over_time;

        public String getMatch_result() {
            return match_result;
        }

        public void setMatch_result(String match_result) {
            this.match_result = match_result;
        }

        private String match_result;
        private List<StarInfoBean> starInfo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMatch_id() {
            return match_id;
        }

        public void setMatch_id(String match_id) {
            this.match_id = match_id;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getOver_time() {
            return over_time;
        }

        public void setOver_time(String over_time) {
            this.over_time = over_time;
        }

        public List<StarInfoBean> getStarInfo() {
            return starInfo;
        }

        public void setStarInfo(List<StarInfoBean> starInfo) {
            this.starInfo = starInfo;
        }

        public static class StarInfoBean {
            /**
             * id : 5
             * name : 特鲁姆普
             * thumb_img : http://img.ttkhj.3z.cc/slk/images/201707/201707041403424328.png?x-oss-process=image/resize,m_fill,h_150,w_150
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
