package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/8/1.
 */

public class GuessMyBean {


    /**
     * msg : success
     * status : 1
     * data : {"bet_record":{"all_total":18,"win_total":7,"loss_total":11,"win_odds":"39%","profit_loss":1347},"tz_detail":[{"id":"13","user_id":"3","match_id":"7","wager":"500","isWin":"1","profit_loss":"1550","jc_type":"2","ctime":"08月07日 05:37","bigMatch_name":"2017香港大师赛","a_win_num":"7","b_win_num":"5","jc_name":">=12","starInfo":[{"id":"1","name":"奥沙利文","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041524356303.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"6","name":"傅家俊","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707051733416925.png?x-oss-process=image/resize,m_fill,h_150,w_150"}]},{"id":"11","user_id":"3","match_id":"5","wager":"1600","isWin":"1","profit_loss":"2400","jc_type":"1","ctime":"08月07日 04:45","bigMatch_name":"2017香港大师赛","a_win_num":"6","b_win_num":"5","jc_name":"胜","starInfo":[{"id":"1","name":"奥沙利文","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041524356303.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"5","name":"特鲁姆普","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041403424328.png?x-oss-process=image/resize,m_fill,h_150,w_150"}]},{"id":"10","user_id":"3","match_id":"5","wager":"400","isWin":"1","profit_loss":"560","jc_type":"2","ctime":"08月07日 04:45","bigMatch_name":"2017香港大师赛","a_win_num":"6","b_win_num":"5","jc_name":">=10","starInfo":[{"id":"1","name":"奥沙利文","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041524356303.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"5","name":"特鲁姆普","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041403424328.png?x-oss-process=image/resize,m_fill,h_150,w_150"}]},{"id":"9","user_id":"3","match_id":"5","wager":"600","isWin":"1","profit_loss":"900","jc_type":"1","ctime":"08月07日 04:45","bigMatch_name":"2017香港大师赛","a_win_num":"6","b_win_num":"5","jc_name":"胜","starInfo":[{"id":"1","name":"奥沙利文","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041524356303.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"5","name":"特鲁姆普","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041403424328.png?x-oss-process=image/resize,m_fill,h_150,w_150"}]},{"id":"5","user_id":"3","match_id":"2","wager":"600","isWin":"2","profit_loss":"600","jc_type":"2","ctime":"08月07日 02:23","bigMatch_name":"2017香港大师赛","a_win_num":"5","b_win_num":"1","jc_name":">=13","starInfo":[{"id":"6","name":"傅家俊","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707051733416925.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"8","name":"罗伯逊","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041446118649.png?x-oss-process=image/resize,m_fill,h_150,w_150"}]},{"id":"4","user_id":"3","match_id":"2","wager":"300","isWin":"2","profit_loss":"300","jc_type":"1","ctime":"08月07日 02:23","bigMatch_name":"2017香港大师赛","a_win_num":"5","b_win_num":"1","jc_name":"负","starInfo":[{"id":"6","name":"傅家俊","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707051733416925.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"8","name":"罗伯逊","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041446118649.png?x-oss-process=image/resize,m_fill,h_150,w_150"}]},{"id":"2","user_id":"3","match_id":"4","wager":"1000","isWin":"1","profit_loss":"2200","jc_type":"1","ctime":"08月07日 02:23","bigMatch_name":"2017香港大师赛","a_win_num":"4","b_win_num":"5","jc_name":"胜","starInfo":[{"id":"1","name":"奥沙利文","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041524356303.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"3","name":"希金斯","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041031448646.png?x-oss-process=image/resize,m_fill,h_150,w_150"}]}]}
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
         * bet_record : {"all_total":18,"win_total":7,"loss_total":11,"win_odds":"39%","profit_loss":1347}
         * tz_detail : [{"id":"13","user_id":"3","match_id":"7","wager":"500","isWin":"1","profit_loss":"1550","jc_type":"2","ctime":"08月07日 05:37","bigMatch_name":"2017香港大师赛","a_win_num":"7","b_win_num":"5","jc_name":">=12","starInfo":[{"id":"1","name":"奥沙利文","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041524356303.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"6","name":"傅家俊","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707051733416925.png?x-oss-process=image/resize,m_fill,h_150,w_150"}]},{"id":"11","user_id":"3","match_id":"5","wager":"1600","isWin":"1","profit_loss":"2400","jc_type":"1","ctime":"08月07日 04:45","bigMatch_name":"2017香港大师赛","a_win_num":"6","b_win_num":"5","jc_name":"胜","starInfo":[{"id":"1","name":"奥沙利文","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041524356303.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"5","name":"特鲁姆普","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041403424328.png?x-oss-process=image/resize,m_fill,h_150,w_150"}]},{"id":"10","user_id":"3","match_id":"5","wager":"400","isWin":"1","profit_loss":"560","jc_type":"2","ctime":"08月07日 04:45","bigMatch_name":"2017香港大师赛","a_win_num":"6","b_win_num":"5","jc_name":">=10","starInfo":[{"id":"1","name":"奥沙利文","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041524356303.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"5","name":"特鲁姆普","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041403424328.png?x-oss-process=image/resize,m_fill,h_150,w_150"}]},{"id":"9","user_id":"3","match_id":"5","wager":"600","isWin":"1","profit_loss":"900","jc_type":"1","ctime":"08月07日 04:45","bigMatch_name":"2017香港大师赛","a_win_num":"6","b_win_num":"5","jc_name":"胜","starInfo":[{"id":"1","name":"奥沙利文","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041524356303.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"5","name":"特鲁姆普","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041403424328.png?x-oss-process=image/resize,m_fill,h_150,w_150"}]},{"id":"5","user_id":"3","match_id":"2","wager":"600","isWin":"2","profit_loss":"600","jc_type":"2","ctime":"08月07日 02:23","bigMatch_name":"2017香港大师赛","a_win_num":"5","b_win_num":"1","jc_name":">=13","starInfo":[{"id":"6","name":"傅家俊","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707051733416925.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"8","name":"罗伯逊","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041446118649.png?x-oss-process=image/resize,m_fill,h_150,w_150"}]},{"id":"4","user_id":"3","match_id":"2","wager":"300","isWin":"2","profit_loss":"300","jc_type":"1","ctime":"08月07日 02:23","bigMatch_name":"2017香港大师赛","a_win_num":"5","b_win_num":"1","jc_name":"负","starInfo":[{"id":"6","name":"傅家俊","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707051733416925.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"8","name":"罗伯逊","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041446118649.png?x-oss-process=image/resize,m_fill,h_150,w_150"}]},{"id":"2","user_id":"3","match_id":"4","wager":"1000","isWin":"1","profit_loss":"2200","jc_type":"1","ctime":"08月07日 02:23","bigMatch_name":"2017香港大师赛","a_win_num":"4","b_win_num":"5","jc_name":"胜","starInfo":[{"id":"1","name":"奥沙利文","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041524356303.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"3","name":"希金斯","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041031448646.png?x-oss-process=image/resize,m_fill,h_150,w_150"}]}]
         */

