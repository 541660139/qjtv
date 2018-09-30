package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by ZhengQian on 2018/1/29.
 * E-mail : wwwiiivip@yeah.net
 * If anyelse things , mail to it . I'll be soon to reply .
 */

public class BBSDataBean {


    /**
     * msg : success
     * status : 1
     * data : {"banner":[{"id":"1","title":"奥沙利文无愧斯诺克历史第一人","sort":"1","picture":"http://img.8gu.com/slk/images/201803/201803191133471578.jpg","link":"811","link_url":"http://slk.3z.cc/index.php?tp=mobile/schedule","link_type":"2","v_type":"1","pt":"3","origin":"2"},{"id":"8","title":"竞猜","sort":"3","picture":"http://img.8gu.com/slk/images/201710/201710261905078173.jpg","link":"13","link_url":"","link_type":"4","v_type":"1","pt":"3","origin":"2"}],"list":[{"id":"1","title":"综合讨论区","des":"APP使用交流综合讨论","icon":"http://img.8gu.com/slk/images/201803/201803171512378081.png","num_mes":"0","dstatus":"1"},{"id":"2","title":"球星讨论区","des":"丁俊晖 / 奥沙利文 / 塞尔比 ...","icon":"http://img.8gu.com/slk/images/201803/201803171509061685.jpg","num_mes":"0","dstatus":"1"},{"id":"4","title":"赛事讨论区","des":"罗马尼亚大师 / 中国公开 ...","icon":"http://img.8gu.com/slk/images/201803/201803171528486704.jpg","num_mes":"0","dstatus":"1"}],"starList":{"column_id":4,"column_name":"明星视频","column_english_name":"starList","icon":"http://img.ttkhj.3z.cc/slk/index/star.png","dataList":[{"id":"4","name":"塞尔比","thumb_img":"http://img.8gu.com/slk/images/201707/201707041358098037.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"1","name":"奥沙利文","thumb_img":"http://img.8gu.com/slk/images/201707/201707041524356303.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"7","name":"威廉姆斯","thumb_img":"http://img.8gu.com/slk/images/201707/201707041530596106.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"3","name":"希金斯","thumb_img":"http://img.8gu.com/slk/images/201707/201707041031448646.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"5","name":"特鲁姆普","thumb_img":"http://img.8gu.com/slk/images/201707/201707041403424328.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"2","name":"丁俊晖","thumb_img":"http://img.8gu.com/slk/images/201707/201707041421082709.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"9","name":"墨菲","thumb_img":"http://img.8gu.com/slk/images/201707/201707041453081127.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"8","name":"罗伯逊","thumb_img":"http://img.8gu.com/slk/images/201707/201707041446118649.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"6","name":"傅家俊","thumb_img":"http://img.8gu.com/slk/images/201707/201707051733416925.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"11","name":"宾汉姆","thumb_img":"http://img.8gu.com/slk/images/201707/201707041456323767.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0}]},"notification":"0"}
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
         * banner : [{"id":"1","title":"奥沙利文无愧斯诺克历史第一人","sort":"1","picture":"http://img.8gu.com/slk/images/201803/201803191133471578.jpg","link":"811","link_url":"http://slk.3z.cc/index.php?tp=mobile/schedule","link_type":"2","v_type":"1","pt":"3","origin":"2"},{"id":"8","title":"竞猜","sort":"3","picture":"http://img.8gu.com/slk/images/201710/201710261905078173.jpg","link":"13","link_url":"","link_type":"4","v_type":"1","pt":"3","origin":"2"}]
         * list : [{"id":"1","title":"综合讨论区","des":"APP使用交流综合讨论","icon":"http://img.8gu.com/slk/images/201803/201803171512378081.png","num_mes":"0","dstatus":"1"},{"id":"2","title":"球星讨论区","des":"丁俊晖 / 奥沙利文 / 塞尔比 ...","icon":"http://img.8gu.com/slk/images/201803/201803171509061685.jpg","num_mes":"0","dstatus":"1"},{"id":"4","title":"赛事讨论区","des":"罗马尼亚大师 / 中国公开 ...","icon":"http://img.8gu.com/slk/images/201803/201803171528486704.jpg","num_mes":"0","dstatus":"1"}]
         * starList : {"column_id":4,"column_name":"明星视频","column_english_name":"starList","icon":"http://img.ttkhj.3z.cc/slk/index/star.png","dataList":[{"id":"4","name":"塞尔比","thumb_img":"http://img.8gu.com/slk/images/201707/201707041358098037.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"1","name":"奥沙利文","thumb_img":"http://img.8gu.com/slk/images/201707/201707041524356303.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"7","name":"威廉姆斯","thumb_img":"http://img.8gu.com/slk/images/201707/201707041530596106.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"3","name":"希金斯","thumb_img":"http://img.8gu.com/slk/images/201707/201707041031448646.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"5","name":"特鲁姆普","thumb_img":"http://img.8gu.com/slk/images/201707/201707041403424328.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"2","name":"丁俊晖","thumb_img":"http://img.8gu.com/slk/images/201707/201707041421082709.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"9","name":"墨菲","thumb_img":"http://img.8gu.com/slk/images/201707/201707041453081127.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"8","name":"罗伯逊","thumb_img":"http://img.8gu.com/slk/images/201707/201707041446118649.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"6","name":"傅家俊","thumb_img":"http://img.8gu.com/slk/images/201707/201707051733416925.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"11","name":"宾汉姆","thumb_img":"http://img.8gu.com/slk/images/201707/201707041456323767.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0}]}
         * notification : 0
         */

