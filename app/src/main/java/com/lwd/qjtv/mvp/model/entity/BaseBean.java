package com.lwd.qjtv.mvp.model.entity;

import java.io.Serializable;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/28.
 */

public class BaseBean implements Serializable {

    /**
     * msg : 下注成功
     * status : 1
     */

    private String msg;
    private String status;

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
}
