package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/8/1.
 */

public class ChampionGuess {

    /**
     * msg : success
     * status : 1
     * data : [{"player_id":"3","wager":"300","peilv":"2.60","isWin":"1","profit_loss":"780","ctime":"07月21日 17:23","player":"希金斯","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041031448646.png?x-oss-process=image/resize,m_fill,h_150,w_150","phaseMatch_id":"8进4","bigMatch_name":"2017香港大师赛"},{"player_id":"13","wager":"1000","peilv":"3.60","isWin":"2","profit_loss":"1000","ctime":"07月27日 20:07","player":"艾伦","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041500054661.png?x-oss-process=image/resize,m_fill,h_150,w_150","phaseMatch_id":"8进4","bigMatch_name":"2017香港大师赛"},{"player_id":"13","wager":"300","peilv":"3.60","isWin":"2","profit_loss":"300","ctime":"07月27日 21:32","player":"艾伦","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041500054661.png?x-oss-process=image/resize,m_fill,h_150,w_150","phaseMatch_id":"8进4","bigMatch_name":"2017香港大师赛"}]
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
         * player_id : 3
         * wager : 300
         * peilv : 2.60
         * isWin : 1
         * profit_loss : 780
         * ctime : 07月21日 17:23
         * player : 希金斯
         * thumb_img : http://img.ttkhj.3z.cc/slk/images/201707/201707041031448646.png?x-oss-process=image/resize,m_fill,h_150,w_150
         * phaseMatch_id : 8进4
         * bigMatch_name : 2017香港大师赛
         */

        private String player_id;
        private String wager;
        private String peilv;
        private String isWin;
        private String profit_loss;
        private String ctime;
        private String player;
        private String thumb_img;
        private String phaseMatch_id;
        private String bigMatch_name;

        public String getPlayer_id() {
            return player_id;
        }

        public void setPlayer_id(String player_id) {
            this.player_id = player_id;
        }

        public String getWager() {
            return wager;
        }

        public void setWager(String wager) {
            this.wager = wager;
        }

        public String getPeilv() {
            return peilv;
        }

        public void setPeilv(String peilv) {
            this.peilv = peilv;
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

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getPlayer() {
            return player;
        }

        public void setPlayer(String player) {
            this.player = player;
        }

        public String getThumb_img() {
            return thumb_img;
        }

        public void setThumb_img(String thumb_img) {
            this.thumb_img = thumb_img;
        }

        public String getPhaseMatch_id() {
            return phaseMatch_id;
        }

        public void setPhaseMatch_id(String phaseMatch_id) {
            this.phaseMatch_id = phaseMatch_id;
        }

        public String getBigMatch_name() {
            return bigMatch_name;
        }

        public void setBigMatch_name(String bigMatch_name) {
            this.bigMatch_name = bigMatch_name;
        }
    }
}
