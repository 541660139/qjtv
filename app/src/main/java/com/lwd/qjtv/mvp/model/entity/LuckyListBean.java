package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/8/17.
 */

public class LuckyListBean {


    /**
     * msg : success
     * status : 1
     * data : {"awrad_id":1,"award_num":0,"award_pool":1000000,"user_type":1,"award_list":[{"id":"1","name":"经验20","prize":"20","probability":"40.0000","img":"经验.png"},{"id":"2","name":"积分10","prize":"10","probability":"20.0000","img":"积分.png"},{"id":"3","name":"经验100","prize":"100","probability":"23.2200","img":"经验.png"},{"id":"4","name":"积分100","prize":"100","probability":"3.0000","img":"积分.png"},{"id":"5","name":"经验200","prize":"200","probability":"11.5000","img":"经验.png"},{"id":"6","name":"积分1000","prize":"1000","probability":"0.2500","img":"积分.png"},{"id":"7","name":"经验1000","prize":"1000","probability":"2.0000","img":"经验.png"},{"id":"8","name":"积分10000","prize":"10000","probability":"0.0300","img":"积分.png"}]}
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
         * awrad_id : 1
         * award_num : 0
         * award_pool : 1000000
         * user_type : 1
         * award_list : [{"id":"1","name":"经验20","prize":"20","probability":"40.0000","img":"经验.png"},{"id":"2","name":"积分10","prize":"10","probability":"20.0000","img":"积分.png"},{"id":"3","name":"经验100","prize":"100","probability":"23.2200","img":"经验.png"},{"id":"4","name":"积分100","prize":"100","probability":"3.0000","img":"积分.png"},{"id":"5","name":"经验200","prize":"200","probability":"11.5000","img":"经验.png"},{"id":"6","name":"积分1000","prize":"1000","probability":"0.2500","img":"积分.png"},{"id":"7","name":"经验1000","prize":"1000","probability":"2.0000","img":"经验.png"},{"id":"8","name":"积分10000","prize":"10000","probability":"0.0300","img":"积分.png"}]
         */

        private int awrad_id;
        private int award_num;
        private int award_pool;
        private int user_type;
        private int memberLevel;
        private int b_coin;

        public int getB_coin() {
            return b_coin;
        }

        public void setB_coin(int b_coin) {
            this.b_coin = b_coin;
        }

        public int getMemberLevel() {
            return memberLevel;
        }

        public void setMemberLevel(int memberLevel) {
            this.memberLevel = memberLevel;
        }

        private List<AwardListBean> award_list;

        public int getAwrad_id() {
            return awrad_id;
        }

        public void setAwrad_id(int awrad_id) {
            this.awrad_id = awrad_id;
        }

        public int getAward_num() {
            return award_num;
        }

        public void setAward_num(int award_num) {
            this.award_num = award_num;
        }

        public int getAward_pool() {
            return award_pool;
        }

        public void setAward_pool(int award_pool) {
            this.award_pool = award_pool;
        }

        public int getUser_type() {
            return user_type;
        }

        public void setUser_type(int user_type) {
            this.user_type = user_type;
        }

        public List<AwardListBean> getAward_list() {
            return award_list;
        }

        public void setAward_list(List<AwardListBean> award_list) {
            this.award_list = award_list;
        }

        public static class AwardListBean {
            /**
             * id : 1
             * name : 经验20
             * prize : 20
             * probability : 40.0000
             * img : 经验.png
             */

            private String id;
            private String name;
            private String prize;
            private String probability;
            private String img;

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

            public String getPrize() {
                return prize;
            }

            public void setPrize(String prize) {
                this.prize = prize;
            }

            public String getProbability() {
                return probability;
            }

            public void setProbability(String probability) {
                this.probability = probability;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
