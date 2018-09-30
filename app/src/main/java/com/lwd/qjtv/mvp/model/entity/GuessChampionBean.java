package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/18.
 */

public class GuessChampionBean {


    /**
     * msg : success
     * status : 1
     * data : {"bigMatch_name":"2017斯诺克世界杯","matchPlayer_list":[{"id":"801","name":"英格兰","avater":"http://img.ttkhj.3z.cc/slk/images/201707/201707081140561178.png","world_rank":"0","is_play":2,"player_peilv":"3.60","match_id":"70"},{"id":"802","name":"北爱尔兰","avater":"http://img.ttkhj.3z.cc/slk/images/201707/201707081141301594.png","world_rank":"0","is_play":2,"player_peilv":"1.60","match_id":"70"},{"id":"803","name":"泰国","avater":"http://img.ttkhj.3z.cc/slk/images/201707/201707081141535114.png","world_rank":"0","is_play":1,"player_peilv":"1.20","match_id":"71"},{"id":"804","name":"伊朗","avater":"http://img.ttkhj.3z.cc/slk/images/201707/201707081142032397.png","world_rank":"0","is_play":1,"player_peilv":"1.30","match_id":"71"},{"id":"805","name":"比利时","avater":"http://img.ttkhj.3z.cc/slk/images/201707/201707081142141494.png","world_rank":"0","is_play":2,"player_peilv":"1.60","match_id":"73"},{"id":"806","name":"中国A队","avater":"http://img.ttkhj.3z.cc/slk/images/201707/201707081142306808.png","world_rank":"0","is_play":2,"player_peilv":"1.68","match_id":"72"},{"id":"807","name":"中国B队","avater":"http://img.ttkhj.3z.cc/slk/images/201707/201707081142434586.png","world_rank":"0","is_play":2,"player_peilv":"3.20","match_id":"73"},{"id":"808","name":"威尔士","avater":"http://img.ttkhj.3z.cc/slk/images/201707/201707081142596567.png","world_rank":"0","is_play":2,"player_peilv":"1.86","match_id":"72"}]}
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
         * bigMatch_name : 2017斯诺克世界杯
         * matchPlayer_list : [{"id":"801","name":"英格兰","avater":"http://img.ttkhj.3z.cc/slk/images/201707/201707081140561178.png","world_rank":"0","is_play":2,"player_peilv":"3.60","match_id":"70"},{"id":"802","name":"北爱尔兰","avater":"http://img.ttkhj.3z.cc/slk/images/201707/201707081141301594.png","world_rank":"0","is_play":2,"player_peilv":"1.60","match_id":"70"},{"id":"803","name":"泰国","avater":"http://img.ttkhj.3z.cc/slk/images/201707/201707081141535114.png","world_rank":"0","is_play":1,"player_peilv":"1.20","match_id":"71"},{"id":"804","name":"伊朗","avater":"http://img.ttkhj.3z.cc/slk/images/201707/201707081142032397.png","world_rank":"0","is_play":1,"player_peilv":"1.30","match_id":"71"},{"id":"805","name":"比利时","avater":"http://img.ttkhj.3z.cc/slk/images/201707/201707081142141494.png","world_rank":"0","is_play":2,"player_peilv":"1.60","match_id":"73"},{"id":"806","name":"中国A队","avater":"http://img.ttkhj.3z.cc/slk/images/201707/201707081142306808.png","world_rank":"0","is_play":2,"player_peilv":"1.68","match_id":"72"},{"id":"807","name":"中国B队","avater":"http://img.ttkhj.3z.cc/slk/images/201707/201707081142434586.png","world_rank":"0","is_play":2,"player_peilv":"3.20","match_id":"73"},{"id":"808","name":"威尔士","avater":"http://img.ttkhj.3z.cc/slk/images/201707/201707081142596567.png","world_rank":"0","is_play":2,"player_peilv":"1.86","match_id":"72"}]
         */

        private String bigMatch_name;
        private String time_tip_msg;

        public String getTime_tip_msg() {
            return time_tip_msg;
        }

        public void setTime_tip_msg(String time_tip_msg) {
            this.time_tip_msg = time_tip_msg;
        }

        private List<MatchPlayerListBean> matchPlayer_list;

        public String getBigMatch_name() {
            return bigMatch_name;
        }

        public void setBigMatch_name(String bigMatch_name) {
            this.bigMatch_name = bigMatch_name;
        }

        public List<MatchPlayerListBean> getMatchPlayer_list() {
            return matchPlayer_list;
        }

        public void setMatchPlayer_list(List<MatchPlayerListBean> matchPlayer_list) {
            this.matchPlayer_list = matchPlayer_list;
        }

        public static class MatchPlayerListBean {
            /**
             * id : 801
             * name : 英格兰
             * avater : http://img.ttkhj.3z.cc/slk/images/201707/201707081140561178.png
             * world_rank : 0
             * is_play : 2
             * player_peilv : 3.60
             * match_id : 70
             */

            private String id;
            private String name;
            private String avater;
            private String world_rank;
            private int is_play;
            private String player_peilv;
            private String match_id;
            private String startTime;

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

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

            public String getAvater() {
                return avater;
            }

            public void setAvater(String avater) {
                this.avater = avater;
            }

            public String getWorld_rank() {
                return world_rank;
            }

            public void setWorld_rank(String world_rank) {
                this.world_rank = world_rank;
            }

            public int getIs_play() {
                return is_play;
            }

            public void setIs_play(int is_play) {
                this.is_play = is_play;
            }

            public String getPlayer_peilv() {
                return player_peilv;
            }

            public void setPlayer_peilv(String player_peilv) {
                this.player_peilv = player_peilv;
            }

            public String getMatch_id() {
                return match_id;
            }

            public void setMatch_id(String match_id) {
                this.match_id = match_id;
            }
        }
    }
}
