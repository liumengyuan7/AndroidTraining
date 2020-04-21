package com.entity;

/**
 * @className Comment
 * @auther 刘梦圆
 * @description 评论信息
 * @date 2020/04/21 8:52
 */

public class Comment {
    private Integer id;
    private Integer userId;//评论者id
    private String content;//评论内容

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
