package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by ZhengQian on 2018/2/1.
 * E-mail : wwwiiivip@yeah.net
 * If anyelse things , mail to it . I'll be soon to reply .
 */

public class BBSNewsListBean {


    /**
     * msg : success
     * status : 1
     * data : [{"id":"6","author":"jiemian","title":"界面发布2018中国最富1000人","ctime":"2018-01-31","click":"0","pic":["http://img.8gu.com/slk/news/201801/20180131103552_7821.jpg"],"is_more":"0"},{"id":"5","author":"jiemian","title":"界面发布2018中国最富1000人","ctime":"2018-01-31","click":"0","pic":["http://img.8gu.com/slk/news/201801/20180131103552_7821.jpg"],"is_more":"0"},{"id":"4","author":"jiemian","title":"界面发布2018中国最富1000人","ctime":"2018-01-31","click":"3","pic":["http://img.8gu.com/slk/news/201801/20180131103552_7821.jpg","http://img.8gu.com/slk/news/201801/20180131103552_7821.jpg","http://img.8gu.com/slk/news/201801/20180131103552_7821.jpg"],"is_more":"1"},{"id":"3","author":"jiemian","title":"界面发布2018中国最富1000人","ctime":"2018-01-31","click":"0","pic":["http://img.8gu.com/slk/news/201801/20180131103552_7821.jpg"],"is_more":"0"},{"id":"2","author":"jiemian","title":"界面发布2018中国最富1000人","ctime":"2018-01-31","click":"0","pic":["http://img.8gu.com/slk/news/201802/20180201140818_6692.jpg","http://img.8gu.com/slk/news/201802/20180201142507_2701.jpg","http://img.8gu.com/slk/news/201802/20180201142629_1674.png"],"is_more":"1"}]
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
         * id : 6
         * author : jiemian
         * title : 界面发布2018中国最富1000人
         * ctime : 2018-01-31
         * click : 0
         * pic : ["http://img.8gu.com/slk/news/201801/20180131103552_7821.jpg"]
         * is_more : 0
         */

        private String id;
        private String author;
        private String title;
        private String ctime;
        private String click;
        private String is_more;
        private List<String> pic;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getClick() {
            return click;
        }

        public void setClick(String click) {
            this.click = click;
        }

        public String getIs_more() {
            return is_more;
        }

        public void setIs_more(String is_more) {
            this.is_more = is_more;
        }

        public List<String> getPic() {
            return pic;
        }

        public void setPic(List<String> pic) {
            this.pic = pic;
        }
    }
}
