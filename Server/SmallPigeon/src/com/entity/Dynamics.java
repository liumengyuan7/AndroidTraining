package com.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @className Dynamics
 * @auther 刘梦圆
 * @description 动态基本数据
 * @date 2020/04/24 16:59
 */

public class Dynamics {
    private int id;
    private String nickName;
    private String userEmail;
    private Date pushTime;
    private String pushContent;
    private String pushImage;
    private int zanNum;
    private int forwardId;
    private List<Comment> comments = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public String getPushContent() {
        return pushContent;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent;
    }

    public String getPushImage() {
        return pushImage;
    }

    public void setPushImage(String pushImage) {
        this.pushImage = pushImage;
    }

    public int getZanNum() {
        return zanNum;
    }

    public void setZanNum(int zanNum) {
        this.zanNum = zanNum;
    }

    public int getForwardId() {
        return forwardId;
    }

    public void setForwardId(int forwardId) {
        this.forwardId = forwardId;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
