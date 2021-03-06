package com.example.smallpigeon.Entity;

import java.io.Serializable;
import java.util.List;


public class CommentDetailBean implements Serializable {
    private int id;
    private String nickName;
    private String userLogo;
    private String content;
    private String imgId;
    private int dynamicId;
    private int commentFromId;
    private int replyTotal;
    private String createDate;
    private int comomentZanNum;
    private boolean zanFocus;
    private List<ReplyDetailBean> replyList;
    public CommentDetailBean() {

    }
    public CommentDetailBean(String nickName, String content, String createDate) {
        this.nickName = nickName;
        this.content = content;
        this.createDate = createDate;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getNickName() {
        return nickName;
    }

    public int getCommentFromId() {
        return commentFromId;
    }

    public void setCommentFromId(int commentFromId) {
        this.commentFromId = commentFromId;
    }

    public int getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(int dynamicId) {
        this.dynamicId = dynamicId;
    }

    public void setUserLogo(String userLogo) {
        this.userLogo = userLogo;
    }
    public String getUserLogo() {
        return userLogo;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }
    public String getImgId() {
        return imgId;
    }

    public void setReplyTotal(int replyTotal) {
        this.replyTotal = replyTotal;
    }
    public int getReplyTotal() {
        return replyTotal;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getCreateDate() {
        return createDate;
    }

    public void setReplyList(List<ReplyDetailBean> replyList) {
        this.replyList = replyList;
    }
    public List<ReplyDetailBean> getReplyList() {
        return replyList;
    }

    public int getComomentZanNum() {
        return comomentZanNum;
    }

    public void setComomentZanNum(int comomentZanNum) {
        this.comomentZanNum = comomentZanNum;
    }

    public boolean isZanFocus() {
        return zanFocus;
    }

    public void setZanFocus(boolean zanFocus) {
        this.zanFocus = zanFocus;
    }

    @Override
    public String toString() {
        return "CommentDetailBean{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", userLogo='" + userLogo + '\'' +
                ", content='" + content + '\'' +
                ", imgId='" + imgId + '\'' +
                ", dynamicId=" + dynamicId +
                ", commentFromId=" + commentFromId +
                ", replyTotal=" + replyTotal +
                ", createDate='" + createDate + '\'' +
                ", comomentZanNum=" + comomentZanNum +
                '}';
    }
}
