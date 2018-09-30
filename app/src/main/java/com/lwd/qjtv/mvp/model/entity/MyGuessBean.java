package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/28.
 */

public class MyGuessBean {

    /**
     * msg : success
     * status : 1
     * data : {"bet_record":{"all_total":"3","win_total":"1","lj_total":"1","win_odds":"33%","profit_loss":5},"tz_detail":[{"id":"2","user_id":"2","match_id":"6","wager":"200","isWin":"1","matcheName":"世锦赛决赛","startTime":"2017年05月18日 05:30","a_win_num":"5","b_win_num":"6","peilv":"1.68","star_info":[{"id":"1","name":"柳德焕","thumb_img":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703161603467643.jpg?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"3","name":"李敏镐","thumb_img":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703031057259884.jpg?x-oss-process=image/resize,m_fill,h_150,w_150"}]},{"id":"3","user_id":"2","match_id":"6","wager":"200","isWin":"2","matcheName":"世锦赛决赛","startTime":"2017年05月18日 05:30","a_win_num":"5","b_win_num":"6","peilv":"1.68","star_info":[{"id":"1","name":"柳德焕","thumb_img":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703161603467643.jpg?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"3","name":"李敏镐","thumb_img":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703031057259884.jpg?x-oss-process=image/resize,m_fill,h_150,w_150"}]},{"id":"4","user_id":"2","match_id":"6","wager":"200","isWin":"0","matcheName":"世锦赛决赛","startTime":"2017年05月18日 05:30","a_win_num":"5","b_win_num":"6","peilv":"1.68","star_info":[{"id":"1","name":"柳德焕","thumb_img":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703161603467643.jpg?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"3","name":"李敏镐","thumb_img":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703031057259884.jpg?x-oss-process=image/resize,m_fill,h_150,w_150"}]}]}
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
         * bet_record : {"all_total":"3","win_total":"1","lj_total":"1","win_odds":"33%","profit_loss":5}
         * tz_detail : [{"id":"2","user_id":"2","match_id":"6","wager":"200","isWin":"1","matcheName":"世锦赛决赛","startTime":"2017年05月18日 05:30","a_win_num":"5","b_win_num":"6","peilv":"1.68","star_info":[{"id":"1","name":"柳德焕","thumb_img":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703161603467643.jpg?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"3","name":"李敏镐","thumb_img":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703031057259884.jpg?x-oss-process=image/resize,m_fill,h_150,w_150"}]},{"id":"3","user_id":"2","match_id":"6","wager":"200","isWin":"2","matcheName":"世锦赛决赛","startTime":"2017年05月18日 05:30","a_win_num":"5","b_win_num":"6","peilv":"1.68","star_info":[{"id":"1","name":"柳德焕","thumb_img":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703161603467643.jpg?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"3","name":"李敏镐","thumb_img":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703031057259884.jpg?x-oss-process=image/resize,m_fill,h_150,w_150"}]},{"id":"4","user_id":"2","match_id":"6","wager":"200","isWin":"0","matcheName":"世锦赛决赛","startTime":"2017年05月18日 05:30","a_win_num":"5","b_win_num":"6","peilv":"1.68","star_info":[{"id":"1","name":"柳德焕","thumb_img":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703161603467643.jpg?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"3","name":"李敏镐","thumb_img":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703031057259884.jpg?x-oss-process=image/resize,m_fill,h_150,w_150"}]}]
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
             * all_total : 3
             * win_total : 1
             * lj_total : 1
             * win_odds : 33%
             * profit_loss : 5
             */

            private String all_total;
            private String win_total;
            private String lj_total;
            private String win_odds;
            private int profit_loss;

            public String getAll_total() {
                return all_total;
            }

            public void setAll_total(String all_total) {
                this.all_total = all_total;
            }

            public String getWin_total() {
                return win_total;
            }

            public void setWin_total(String win_total) {
                this.win_total = win_total;
            }

            public String getLj_total() {
                return lj_total;
            }

            public void setLj_total(String lj_total) {
                this.lj_total = lj_total;
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
             * id : 2
             * user_id : 2
             * match_id : 6
             * wager : 200
             * isWin : 1
             * matcheName : 世锦赛决赛
             * startTime : 2017年05月18日 05:30
             * a_win_num : 5
             * b_win_num : 6
             * peilv : 1.68
             * star_info : [{"id":"1","name":"柳德焕","thumb_img":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703161603467643.jpg?x-oss-process=image/resize,m_fill,h_150,w_150"},{"id":"3","name":"李敏镐","thumb_img":"http://img.ttkhj.3z.cc/upload/video_img/201703/201703031057259884.jpg?x-oss-process=image/resize,m_fill,h_150,w_150"}]
             */

            private String id;
            private String user_id;
            private String match_id;
            private String wager;
            private String isWin;
            private String matcheName;
            private String startTime;
            private String a_win_num;
            private String b_win_num;
            private String peilv;
            private List<StarInfoBean> star_info;

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

            public String getMatcheName() {
                return matcheName;
            }

            public void setMatcheName(String matcheName) {
                this.matcheName = matcheName;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
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

            public String getPeilv() {
                return peilv;
            }

            public void setPeilv(String peilv) {
                this.peilv = peilv;
            }

            public List<StarInfoBean> getStar_info() {
                return star_info;
            }

            public void setStar_info(List<StarInfoBean> star_info) {
                this.star_info = star_info;
            }

            public static class StarInfoBean {
                /**
                 * id : 1
                 * name : 柳德焕
                 * thumb_img : http://img.ttkhj.3z.cc/upload/video_img/201703/201703161603467643.jpg?x-oss-process=image/resize,m_fill,h_150,w_150
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
