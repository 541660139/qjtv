package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by ZhengQian on 2018/1/31.
 * E-mail : wwwiiivip@yeah.net
 * If anyelse things , mail to it . I'll be soon to reply .
 */

public class MyLikeListBean {

    /**
     * msg : ok
     * status : 1
     * data : {"avater":[{"avater":["http://img.8gu.com/slk/user_profile/201711/201711231612442522.jpeg","http://img.8gu.com/slk/user_profile/avatars/5.jpg"],"msg":"u100000 等2位用户赞了你的帖子","ctime":"2018-01-29 14:05:11","title":"帖子标题666666","pic_h":"http://img.8gu.com/slk/images/201801/201801221738412986.png","card_id":"10"},{"avater":["http://img.8gu.com/slk/user_profile/201712/201712140000571501.jpeg"],"msg":"超管110 等1位用户赞了你的帖子","ctime":"2018-01-29 14:05:12","title":"帖子标题777","pic_h":"http://img.8gu.com/slk/images/201801/201801221738412986.png","card_id":"11"}]}
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
        private List<AvaterBean> avater;

        public List<AvaterBean> getAvater() {
            return avater;
        }

        public void setAvater(List<AvaterBean> avater) {
            this.avater = avater;
        }

        public static class AvaterBean {
            /**
             * avater : ["http://img.8gu.com/slk/user_profile/201711/201711231612442522.jpeg","http://img.8gu.com/slk/user_profile/avatars/5.jpg"]
             * msg : u100000 等2位用户赞了你的帖子
             * ctime : 2018-01-29 14:05:11
             * title : 帖子标题666666
             * pic_h : http://img.8gu.com/slk/images/201801/201801221738412986.png
             * card_id : 10
             */

            private String msg;
            private String ctime;
            private String title;
            private String pic_h;
            private String card_id;
            private List<String> avater;

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
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

            public String getCard_id() {
                return card_id;
            }

            public void setCard_id(String card_id) {
                this.card_id = card_id;
            }

            public List<String> getAvater() {
                return avater;
            }

            public void setAvater(List<String> avater) {
                this.avater = avater;
            }
        }
    }
}
