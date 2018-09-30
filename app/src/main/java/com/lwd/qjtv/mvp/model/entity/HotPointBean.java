package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by DELL on 2018/8/21.
 */

public class HotPointBean {


    /**
     * msg : success
     * status : 1
     * data : [{"id":"1","title":"林丹大满贯","author":"小杰","browse_number":"11","img_url":"http://m.fangxiaotao.cn/ymq/user_profile/201806/1.jpeg,http://m.fangxiaotao.cn/ymq/user_profile/201806/2.jpeg,http://m.fangxiaotao.cn/ymq/user_profile/201806/3.jpeg","content_type":"1","bitmap":"","video_source":"","analysis_type":"0","create_time":"2018-07-19"},{"id":"2","title":"武汉体育馆羽毛球比赛","author":"大雨","browse_number":"100","img_url":"http://m.fangxiaotao.cn/ymq/user_profile/201806/16.jpeg","content_type":"0","bitmap":"","video_source":"","analysis_type":"0","create_time":"2018-07-19"},{"id":"3","title":"羽毛球情怀","author":"杰哥","browse_number":"66","img_url":"http://m.fangxiaotao.cn/ymq/user_profile/201806/8.jpeg","content_type":"0","bitmap":"","video_source":"","analysis_type":"0","create_time":"2018-07-19"},{"id":"4","title":"阿纲","author":"SaaS","browse_number":"24","img_url":"http://m.fangxiaotao.cn/ymq/user_profile/201806/13.jpeg,http://m.fangxiaotao.cn/ymq/user_profile/201806/14.jpeg,http://m.fangxiaotao.cn/ymq/user_profile/201806/15.jpeg","content_type":"1","bitmap":"","video_source":"","analysis_type":"0","create_time":"2018-07-19"},{"id":"5","title":"杰哥的侧脸","author":"js","browse_number":"666","img_url":"http://m.fangxiaotao.cn/ymq/video/1.mp4","content_type":"2","bitmap":"http://m.fangxiaotao.cn/ymq/user_profile/201806/29.jpeg","video_source":"录播","analysis_type":"0","create_time":"2018-07-19"}]
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
         * id : 1
         * title : 林丹大满贯
         * author : 小杰
         * browse_number : 11
         * img_url : http://m.fangxiaotao.cn/ymq/user_profile/201806/1.jpeg,http://m.fangxiaotao.cn/ymq/user_profile/201806/2.jpeg,http://m.fangxiaotao.cn/ymq/user_profile/201806/3.jpeg
         * content_type : 1
         * bitmap :
         * video_source :
         * analysis_type : 0
         * create_time : 2018-07-19
         */

        private String id;
        private String title;
        private String author;
        private String browse_number;
        private String img_url;
        private String content_type;
        private String bitmap;
        private String video_source;
        private String analysis_type;
        private String create_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getBrowse_number() {
            return browse_number;
        }

        public void setBrowse_number(String browse_number) {
            this.browse_number = browse_number;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getContent_type() {
            return content_type;
        }

        public void setContent_type(String content_type) {
            this.content_type = content_type;
        }

        public String getBitmap() {
            return bitmap;
        }

        public void setBitmap(String bitmap) {
            this.bitmap = bitmap;
        }

        public String getVideo_source() {
            return video_source;
        }

        public void setVideo_source(String video_source) {
            this.video_source = video_source;
        }

        public String getAnalysis_type() {
            return analysis_type;
        }

        public void setAnalysis_type(String analysis_type) {
            this.analysis_type = analysis_type;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
