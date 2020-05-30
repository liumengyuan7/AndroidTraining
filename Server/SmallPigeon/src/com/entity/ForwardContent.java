package com.entity;

import java.util.Date;

/**
 * @className ForwardContent
 * @auther 刘梦圆
 * @description 转发类
 * @date 2020/05/14 8:54
 */

public class ForwardContent {
//    private int id;
//    private User user;
//    private Dynamics dynamic;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public Dynamics getDynamic() {
//        return dynamic;
//    }
//
//    public void setDynamic(Dynamics dynamic) {
//        this.dynamic = dynamic;
//    }
    private int did;
    private String duserNickname;
    private String duserEmail;
    private Date dpushTime;
    private String dpushContent;
    private String dpushImage;
    private String device;
    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getDuserNickname() {
        return duserNickname;
    }

    public void setDuserNickname(String duserNickname) {
        this.duserNickname = duserNickname;
    }

    public String getDuserEmail() {
        return duserEmail;
    }

    public void setDuserEmail(String duserEmail) {
        this.duserEmail = duserEmail;
    }

    public Date getDpushTime() {
        return dpushTime;
    }

    public void setDpushTime(Date dpushTime) {
        this.dpushTime = dpushTime;
    }

    public String getDpushContent() {
        return dpushContent;
    }

    public void setDpushContent(String dpushContent) {
        this.dpushContent = dpushContent;
    }

    public String getDpushImage() {
        return dpushImage;
    }

    public void setDpushImage(String dpushImage) {
        this.dpushImage = dpushImage;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "ForwardContent{" +
                "did=" + did +
                ", duserNickname='" + duserNickname + '\'' +
                ", duserEmail='" + duserEmail + '\'' +
                ", dpushTime=" + dpushTime +
                ", dpushContent='" + dpushContent + '\'' +
                ", dpushImage='" + dpushImage + '\'' +
                '}';
    }
}
