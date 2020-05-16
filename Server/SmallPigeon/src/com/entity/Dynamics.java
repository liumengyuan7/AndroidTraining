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
    private int commentNum;
    private int collectNum;
    private int dtype;
    private ForwardContent forwardContent;
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

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(int collectNum) {
        this.collectNum = collectNum;
    }

    public ForwardContent getForwardContent() {
        return forwardContent;
    }

    public void setForwardContent(ForwardContent forwardContent) {
        this.forwardContent = forwardContent;
    }


    public int getDtype() {
        return dtype;
    }

    public void setDtype(int dtype) {
        this.dtype = dtype;
    }

    @Override
    public String toString() {
        return "Dynamics{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", pushTime=" + pushTime +
                ", pushContent='" + pushContent + '\'' +
                ", pushImage='" + pushImage + '\'' +
                ", zanNum=" + zanNum +
                ", forwardId=" + forwardId +
                ", commentNum=" + commentNum +
                ", collectNum=" + collectNum +
                ", dtype=" + dtype +
                '}';
    }
}
