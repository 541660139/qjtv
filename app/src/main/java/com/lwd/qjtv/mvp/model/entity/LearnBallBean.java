package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/12.
 */

public class LearnBallBean {


    /**
     * msg : success
     * status : 1
     * data : {"xiaobian":{"column_id":1,"column_name":"小编推荐","column_english_name":"xiaobian","icon":"http://img.ttkhj.3z.cc/slk/index/recomand.png","dataList":[{"id":"263","name":"奥沙利文开球后的这杆斯诺克太绝了","pic_h":"http://img.8gu.com/slk/images/201711/201711161422436676.png","score":"7.0","starId":"0","matchPeople":"奥沙利文vs塞尔比","v_type":"3"},{"id":"224","name":"完美教学卡特如何解球","pic_h":"http://img.8gu.com/slk/images/201710/201710181615117580.jpg","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"223","name":"奥沙利文认真做斯诺克, 谁碰到谁得头疼!","pic_h":"http://img.8gu.com/slk/images/201710/201710181549565937.jpg","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"222","name":"对奥沙利文来说领先46分也无妨,","pic_h":"http://img.8gu.com/slk/images/201710/201710181547222101.jpg","score":"7.0","starId":"0","matchPeople":"","v_type":"3"}]},"dashi":{"column_id":2,"column_name":"大师教学","column_english_name":"dashi","icon":"http://img.ttkhj.3z.cc/slk/index/master.png","dataList":[{"id":"213","name":"奥沙利文斯诺克教学－站恣","pic_h":"http://img.8gu.com/slk/images/201710/201710181406315298.jpg","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"212","name":"奥沙利文斯诺克教学－握杆","pic_h":"http://img.8gu.com/slk/images/201710/201710181404293942.jpg","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"211","name":"奥沙利文斯诺克教学－瞄球","pic_h":"http://img.8gu.com/slk/images/201710/201710181403239099.jpg","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"207","name":"奥沙利文斯诺克教学－出杆","pic_h":"http://img.8gu.com/slk/images/201710/201710181050515668.jpg","score":"7.0","starId":"0","matchPeople":"","v_type":"3"}]},"xilie":{"column_id":3,"column_name":"系列视频","column_english_name":"xilie","icon":"http://img.ttkhj.3z.cc/slk/index/series.png","dataList":[{"id":"136","name":"奥沙利文斯诺克秀杆法第八期","pic_h":"http://img.8gu.com/slk/images/201709/201709141617234122.png","score":"7.0","starId":"1","matchPeople":"","v_type":"3"},{"id":"135","name":"奥沙利文斯诺克秀杆法第七期","pic_h":"http://img.8gu.com/slk/images/201709/201709141615508409.png","score":"7.0","starId":"1","matchPeople":"","v_type":"3"},{"id":"134","name":"奥沙利文斯诺克秀杆法第六期","pic_h":"http://img.8gu.com/slk/images/201709/201709141614264360.png","score":"7.0","starId":"1","matchPeople":"","v_type":"3"},{"id":"133","name":"奥沙利文斯诺克秀杆法第五期","pic_h":"http://img.8gu.com/slk/images/201709/201709141613138621.png","score":"7.0","starId":"1","matchPeople":"","v_type":"3"}]},"singlePole":{"column_id":4,"column_name":"单杆破百","column_english_name":"singlePole","icon":"http://img.ttkhj.3z.cc/slk/index/series.png","dataList":[{"id":"12","name":"尼克保罗教学04","pic_h":"http://img.8gu.com/slk/images/201707/201707041735113024.png","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"11","name":"尼克保罗教学03","pic_h":"http://img.8gu.com/slk/images/201707/201707041730467148.png","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"10","name":"尼克保罗教学02","pic_h":"http://img.8gu.com/slk/images/201707/201707041728037124.png","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"9","name":"尼克保罗教学01","pic_h":"http://img.8gu.com/slk/images/201707/201707041726467045.png","score":"7.0","starId":"0","matchPeople":"","v_type":"3"}]}}
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
         * xiaobian : {"column_id":1,"column_name":"小编推荐","column_english_name":"xiaobian","icon":"http://img.ttkhj.3z.cc/slk/index/recomand.png","dataList":[{"id":"263","name":"奥沙利文开球后的这杆斯诺克太绝了","pic_h":"http://img.8gu.com/slk/images/201711/201711161422436676.png","score":"7.0","starId":"0","matchPeople":"奥沙利文vs塞尔比","v_type":"3"},{"id":"224","name":"完美教学卡特如何解球","pic_h":"http://img.8gu.com/slk/images/201710/201710181615117580.jpg","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"223","name":"奥沙利文认真做斯诺克, 谁碰到谁得头疼!","pic_h":"http://img.8gu.com/slk/images/201710/201710181549565937.jpg","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"222","name":"对奥沙利文来说领先46分也无妨,","pic_h":"http://img.8gu.com/slk/images/201710/201710181547222101.jpg","score":"7.0","starId":"0","matchPeople":"","v_type":"3"}]}
         * dashi : {"column_id":2,"column_name":"大师教学","column_english_name":"dashi","icon":"http://img.ttkhj.3z.cc/slk/index/master.png","dataList":[{"id":"213","name":"奥沙利文斯诺克教学－站恣","pic_h":"http://img.8gu.com/slk/images/201710/201710181406315298.jpg","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"212","name":"奥沙利文斯诺克教学－握杆","pic_h":"http://img.8gu.com/slk/images/201710/201710181404293942.jpg","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"211","name":"奥沙利文斯诺克教学－瞄球","pic_h":"http://img.8gu.com/slk/images/201710/201710181403239099.jpg","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"207","name":"奥沙利文斯诺克教学－出杆","pic_h":"http://img.8gu.com/slk/images/201710/201710181050515668.jpg","score":"7.0","starId":"0","matchPeople":"","v_type":"3"}]}
         * xilie : {"column_id":3,"column_name":"系列视频","column_english_name":"xilie","icon":"http://img.ttkhj.3z.cc/slk/index/series.png","dataList":[{"id":"136","name":"奥沙利文斯诺克秀杆法第八期","pic_h":"http://img.8gu.com/slk/images/201709/201709141617234122.png","score":"7.0","starId":"1","matchPeople":"","v_type":"3"},{"id":"135","name":"奥沙利文斯诺克秀杆法第七期","pic_h":"http://img.8gu.com/slk/images/201709/201709141615508409.png","score":"7.0","starId":"1","matchPeople":"","v_type":"3"},{"id":"134","name":"奥沙利文斯诺克秀杆法第六期","pic_h":"http://img.8gu.com/slk/images/201709/201709141614264360.png","score":"7.0","starId":"1","matchPeople":"","v_type":"3"},{"id":"133","name":"奥沙利文斯诺克秀杆法第五期","pic_h":"http://img.8gu.com/slk/images/201709/201709141613138621.png","score":"7.0","starId":"1","matchPeople":"","v_type":"3"}]}
         * singlePole : {"column_id":4,"column_name":"单杆破百","column_english_name":"singlePole","icon":"http://img.ttkhj.3z.cc/slk/index/series.png","dataList":[{"id":"12","name":"尼克保罗教学04","pic_h":"http://img.8gu.com/slk/images/201707/201707041735113024.png","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"11","name":"尼克保罗教学03","pic_h":"http://img.8gu.com/slk/images/201707/201707041730467148.png","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"10","name":"尼克保罗教学02","pic_h":"http://img.8gu.com/slk/images/201707/201707041728037124.png","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"9","name":"尼克保罗教学01","pic_h":"http://img.8gu.com/slk/images/201707/201707041726467045.png","score":"7.0","starId":"0","matchPeople":"","v_type":"3"}]}
         */

