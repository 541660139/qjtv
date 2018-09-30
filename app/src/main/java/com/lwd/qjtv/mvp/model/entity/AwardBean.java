package com.lwd.qjtv.mvp.model.entity;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/8/17.
 */

public class AwardBean {


    /**
     * msg : success
     * status : 1
     * data : {"memberLevel":5,"awrad_id":5,"award_num":8,"award_pool":1000000}
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
         * memberLevel : 5
         * awrad_id : 5
         * award_num : 8
         * award_pool : 1000000
         */

        private int memberLevel;
        private int awrad_id;
        private int award_num;
        private int award_pool;
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
    }
}
