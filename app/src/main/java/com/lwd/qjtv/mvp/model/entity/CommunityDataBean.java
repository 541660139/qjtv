package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by DELL on 2018/8/27.
 */

public class CommunityDataBean {

    /**
     * msg : success
     * status : 1
     * data : [{"post_id":"5","title":"下周就发？华为Mate 20 Lite新渲染图曝光","picture":"http://m.fangxiaotao.cn/ymq/news/7.jpg","create_time":"2018-08-23","is_follow":0,"uid":"7","username":"u150429","avater":"http://img.8gu.com/slk/user_profile/avatars/2.jpg"},{"post_id":"4","title":"曝41岁妖刀正认真考虑退役 马刺还有一次机会","picture":"http://m.fangxiaotao.cn/ymq/news/6.jpg","create_time":"2018-08-23","is_follow":0,"uid":"6","username":"啦啦啦啦","avater":"http://img.8gu.com/slk/user_profile/avatars/3.jpg"},{"post_id":"3","title":"中国研世界最大4米直径反射镜头 超美军最强侦察卫星","picture":"http://m.fangxiaotao.cn/ymq/news/3.jpg","create_time":"2018-08-22","is_follow":0,"uid":"4","username":"u150426","avater":"http://img.8gu.com/slk/user_profile/avatars/6.jpg"},{"post_id":"2","title":"太白旗悬叛将头：解放军如何迅速打赢台北城市巷战","picture":"http://m.fangxiaotao.cn/ymq/news/2.png","create_time":"2018-08-22","is_follow":0,"uid":"3","username":"横说竖说","avater":"http://img.8gu.com/slk/user_profile/avatars/6.jpg"},{"post_id":"1","title":"做好宣传思想工作，习近平提出要因势而谋应势而动顺势而为","picture":"http://m.fangxiaotao.cn/ymq/news/1.jpg,http://m.fangxiaotao.cn/ymq/news/4.jpg,http://m.fangxiaotao.cn/ymq/news/5.jpg","create_time":"2018-08-22","is_follow":0,"uid":"1","username":"u150423","avater":"http://img.8gu.com/slk/user_profile/avatars/1.jpg"}]
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
         * post_id : 5
         * title : 下周就发？华为Mate 20 Lite新渲染图曝光
         * picture : http://m.fangxiaotao.cn/ymq/news/7.jpg
         * create_time : 2018-08-23
         * is_follow : 0
         * uid : 7
         * username : u150429
         * avater : http://img.8gu.com/slk/user_profile/avatars/2.jpg
         */

        private String post_id;
        private String title;
        private String picture;
        private String create_time;
        private int is_follow;
        private String username;
        private String avater;
        private String star_id;

        public String getPost_id() {
            return post_id;
        }

        public void setPost_id(String post_id) {
            this.post_id = post_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getIs_follow() {
            return is_follow;
        }

        public void setIs_follow(int is_follow) {
            this.is_follow = is_follow;
        }

        public String getUid() {
            return star_id;
        }

        public void setUid(String uid) {
            this.star_id = uid;
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
    }
}
