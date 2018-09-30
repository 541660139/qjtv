package com.lwd.qjtv.mvp.model.entity;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/13.
 */

public class LearnBallDetailsBean {

    /**
     * msg : success
     * status : 1
     * data : {"id":"5","name":"孔刘","english_name":"Gong Yoo","is_star":"1","gender":"1","birth":"1979年7月10日","constellation":"巨蟹座","intro":"2001年拍摄KBS电视剧《学校4》出道。2003年凭藉《梦想的屏幕》获SBS演技大赏新人奖。2006年更以《精彩的一天》获MBC大赏演技奖。2007年出演《咖啡王子一号店》获MBC优秀男演员奖。2011年参与及促成电影《熔炉》的拍摄，2016年孔刘出演电影《男与女》、《釜山行》、《密探》及电视剧《孤单又灿烂的神-鬼怪》。","thumb_img":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703151340032731.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","avater":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703151340032731.jpg","ctime":"0","mtime":"0","dstatus":"0"}
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
         * id : 5
         * name : 孔刘
         * english_name : Gong Yoo
         * is_star : 1
         * gender : 1
         * birth : 1979年7月10日
         * constellation : 巨蟹座
         * intro : 2001年拍摄KBS电视剧《学校4》出道。2003年凭藉《梦想的屏幕》获SBS演技大赏新人奖。2006年更以《精彩的一天》获MBC大赏演技奖。2007年出演《咖啡王子一号店》获MBC优秀男演员奖。2011年参与及促成电影《熔炉》的拍摄，2016年孔刘出演电影《男与女》、《釜山行》、《密探》及电视剧《孤单又灿烂的神-鬼怪》。
         * thumb_img : http://img.ttkhj.3z.cc/upload/video_img/201703/201703151340032731.jpg?x-oss-process=image/resize,m_fill,h_150,w_150
         * avater : http://img.ttkhj.3z.cc/upload/video_img/201703/201703151340032731.jpg
         * ctime : 0
         * mtime : 0
         * dstatus : 0
         */

        private String id;
        private String name;
        private String english_name;
        private String is_star;
        private String gender;
        private String birth;
        private String constellation;
        private String intro;
        private String thumb_img;
        private String avater;
        private String ctime;
        private String mtime;
        private String dstatus;

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

        public String getEnglish_name() {
            return english_name;
        }

        public void setEnglish_name(String english_name) {
            this.english_name = english_name;
        }

        public String getIs_star() {
            return is_star;
        }

        public void setIs_star(String is_star) {
            this.is_star = is_star;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getBirth() {
            return birth;
        }

        public void setBirth(String birth) {
            this.birth = birth;
        }

        public String getConstellation() {
            return constellation;
        }

        public void setConstellation(String constellation) {
            this.constellation = constellation;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getThumb_img() {
            return thumb_img;
        }

        public void setThumb_img(String thumb_img) {
            this.thumb_img = thumb_img;
        }

        public String getAvater() {
            return avater;
        }

        public void setAvater(String avater) {
            this.avater = avater;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getMtime() {
            return mtime;
        }

        public void setMtime(String mtime) {
            this.mtime = mtime;
        }

        public String getDstatus() {
            return dstatus;
        }

        public void setDstatus(String dstatus) {
            this.dstatus = dstatus;
        }
    }
}
