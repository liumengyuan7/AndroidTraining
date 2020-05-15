package com.entity;

/**
 * @className ForwardContent
 * @auther 刘梦圆
 * @description 转发类
 * @date 2020/05/14 8:54
 */

public class ForwardContent {
    private int id;
    private User user;
    private Dynamics dynamic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dynamics getDynamic() {
        return dynamic;
    }

    public void setDynamic(Dynamics dynamic) {
        this.dynamic = dynamic;
    }
}
