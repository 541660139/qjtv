package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by DELL on 2018/8/22.
 */

public class BannerBean {

    /**
     * msg : success
     * status : 1
     * data : [{"id":"1","banner_url":"http://m.fangxiaotao.cn/ymq/images/d1.jpg","redirect_type":"0","title":"迪丽热巴直播","create_time":"2018-08-22","delete_time":"0"},{"id":"2","banner_url":"http://m.fangxiaotao.cn/ymq/images/d2.jpg","redirect_type":"1","title":"集锦详情","create_time":"2018-08-22","delete_time":"0"},{"id":"3","banner_url":"http://m.fangxiaotao.cn/ymq/images/d3.jpg","redirect_type":"2","title":"资讯详情","create_time":"2018-08-22","delete_time":"0"}]
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
         * banner_url : http://m.fangxiaotao.cn/ymq/images/d1.jpg
         * redirect_type : 0
         * title : 迪丽热巴直播
         * create_time : 2018-08-22
         * delete_time : 0
         */

        private String id;
        private String banner_url;
        private String redirect_type;
        private String title;
        private String create_time;
        private String delete_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBanner_url() {
            return banner_url;
        }

        public void setBanner_url(String banner_url) {
            this.banner_url = banner_url;
        }

        public String getRedirect_type() {
            return redirect_type;
        }

        public void setRedirect_type(String redirect_type) {
            this.redirect_type = redirect_type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(String delete_time) {
            this.delete_time = delete_time;
        }
    }
}