        private BetRecordBean bet_record;
        private List<TzDetailBean> tz_detail;

        public BetRecordBean getBet_record() {
            return bet_record;
        }

        public void setBet_record(BetRecordBean bet_record) {
            this.bet_record = bet_record;
        }

        public List<TzDetailBean> getTz_detail() {
            return tz_detail;
        }

        public void setTz_detail(List<TzDetailBean> tz_detail) {
            this.tz_detail = tz_detail;
        }

        public static class BetRecordBean {
            /**
             * all_total : 18
             * win_total : 7
             * loss_total : 11
             * win_odds : 39%
             * profit_loss : 1347
             */

            private int all_total;
            private int win_total;
            private int loss_total;
            private String win_odds;
            private int profit_loss;

            public int getAll_total() {
                return all_total;
            }

            public void setAll_total(int all_total) {
                this.all_total = all_total;
            }

            public int getWin_total() {
                return win_total;
            }

            public void setWin_total(int win_total) {
                this.win_total = win_total;
            }

            public int getLoss_total() {
                return loss_total;
            }

            public void setLoss_total(int loss_total) {
                this.loss_total = loss_total;
            }

            public String getWin_odds() {
                return win_odds;
            }

            public void setWin_odds(String win_odds) {
                this.win_odds = win_odds;
            }

            public int getProfit_loss() {
                return profit_loss;
            }

            public void setProfit_loss(int profit_loss) {
                this.profit_loss = profit_loss;
            }
        }

        public static class TzDetailBean {
            /**
             * id : 13
             * user_id : 3
             * match_id : 7
             * wager : 500
             * isWin : 1
             * profit_loss : 1550
             * jc_type : 2
             * ctime : 08月07日 05:37
             * bigMatch_name : 2017香港大师赛
             * a_win_num : 7
             * b_win_num : 5
             * jc_name : >=12
             * starInfo : [{"id":"1","name":"奥沙利文","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041524356303.png?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"6","name":"傅家俊","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707051733416925.png?x-oss-process=image/resize,m_fill,h_150,w_150"}]
             */

            private String id;
            private String user_id;
            private String match_id;
            private String wager;
            private String isWin;
            private String profit_loss;
            private String jc_type;
            private String ctime;
            private String bigMatch_name;
            private String a_win_num;
            private String b_win_num;
            private String jc_name;

            public String getPeilv() {
                return peilv;
            }

            public void setPeilv(String peilv) {
                this.peilv = peilv;
            }

            private String peilv;
            private List<StarInfoBean> starInfo;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getMatch_id() {
                return match_id;
            }

            public void setMatch_id(String match_id) {
                this.match_id = match_id;
            }

            public String getWager() {
                return wager;
            }

            public void setWager(String wager) {
                this.wager = wager;
            }

            public String getIsWin() {
                return isWin;
            }

            public void setIsWin(String isWin) {
                this.isWin = isWin;
            }

            public String getProfit_loss() {
                return profit_loss;
            }

            public void setProfit_loss(String profit_loss) {
                this.profit_loss = profit_loss;
            }

            public String getJc_type() {
                return jc_type;
            }

            public void setJc_type(String jc_type) {
                this.jc_type = jc_type;
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

            public String getA_win_num() {
                return a_win_num;
            }

            public void setA_win_num(String a_win_num) {
                this.a_win_num = a_win_num;
            }

            public String getB_win_num() {
                return b_win_num;
            }

            public void setB_win_num(String b_win_num) {
                this.b_win_num = b_win_num;
            }

            public String getJc_name() {
                return jc_name;
            }

            public void setJc_name(String jc_name) {
                this.jc_name = jc_name;
            }

            public List<StarInfoBean> getStarInfo() {
                return starInfo;
            }

            public void setStarInfo(List<StarInfoBean> starInfo) {
                this.starInfo = starInfo;
            }

            public static class StarInfoBean {
                /**
                 * id : 1
                 * name : 奥沙利文
                 * thumb_img : http://img.ttkhj.3z.cc/slk/images/201707/201707041524356303.png?x-oss-process=image/resize,m_fill,h_150,w_150
                 */

                private String id;
                private String name;
                private String thumb_img;

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

                public String getThumb_img() {
                    return thumb_img;
                }

                public void setThumb_img(String thumb_img) {
                    this.thumb_img = thumb_img;
                }
            }
        }
    }
}
