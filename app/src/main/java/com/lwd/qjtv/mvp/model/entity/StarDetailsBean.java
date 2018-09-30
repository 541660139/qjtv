package com.lwd.qjtv.mvp.model.entity;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/5.
 */

public class StarDetailsBean {

    /**
     * msg : success
     * status : 1
     * data : {"id":"5","name":"孔刘","english_name":"Gong Yoo","intro":"2001年拍摄KBS电视剧《学校4》出道。","avater":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703151340032731.jpg"}
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
         * intro : 2001年拍摄KBS电视剧《学校4》出道。
         * avater : http://img.ttkhj.3z.cc/upload/video_img/201703/201703151340032731.jpg
         */

        private String id;
        private String name;
        private String english_name;
        private String intro;
        private String avater;

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

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getAvater() {
            return avater;
        }

        public void setAvater(String avater) {
            this.avater = avater;
        }
    }
}
