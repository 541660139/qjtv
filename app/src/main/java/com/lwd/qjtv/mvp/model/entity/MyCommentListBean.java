package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by ZhengQian on 2018/1/31.
 * E-mail : wwwiiivip@yeah.net
 * If anyelse things , mail to it . I'll be soon to reply .
 */

public class MyCommentListBean {

    /**
     * msg : ok
     * status : 1
     * data : [{"card_id":"11","username":"u107018","avater":"http://img.8gu.com/slk/user_profile/avatars/5.jpg","uid":"7187","ctime":"2018-01-25 11:48:42","content":"这里是评论这里是评论这里是评论7","title":"帖子标题777","pic_h":"http://img.8gu.com/slk/images/201801/201801221738412986.png"},{"card_id":"11","username":"超管110","avater":"http://img.8gu.com/slk/user_profile/201712/201712140000571501.jpeg","uid":"8","ctime":"2018-01-25 11:47:08","content":"这里是评论这里是评论这里是评论7","title":"帖子标题777","pic_h":"http://img.8gu.com/slk/images/201801/201801221738412986.png"},{"card_id":"10","username":"超管110","avater":"http://img.8gu.com/slk/user_profile/201712/201712140000571501.jpeg","uid":"8","ctime":"2018-01-25 11:47:06","content":"这里是评论这里是评论这里是评论5","title":"帖子标题666666","pic_h":"http://img.8gu.com/slk/images/201801/201801221738412986.png"},{"card_id":"11","username":"u107018","avater":"http://img.8gu.com/slk/user_profile/avatars/5.jpg","uid":"7187","ctime":"2018-01-25 11:47:03","content":"这里是评论这里是评论这里是评论3","title":"帖子标题777","pic_h":"http://img.8gu.com/slk/images/201801/201801221738412986.png"}]
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
         * card_id : 11
         * username : u107018
         * avater : http://img.8gu.com/slk/user_profile/avatars/5.jpg
         * uid : 7187
         * ctime : 2018-01-25 11:48:42
         * content : 这里是评论这里是评论这里是评论7
         * title : 帖子标题777
         * pic_h : http://img.8gu.com/slk/images/201801/201801221738412986.png
         */

        private String card_id;
        private String username;
        private String avater;
        private String uid;
        private String ctime;
        private String content;
        private String title;
        private String pic_h;

        public String getCard_id() {
            return card_id;
        }

        public void setCard_id(String card_id) {
            this.card_id = card_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAvater() {
            return avater;
        }

        public void setAvater(String avater) {
            this.avater = avater;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic_h() {
            return pic_h;
        }

        public void setPic_h(String pic_h) {
            this.pic_h = pic_h;
        }
    }
}
