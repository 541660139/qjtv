package com.lwd.qjtv.mvp.model.entity;

import java.io.Serializable;

/**
 * 回复
 * Created by wangfeng on 2018/8/30.
 *
 * "id": "4",
 "post_id": "41",
 "review_id": "14",
 "uid": "3",
 "reply_uid": "0",
 "content": "Tfggggg",
 "create_time": "2018-09-05",
 "reply_username": null,
 "to_name": "横说竖说"
 **/
public class ReplyBean implements Serializable {
    private String id;
    private String post_id;
    private String review_id;

    private String uid;
    private String reply_uid;


    private String content;

    private String create_time;


    private String reply_username;

    private String to_name;
    private String  one_id;
    public String getOne_id() {
        return one_id;
    }

    public void setOne_id(String one_id) {
        this.one_id = one_id;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getReply_uid() {
        return reply_uid;
    }

    public void setReply_uid(String reply_uid) {
        this.reply_uid = reply_uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getReply_username() {
        return reply_username;
    }

    public void setReply_username(String reply_username) {
        this.reply_username = reply_username;
    }

    public String getTo_name() {
        return to_name;
    }

    public void setTo_name(String to_name) {
        this.to_name = to_name;
    }



}
