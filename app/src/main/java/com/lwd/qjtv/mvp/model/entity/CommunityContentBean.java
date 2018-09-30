package com.lwd.qjtv.mvp.model.entity;

import java.util.List;

/**
 * Created by DELL on 2018/9/5.
 */

public class CommunityContentBean {


    /**
     * review_id : 5
     * post_id : 41
     * uid : 5
     * content : 突然想起
     * create_time : 1970-01-01
     * username : u150427
     * avater : http://img.8gu.com/slk/user_profile/avatars/1.jpg
     * review_reply_list : []
     */

    private String review_id;
    private String post_id;
    private String uid;
    private String content;
    private String create_time;
    private String username;
    private String avater;
    private String  one_id;
    private boolean isShowAll;

    public boolean isShowAll() {
        return isShowAll;
    }

    public void setShowAll(boolean showAll) {
        isShowAll = showAll;
    }

    private List<ReplyBean> review_reply_list;

    public String getReview_id() {
        return review_id;
    }

    public String getOne_id() {
        return one_id;
    }

    public void setOne_id(String one_id) {
        this.one_id = one_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public List<ReplyBean> getReview_reply_list() {
        return review_reply_list;
    }

    public void setReview_reply_list(List<ReplyBean> review_reply_list) {
        this.review_reply_list = review_reply_list;
    }

}
