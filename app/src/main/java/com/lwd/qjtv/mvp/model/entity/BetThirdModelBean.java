package com.lwd.qjtv.mvp.model.entity;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/24.
 */

public class BetThirdModelBean {
    @Id
    private long id;
    public String match_id;
    public String jc_id;
    public String jc_pl_id;
    public String jc_type;
    public String left_pic;
    public String right_pic;
    public String left_name;
    public String right_name;
    public String jc_name;
    public String jc_peilv;
    public String match_name;

    @Generated
    public BetThirdModelBean() {
    }

    @Generated
    public BetThirdModelBean(long id, String match_id, String jc_id, String jc_pl_id, String jc_type, String left_pic, String right_pic, String left_name, String right_name, String jc_name, String jc_peilv, String match_name) {
        this.id = id;
        this.match_id = match_id;
        this.jc_id = jc_id;
        this.jc_pl_id = jc_pl_id;
        this.jc_type = jc_type;
        this.left_pic = left_pic;
        this.right_pic = right_pic;
        this.left_name = left_name;
        this.right_name = right_name;
        this.jc_name = jc_name;
        this.jc_peilv = jc_peilv;
        this.match_name = match_name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getJc_id() {
        return jc_id;
    }

    public void setJc_id(String jc_id) {
        this.jc_id = jc_id;
    }

    public String getJc_pl_id() {
        return jc_pl_id;
    }

    public void setJc_pl_id(String jc_pl_id) {
        this.jc_pl_id = jc_pl_id;
    }

    public String getJc_type() {
        return jc_type;
    }

    public void setJc_type(String jc_type) {
        this.jc_type = jc_type;
    }

    public String getLeft_pic() {
        return left_pic;
    }

    public void setLeft_pic(String left_pic) {
        this.left_pic = left_pic;
    }

    public String getRight_pic() {
        return right_pic;
    }

    public void setRight_pic(String right_pic) {
        this.right_pic = right_pic;
    }

    public String getLeft_name() {
        return left_name;
    }

    public void setLeft_name(String left_name) {
        this.left_name = left_name;
    }

    public String getRight_name() {
        return right_name;
    }

    public void setRight_name(String right_name) {
        this.right_name = right_name;
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

    public String getMatch_name() {
        return match_name;
    }

    public void setMatch_name(String match_name) {
        this.match_name = match_name;
    }
}
