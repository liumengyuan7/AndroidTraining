package com.example.smallpigeon.Entity;

public class CommentContent {

    private int id;
    private int dynamics_id;
    private String user_id;
    private String comment_content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDynamics_id() {
        return dynamics_id;
    }

    public void setDynamics_id(int dynamics_id) {
        this.dynamics_id = dynamics_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }
}