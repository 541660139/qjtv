package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/5.
 */

public class VideoCollectionBean {



    /**
     * msg : success
     * status : 1
     * data : [{"video_id":"1","video_title":"呵呵大","bitmap":"http://img.8gu.com/slk/images/201806/201806280859543089.png"},{"video_id":"2","video_title":"王校长","bitmap":"http://img.8gu.com/slk/images/201806/201806280901308361.png"}]
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
         * video_id : 1
         * video_title : 呵呵大
         * bitmap : http://img.8gu.com/slk/images/201806/201806280859543089.png
         * analysis_type
         */

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        private String video_id;
        private String video_title;
        private String bitmap;
        private String  analysis_type;

        public String getAnalysis_type() {
            return analysis_type;
        }

        public void setAnalysis_type(String analysis_type) {
            this.analysis_type = analysis_type;
        }

        private boolean isCheck;


        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getVideo_id() {
            return video_id;

        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getVideo_title() {
            return video_title;
        }

        public void setVideo_title(String video_title) {
            this.video_title = video_title;
        }

        public String getBitmap() {
            return bitmap;
        }

        public void setBitmap(String bitmap) {
            this.bitmap = bitmap;
        }
    }

}
