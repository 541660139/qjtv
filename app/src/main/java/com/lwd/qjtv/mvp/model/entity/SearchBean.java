package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/10.
 */

public class SearchBean {

    /**
     * msg : success
     * status : 1
     * data : [{"id":"644","name":"请回答1988","matchPeople":"丁俊辉vs奥沙利文","score":"9.5","pic_h":"http://img.ttkhj.3z.cc/upload/video_img/201704/201704011009202007.jpg","v_type":"1"},{"id":"597","name":"未生","matchPeople":"丁俊辉vs奥沙利文","score":"9.3","pic_h":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703091749156601.jpg","v_type":"1"},{"id":"176","name":"未生-已删除","matchPeople":"丁俊辉vs奥沙利文","score":"9.3","pic_h":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703091632004819.jpg","v_type":"2"},{"id":"632","name":"芝加哥打字机","matchPeople":"丁俊辉vs奥沙利文","score":"9.2","pic_h":"http://img.ttkhj.3z.cc/upload/video_img/201704/201704081105503713.jpg","v_type":"1"},{"id":"518","name":"信号","matchPeople":"丁俊辉vs奥沙利文","score":"9.1","pic_h":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703031419076254.jpg","v_type":"1"},{"id":"567","name":"请回答1997","matchPeople":"丁俊辉vs奥沙利文","score":"9.1","pic_h":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703031431438954.jpg","v_type":"1"},{"id":"484","name":"太阳的后裔","matchPeople":"丁俊辉vs奥沙利文","score":"9.1","pic_h":"http://img.ttkhj.3z.cc/upload/video_img/48712679a8365e07cb4e0fd94ae4864a.png","v_type":"1"},{"id":"517","name":"心里的声音","matchPeople":"丁俊辉vs奥沙利文","score":"9.0","pic_h":"http://img.ttkhj.3z.cc/upload/video_img/432b02ccc0586deb5acf66d1a19b7501.jpg","v_type":"1"},{"id":"559","name":"特殊失踪专案组M","matchPeople":"丁俊辉vs奥沙利文","score":"9.0","pic_h":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703011002122099.jpg","v_type":"1"},{"id":"114","name":"我亲爱的朋友们","matchPeople":"丁俊辉vs奥沙利文","score":"9.0","pic_h":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703031556432062.jpg","v_type":"2"}]
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
         * id : 644
         * name : 请回答1988
         * matchPeople : 丁俊辉vs奥沙利文
         * score : 9.5
         * pic_h : http://img.ttkhj.3z.cc/upload/video_img/201704/201704011009202007.jpg
         * v_type : 1
         */

        private String id;
        private String name;
        private String matchPeople;
        private String score;
        private String pic_h;
        private String v_type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMatchPeople() {
            return matchPeople;
        }

        public void setMatchPeople(String matchPeople) {
            this.matchPeople = matchPeople;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getPic_h() {
            return pic_h;
        }

        public void setPic_h(String pic_h) {
            this.pic_h = pic_h;
        }

        public String getV_type() {
            return v_type;
        }

        public void setV_type(String v_type) {
            this.v_type = v_type;
        }
    }
}
