package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by DELL on 2018/8/30.
 */

public class PublisDataBean {

    /**
     * msg : success
     * status : 1
     * data : [{"title":"太白旗悬叛将头：解放军如何迅速打赢台北城市巷战","picture":"http://m.fangxiaotao.cn/ymq/news/2.png","create_time":"2018-08-22"},{"title":"Bing bing shi zhen de you xiu","picture":"http://m.fangxiaotao.cn/ymq/user_profile/201808/201808271459133858.jpeg#http://m.fangxiaotao.cn/ymq/user_profile/201808/201808271459134023.jpeg#http://m.fangxiaotao.cn/ymq/user_profile/201808/201808271459131334.jpeg#http://m.fangxiaotao.cn/ymq/user_profile/201808/201808271459134029.jpeg#http://m.fangxiaotao.cn/ymq/user_profile/201808/201808271459132233.jpeg","create_time":"2018-08-27"},{"title":"Bing bing shi zhen de you xiu","picture":"http://m.fangxiaotao.cn/ymq/user_profile/201808/201808271501418115.jpeg,http://m.fangxiaotao.cn/ymq/user_profile/201808/201808271501416322.jpeg,http://m.fangxiaotao.cn/ymq/user_profile/201808/201808271501417647.jpeg,http://m.fangxiaotao.cn/ymq/user_profile/201808/201808271501417186.jpeg,http://m.fangxiaotao.cn/ymq/user_profile/201808/201808271501416335.jpeg,http://m.fangxiaotao.cn/ymq/user_profile/201808/201808271501419850.jpeg,http://m.fangxiaotao.cn/ymq/user_profile/201808/201808271501413456.jpeg,http://m.fangxiaotao.cn/ymq/user_profile/201808/201808271501417108.jpeg","create_time":"2018-08-27"},{"title":"Bing bing shi zhen de you xiu","picture":"http://m.fangxiaotao.cn/ymq/user_profile/201808/201808271511244854.jpeg,http://m.fangxiaotao.cn/ymq/user_profile/201808/201808271511246159.jpeg,http://m.fangxiaotao.cn/ymq/user_profile/201808/201808271511249960.jpeg,http://m.fangxiaotao.cn/ymq/user_profile/201808/201808271511243992.jpeg,http://m.fangxiaotao.cn/ymq/user_profile/201808/201808271511247682.jpeg,http://m.fangxiaotao.cn/ymq/user_profile/201808/201808271511242035.jpeg,http://m.fangxiaotao.cn/ymq/user_profile/201808/201808271511247227.jpeg,http://m.fangxiaotao.cn/ymq/user_profile/201808/201808271511241449.jpeg","create_time":"2018-08-27"},{"title":"Bing bing shi zhen de you xiu","picture":"http://m.fangxiaotao.cn/ymq/user_profile/201808/201808271517369956.jpeg","create_time":"2018-08-27"},{"title":"Safe facade","picture":"","create_time":"2018-08-29"}]
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
         * title : 太白旗悬叛将头：解放军如何迅速打赢台北城市巷战
         * picture : http://m.fangxiaotao.cn/ymq/news/2.png
         * create_time : 2018-08-22
         * isVisible
         * isSeleter
         * post_id
         */

        private String title;
        private String picture;
        private String create_time;
        private String post_id;
        private boolean isVisible;

        private boolean isSeleter;

        public String getPost_id() {
            return post_id;
        }

        public void setPost_id(String post_id) {
            this.post_id = post_id;
        }


        public boolean isSeleter() {
            return isSeleter;
        }

        public void setSeleter(boolean seleter) {
            isSeleter = seleter;
        }

        public boolean isVisible() {
            return isVisible;
        }

        public void setVisible(boolean visible) {
            isVisible = visible;
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
    }
}
