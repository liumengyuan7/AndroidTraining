package com.entity;

/**
 * @className Reply
 * @auther 刘梦圆
 * @description 回复信息
 * @date 2020/04/24 17:07
 */

public class Reply {
    private Integer id;
    private Integer commentId;
    private Integer fromId;
    private String fEmail;
    private String fNickname;
    private Integer toId;
    private String replyContent;
    private String replyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getfEmail() {
        return fEmail;
    }

    public void setfEmail(String fEmail) {
        this.fEmail = fEmail;
    }

    public String getfNickname() {
        return fNickname;
    }

    public void setfNickname(String fNickname) {
        this.fNickname = fNickname;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getToId() {
        return toId;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }
}
