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
    private int type;
    private int comment_Num;
    private int collect_Num;
    private String img2;
    private ForwardContent forwardContent;
    private List<CommentDetailBean> commentDetailBeans = new ArrayList<>();

    public DynamicContent(){}

    public ForwardContent getForwardContent() {
        return forwardContent;
    }

    public void setForwardContent(ForwardContent forwardContent) {
        this.forwardContent = forwardContent;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getCollect_Num() {
        return collect_Num;
    }

    public void setCollect_Num(int collect_Num) {
        this.collect_Num = collect_Num;
    }

    @Override
    public String toString() {
        return "DynamicContent{" +
                "dynamicId=" + dynamicId +
                ", userContent=" + userContent +
                ", date='" + date + '\'' +
                ", device='" + device + '\'' +
                ", content='" + content + '\'' +
                ", img='" + img + '\'' +
                ", zan_num=" + zan_num +
                ", type=" + type +
                ", comment_Num=" + comment_Num +
                ", collect_Num=" + collect_Num +
                ", img2='" + img2 + '\'' +
                '}';
    }
}
