package com.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @className Comment
 * @auther 刘梦圆
 * @description 评论数据
 * @date 2020/04/24 17:03
 */

public class Comment {
    private Integer id;
    private Integer dynamicId;
    private Integer commenmtFromId;
    private String commentFromNickname;
    private String commentFromEmail;
    private String commentFromContent;
    private String commentFromTime;
    private List<Reply> replies = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(Integer dynamicId) {
        this.dynamicId = dynamicId;
    }

    public Integer getCommenmtFromId() {
        return commenmtFromId;
    }

    public void setCommenmtFromId(Integer commenmtFromId) {
        this.commenmtFromId = commenmtFromId;
    }

    public String getCommentFromNickname() {
        return commentFromNickname;
    }

    public void setCommentFromNickname(String commentFromNickname) {
        this.commentFromNickname = commentFromNickname;
    }

    public String getCommentFromEmail() {
        return commentFromEmail;
    }

    public void setCommentFromEmail(String commentFromEmail) {
        this.commentFromEmail = commentFromEmail;
    }

    public String getCommentFromContent() {
        return commentFromContent;
    }

    public void setCommentFromContent(String commentFromContent) {
        this.commentFromContent = commentFromContent;
    }

    public String getCommentFromTime() {
        return commentFromTime;
    }

    public void setCommentFromTime(String commentFromTime) {
        this.commentFromTime = commentFromTime;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }
}