        private XiaobianBean xiaobian;
        private DashiBean dashi;
        private XilieBean xilie;
        private SinglePoleBean singlePole;

        public XiaobianBean getXiaobian() {
            return xiaobian;
        }

        public void setXiaobian(XiaobianBean xiaobian) {
            this.xiaobian = xiaobian;
        }

        public DashiBean getDashi() {
            return dashi;
        }

        public void setDashi(DashiBean dashi) {
            this.dashi = dashi;
        }

        public XilieBean getXilie() {
            return xilie;
        }

        public void setXilie(XilieBean xilie) {
            this.xilie = xilie;
        }

        public SinglePoleBean getSinglePole() {
            return singlePole;
        }

        public void setSinglePole(SinglePoleBean singlePole) {
            this.singlePole = singlePole;
        }

        public static class XiaobianBean {
            /**
             * column_id : 1
             * column_name : 小编推荐
             * column_english_name : xiaobian
             * icon : http://img.ttkhj.3z.cc/slk/index/recomand.png
             * dataList : [{"id":"263","name":"奥沙利文开球后的这杆斯诺克太绝了","pic_h":"http://img.8gu.com/slk/images/201711/201711161422436676.png","score":"7.0","starId":"0","matchPeople":"奥沙利文vs塞尔比","v_type":"3"},{"id":"224","name":"完美教学卡特如何解球","pic_h":"http://img.8gu.com/slk/images/201710/201710181615117580.jpg","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"223","name":"奥沙利文认真做斯诺克, 谁碰到谁得头疼!","pic_h":"http://img.8gu.com/slk/images/201710/201710181549565937.jpg","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"222","name":"对奥沙利文来说领先46分也无妨,","pic_h":"http://img.8gu.com/slk/images/201710/201710181547222101.jpg","score":"7.0","starId":"0","matchPeople":"","v_type":"3"}]
             */

