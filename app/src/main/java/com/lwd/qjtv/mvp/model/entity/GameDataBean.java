package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by DELL on 2018/9/7.
 */

public class GameDataBean {

    /**
     * msg : success
     * status : 1
     * data : [{"time":"09月05日星期三","phase_list":[{"id":"1","start_time":"09月05日星期三","pic_a":"http://m.fangxiaotao.cn/ymq/game/china.jpg","score_a":"20","play_a":"中国","pic_b":"http://m.fangxiaotao.cn/ymq/game/USA.jpg","score_b":"10","play_b":"美国","complete_name":"谁的羽毛球打的好1/8决赛","hour_second":"15:22"},{"id":"2","start_time":"09月05日星期三","pic_a":"http://m.fangxiaotao.cn/ymq/game/china.jpg","score_a":"30","play_a":"中国","pic_b":"http://m.fangxiaotao.cn/ymq/game/Korea.jpg","score_b":"6","play_b":"韩国","complete_name":"谁的羽毛球打的好1/4决赛","hour_second":"15:22"}]},{"time":"09月10日星期一","phase_list":[{"id":"3","start_time":"09月10日星期一","pic_a":"http://m.fangxiaotao.cn/ymq/game/china.jpg","score_a":"100","play_a":"中国","pic_b":"http://m.fangxiaotao.cn/ymq/game/russia.jpg","score_b":"60","play_b":"俄罗斯","complete_name":"谁的羽毛球打的好半决赛","hour_second":"09:30"}]}]
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
         * time : 09月05日星期三
         * phase_list : [{"id":"1","start_time":"09月05日星期三","pic_a":"http://m.fangxiaotao.cn/ymq/game/china.jpg","score_a":"20","play_a":"中国","pic_b":"http://m.fangxiaotao.cn/ymq/game/USA.jpg","score_b":"10","play_b":"美国","complete_name":"谁的羽毛球打的好1/8决赛","hour_second":"15:22"},{"id":"2","start_time":"09月05日星期三","pic_a":"http://m.fangxiaotao.cn/ymq/game/china.jpg","score_a":"30","play_a":"中国","pic_b":"http://m.fangxiaotao.cn/ymq/game/Korea.jpg","score_b":"6","play_b":"韩国","complete_name":"谁的羽毛球打的好1/4决赛","hour_second":"15:22"}]
         */

        private String time;
        private List<PhaseListBean> game_list;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<PhaseListBean> getPhase_list() {
            return game_list;
        }

        public void setPhase_list(List<PhaseListBean> phase_list) {
            this.game_list = phase_list;
        }

        public static class PhaseListBean {
            /**
             * id : 1
             * start_time : 09月05日星期三
             * pic_a : http://m.fangxiaotao.cn/ymq/game/china.jpg
             * score_a : 20
             * play_a : 中国
             * pic_b : http://m.fangxiaotao.cn/ymq/game/USA.jpg
             * score_b : 10
             * play_b : 美国
             * complete_name : 谁的羽毛球打的好1/8决赛
             * hour_second : 15:22
             * is_end:1
             * roomId:3
             * game_id
             * liveUrl  直播地址
             */

            private String roomId;


            private String game_id;
            private String liveUrl;

            public String getLiveUrl() {
                return liveUrl;
            }

            public void setLiveUrl(String liveUrl) {
                this.liveUrl = liveUrl;
            }

            public String getGame_id() {
                return game_id;
            }

            public void setGame_id(String game_id) {
                this.game_id = game_id;
            }

            public String getRoomId() {
                return roomId;
            }

            public void setRoomId(String roomId) {
                this.roomId = roomId;
            }

            private String id;
            private String start_time;
            private String pic_a;
            private String score_a;
            private String play_a;
            private String pic_b;
            private String score_b;
            private String play_b;
            private String complete_name;
            private String hour_second;
            private String is_end;


            public String getIs_end() {
                return is_end;
            }

            public void setIs_end(String is_end) {
                this.is_end = is_end;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getPic_a() {
                return pic_a;
            }

            public void setPic_a(String pic_a) {
                this.pic_a = pic_a;
            }

            public String getScore_a() {
                return score_a;
            }

            public void setScore_a(String score_a) {
                this.score_a = score_a;
            }

            public String getPlay_a() {
                return play_a;
            }

            public void setPlay_a(String play_a) {
                this.play_a = play_a;
            }

            public String getPic_b() {
                return pic_b;
            }

            public void setPic_b(String pic_b) {
                this.pic_b = pic_b;
            }

            public String getScore_b() {
                return score_b;
            }

            public void setScore_b(String score_b) {
                this.score_b = score_b;
            }

            public String getPlay_b() {
                return play_b;
            }

            public void setPlay_b(String play_b) {
                this.play_b = play_b;
            }

            public String getComplete_name() {
                return complete_name;
            }

            public void setComplete_name(String complete_name) {
                this.complete_name = complete_name;
            }

            public String getHour_second() {
                return hour_second;
            }

            public void setHour_second(String hour_second) {
                this.hour_second = hour_second;
            }
        }
    }
}