        private StarListBean starList;
        private String notification;
        private List<BannerBean> banner;
        public StarListBean getStarList() {
            return starList;
        }

        private List<ListBean> list;

        public void setStarList(StarListBean starList) {
            this.starList = starList;
        }

        public String getNotification() {
            return notification;
        }

        public void setNotification(String notification) {
            this.notification = notification;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class StarListBean {
            /**
             * column_id : 4
             * column_name : 明星视频
             * column_english_name : starList
             * icon : http://img.ttkhj.3z.cc/slk/index/star.png
             * dataList : [{"id":"4","name":"塞尔比","thumb_img":"http://img.8gu.com/slk/images/201707/201707041358098037.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"1","name":"奥沙利文","thumb_img":"http://img.8gu.com/slk/images/201707/201707041524356303.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"7","name":"威廉姆斯","thumb_img":"http://img.8gu.com/slk/images/201707/201707041530596106.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"3","name":"希金斯","thumb_img":"http://img.8gu.com/slk/images/201707/201707041031448646.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"5","name":"特鲁姆普","thumb_img":"http://img.8gu.com/slk/images/201707/201707041403424328.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"2","name":"丁俊晖","thumb_img":"http://img.8gu.com/slk/images/201707/201707041421082709.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"9","name":"墨菲","thumb_img":"http://img.8gu.com/slk/images/201707/201707041453081127.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"8","name":"罗伯逊","thumb_img":"http://img.8gu.com/slk/images/201707/201707041446118649.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"6","name":"傅家俊","thumb_img":"http://img.8gu.com/slk/images/201707/201707051733416925.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0},{"id":"11","name":"宾汉姆","thumb_img":"http://img.8gu.com/slk/images/201707/201707041456323767.png?x-oss-process=image/resize,m_fill,h_150,w_150","isEmpty":0}]
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
                 * id : 4
                 * name : 塞尔比
                 * thumb_img : http://img.8gu.com/slk/images/201707/201707041358098037.png?x-oss-process=image/resize,m_fill,h_150,w_150
                 * isEmpty : 0
                 */

                private String id;
                private String name;
                private String thumb_img;
                private int isEmpty;

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

                public int getIsEmpty() {
                    return isEmpty;
                }

                public void setIsEmpty(int isEmpty) {
                    this.isEmpty = isEmpty;
                }
            }
        }

        public static class BannerBean {
            /**
             * id : 1
             * title : 奥沙利文无愧斯诺克历史第一人
             * sort : 1
             * picture : http://img.8gu.com/slk/images/201803/201803191133471578.jpg
             * link : 811
             * link_url : http://slk.3z.cc/index.php?tp=mobile/schedule
             * link_type : 2
             * v_type : 1
             * pt : 3
             * origin : 2
             */

            private String id;
            private String title;
            private String sort;
            private String picture;
            private String link;
            private String link_url;
            private String link_type;
            private String v_type;
            private String pt;
            private String origin;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getLink_url() {
                return link_url;
            }

            public void setLink_url(String link_url) {
                this.link_url = link_url;
            }

            public String getLink_type() {
                return link_type;
            }

            public void setLink_type(String link_type) {
                this.link_type = link_type;
            }

            public String getV_type() {
                return v_type;
            }

            public void setV_type(String v_type) {
                this.v_type = v_type;
            }

            public String getPt() {
                return pt;
            }

            public void setPt(String pt) {
                this.pt = pt;
            }

            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }
        }

        public static class ListBean {
            /**
             * id : 1
             * title : 综合讨论区
             * des : APP使用交流综合讨论
             * icon : http://img.8gu.com/slk/images/201803/201803171512378081.png
             * num_mes : 0
             * dstatus : 1
             */

            private String id;
            private String title;
            private String des;
            private String icon;
            private String num_mes;
            private String dstatus;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDes() {
                return des;
            }

            public void setDes(String des) {
                this.des = des;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getNum_mes() {
                return num_mes;
            }

            public void setNum_mes(String num_mes) {
                this.num_mes = num_mes;
            }

            public String getDstatus() {
                return dstatus;
            }

            public void setDstatus(String dstatus) {
                this.dstatus = dstatus;
            }
        }
    }
}
