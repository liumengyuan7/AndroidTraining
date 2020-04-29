package com.example.smallpigeon.Entity;

import java.io.Serializable;

public class ReplyDetailBean implements Serializable {
    private String nickName;
    private String userLogo;
    private int id;
    private int fromId;
    private int toId;
    private String commentId;
    private String content;
    private String status;
    private String createDate;

    public ReplyDetailBean() {

    }

    public ReplyDetailBean(String nickName, String content) {
        this.nickName = nickName;
        this.content = content;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getNickName() {
        return nickName;
    }

    public void setUserLogo(String userLogo) {
        this.userLogo = userLogo;
    }
    public String getUserLogo() {
        return userLogo;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
    public String getCommentId() {
        return commentId;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "ReplyDetailBean{" +
                "nickName='" + nickName + '\'' +
                ", userLogo='" + userLogo + '\'' +
                ", id=" + id +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", commentId='" + commentId + '\'' +
                ", content='" + content + '\'' +
                ", status='" + status + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
