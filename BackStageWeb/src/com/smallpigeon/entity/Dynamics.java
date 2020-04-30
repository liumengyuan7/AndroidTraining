package com.smallpigeon.entity;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

public class Dynamics {
    private Integer id;
    private Integer userId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date pushTime;
    private String pushContent;
    private String pushImage;
    private Integer zanNum;
    private Integer forwardId;

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

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public String getPushContent() {
        return pushContent;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent;
    }

    public String getPushImage() {
        return pushImage;
    }

    public void setPushImage(String pushImage) {
        this.pushImage = pushImage;
    }

    public Integer getZanNum() {
        return zanNum;
    }

    public void setZanNum(Integer zanNum) {
        this.zanNum = zanNum;
    }

    public Integer getForwardId() {
        return forwardId;
    }

    public void setForwardId(Integer forwardId) {
        this.forwardId = forwardId;
    }
}
