package com.lwd.qjtv.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/23.
 */

public class GuessDetailsBean {


    /**
     * msg : success
     * status : 1
     * data : {"guess_detail":{"id":"9","jc_num":"0","match_id":4,"start_time":"08月16日 10:15","jc_over_time":"1502865000","gamenumber":"5","matcheName":"","hr_a_win_count":"0","hr_b_win_count":"0","starInfo":[{"id":"4","name":"塞尔比","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041358098037.png?x-oss-process=image/resize,m_fill,h_150,w_150","winCount":0},{"id":"135","name":"罗弘昊","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201708/201708152149549671.png?x-oss-process=image/resize,m_fill,h_150,w_150","winCount":0}]},"guess_winner":{"id":"9","jc_type":"1","plInfo":[{"id":1,"jc_name":"左胜","jc_peilv":"1.03"},{"id":2,"jc_name":"左负","jc_peilv":"10.00"}]},"guess_gameNumber":{"id":"9","1_jushu":"5","2_jushu":"6-6","3_jushu":"7","jc_type":"2","plInfo":[{"id":1,"jc_name":"1-5","jc_peilv":"3.50"},{"id":2,"jc_name":"6","jc_peilv":"3.00"},{"id":3,"jc_name":"7-10","jc_peilv":"2.10"}]},"guess_score":{"id":"9","jc_type":"3","plInfo":[{"id":1,"jc_name":"5:0","jc_peilv":"3.50"},{"id":2,"jc_name":"0:5","jc_peilv":"151.00"},{"id":3,"jc_name":"5:1","jc_peilv":"2.87"},{"id":4,"jc_name":"1:5","jc_peilv":"67.00"},{"id":5,"jc_name":"5:2","jc_peilv":"4.00"},{"id":6,"jc_name":"2:5","jc_peilv":"41.00"},{"id":7,"jc_name":"5:3","jc_peilv":"6.50"},{"id":8,"jc_name":"3:5","jc_peilv":"34.00"},{"id":9,"jc_name":"5:4","jc_peilv":"12.00"},{"id":10,"jc_name":"4:5","jc_peilv":"26.00"}]}}
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
         * guess_detail : {"id":"9","jc_num":"0","match_id":4,"start_time":"08月16日 10:15","jc_over_time":"1502865000","gamenumber":"5","matcheName":"","hr_a_win_count":"0","hr_b_win_count":"0","starInfo":[{"id":"4","name":"塞尔比","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041358098037.png?x-oss-process=image/resize,m_fill,h_150,w_150","winCount":0},{"id":"135","name":"罗弘昊","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201708/201708152149549671.png?x-oss-process=image/resize,m_fill,h_150,w_150","winCount":0}]}
         * guess_winner : {"id":"9","jc_type":"1","plInfo":[{"id":1,"jc_name":"左胜","jc_peilv":"1.03"},{"id":2,"jc_name":"左负","jc_peilv":"10.00"}]}
         * guess_gameNumber : {"id":"9","1_jushu":"5","2_jushu":"6-6","3_jushu":"7","jc_type":"2","plInfo":[{"id":1,"jc_name":"1-5","jc_peilv":"3.50"},{"id":2,"jc_name":"6","jc_peilv":"3.00"},{"id":3,"jc_name":"7-10","jc_peilv":"2.10"}]}
         * guess_score : {"id":"9","jc_type":"3","plInfo":[{"id":1,"jc_name":"5:0","jc_peilv":"3.50"},{"id":2,"jc_name":"0:5","jc_peilv":"151.00"},{"id":3,"jc_name":"5:1","jc_peilv":"2.87"},{"id":4,"jc_name":"1:5","jc_peilv":"67.00"},{"id":5,"jc_name":"5:2","jc_peilv":"4.00"},{"id":6,"jc_name":"2:5","jc_peilv":"41.00"},{"id":7,"jc_name":"5:3","jc_peilv":"6.50"},{"id":8,"jc_name":"3:5","jc_peilv":"34.00"},{"id":9,"jc_name":"5:4","jc_peilv":"12.00"},{"id":10,"jc_name":"4:5","jc_peilv":"26.00"}]}
         */

        private GuessDetailBean guess_detail;
        private GuessWinnerBean guess_winner;
        private GuessGameNumberBean guess_gameNumber;
        private GuessScoreBean guess_score;
        private GuessRangfenBean guess_rangfen;

        public GuessRangfenBean getGuess_rangfen() {
            return guess_rangfen;
        }

        public void setGuess_rangfen(GuessRangfenBean guess_rangfen) {
            this.guess_rangfen = guess_rangfen;
        }

        public GuessDetailBean getGuess_detail() {
            return guess_detail;
        }

        public void setGuess_detail(GuessDetailBean guess_detail) {
            this.guess_detail = guess_detail;
        }

        public GuessWinnerBean getGuess_winner() {
            return guess_winner;
        }

        public void setGuess_winner(GuessWinnerBean guess_winner) {
            this.guess_winner = guess_winner;
        }

        public GuessGameNumberBean getGuess_gameNumber() {
            return guess_gameNumber;
        }

