package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by ZhengQian on 2017/11/23.
 * E-mail : wwwiiivip@yeah.net
 * If anyelse things , mail to it . I'll be soon to reply .
 */

public class HotSearchCollectionBean {

    /**
     * msg : success
     * status : 1
     * data : ["丁俊晖","奥沙利文","塞尔比","特朗普"]
     */

    private String msg;
    private String status;
    private List<String> data;

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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
