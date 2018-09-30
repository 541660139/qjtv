package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by DELL on 2018/6/25.
 */

public class ChoiceDianZanBean {

    /**
     * msg : success
     * status : 1
     * data : [{"favorite":"1","uid":"1","video_id":"842","id":"842","v_type":"2","origin":"4","url":"https://v.youku.com/v_show/id_XMzY3OTgxODMwOA==.html?spm=a2h1n.8251846.0.0&amp;qq-pf-to=pcqq.group","video_length":"4","pic_h":"http://img.8gu.com/slk/images/201806/201806141612192325.jpg","zan_number":"0","main_title":"哈哈哈","name":"测试一下啦","comment_nums":"1"},{"favorite":"2","uid":"1","video_id":"843","id":"843","v_type":"2","origin":"1","url":"https://v.youku.com/v_show/id_XMzY3OTgxODMwOA==.html?spm=a2h1n.8251846.0.0&amp;qq-pf-to=pcqq.group","video_length":"","pic_h":"http://img.8gu.com/slk/images/201806/201806151015345243.jpg","zan_number":"0","main_title":"没有标题","name":"哈哈哈哈","comment_nums":"0"},{"favorite":"0","uid":"0","video_id":"0","id":"844","v_type":"2","origin":"4","url":"http://www.baidu.com","video_length":"","pic_h":"http://img.8gu.com/slk/images/201806/201806221836299067.jpg","zan_number":"0","main_title":"","name":"奥术法师","comment_nums":"0"}]
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
         * favorite : 1
         * uid : 1
         * video_id : 842
         * id : 842
         * v_type : 2
         * origin : 4
         * url : https://v.youku.com/v_show/id_XMzY3OTgxODMwOA==.html?spm=a2h1n.8251846.0.0&amp;qq-pf-to=pcqq.group
         * video_length : 4
         * pic_h : http://img.8gu.com/slk/images/201806/201806141612192325.jpg
         * zan_number : 0
         * main_title : 哈哈哈
         * name : 测试一下啦
         * comment_nums : 1
         */

        private String favorite;
        private String uid;
        private String video_id;
        private String id;
        private String v_type;
        private String origin;
        private String url;
        private String video_length;
        private String pic_h;
        private String zan_number;
        private String main_title;
        private String name;
        private String comment_nums;

        public String getFavorite() {
            return favorite;
        }

        public void setFavorite(String favorite) {
            this.favorite = favorite;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getV_type() {
            return v_type;
        }

        public void setV_type(String v_type) {
            this.v_type = v_type;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVideo_length() {
            return video_length;
        }

        public void setVideo_length(String video_length) {
            this.video_length = video_length;
        }

        public String getPic_h() {
            return pic_h;
        }

        public void setPic_h(String pic_h) {
            this.pic_h = pic_h;
        }

        public String getZan_number() {
            return zan_number;
        }

        public void setZan_number(String zan_number) {
            this.zan_number = zan_number;
        }

        public String getMain_title() {
            return main_title;
        }

        public void setMain_title(String main_title) {
            this.main_title = main_title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getComment_nums() {
            return comment_nums;
        }

        public void setComment_nums(String comment_nums) {
            this.comment_nums = comment_nums;
        }
    }
}
