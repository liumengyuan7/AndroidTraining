package com.example.smallpigeon.Entity;

import java.io.Serializable;

public class ForwardContent implements Serializable {
    private int id;
    private UserContent userContent;
    private DynamicContent dynamicContent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserContent getUserContent() {
        return userContent;
    }

    public void setUserContent(UserContent userContent) {
        this.userContent = userContent;
    }

    public DynamicContent getDynamicContent() {
        return dynamicContent;
    }

    public void setDynamicContent(DynamicContent dynamicContent) {
        this.dynamicContent = dynamicContent;
    }

    @Override
    public String toString() {
        return "ForwardContent{" +
                "id=" + id +
                ", userContent=" + userContent +
                ", dynamicContent=" + dynamicContent +
                '}';
    }
}
