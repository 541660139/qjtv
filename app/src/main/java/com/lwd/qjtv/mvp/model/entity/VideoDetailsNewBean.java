package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by DELL on 2018/7/2.
 */

public class VideoDetailsNewBean {


    /**
     * msg : success
     * status : 1
     * data : {"video":{"id":"1","is_zan":0,"is_collect":0,"video_title":"呵呵大","video_zancount":"1","video_reviews":"11","video_length":"6","analysis_type":"0","video_url":"https://v.youku.com/v_show/id_XMzY2NDMxMzgwMA==.html?spm=a2h0k.11417342.soresults.dtitle","bitmap":"http://img.8gu.com/slk/images/201806/201806280859543089.png"},"video_review":[{"video_id":"1","avater":"http://img.8gu.com/slk/user_profile/avatars/1.jpg","username":"u150423","review_id":"1","content":"你是很的6","like_num":"55","is_review_like":0,"review_time":"2018-08-23"},{"video_id":"1","avater":"http://img.8gu.com/slk/user_profile/avatars/6.jpg","username":"横说竖说","review_id":"3","content":"加剧了高考","like_num":"31","is_review_like":0,"review_time":"2018-08-23"},{"video_id":"1","avater":"http://img.8gu.com/slk/user_profile/avatars/6.jpg","username":"u150426","review_id":"2","content":"哈就好噶","like_num":"21","is_review_like":0,"review_time":"2018-08-23"},{"video_id":"1","avater":"http://img.8gu.com/slk/user_profile/avatars/1.jpg","username":"u150423","review_id":"4","content":"开学了,开学了123ssf","like_num":"0","is_review_like":0,"review_time":"2018-08-22"},{"video_id":"1","avater":"http://img.8gu.com/slk/user_profile/avatars/1.jpg","username":"u150423","review_id":"7","content":"再试一次sa","like_num":"0","is_review_like":0,"review_time":"2018-08-22"},{"video_id":"1","avater":"http://img.8gu.com/slk/user_profile/avatars/6.jpg","username":"横说竖说","review_id":"11","content":"Cadaver","like_num":"0","is_review_like":0,"review_time":"2018-08-23"},{"video_id":"1","avater":"http://img.8gu.com/slk/user_profile/avatars/1.jpg","username":"u150423","review_id":"6","content":"再试一次","like_num":"0","is_review_like":0,"review_time":"2018-08-22"},{"video_id":"1","avater":"http://img.8gu.com/slk/user_profile/avatars/6.jpg","username":"横说竖说","review_id":"9","content":"Sb","like_num":"0","is_review_like":0,"review_time":"2018-08-23"}]}
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
         * video : {"id":"1","is_zan":0,"is_collect":0,"video_title":"呵呵大","video_zancount":"1","video_reviews":"11","video_length":"6","analysis_type":"0","video_url":"https://v.youku.com/v_show/id_XMzY2NDMxMzgwMA==.html?spm=a2h0k.11417342.soresults.dtitle","bitmap":"http://img.8gu.com/slk/images/201806/201806280859543089.png"}
         * video_review : [{"video_id":"1","avater":"http://img.8gu.com/slk/user_profile/avatars/1.jpg","username":"u150423","review_id":"1","content":"你是很的6","like_num":"55","is_review_like":0,"review_time":"2018-08-23"},{"video_id":"1","avater":"http://img.8gu.com/slk/user_profile/avatars/6.jpg","username":"横说竖说","review_id":"3","content":"加剧了高考","like_num":"31","is_review_like":0,"review_time":"2018-08-23"},{"video_id":"1","avater":"http://img.8gu.com/slk/user_profile/avatars/6.jpg","username":"u150426","review_id":"2","content":"哈就好噶","like_num":"21","is_review_like":0,"review_time":"2018-08-23"},{"video_id":"1","avater":"http://img.8gu.com/slk/user_profile/avatars/1.jpg","username":"u150423","review_id":"4","content":"开学了,开学了123ssf","like_num":"0","is_review_like":0,"review_time":"2018-08-22"},{"video_id":"1","avater":"http://img.8gu.com/slk/user_profile/avatars/1.jpg","username":"u150423","review_id":"7","content":"再试一次sa","like_num":"0","is_review_like":0,"review_time":"2018-08-22"},{"video_id":"1","avater":"http://img.8gu.com/slk/user_profile/avatars/6.jpg","username":"横说竖说","review_id":"11","content":"Cadaver","like_num":"0","is_review_like":0,"review_time":"2018-08-23"},{"video_id":"1","avater":"http://img.8gu.com/slk/user_profile/avatars/1.jpg","username":"u150423","review_id":"6","content":"再试一次","like_num":"0","is_review_like":0,"review_time":"2018-08-22"},{"video_id":"1","avater":"http://img.8gu.com/slk/user_profile/avatars/6.jpg","username":"横说竖说","review_id":"9","content":"Sb","like_num":"0","is_review_like":0,"review_time":"2018-08-23"}]
         */