        public void setGuess_gameNumber(GuessGameNumberBean guess_gameNumber) {
            this.guess_gameNumber = guess_gameNumber;
        }

        public GuessScoreBean getGuess_score() {
            return guess_score;
        }

        public void setGuess_score(GuessScoreBean guess_score) {
            this.guess_score = guess_score;
        }

        public static class GuessDetailBean {
            /**
             * id : 9
             * jc_num : 0
             * match_id : 4
             * start_time : 08月16日 10:15
             * jc_over_time : 1502865000
             * gamenumber : 5
             * matcheName :
             * hr_a_win_count : 0
             * hr_b_win_count : 0
             * starInfo : [{"id":"4","name":"塞尔比","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201707/201707041358098037.png?x-oss-process=image/resize,m_fill,h_150,w_150","winCount":0},{"id":"135","name":"罗弘昊","thumb_img":"http://img.ttkhj.3z.cc/slk/images/201708/201708152149549671.png?x-oss-process=image/resize,m_fill,h_150,w_150","winCount":0}]
             */

            private String id;
            private String jc_num;
            private int match_id;
            private String start_time;
            private String jc_over_time;
            private String gamenumber;
            private String matcheName;
            private String hr_a_win_count;
            private String hr_b_win_count;
            private List<StarInfoBean> starInfo;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getJc_num() {
                return jc_num;
            }

            public void setJc_num(String jc_num) {
                this.jc_num = jc_num;
            }

            public int getMatch_id() {
                return match_id;
            }

            public void setMatch_id(int match_id) {
                this.match_id = match_id;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getJc_over_time() {
                return jc_over_time;
            }

            public void setJc_over_time(String jc_over_time) {
                this.jc_over_time = jc_over_time;
            }

            public String getGamenumber() {
                return gamenumber;
            }

            public void setGamenumber(String gamenumber) {
                this.gamenumber = gamenumber;
            }

            public String getMatcheName() {
                return matcheName;
            }

            public void setMatcheName(String matcheName) {
                this.matcheName = matcheName;
            }

            public String getHr_a_win_count() {
                return hr_a_win_count;
            }

            public void setHr_a_win_count(String hr_a_win_count) {
                this.hr_a_win_count = hr_a_win_count;
            }

            public String getHr_b_win_count() {
                return hr_b_win_count;
            }

            public void setHr_b_win_count(String hr_b_win_count) {
                this.hr_b_win_count = hr_b_win_count;
            }

            public List<StarInfoBean> getStarInfo() {
                return starInfo;
            }

            public void setStarInfo(List<StarInfoBean> starInfo) {
                this.starInfo = starInfo;
            }

            public static class StarInfoBean {
                /**
                 * id : 4
                 * name : 塞尔比
                 * thumb_img : http://img.ttkhj.3z.cc/slk/images/201707/201707041358098037.png?x-oss-process=image/resize,m_fill,h_150,w_150
                 * winCount : 0
                 */

                private String id;
                private String name;
                private String thumb_img;
                private int winCount;

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

                public int getWinCount() {
                    return winCount;
                }

                public void setWinCount(int winCount) {
                    this.winCount = winCount;
                }
            }
        }

        public static class GuessWinnerBean {
            /**
             * id : 9
             * jc_type : 1
             * plInfo : [{"id":1,"jc_name":"左胜","jc_peilv":"1.03"},{"id":2,"jc_name":"左负","jc_peilv":"10.00"}]
             */

            private String id;
            private String jc_type;
            private List<PlInfoBean> plInfo;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getJc_type() {
                return jc_type;
            }

            public void setJc_type(String jc_type) {
                this.jc_type = jc_type;
            }

            public List<PlInfoBean> getPlInfo() {
                return plInfo;
            }

            public void setPlInfo(List<PlInfoBean> plInfo) {
                this.plInfo = plInfo;
            }

            public static class PlInfoBean {
                /**
                 * id : 1
                 * jc_name : 左胜
                 * jc_peilv : 1.03
                 */

                private int id;
                private String jc_name;
                private String jc_peilv;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getJc_name() {
                    return jc_name;
                }

                public void setJc_name(String jc_name) {
                    this.jc_name = jc_name;
                }

                public String getJc_peilv() {
                    return jc_peilv;
                }

                public void setJc_peilv(String jc_peilv) {
                    this.jc_peilv = jc_peilv;
                }
            }
        }

        public static class GuessGameNumberBean {
            /**
             * id : 9
             * 1_jushu : 5
             * 2_jushu : 6-6
             * 3_jushu : 7
             * jc_type : 2
             * plInfo : [{"id":1,"jc_name":"1-5","jc_peilv":"3.50"},{"id":2,"jc_name":"6","jc_peilv":"3.00"},{"id":3,"jc_name":"7-10","jc_peilv":"2.10"}]
             */

            private String id;
            @SerializedName("1_jushu")
            private String _$1_jushu;
            @SerializedName("2_jushu")
            private String _$2_jushu;
            @SerializedName("3_jushu")
            private String _$3_jushu;
            private String jc_type;
            private List<PlInfoBeanX> plInfo;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String get_$1_jushu() {
                return _$1_jushu;
            }

