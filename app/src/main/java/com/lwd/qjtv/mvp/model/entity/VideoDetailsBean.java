package com.lwd.qjtv.mvp.model.entity;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/5.
 */

public class VideoDetailsBean {


    /**
     * msg : success
     * status : 1
     * data : {"id":"9","name":"通往机场的路","starId":"12","score":"8.7","url":"http://www.le.com/ptv/vplay/1562265.html#vid=1562265","pic_h":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703091055403636.jpg","matchPeople":"丁俊辉vs奥沙利文","des":"该剧从\u201c已婚男女的世界中难道没有正当的关系吗\u201d的提问出发，通过讲述主人公之间的爱情纠葛给观众带来共鸣、安慰，展现极致的爱情......","video_length":"60分钟","v_type":"1","teachVideoType":"0","isCollect":0}
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
         * id : 9
         * name : 通往机场的路
         * starId : 12
         * score : 8.7
         * url : http://www.le.com/ptv/vplay/1562265.html#vid=1562265
         * pic_h : http://img.ttkhj.3z.cc/upload/video_img/201703/201703091055403636.jpg
         * matchPeople : 丁俊辉vs奥沙利文
         * des : 该剧从“已婚男女的世界中难道没有正当的关系吗”的提问出发，通过讲述主人公之间的爱情纠葛给观众带来共鸣、安慰，展现极致的爱情......
         * video_length : 60分钟
         * v_type : 1
         * teachVideoType : 0
         * isCollect : 0
         * origin 1：人人影视  | 2 自己抓取(直接播放)
         *
         */

        private String id;
        private String name;
        private String starId;
        private String score;
        private String url;
        private String pic_h;
        private String matchPeople;
        private String des;
        private String video_length;
        private String v_type;
        private String teachVideoType;
        private int isCollect;
        private String origin;

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

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

        public String getStarId() {
            return starId;
        }

        public void setStarId(String starId) {
            this.starId = starId;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPic_h() {
            return pic_h;
        }

        public void setPic_h(String pic_h) {
            this.pic_h = pic_h;
        }

        public String getMatchPeople() {
            return matchPeople;
        }

        public void setMatchPeople(String matchPeople) {
            this.matchPeople = matchPeople;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getVideo_length() {
            return video_length;
        }

        public void setVideo_length(String video_length) {
            this.video_length = video_length;
        }

        public String getV_type() {
            return v_type;
        }

        public void setV_type(String v_type) {
            this.v_type = v_type;
        }

        public String getTeachVideoType() {
            return teachVideoType;
        }

        public void setTeachVideoType(String teachVideoType) {
            this.teachVideoType = teachVideoType;
        }

        public int getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(int isCollect) {
            this.isCollect = isCollect;
        }
    }
}
