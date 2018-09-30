package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by DELL on 2018/9/6.
 */

public class CommunityHfBean {

    /**
     * msg : 回复评论成功
     * status : 1
     * data : [{"post_id":"41","uid":"5","review_id":"63","one_id":"5","content":"%E6%98%8E","reply_username":"u150427","to_name":"u150427"},{"post_id":"41","uid":"5","review_id":"63","one_id":"5","content":"%E6%98%8E","reply_username":"u150427","to_name":"u150427"}]
     */

    private String msg;
    private String status;
    private List<ReplyBean> data;

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

    public List<ReplyBean> getData() {
        return data;
    }

    public void setData(List<ReplyBean> data) {
        this.data = data;
    }


}
