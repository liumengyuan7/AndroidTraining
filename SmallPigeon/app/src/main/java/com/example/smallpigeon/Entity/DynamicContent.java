package com.example.smallpigeon.Entity;

public class DynamicContent {
    private UserContent userContent;
    private String date;
    private String device;
    private String content;
    private String img;
    private int zan_num;
    private String img2;
    public DynamicContent(){}


    public void setImg(String img) {
        this.img = img;
    }

    public void setZan_num(int zan_num) {
        this.zan_num = zan_num;
    }

    public String getImg() {
        return img;
    }

    public int getZan_num() {
        return zan_num;
    }

    public UserContent getUserContent() {
        return userContent;
    }

    public void setUserContent(UserContent userContent) {
        this.userContent = userContent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }
}
