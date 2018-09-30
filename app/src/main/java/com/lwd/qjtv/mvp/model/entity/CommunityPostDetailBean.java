package com.lwd.qjtv.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 社区帖子评论
 * Created by wangfeng on 2018/8/24.
 * <p>
 * "review_id": "5",
 * "post_id": "41",
 * "uid": "5",
 * "content": "突然想起",
 * "create_time": "1970-01-01",
 * "username": "u150427",
 * "avater": "http://img.8gu.com/slk/user_profile/avatars/1.jpg",
 * "review_reply_list": []
 **/
public class CommunityPostDetailBean implements Serializable {


    private String id;
    private String uid;
    private String post_id;
    private String content;
    private String create_time;


    private String ip;
    private String like_num;
    private int is_read;
    private int is_zan;
    private String username;
    private String avater;
    private List<ReplyBean> review_reply_list;
    private int is_hf;


    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCard_id() {
        return post_id;
    }

    public void setCard_id(String post_id) {
        this.post_id = post_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLike_num() {
        return like_num;
    }

    public void setLike_num(String like_num) {
        this.like_num = like_num;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public int getIs_zan() {
        return is_zan;
    }

    public void setIs_zan(int is_zan) {
        this.is_zan = is_zan;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHead_portrait() {
        return avater;
    }

    public void setHead_portrait(String avater) {
        this.avater = avater;
    }

    public List<ReplyBean> getHf() {
        return review_reply_list;
    }

    public void setHf(List<ReplyBean> review_reply_list) {
        this.review_reply_list = review_reply_list;
    }

    public int getIs_hf() {
        return is_hf;
    }

    public void setIs_hf(int is_hf) {
        this.is_hf = is_hf;
    }
}
