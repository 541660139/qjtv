package com.shuyu.gsyvideoplayer.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Brian on 2017/3/17.17:08
 * mail:kandra@163.com
 */

public class MovieVideoDetails {

    /**
     * msg : success
     * status : 1
     * data : {"video_detail":[{"id":"1","name":"致美丽的你","pic_h":"http://img.ttkhj.3z.cc/upload/video_img/20170310124957.jpg","language":"英语","release_time":"2016-1-3","box_office":"12345.6万","glory":"大鱼奖(提名)","director":"宋建魁","screenwriter":"孙俊","actor":["高世元","李勋","李玹雨","金智媛","黄光熙"],"mtype":[{"cat_id":"1","name":"爱情"},{"cat_id":"3","name":"喜剧"},{"cat_id":"4","name":"家庭"},{"cat_id":"6","name":"音乐"},{"cat_id":"7","name":"歌舞"}],"ctype":[{"column_id":"1","column_name":"英文经典","token":"1","v_type":"1","completed":"0"}],"column_type":"1","mbinary":"109","film_length":"1小时38分","intro":"一个未来主题公园的故事","origin":"0","url":"http://v.youku.com/v_show/id_XMTg2NjIxNzkzMg==.html?&f=28933394&from=y1.2-3.4.2&spm=a2h0j.8191423.item_XMTg2NjIxNzkzMg==.A","v_type":"1","isCollect":1,"click_director":[],"click_screenwriter":["孙俊"],"click_actor":["高世元","高世元","李勋","李勋","孙俊"]}],"videoNum_list":[{"id":"1","vid":"1","url_name":"致美丽的你","url":"http://v.youku.com/v_show/id_XMTg2NjIxNzkzMg==.html?&f=28933394&from=y1.2-3.4.2&spm=a2h0j.8191423.item_XMTg2NjIxNzkzMg==.A","video_num":1,"pic_h":"http://img.ttkhj.3z.cc/upload/video_img/20170310124957.jpg","v_type":"1"}]}
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
        private List<VideoDetailBean> video_detail;
        private List<VideoNumListBean> videoNum_list;

        public List<VideoDetailBean> getVideo_detail() {
            return video_detail;
        }

        public void setVideo_detail(List<VideoDetailBean> video_detail) {
            this.video_detail = video_detail;
        }

        public List<VideoNumListBean> getVideoNum_list() {
            return videoNum_list;
        }

        public void setVideoNum_list(List<VideoNumListBean> videoNum_list) {
            this.videoNum_list = videoNum_list;
        }

        public static class VideoDetailBean {
            /**
             * id : 1
             * name : 致美丽的你
             * pic_h : http://img.ttkhj.3z.cc/upload/video_img/20170310124957.jpg
             * language : 英语
             * release_time : 2016-1-3
             * box_office : 12345.6万
             * glory : 大鱼奖(提名)
             * director : 宋建魁
             * screenwriter : 孙俊
             * actor : ["高世元","李勋","李玹雨","金智媛","黄光熙"]
             * mtype : [{"cat_id":"1","name":"爱情"},{"cat_id":"3","name":"喜剧"},{"cat_id":"4","name":"家庭"},{"cat_id":"6","name":"音乐"},{"cat_id":"7","name":"歌舞"}]
             * ctype : [{"column_id":"1","column_name":"英文经典","token":"1","v_type":"1","completed":"0"}]
             * column_type : 1
             * mbinary : 109
             * film_length : 1小时38分
             * intro : 一个未来主题公园的故事
             * origin : 0
             * url : http://v.youku.com/v_show/id_XMTg2NjIxNzkzMg==.html?&f=28933394&from=y1.2-3.4.2&spm=a2h0j.8191423.item_XMTg2NjIxNzkzMg==.A
             * v_type : 1
             * isCollect : 1
             * click_director : []
             * click_screenwriter : ["孙俊"]
             * click_actor : ["高世元","高世元","李勋","李勋","孙俊"]
             */

            private String id;
            private String name;
            private String pic_h;
            private String language;
            private String release_time;
            private String box_office;
            private String glory;
            private List<String> director;
            private List<String> screenwriter;
            private String column_type;
            private String mbinary;
            private String film_length;
            private String intro;
            private String des;
            private String origin;
            private String url;
            private String v_type;
            private int isCollect;
            private List<String> actor;
            private List<MtypeBean> mtype;
            private List<CtypeBean> ctype;
            private List<?> click_director;
            private List<String> click_screenwriter;
            private List<String> click_actor;

            public String getDes() {
                return des;
            }

