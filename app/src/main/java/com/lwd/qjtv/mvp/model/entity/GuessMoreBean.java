package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/8/2.
 */

public class GuessMoreBean {


    /**
     * msg : success
     * status : 1
     * data : [{"isWin":"2","wager":"100","profit_loss":"100","ctime":"07月20日 21:26","bigMatch_name":"2017香港大师赛","tz_detail":[{"match_id":"1","jc_type":"1","starInfo":[{"id":"6","name":"傅家俊"},{"id":"12","name":"霍金斯"}],"tz_name":"6"},{"match_id":"1","jc_type":"2","starInfo":[{"id":"6","name":"傅家俊"},{"id":"12","name":"霍金斯"}],"tz_name":"7-12"}]},{"isWin":"2","wager":"300","profit_loss":"300","ctime":"07月24日 10:53","bigMatch_name":"2017香港大师赛","tz_detail":[{"match_id":"1","jc_type":"1","starInfo":[{"id":"6","name":"傅家俊"},{"id":"12","name":"霍金斯"}],"tz_name":"9"},{"match_id":"1","jc_type":"2","starInfo":[{"id":"6","name":"傅家俊"},{"id":"12","name":"霍金斯"}],"tz_name":"-"}]},{"isWin":"1","wager":"200","profit_loss":"512","ctime":"07月24日 17:53","bigMatch_name":"2017香港大师赛","tz_detail":[{"match_id":"7","jc_type":"2","starInfo":[{"id":"9","name":"墨菲"},{"id":"13","name":"艾伦"}],"tz_name":"7-12"},{"match_id":"7","jc_type":"2","starInfo":[{"id":"9","name":"墨菲"},{"id":"13","name":"艾伦"}],"tz_name":"7-12"}]},{"isWin":"0","wager":"600","profit_loss":"600","ctime":"07月24日 19:32","bigMatch_name":"2017香港大师赛","tz_detail":[{"match_id":"1","jc_type":"1","starInfo":[{"id":"6","name":"傅家俊"},{"id":"12","name":"霍金斯"}],"tz_name":"9"},{"match_id":"1","jc_type":"2","starInfo":[{"id":"6","name":"傅家俊"},{"id":"12","name":"霍金斯"}],"tz_name":"-"}]},{"isWin":"1","wager":"300","profit_loss":"972","ctime":"07月25日 16:10","bigMatch_name":"2017香港大师赛","tz_detail":[{"match_id":"1","jc_type":"1","starInfo":[{"id":"6","name":"傅家俊"},{"id":"12","name":"霍金斯"}],"tz_name":"12"},{"match_id":"1","jc_type":"2","starInfo":[{"id":"6","name":"傅家俊"},{"id":"12","name":"霍金斯"}],"tz_name":"13"}]},{"isWin":"0","wager":"200","profit_loss":"0","ctime":"07月28日 11:44","bigMatch_name":"2017香港大师赛","tz_detail":[{"match_id":"3","jc_type":"2","starInfo":[{"id":"5","name":"特鲁姆普"},{"id":"9","name":"墨菲"}],"tz_name":"7-12"}]}]
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
         * isWin : 2
         * wager : 100
         * profit_loss : 100
         * ctime : 07月20日 21:26
         * bigMatch_name : 2017香港大师赛
         * tz_detail : [{"match_id":"1","jc_type":"1","starInfo":[{"id":"6","name":"傅家俊"},{"id":"12","name":"霍金斯"}],"tz_name":"6"},{"match_id":"1","jc_type":"2","starInfo":[{"id":"6","name":"傅家俊"},{"id":"12","name":"霍金斯"}],"tz_name":"7-12"}]
         */

        private String isWin;
        private String wager;
        private String profit_loss;
        private String ctime;
        private String bigMatch_name;

        public String getPeilv() {
            return peilv;
        }

        public void setPeilv(String peilv) {
            this.peilv = peilv;
        }

        private String peilv;
        private List<TzDetailBean> tz_detail;

        public String getIsWin() {
            return isWin;
        }

        public void setIsWin(String isWin) {
            this.isWin = isWin;
        }

        public String getWager() {
            return wager;
        }

        public void setWager(String wager) {
            this.wager = wager;
        }

        public String getProfit_loss() {
            return profit_loss;
        }

        public void setProfit_loss(String profit_loss) {
            this.profit_loss = profit_loss;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getBigMatch_name() {
            return bigMatch_name;
        }

        public void setBigMatch_name(String bigMatch_name) {
            this.bigMatch_name = bigMatch_name;
        }

        public List<TzDetailBean> getTz_detail() {
            return tz_detail;
        }

        public void setTz_detail(List<TzDetailBean> tz_detail) {
            this.tz_detail = tz_detail;
        }

        public static class TzDetailBean {
            /**
             * match_id : 1
             * jc_type : 1
             * starInfo : [{"id":"6","name":"傅家俊"},{"id":"12","name":"霍金斯"}]
             * tz_name : 6
             */

            private String match_id;
            private String jc_type;
            private String tz_name;

            public String getPeilv() {
                return peilv;
            }

            public void setPeilv(String peilv) {
                this.peilv = peilv;
            }

            private String peilv;
            private List<StarInfoBean> starInfo;

            public String getMatch_id() {
                return match_id;
            }

            public void setMatch_id(String match_id) {
                this.match_id = match_id;
            }

            public String getJc_type() {
                return jc_type;
            }

            public void setJc_type(String jc_type) {
                this.jc_type = jc_type;
            }

            public String getTz_name() {
                return tz_name;
            }

            public void setTz_name(String tz_name) {
                this.tz_name = tz_name;
            }

            public List<StarInfoBean> getStarInfo() {
                return starInfo;
            }

            public void setStarInfo(List<StarInfoBean> starInfo) {
                this.starInfo = starInfo;
            }

            public static class StarInfoBean {
                /**
                 * id : 6
                 * name : 傅家俊
                 */

                private String id;
                private String name;

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
            }
        }
    }
}
