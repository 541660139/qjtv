package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by DELL on 2018/6/14.
 */

public class ChoiceDataBean {


    /**
     * msg : success
     * status : 1
     * data : [{"id":"1","video_url":"https://v.youku.com/v_show/id_XMzY2NDMxMzgwMA==.html?spm=a2h0k.11417342.soresults.dtitle","video_title":"呵呵大","video_zancount":"1","video_reviews":"8","video_source":"优酷","analysis_type":"0","bitmap":"http://img.8gu.com/slk/images/201806/201806280859543089.png","author":"","viewing_count":"0","create_time":"1970-01-01","update_time":"0"},{"id":"2","video_url":"https://v.youku.com/v_show/id_XMzYxNDM1MjAwMA==.html?spm=a2h0k.11417342.soresults.dtitle","video_title":"王校长","video_zancount":"1","video_reviews":"5","video_source":"优酷","analysis_type":"1","bitmap":"http://img.8gu.com/slk/images/201806/201806280901308361.png","author":"","viewing_count":"0","create_time":"1970-01-01","update_time":"0"},{"id":"3","video_url":"https://v.youku.com/v_show/id_XMzYyNzQ5NzQxNg==.html?spm=a2h0k.11417342.soresults.dtitle","video_title":"厉害了","video_zancount":"0","video_reviews":"5","video_source":"爱奇艺","analysis_type":"1","bitmap":"http://img.8gu.com/slk/images/201806/201806280902293141.png","author":"","viewing_count":"0","create_time":"1970-01-01","update_time":"0"}]
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
         * video_url : https://v.youku.com/v_show/id_XMzY2NDMxMzgwMA==.html?spm=a2h0k.11417342.soresults.dtitle
         * video_title : 呵呵大
         * video_zancount : 1
         * video_reviews : 8
         * video_source : 优酷
         * analysis_type : 0
         * bitmap : http://img.8gu.com/slk/images/201806/201806280859543089.png
         * author :
         * viewing_count : 0
         * create_time : 1970-01-01
         * update_time : 0
         */

        private String id;
        private String video_url;
        private String video_title;
        private String video_zancount;
        private String video_reviews;
        private String video_source;
        private String analysis_type;
        private String bitmap;
        private String author;
        private String viewing_count;
        private String create_time;
        private String update_time;
        private String is_zan;

        public String getIs_zan() {
            return is_zan;
        }

        public void setIs_zan(String is_zan) {
            this.is_zan = is_zan;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public String getVideo_title() {
            return video_title;
        }

        public void setVideo_title(String video_title) {
            this.video_title = video_title;
        }

        public String getVideo_zancount() {
            return video_zancount;
        }

        public void setVideo_zancount(String video_zancount) {
            this.video_zancount = video_zancount;
        }

        public String getVideo_reviews() {
            return video_reviews;
        }

        public void setVideo_reviews(String video_reviews) {
            this.video_reviews = video_reviews;
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

        public String getBitmap() {
            return bitmap;
        }

        public void setBitmap(String bitmap) {
            this.bitmap = bitmap;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getViewing_count() {
            return viewing_count;
        }

        public void setViewing_count(String viewing_count) {
            this.viewing_count = viewing_count;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
    }
}
