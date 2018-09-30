package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by ZhengQian on 2017/11/22.
 * E-mail : wwwiiivip@yeah.net
 * If anyelse things , mail to it . I'll be soon to reply .
 */

public class MatchCollectionMoreBean {


   /**
     * msg : success
     * status : 1
     * data : [{"bigMatch_id":"39","name":"2017斯诺克上海大师赛","pic_h":"http://img.8gu.com/slk/images/201708/201708081514226357.jpg"},{"bigMatch_id":"38","name":"2017斯诺克冠中冠","pic_h":"http://img.8gu.com/slk/images/201708/201708081514226357.jpg"},{"bigMatch_id":"32","name":"2017斯诺克国际锦标赛","pic_h":"http://img.8gu.com/slk/images/201708/201708081514226357.jpg"},{"bigMatch_id":"30","name":"2017斯诺克英格兰公开赛","pic_h":"http://img.8gu.com/slk/images/201708/201708081514226357.jpg"},{"bigMatch_id":"29","name":"2017斯诺克上海大师赛资格赛","pic_h":"http://img.8gu.com/slk/images/201708/201708081514226357.jpg"},{"bigMatch_id":"27","name":"2017斯诺克欧洲大师赛","pic_h":"http://img.8gu.com/slk/images/201708/201708081514226357.jpg"},{"bigMatch_id":"25","name":"2017斯诺克国际锦标赛资格赛","pic_h":"http://img.8gu.com/slk/images/201708/201708081514226357.jpg"},{"bigMatch_id":"24","name":"2017斯诺克世界公开赛","pic_h":"http://img.8gu.com/slk/images/201708/201708081514226357.jpg"},{"bigMatch_id":"20","name":"2017斯诺克印度公开赛","pic_h":"http://img.8gu.com/slk/images/201708/201708081514226357.jpg"},{"bigMatch_id":"07","name":"2017斯诺克六红球世界锦标赛","pic_h":"http://img.8gu.com/slk/images/201708/201708081514226357.jpg"}]
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
         * bigMatch_id : 39
         * name : 2017斯诺克上海大师赛
         * pic_h : http://img.8gu.com/slk/images/201708/201708081514226357.jpg
         */

        private String bigMatch_id;
        private String name;
        private String pic_h;

        public String getBigMatch_id() {
            return bigMatch_id;
        }

        public void setBigMatch_id(String bigMatch_id) {
            this.bigMatch_id = bigMatch_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic_h() {
            return pic_h;
        }

        public void setPic_h(String pic_h) {
            this.pic_h = pic_h;
        }
    }
}