        private VideoBean video;
        private List<VideoReviewBean> video_review;

        public VideoBean getVideo() {
            return video;
        }

        public void setVideo(VideoBean video) {
            this.video = video;
        }

        public List<VideoReviewBean> getVideo_review() {
            return video_review;
        }

        public void setVideo_review(List<VideoReviewBean> video_review) {
            this.video_review = video_review;
        }

        public static class VideoBean {
            /**
             * id : 1
             * is_zan : 0
             * is_collect : 0
             * video_title : 呵呵大
             * video_zancount : 1
             * video_reviews : 11
             * video_length : 6
             * analysis_type : 0
             * video_url : https://v.youku.com/v_show/id_XMzY2NDMxMzgwMA==.html?spm=a2h0k.11417342.soresults.dtitle
             * bitmap : http://img.8gu.com/slk/images/201806/201806280859543089.png
             */

            private String id;
            private int is_zan;
            private int is_collect;
            private String video_title;
            private String video_zancount;
            private String video_reviews;
            private String video_length;
            private String analysis_type;
            private String video_url;
            private String bitmap;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getIs_zan() {
                return is_zan;
            }

            public void setIs_zan(int is_zan) {
                this.is_zan = is_zan;
            }

            public int getIs_collect() {
                return is_collect;
            }

            public void setIs_collect(int is_collect) {
                this.is_collect = is_collect;
            }

            public String getVideo_title() {
                return video_title;
            }

            public void setVideo_title(String video_title) {
                this.video_title = video_title;
            }

            public String getVideo_zancount() {
                return video_zancount;
            }

            public void setVideo_zancount(String video_zancount) {
                this.video_zancount = video_zancount;
            }

            public String getVideo_reviews() {
                return video_reviews;
            }

            public void setVideo_reviews(String video_reviews) {
                this.video_reviews = video_reviews;
            }

            public String getVideo_length() {
                return video_length;
            }

            public void setVideo_length(String video_length) {
                this.video_length = video_length;
            }

            public String getAnalysis_type() {
                return analysis_type;
            }

            public void setAnalysis_type(String analysis_type) {
                this.analysis_type = analysis_type;
            }

            public String getVideo_url() {
                return video_url;
            }

            public void setVideo_url(String video_url) {
                this.video_url = video_url;
            }

            public String getBitmap() {
                return bitmap;
            }

            public void setBitmap(String bitmap) {
                this.bitmap = bitmap;
            }
        }

        public static class VideoReviewBean {
            /**
             * video_id : 1
             * avater : http://img.8gu.com/slk/user_profile/avatars/1.jpg
             * username : u150423
             * review_id : 1
             * content : 你是很的6
             * like_num : 55
             * is_review_like : 0
             * review_time : 2018-08-23
             */

            private String video_id;
            private String avater;
            private String username;
            private String review_id;
            private String content;
            private String like_num;
            private int is_review_like;
            private String review_time;

            public String getVideo_id() {
                return video_id;
            }

            public void setVideo_id(String video_id) {
                this.video_id = video_id;
            }

            public String getAvater() {
                return avater;
            }

            public void setAvater(String avater) {
                this.avater = avater;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getReview_id() {
                return review_id;
            }

            public void setReview_id(String review_id) {
                this.review_id = review_id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getLike_num() {
                return like_num;
            }

            public void setLike_num(String like_num) {
                this.like_num = like_num;
            }

            public int getIs_review_like() {
                return is_review_like;
            }

            public void setIs_review_like(int is_review_like) {
                this.is_review_like = is_review_like;
            }

            public String getReview_time() {
                return review_time;
            }

            public void setReview_time(String review_time) {
                this.review_time = review_time;
            }
        }
    }
}
