package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by DELL on 2018/9/5.
 */

public class CommunityAllBean {

    /**
     * msg : success
     * status : 1
     * data : [{"review_id":"5","post_id":"41","uid":"5","content":"突然想起","create_time":"1970-01-01","username":"u150427","avater":"http://img.8gu.com/slk/user_profile/avatars/1.jpg","review_reply_list":[]},{"review_id":"6","post_id":"41","uid":"5","content":"是啥意思😳","create_time":"1970-01-01","username":"u150427","avater":"http://img.8gu.com/slk/user_profile/avatars/1.jpg","review_reply_list":[]}]
     */

    private String msg;
    private String status;
    private List<CommunityContentBean> data;

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

    public List<CommunityContentBean> getData() {
        return data;
    }

    public void setData(List<CommunityContentBean> data) {
        this.data = data;
    }

}
