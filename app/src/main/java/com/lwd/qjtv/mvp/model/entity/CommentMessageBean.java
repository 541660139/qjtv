package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/5.
 */

public class CommentMessageBean {

    /**
     * msg : success
     * status : 1
     * data : {"comments_list":[{"id":"2","from_uid":"2","video_id":"2","content":"好看的视频","ctime":"3天前","mtime":"0","dstatus":"1","ip":"湖北省武汉市","appid":"0","like_num":"0","v_type":"0","username":"kafuka","area":"湖北省武汉市","avater":"http://img.ttkhj.3z.cc/ttys/user_profile/201703/201703281352252202.jpeg","is_liked":0},{"id":"1","from_uid":"2","video_id":"2","content":"好看的视频","ctime":"3天前","mtime":"0","dstatus":"1","ip":"湖北省武汉市","appid":"0","like_num":"1","v_type":"0","username":"kafuka","area":"湖北省武汉市","avater":"http://img.ttkhj.3z.cc/ttys/user_profile/201703/201703281352252202.jpeg","is_liked":1}],"c_count":"2"}
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
         * comments_list : [{"id":"2","from_uid":"2","video_id":"2","content":"好看的视频","ctime":"3天前","mtime":"0","dstatus":"1","ip":"湖北省武汉市","appid":"0","like_num":"0","v_type":"0","username":"kafuka","area":"湖北省武汉市","avater":"http://img.ttkhj.3z.cc/ttys/user_profile/201703/201703281352252202.jpeg","is_liked":0},{"id":"1","from_uid":"2","video_id":"2","content":"好看的视频","ctime":"3天前","mtime":"0","dstatus":"1","ip":"湖北省武汉市","appid":"0","like_num":"1","v_type":"0","username":"kafuka","area":"湖北省武汉市","avater":"http://img.ttkhj.3z.cc/ttys/user_profile/201703/201703281352252202.jpeg","is_liked":1}]
         * c_count : 2
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
             * video_id : 2
             * content : 好看的视频
             * ctime : 3天前
             * mtime : 0
             * dstatus : 1
             * ip : 湖北省武汉市
             * appid : 0
             * like_num : 0
             * v_type : 0
             * username : kafuka
             * area : 湖北省武汉市
             * avater : http://img.ttkhj.3z.cc/ttys/user_profile/201703/201703281352252202.jpeg
             * is_liked : 0
             */

            private String id;
            private String from_uid;
            private String video_id;
            private String content;
            private String ctime;
            private String mtime;
            private String dstatus;
            private String ip;
            private String appid;
            private String like_num;
            private String v_type;
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

            public String getVideo_id() {
                return video_id;
            }

            public void setVideo_id(String video_id) {
                this.video_id = video_id;
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

            public String getV_type() {
                return v_type;
            }

            public void setV_type(String v_type) {
                this.v_type = v_type;
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