            public void set_$1_jushu(String _$1_jushu) {
                this._$1_jushu = _$1_jushu;
            }

            public String get_$2_jushu() {
                return _$2_jushu;
            }

            public void set_$2_jushu(String _$2_jushu) {
                this._$2_jushu = _$2_jushu;
            }

            public String get_$3_jushu() {
                return _$3_jushu;
            }

            public void set_$3_jushu(String _$3_jushu) {
                this._$3_jushu = _$3_jushu;
            }

            public String getJc_type() {
                return jc_type;
            }

            public void setJc_type(String jc_type) {
                this.jc_type = jc_type;
            }

            public List<PlInfoBeanX> getPlInfo() {
                return plInfo;
            }

            public void setPlInfo(List<PlInfoBeanX> plInfo) {
                this.plInfo = plInfo;
            }

            public static class PlInfoBeanX {
                /**
                 * id : 1
                 * jc_name : 1-5
                 * jc_peilv : 3.50
                 */

                private int id;
                private String jc_name;
                private String jc_peilv;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getJc_name() {
                    return jc_name;
                }

                public void setJc_name(String jc_name) {
                    this.jc_name = jc_name;
                }

                public String getJc_peilv() {
                    return jc_peilv;
                }

                public void setJc_peilv(String jc_peilv) {
                    this.jc_peilv = jc_peilv;
                }
            }
        }

        public static class GuessScoreBean {
            /**
             * id : 9
             * jc_type : 3
             * plInfo : [{"id":1,"jc_name":"5:0","jc_peilv":"3.50"},{"id":2,"jc_name":"0:5","jc_peilv":"151.00"},{"id":3,"jc_name":"5:1","jc_peilv":"2.87"},{"id":4,"jc_name":"1:5","jc_peilv":"67.00"},{"id":5,"jc_name":"5:2","jc_peilv":"4.00"},{"id":6,"jc_name":"2:5","jc_peilv":"41.00"},{"id":7,"jc_name":"5:3","jc_peilv":"6.50"},{"id":8,"jc_name":"3:5","jc_peilv":"34.00"},{"id":9,"jc_name":"5:4","jc_peilv":"12.00"},{"id":10,"jc_name":"4:5","jc_peilv":"26.00"}]
             */

            private String id;
            private String jc_type;
            private List<PlInfoBeanXX> plInfo;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getJc_type() {
                return jc_type;
            }

            public void setJc_type(String jc_type) {
                this.jc_type = jc_type;
            }

            public List<PlInfoBeanXX> getPlInfo() {
                return plInfo;
            }

            public void setPlInfo(List<PlInfoBeanXX> plInfo) {
                this.plInfo = plInfo;
            }

            public static class PlInfoBeanXX {
                /**
                 * id : 1
                 * jc_name : 5:0
                 * jc_peilv : 3.50
                 */

                private int id;
                private String jc_name;
                private String jc_peilv;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getJc_name() {
                    return jc_name;
                }

                public void setJc_name(String jc_name) {
                    this.jc_name = jc_name;
                }

                public String getJc_peilv() {
                    return jc_peilv;
                }

                public void setJc_peilv(String jc_peilv) {
                    this.jc_peilv = jc_peilv;
                }
            }
        }

        public static class GuessRangfenBean {
            /**
             * id : 9
             * jc_type : 3
             * plInfo : [{"id":1,"jc_name":"5:0","jc_peilv":"3.50"},{"id":2,"jc_name":"0:5","jc_peilv":"151.00"},{"id":3,"jc_name":"5:1","jc_peilv":"2.87"},{"id":4,"jc_name":"1:5","jc_peilv":"67.00"},{"id":5,"jc_name":"5:2","jc_peilv":"4.00"},{"id":6,"jc_name":"2:5","jc_peilv":"41.00"},{"id":7,"jc_name":"5:3","jc_peilv":"6.50"},{"id":8,"jc_name":"3:5","jc_peilv":"34.00"},{"id":9,"jc_name":"5:4","jc_peilv":"12.00"},{"id":10,"jc_name":"4:5","jc_peilv":"26.00"}]
             */

            private String id;
            private String jc_type;
            private List<PlInfoBeanXXX> plInfo;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getJc_type() {
                return jc_type;
            }

            public void setJc_type(String jc_type) {
                this.jc_type = jc_type;
            }

            public List<PlInfoBeanXXX> getPlInfo() {
                return plInfo;
            }

            public void setPlInfo(List<PlInfoBeanXXX> plInfo) {
                this.plInfo = plInfo;
            }

            public static class PlInfoBeanXXX {
                /**
                 * id : 1
                 * jc_name : 5:0
                 * jc_peilv : 3.50
                 */

                private int id;
                private String jc_name;
                private String jc_peilv;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getJc_name() {
                    return jc_name;
                }

                public void setJc_name(String jc_name) {
                    this.jc_name = jc_name;
                }

                public String getJc_peilv() {
                    return jc_peilv;
                }

                public void setJc_peilv(String jc_peilv) {
                    this.jc_peilv = jc_peilv;
                }
            }
        }
    }
}
