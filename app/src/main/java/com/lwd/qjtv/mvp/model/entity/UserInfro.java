package com.lwd.qjtv.mvp.model.entity;

/**
 * Created by DELL on 2018/8/28.
 */

public class UserInfro {

    /**
     * msg : success
     * status : 1
     * data : {"username":"u150427","avater":"http://img.8gu.com/slk/user_profile/avatars/1.jpg","follow_count":"7","fan_count":"1","my_publish_count":"1"}
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
         * username : u150427
         * avater : http://img.8gu.com/slk/user_profile/avatars/1.jpg
         * follow_count : 7
         * fan_count : 1
         * my_publish_count : 1
         */

        private String username;
        private String avater;
        private String follow_count;
        private String fan_count;
        private String my_publish_count;

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

        public String getFollow_count() {
            return follow_count;
        }

        public void setFollow_count(String follow_count) {
            this.follow_count = follow_count;
        }

        public String getFan_count() {
            return fan_count;
        }

        public void setFan_count(String fan_count) {
            this.fan_count = fan_count;
        }

        public String getMy_publish_count() {
            return my_publish_count;
        }

        public void setMy_publish_count(String my_publish_count) {
            this.my_publish_count = my_publish_count;
        }
    }
}
