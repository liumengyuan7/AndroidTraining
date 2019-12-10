package com.example.smallpigeon.Entity;

public class DynamicContent {
    private UserContent userContent;
    private String date;
    private String device;
    private String content;

    public DynamicContent(){}

    public DynamicContent(UserContent userContent, String date, String device, String content) {
        this.userContent = userContent;
        this.date = date;
        this.device = device;
        this.content = content;
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
}