            private int column_id;
            private String column_name;
            private String column_english_name;
            private String icon;
            private List<DataListBean> dataList;

            public int getColumn_id() {
                return column_id;
            }

            public void setColumn_id(int column_id) {
                this.column_id = column_id;
            }

            public String getColumn_name() {
                return column_name;
            }

            public void setColumn_name(String column_name) {
                this.column_name = column_name;
            }

            public String getColumn_english_name() {
                return column_english_name;
            }

            public void setColumn_english_name(String column_english_name) {
                this.column_english_name = column_english_name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public List<DataListBean> getDataList() {
                return dataList;
            }

            public void setDataList(List<DataListBean> dataList) {
                this.dataList = dataList;
            }

            public static class DataListBean {
                /**
                 * id : 263
                 * name : 奥沙利文开球后的这杆斯诺克太绝了
                 * pic_h : http://img.8gu.com/slk/images/201711/201711161422436676.png
                 * score : 7.0
                 * starId : 0
                 * matchPeople : 奥沙利文vs塞尔比
                 * v_type : 3
                 */

                private String id;
                private String name;
                private String pic_h;
                private String score;
                private String starId;
                private String matchPeople;
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

                public String getPic_h() {
                    return pic_h;
                }

                public void setPic_h(String pic_h) {
                    this.pic_h = pic_h;
                }

                public String getScore() {
                    return score;
                }

                public void setScore(String score) {
                    this.score = score;
                }

                public String getStarId() {
                    return starId;
                }

                public void setStarId(String starId) {
                    this.starId = starId;
                }

                public String getMatchPeople() {
                    return matchPeople;
                }

                public void setMatchPeople(String matchPeople) {
                    this.matchPeople = matchPeople;
                }

                public String getV_type() {
                    return v_type;
                }

                public void setV_type(String v_type) {
                    this.v_type = v_type;
                }
            }
        }

        public static class DashiBean {
            /**
             * column_id : 2
             * column_name : 大师教学
             * column_english_name : dashi
             * icon : http://img.ttkhj.3z.cc/slk/index/master.png
             * dataList : [{"id":"213","name":"奥沙利文斯诺克教学－站恣","pic_h":"http://img.8gu.com/slk/images/201710/201710181406315298.jpg","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"212","name":"奥沙利文斯诺克教学－握杆","pic_h":"http://img.8gu.com/slk/images/201710/201710181404293942.jpg","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"211","name":"奥沙利文斯诺克教学－瞄球","pic_h":"http://img.8gu.com/slk/images/201710/201710181403239099.jpg","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"207","name":"奥沙利文斯诺克教学－出杆","pic_h":"http://img.8gu.com/slk/images/201710/201710181050515668.jpg","score":"7.0","starId":"0","matchPeople":"","v_type":"3"}]
             */

