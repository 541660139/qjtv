package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by DELL on 2018/8/29.
 */

public class FansOrFollerBean {

    /**
     * msg : success
     * status : 1
     * data : [{"username":"u150426","avater":"http://img.8gu.com/slk/user_profile/avatars/6.jpg","star_id":"4","is_follow":1},{"username":"横说竖说","avater":"http://img.8gu.com/slk/user_profile/avatars/6.jpg","star_id":"3","is_follow":1},{"username":"啦啦啦啦","avater":"http://img.8gu.com/slk/user_profile/avatars/3.jpg","star_id":"6","is_follow":1},{"username":"u150433","avater":"http://img.8gu.com/slk/user_profile/avatars/1.jpg","star_id":"11","is_follow":1},{"username":"u150432","avater":"http://img.8gu.com/slk/user_profile/avatars/5.jpg","star_id":"10","is_follow":1},{"username":"u150429","avater":"http://img.8gu.com/slk/user_profile/avatars/2.jpg","star_id":"7","is_follow":1},{"username":"u150423","avater":"http://img.8gu.com/slk/user_profile/avatars/1.jpg","star_id":"1","is_follow":1}]
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
         * username : u150426
         * avater : http://img.8gu.com/slk/user_profile/avatars/6.jpg
         * star_id : 4
         * is_follow : 1
         * fan_id
         */

        private String username;
        private String avater;
        private String star_id;
        private int is_follow;

        private String fan_id;

        public String getFan_id() {
            return fan_id;
        }

        public void setFan_id(String fan_id) {
            this.fan_id = fan_id;
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

        public String getStar_id() {
            return star_id;
        }

        public void setStar_id(String star_id) {
            this.star_id = star_id;
        }

        public int getIs_follow() {
            return is_follow;
        }

        public void setIs_follow(int is_follow) {
            this.is_follow = is_follow;
        }
    }
}
