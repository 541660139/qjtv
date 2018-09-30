package com.lwd.qjtv.mvp.model.entity;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/29.
 */

public class CheckVersionBean {


    /**
     * msg : success
     * status : 1
     * data : {"is_realease":"0","update_type":"1","update_info":"斯洛克TV发布新版本了（V1.0.5），亲，更多功能更好体验，是否立即下载？","is_guessNotOpen":"2","dstatus_turntable":"2","dstatus_b_coin":"2","version":"1.0.5","url":null,"login_expired":0,"kdInfo":{"id":"3","picture":"http://img.8gu.com/slk/images/201712/201712281431027985.png","pic_proportion":"2.385","link":"666","link_url":"","link_type":"2","pt":"3"}}
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
         * is_realease : 0
         * update_type : 1
         * update_info : 斯洛克TV发布新版本了（V1.0.5），亲，更多功能更好体验，是否立即下载？
         * is_guessNotOpen : 2
         * dstatus_turntable : 2
         * dstatus_b_coin : 2
         * version : 1.0.5
         * url : null
         * login_expired : 0
         * kdInfo : {"id":"3","picture":"http://img.8gu.com/slk/images/201712/201712281431027985.png","pic_proportion":"2.385","link":"666","link_url":"","link_type":"2","pt":"3"}
         * on_off  开关
         */

        private String is_realease;
        private String update_type;
        private String update_info;
        private String is_guessNotOpen;
        private String dstatus_turntable;
        private String dstatus_b_coin;
        private String version;
        private String url;
        private String on_off;

        public String getOn_off() {
            return on_off;
        }

        public void setOn_off(String on_off) {
            this.on_off = on_off;
        }

        private int login_expired;
        private KdInfoBean kdInfo;

        public String getIs_realease() {
            return is_realease;
        }

        public void setIs_realease(String is_realease) {
            this.is_realease = is_realease;
        }

        public String getUpdate_type() {
            return update_type;
        }

        public void setUpdate_type(String update_type) {
            this.update_type = update_type;
        }

        public String getUpdate_info() {
            return update_info;
        }

        public void setUpdate_info(String update_info) {
            this.update_info = update_info;
        }

        public String getIs_guessNotOpen() {
            return is_guessNotOpen;
        }

        public void setIs_guessNotOpen(String is_guessNotOpen) {
            this.is_guessNotOpen = is_guessNotOpen;
        }

        public String getDstatus_turntable() {
            return dstatus_turntable;
        }

        public void setDstatus_turntable(String dstatus_turntable) {
            this.dstatus_turntable = dstatus_turntable;
        }

        public String getDstatus_b_coin() {
            return dstatus_b_coin;
        }

        public void setDstatus_b_coin(String dstatus_b_coin) {
            this.dstatus_b_coin = dstatus_b_coin;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getLogin_expired() {
            return login_expired;
        }

        public void setLogin_expired(int login_expired) {
            this.login_expired = login_expired;
        }

        public KdInfoBean getKdInfo() {
            return kdInfo;
        }

        public void setKdInfo(KdInfoBean kdInfo) {
            this.kdInfo = kdInfo;
        }

        public static class KdInfoBean {
            /**
             * id : 3
             * picture : http://img.8gu.com/slk/images/201712/201712281431027985.png
             * pic_proportion : 2.385
             * link : 666
             * link_url :
             * link_type : 2
             * pt : 3
             */

            private String id;
            private String picture;
            private String pic_proportion;
            private String link;
            private String link_url;
            private String link_type;
            private String pt;
            private String v_type;

            public String getV_type() {
                return v_type;
            }

            public void setV_type(String v_type) {
                this.v_type = v_type;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getPic_proportion() {
                return pic_proportion;
            }

            public void setPic_proportion(String pic_proportion) {
                this.pic_proportion = pic_proportion;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getLink_url() {
                return link_url;
            }

            public void setLink_url(String link_url) {
                this.link_url = link_url;
            }

            public String getLink_type() {
                return link_type;
            }

            public void setLink_type(String link_type) {
                this.link_type = link_type;
            }

            public String getPt() {
                return pt;
            }

            public void setPt(String pt) {
                this.pt = pt;
            }
        }
    }
}
