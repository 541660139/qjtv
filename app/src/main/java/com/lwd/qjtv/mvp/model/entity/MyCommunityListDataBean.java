package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by ZhengQian on 2018/1/29.
 * E-mail : wwwiiivip@yeah.net
 * If anyelse things , mail to it . I'll be soon to reply .
 */

public class MyCommunityListDataBean {


    /**
     * msg : success
     * status : 1
     * data : [{"id":"64","vid":"1","uid":"7193","title":"功能","main_txt":"H游。戏嘻嘻我婆婆送哦","pic_h":["http://img.8gu.com/slk/user_profile/201802/201802081413258675.JPEG"],"num_like":"0","num_msg":"0","ctime":"1518070406","ntime":"0","dstatus":"1","rank":""},{"id":"27","vid":"2","uid":"7193","title":"Djdjdhdhdndndn","main_txt":"Djdjdndnnddndndndnn","pic_h":["http://img.8gu.com/slk/user_profile/201802/201802031657004933.JPEG"],"num_like":"0","num_msg":"0","ctime":"1517648225","ntime":"0","dstatus":"1","rank":""},{"id":"25","vid":"2","uid":"7193","title":"帖子标题2","main_txt":"这里是内容内容2","pic_h":["http://img.8gu.com/slk/images/201801/201801221738412986.png","http://img.8gu.com/slk/images/201801/201801221732026799.png"],"num_like":"0","num_msg":"0","ctime":"1517469620","ntime":"0","dstatus":"1","rank":"1"},{"id":"24","vid":"1","uid":"7193","title":"帖子标题1","main_txt":"这里是内容内容1","pic_h":["http://img.8gu.com/slk/images/201801/201801221738412986.png","http://img.8gu.com/slk/images/201801/201801221732026799.png"],"num_like":"0","num_msg":"0","ctime":"1517469611","ntime":"0","dstatus":"1","rank":""}]
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
         * id : 64
         * vid : 1
         * uid : 7193
         * title : 功能
         * main_txt : H游。戏嘻嘻我婆婆送哦
         * pic_h : ["http://img.8gu.com/slk/user_profile/201802/201802081413258675.JPEG"]
         * num_like : 0
         * num_msg : 0
         * ctime : 1518070406
         * ntime : 0
         * dstatus : 1
         * rank :
         */

        private String id;
        private String vid;
        private String uid;
        private String title;
        private String main_txt;
        private String num_like;
        private String num_msg;
        private String ctime;
        private String ntime;
        private String dstatus;
        private String rank;
        private List<String> pic_h;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMain_txt() {
            return main_txt;
        }

        public void setMain_txt(String main_txt) {
            this.main_txt = main_txt;
        }

        public String getNum_like() {
            return num_like;
        }

        public void setNum_like(String num_like) {
            this.num_like = num_like;
        }

        public String getNum_msg() {
            return num_msg;
        }

        public void setNum_msg(String num_msg) {
            this.num_msg = num_msg;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getNtime() {
            return ntime;
        }

        public void setNtime(String ntime) {
            this.ntime = ntime;
        }

        public String getDstatus() {
            return dstatus;
        }

        public void setDstatus(String dstatus) {
            this.dstatus = dstatus;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public List<String> getPic_h() {
            return pic_h;
        }

        public void setPic_h(List<String> pic_h) {
            this.pic_h = pic_h;
        }
    }
}
