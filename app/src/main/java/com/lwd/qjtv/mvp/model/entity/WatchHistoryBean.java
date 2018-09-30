package com.lwd.qjtv.mvp.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/8/1.
 */
@Entity
public class WatchHistoryBean {

    @Id
    private Long id;
    private String type;
    private String pic;
    private String title;
    private String score;
    private String matchPeople;
    private String time;
    private String starId;
    private boolean isTeach;
    private int intTime;
    private boolean isSelect;

    @Generated(hash = 1300227284)
    public WatchHistoryBean() {
    }

    @Generated(hash = 415146629)
    public WatchHistoryBean(Long id, String type, String pic, String title, String score, String matchPeople, String time, String starId, boolean isTeach, int intTime, boolean isSelect) {
        this.id = id;
        this.type = type;
        this.pic = pic;
        this.title = title;
        this.score = score;
        this.matchPeople = matchPeople;
        this.time = time;
        this.starId = starId;
        this.isTeach = isTeach;
        this.intTime = intTime;
        this.isSelect = isSelect;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isTeach() {
        return isTeach;
    }

    public void setTeach(boolean teach) {
        isTeach = teach;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getMatchPeople() {
        return matchPeople;
    }

    public void setMatchPeople(String matchPeople) {
        this.matchPeople = matchPeople;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean getIsTeach() {
        return this.isTeach;
    }

    public void setIsTeach(boolean isTeach) {
        this.isTeach = isTeach;
    }

    public String getStarId() {
        return this.starId;
    }

    public void setStarId(String starId) {
        this.starId = starId;
    }

    public int getIntTime() {
        return intTime;
    }

    public void setIntTime(int intTime) {
        this.intTime = intTime;
    }

    public boolean getIsSelect() {
        return this.isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }
}
