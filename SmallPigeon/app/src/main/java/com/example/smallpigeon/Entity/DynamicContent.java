package com.example.smallpigeon.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.smallpigeon.Entity.CommentDetailBean;
import com.example.smallpigeon.Entity.UserContent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DynamicContent implements Serializable {
    private int dynamicId;
    private UserContent userContent;
    private String date;
    private String device;
    private String content;
    private String img;
    private int zan_num;
    private int forward_id;
    private int comment_Num;
    private String img2;
    private List<CommentDetailBean> commentDetailBeans = new ArrayList<>();

    public DynamicContent(){}

    public int getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(int dynamicId) {
        this.dynamicId = dynamicId;
    }

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

    public int getForward_id() {
        return forward_id;
    }

    public void setForward_id(int forward_id) {
        this.forward_id = forward_id;
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

    public List<CommentDetailBean> getCommentDetailBeans() {
        return commentDetailBeans;
    }

    public void setCommentDetailBeans(List<CommentDetailBean> commentDetailBeans) {
        this.commentDetailBeans = commentDetailBeans;
    }

    public int getComment_Num() {
        return comment_Num;
    }

    public void setComment_Num(int comment_Num) {
        this.comment_Num = comment_Num;
    }

    @Override
    public String toString() {
        return "DynamicContent{" +
                "userContent=" + userContent +
                ", date='" + date + '\'' +
                ", device='" + device + '\'' +
                ", content='" + content + '\'' +
                ", img='" + img + '\'' +
                ", zan_num=" + zan_num +
                ", img2='" + img2 + '\'' +
                ", commentDetailBeans=" + commentDetailBeans +
                '}';
    }

}
