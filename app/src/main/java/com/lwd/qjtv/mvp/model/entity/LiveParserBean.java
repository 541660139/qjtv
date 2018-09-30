package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by ZhengQian on 2017/11/14.
 * E-mail : wwwiiivip@yeah.net
 * If anyelse things , mail to it . I'll be soon to reply .
 */

public class LiveParserBean {

    /**
     * code : 0
     * message : success
     * cost : 0.159
     * type : live
     * data : {"title":"【虎神】S8新版符文解析","streams":[{"quality":"HD","type":"FLV","url":"http://pl3.live.panda.tv/live_panda/7fb2afc4564d4b2496922ecc0e728607.flv?sign=acb866ae50c61a79a358e9e7bde5c2cd&ts=5a0a8edb&rid=-52943799"},{"quality":"HD","type":"HLS","url":"http://pl-hls3.live.panda.tv/live_panda/7fb2afc4564d4b2496922ecc0e728607.m3u8"}]}
     */

    private int code;
    private String message;
    private double cost;
    private String type;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : 【虎神】S8新版符文解析
         * streams : [{"quality":"HD","type":"FLV","url":"http://pl3.live.panda.tv/live_panda/7fb2afc4564d4b2496922ecc0e728607.flv?sign=acb866ae50c61a79a358e9e7bde5c2cd&ts=5a0a8edb&rid=-52943799"},{"quality":"HD","type":"HLS","url":"http://pl-hls3.live.panda.tv/live_panda/7fb2afc4564d4b2496922ecc0e728607.m3u8"}]
         */

        private String title;
        private List<StreamsBean> streams;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<StreamsBean> getStreams() {
            return streams;
        }

        public void setStreams(List<StreamsBean> streams) {
            this.streams = streams;
        }

        public static class StreamsBean {
            /**
             * quality : HD
             * type : FLV
             * url : http://pl3.live.panda.tv/live_panda/7fb2afc4564d4b2496922ecc0e728607.flv?sign=acb866ae50c61a79a358e9e7bde5c2cd&ts=5a0a8edb&rid=-52943799
             */

            private String quality;
            private String type;
            private String url;

            public String getQuality() {
                return quality;
            }

            public void setQuality(String quality) {
                this.quality = quality;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