            private int column_id;
            private String column_name;
            private String column_english_name;
            private String icon;
            private List<DataListBeanX> dataList;

            public int getColumn_id() {
                return column_id;
            }

            public void setColumn_id(int column_id) {
                this.column_id = column_id;
            }

            public String getColumn_name() {
                return column_name;
            }

            public void setColumn_name(String column_name) {
                this.column_name = column_name;
            }

            public String getColumn_english_name() {
                return column_english_name;
            }

            public void setColumn_english_name(String column_english_name) {
                this.column_english_name = column_english_name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public List<DataListBeanX> getDataList() {
                return dataList;
            }

            public void setDataList(List<DataListBeanX> dataList) {
                this.dataList = dataList;
            }

            public static class DataListBeanX {
                /**
                 * id : 213
                 * name : 奥沙利文斯诺克教学－站恣
                 * pic_h : http://img.8gu.com/slk/images/201710/201710181406315298.jpg
                 * score : 7.0
                 * starId : 0
                 * matchPeople :
                 * v_type : 3
                 */

                private String id;
                private String name;
                private String pic_h;
                private String score;
                private String starId;
                private String matchPeople;
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

                public String getPic_h() {
                    return pic_h;
                }

                public void setPic_h(String pic_h) {
                    this.pic_h = pic_h;
                }

                public String getScore() {
                    return score;
                }

                public void setScore(String score) {
                    this.score = score;
                }

                public String getStarId() {
                    return starId;
                }

                public void setStarId(String starId) {
                    this.starId = starId;
                }

                public String getMatchPeople() {
                    return matchPeople;
                }

                public void setMatchPeople(String matchPeople) {
                    this.matchPeople = matchPeople;
                }

                public String getV_type() {
                    return v_type;
                }

                public void setV_type(String v_type) {
                    this.v_type = v_type;
                }
            }
        }

        public static class XilieBean {
            /**
             * column_id : 3
             * column_name : 系列视频
             * column_english_name : xilie
             * icon : http://img.ttkhj.3z.cc/slk/index/series.png
             * dataList : [{"id":"136","name":"奥沙利文斯诺克秀杆法第八期","pic_h":"http://img.8gu.com/slk/images/201709/201709141617234122.png","score":"7.0","starId":"1","matchPeople":"","v_type":"3"},{"id":"135","name":"奥沙利文斯诺克秀杆法第七期","pic_h":"http://img.8gu.com/slk/images/201709/201709141615508409.png","score":"7.0","starId":"1","matchPeople":"","v_type":"3"},{"id":"134","name":"奥沙利文斯诺克秀杆法第六期","pic_h":"http://img.8gu.com/slk/images/201709/201709141614264360.png","score":"7.0","starId":"1","matchPeople":"","v_type":"3"},{"id":"133","name":"奥沙利文斯诺克秀杆法第五期","pic_h":"http://img.8gu.com/slk/images/201709/201709141613138621.png","score":"7.0","starId":"1","matchPeople":"","v_type":"3"}]
             */

            private int column_id;
            private String column_name;
            private String column_english_name;
            private String icon;
            private List<DataListBeanXX> dataList;

            public int getColumn_id() {
                return column_id;
            }

            public void setColumn_id(int column_id) {
                this.column_id = column_id;
            }

            public String getColumn_name() {
                return column_name;
            }

            public void setColumn_name(String column_name) {
                this.column_name = column_name;
            }

            public String getColumn_english_name() {
                return column_english_name;
            }

