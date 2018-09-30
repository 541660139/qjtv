package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/5.
 */

public class StarCommentBean {

    /**
     * msg : success
     * status : 1
     * data : {"comments_list":[{"id":"2","from_uid":"2","star_id":"2","content":"好赞的明星看","ctime":"37分钟前","mtime":"0","dstatus":"1","ip":"湖北省武汉市","appid":"0","like_num":"0","comments_num":"0","username":"kafuka","area":"湖北省武汉市","avater":"http://img.ttkhj.3z.cc/ttys/user_profile/201703/201703281352252202.jpeg","is_liked":0}],"c_count":"1"}
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
         * comments_list : [{"id":"2","from_uid":"2","star_id":"2","content":"好赞的明星看","ctime":"37分钟前","mtime":"0","dstatus":"1","ip":"湖北省武汉市","appid":"0","like_num":"0","comments_num":"0","username":"kafuka","area":"湖北省武汉市","avater":"http://img.ttkhj.3z.cc/ttys/user_profile/201703/201703281352252202.jpeg","is_liked":0}]
         * c_count : 1
         */

        private String c_count;
        private List<CommentsListBean> comments_list;

        public String getC_count() {
            return c_count;
        }

        public void setC_count(String c_count) {
            this.c_count = c_count;
        }

        public List<CommentsListBean> getComments_list() {
            return comments_list;
        }

        public void setComments_list(List<CommentsListBean> comments_list) {
            this.comments_list = comments_list;
        }

        public static class CommentsListBean {
            /**
             * id : 2
             * from_uid : 2
             * star_id : 2
             * content : 好赞的明星看
             * ctime : 37分钟前
             * mtime : 0
             * dstatus : 1
             * ip : 湖北省武汉市
             * appid : 0
             * like_num : 0
             * comments_num : 0
             * username : kafuka
             * area : 湖北省武汉市
             * avater : http://img.ttkhj.3z.cc/ttys/user_profile/201703/201703281352252202.jpeg
             * is_liked : 0
             */

            private String id;
            private String from_uid;
            private String star_id;
            private String content;
            private String ctime;
            private String mtime;
            private String dstatus;
            private String ip;
            private String appid;
            private String like_num;
            private String comments_num;
            private String username;
            private String area;
            private String avater;
            private int is_liked;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getFrom_uid() {
                return from_uid;
            }

            public void setFrom_uid(String from_uid) {
                this.from_uid = from_uid;
            }

            public String getStar_id() {
                return star_id;
            }

            public void setStar_id(String star_id) {
                this.star_id = star_id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getLike_num() {
                return like_num;
            }

            public void setLike_num(String like_num) {
                this.like_num = like_num;
            }

            public String getComments_num() {
                return comments_num;
            }

            public void setComments_num(String comments_num) {
                this.comments_num = comments_num;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getAvater() {
                return avater;
            }

            public void setAvater(String avater) {
                this.avater = avater;
            }

            public int getIs_liked() {
                return is_liked;
            }

            public void setIs_liked(int is_liked) {
                this.is_liked = is_liked;
            }
        }
    }
}