            public void setDes(String des) {
                this.des = des;
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

            public String getPic_h() {
                return pic_h;
            }

            public void setPic_h(String pic_h) {
                this.pic_h = pic_h;
            }

            public String getLanguage() {
                return language;
            }

            public void setLanguage(String language) {
                this.language = language;
            }

            public String getRelease_time() {
                return release_time;
            }

            public void setRelease_time(String release_time) {
                this.release_time = release_time;
            }

            public String getBox_office() {
                return box_office;
            }

            public void setBox_office(String box_office) {
                this.box_office = box_office;
            }

            public String getGlory() {
                return glory;
            }

            public void setGlory(String glory) {
                this.glory = glory;
            }

            public List<String> getDirector() {
                return director;
            }

            public void setDirector(List<String> director) {
                this.director = director;
            }

            public List<String> getScreenwriter() {
                return screenwriter;
            }

            public void setScreenwriter(List<String> screenwriter) {
                this.screenwriter = screenwriter;
            }

            public String getColumn_type() {
                return column_type;
            }

            public void setColumn_type(String column_type) {
                this.column_type = column_type;
            }

            public String getMbinary() {
                return mbinary;
            }

            public void setMbinary(String mbinary) {
                this.mbinary = mbinary;
            }

            public String getFilm_length() {
                return film_length;
            }

            public void setFilm_length(String film_length) {
                this.film_length = film_length;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getV_type() {
                return v_type;
            }

            public void setV_type(String v_type) {
                this.v_type = v_type;
            }

            public int getIsCollect() {
                return isCollect;
            }

            public void setIsCollect(int isCollect) {
                this.isCollect = isCollect;
            }

            public List<String> getActor() {
                return actor;
            }

            public void setActor(List<String> actor) {
                this.actor = actor;
            }

            public List<MtypeBean> getMtype() {
                return mtype;
            }

            public void setMtype(List<MtypeBean> mtype) {
                this.mtype = mtype;
            }

            public List<CtypeBean> getCtype() {
                return ctype;
            }

            public void setCtype(List<CtypeBean> ctype) {
                this.ctype = ctype;
            }

            public List<?> getClick_director() {
                return click_director;
            }

            public void setClick_director(List<?> click_director) {
                this.click_director = click_director;
            }

            public List<String> getClick_screenwriter() {
                return click_screenwriter;
            }

            public void setClick_screenwriter(List<String> click_screenwriter) {
                this.click_screenwriter = click_screenwriter;
            }

            public List<String> getClick_actor() {
                return click_actor;
            }

            public void setClick_actor(List<String> click_actor) {
                this.click_actor = click_actor;
            }

            public static class MtypeBean {
                /**
                 * cat_id : 1
                 * name : 爱情
                 */

                private String cat_id;
                private String name;

                public String getCat_id() {
                    return cat_id;
                }

                public void setCat_id(String cat_id) {
                    this.cat_id = cat_id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class CtypeBean {
                /**
                 * column_id : 1
                 * column_name : 英文经典
                 * token : 1
                 * v_type : 1
                 * completed : 0
                 */

                private String column_id;
                private String column_name;
                private String token;
                private String v_type;
                private String completed;

                public String getColumn_id() {
                    return column_id;
                }

                public void setColumn_id(String column_id) {
                    this.column_id = column_id;
                }

                public String getColumn_name() {
                    return column_name;
                }

                public void setColumn_name(String column_name) {
                    this.column_name = column_name;
                }

                public String getToken() {
                    return token;
                }

                public void setToken(String token) {
                    this.token = token;
                }

                public String getV_type() {
                    return v_type;
                }

                public void setV_type(String v_type) {
                    this.v_type = v_type;
                }

                public String getCompleted() {
                    return completed;
                }

                public void setCompleted(String completed) {
                    this.completed = completed;
                }
            }
        }

        public static class VideoNumListBean implements Serializable {
            /**
             * id : 1
             * vid : 1
             * url_name : 致美丽的你
             * url : http://v.youku.com/v_show/id_XMTg2NjIxNzkzMg==.html?&f=28933394&from=y1.2-3.4.2&spm=a2h0j.8191423.item_XMTg2NjIxNzkzMg==.A
             * video_num : 1
             * pic_h : http://img.ttkhj.3z.cc/upload/video_img/20170310124957.jpg
             * v_type : 1
             */


            private String id;// 视频ID
            private String vid;// 所属剧集ID
            private String url_name;//视频集数名
            private String url; //视频播放链接
            private int video_num;//视频集数
            private String pic_h;//视频图片
            private String v_type;
            private String origin;//0或1: 人人影视解析播放 | 2播放器直接播放 | 3 网页播放
            private String is_unplay;//视频播放有问题才有此字段
            private String download_origin;//下载方式  0或1：人人解析下载| 2通过我们的链接直接下载
            private String download_url;//下载链接，download_origin=2拿这个字段，其他用播放url


            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }

            public String getIs_unplay() {
                return is_unplay;
            }

            public void setIs_unplay(String is_unplay) {
                this.is_unplay = is_unplay;
            }

            public String getDownload_origin() {
                return download_origin;
            }

            public void setDownload_origin(String download_origin) {
                this.download_origin = download_origin;
            }

            public String getDownload_url() {
                return download_url;
            }

            public void setDownload_url(String download_url) {
                this.download_url = download_url;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getVid() {
                return vid;
            }

            public void setVid(String vid) {
                this.vid = vid;
            }

            public String getUrl_name() {
                return url_name;
            }

            public void setUrl_name(String url_name) {
                this.url_name = url_name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getVideo_num() {
                return video_num;
            }

            public void setVideo_num(int video_num) {
                this.video_num = video_num;
            }

            public String getPic_h() {
                return pic_h;
            }

            public void setPic_h(String pic_h) {
                this.pic_h = pic_h;
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