            public void setColumn_english_name(String column_english_name) {
                this.column_english_name = column_english_name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public List<DataListBeanXX> getDataList() {
                return dataList;
            }

            public void setDataList(List<DataListBeanXX> dataList) {
                this.dataList = dataList;
            }

            public static class DataListBeanXX {
                /**
                 * id : 136
                 * name : 奥沙利文斯诺克秀杆法第八期
                 * pic_h : http://img.8gu.com/slk/images/201709/201709141617234122.png
                 * score : 7.0
                 * starId : 1
                 * matchPeople :
                 * v_type : 3
                 */

                private String id;
                private String name;
                private String pic_h;
                private String score;
                private String starId;
                private String matchPeople;
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

                public String getPic_h() {
                    return pic_h;
                }

                public void setPic_h(String pic_h) {
                    this.pic_h = pic_h;
                }

                public String getScore() {
                    return score;
                }

                public void setScore(String score) {
                    this.score = score;
                }

                public String getStarId() {
                    return starId;
                }

                public void setStarId(String starId) {
                    this.starId = starId;
                }

                public String getMatchPeople() {
                    return matchPeople;
                }

                public void setMatchPeople(String matchPeople) {
                    this.matchPeople = matchPeople;
                }

                public String getV_type() {
                    return v_type;
                }

                public void setV_type(String v_type) {
                    this.v_type = v_type;
                }
            }
        }

        public static class SinglePoleBean {
            /**
             * column_id : 4
             * column_name : 单杆破百
             * column_english_name : singlePole
             * icon : http://img.ttkhj.3z.cc/slk/index/series.png
             * dataList : [{"id":"12","name":"尼克保罗教学04","pic_h":"http://img.8gu.com/slk/images/201707/201707041735113024.png","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"11","name":"尼克保罗教学03","pic_h":"http://img.8gu.com/slk/images/201707/201707041730467148.png","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"10","name":"尼克保罗教学02","pic_h":"http://img.8gu.com/slk/images/201707/201707041728037124.png","score":"7.0","starId":"0","matchPeople":"","v_type":"3"},{"id":"9","name":"尼克保罗教学01","pic_h":"http://img.8gu.com/slk/images/201707/201707041726467045.png","score":"7.0","starId":"0","matchPeople":"","v_type":"3"}]
             */

            private int column_id;
            private String column_name;
            private String column_english_name;
            private String icon;
            private List<DataListBeanXXX> dataList;

            public int getColumn_id() {
                return column_id;
            }

            public void setColumn_id(int column_id) {
                this.column_id = column_id;
            }

            public String getColumn_name() {
                return column_name;
            }

            public void setColumn_name(String column_name) {
                this.column_name = column_name;
            }

            public String getColumn_english_name() {
                return column_english_name;
            }

            public void setColumn_english_name(String column_english_name) {
                this.column_english_name = column_english_name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public List<DataListBeanXXX> getDataList() {
                return dataList;
            }

            public void setDataList(List<DataListBeanXXX> dataList) {
                this.dataList = dataList;
            }

            public static class DataListBeanXXX {
                /**
                 * id : 12
                 * name : 尼克保罗教学04
                 * pic_h : http://img.8gu.com/slk/images/201707/201707041735113024.png
                 * score : 7.0
                 * starId : 0
                 * matchPeople :
                 * v_type : 3
                 */

                private String id;
                private String name;
                private String pic_h;
                private String score;
                private String starId;
                private String matchPeople;
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

                public String getPic_h() {
                    return pic_h;
                }

                public void setPic_h(String pic_h) {
                    this.pic_h = pic_h;
                }

                public String getScore() {
                    return score;
                }

                public void setScore(String score) {
                    this.score = score;
                }

                public String getStarId() {
                    return starId;
                }

                public void setStarId(String starId) {
                    this.starId = starId;
                }

                public String getMatchPeople() {
                    return matchPeople;
                }

                public void setMatchPeople(String matchPeople) {
                    this.matchPeople = matchPeople;
                }

                public String getV_type() {
                    return v_type;
                }

                public void setV_type(String v_type) {
                    this.v_type = v_type;
                }
            }
        }
    }
}
