package com.example.smallpigeon.Entity;

import java.io.Serializable;

public class ForwardContent implements Serializable {
//    private int id;
//    private UserContent userContent;
//    private DynamicContent dynamicContent;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public UserContent getUserContent() {
//        return userContent;
//    }
//
//    public void setUserContent(UserContent userContent) {
//        this.userContent = userContent;
//    }
//
//    public DynamicContent getDynamicContent() {
//        return dynamicContent;
//    }
//
//    public void setDynamicContent(DynamicContent dynamicContent) {
//        this.dynamicContent = dynamicContent;
//    }
//
//    @Override
//    public String toString() {
//        return "ForwardContent{" +
//                "id=" + id +
//                ", userContent=" + userContent +
//                ", dynamicContent=" + dynamicContent +
//                '}';
//    }

    private int did;
    private String duserNickname;
    private String duserEmail;
    private String dpushTime;
    private String dpushContent;
    private String dpushImage;

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

    public String getDpushTime() {
        return dpushTime;
    }

    public void setDpushTime(String dpushTime) {
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

    @Override
    public String toString() {
        return "ForwardContent{" +
                "did=" + did +
                ", duserNickname='" + duserNickname + '\'' +
                ", duserEmail='" + duserEmail + '\'' +
                ", dpushTime='" + dpushTime + '\'' +
                ", dpushContent='" + dpushContent + '\'' +
                ", dpushImage='" + dpushImage + '\'' +
                '}';
    }
}
