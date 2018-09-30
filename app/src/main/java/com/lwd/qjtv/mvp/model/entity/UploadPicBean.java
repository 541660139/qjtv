package com.lwd.qjtv.mvp.model.entity;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/24.
 */

public class UploadPicBean {


    /**
     * msg : 上传成功
     * status : 1
     * data : {"url":"http://img.ttkhj.3z.cc/ttys/user_profile/201703/201703231752037771.jpeg","path":"http://img.ttkhj.3z.cc/ttys/user_profile/201703/201703231752037771.jpeg"}
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
         * url : http://img.ttkhj.3z.cc/ttys/user_profile/201703/201703231752037771.jpeg
         * path : http://img.ttkhj.3z.cc/ttys/user_profile/201703/201703231752037771.jpeg
         */

        private String url;
        private String path;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
