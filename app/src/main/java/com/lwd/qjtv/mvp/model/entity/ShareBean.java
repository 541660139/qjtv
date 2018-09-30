package com.lwd.qjtv.mvp.model.entity;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/8/16.
 */

public class ShareBean {

    /**
     * msg : success
     * status : 1
     * data : {"title":"斯洛克TV","url":"https://itunes.apple.com/us/app/id1258002273?mt=8","icon":"http://img.ttkhj.3z.cc/slk/slk-icon.png","desc":"天天看斯洛克TV,斯洛克TV天天看"}
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
         * title : 斯洛克TV
         * url : https://itunes.apple.com/us/app/id1258002273?mt=8
         * icon : http://img.ttkhj.3z.cc/slk/slk-icon.png
         * desc : 天天看斯洛克TV,斯洛克TV天天看
         */

        private String title;
        private String url;
        private String icon;
        private String desc;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
