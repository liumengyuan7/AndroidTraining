package com.smallpigeon.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @className Record
 * @auther 吴东枚
 * @description 计划信息
 * @date 2020/04/28 12:58
 */
public class Plan {
    private Integer id;
    private Integer userId;
    private Integer companionId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date planTime;
    private String planAddress;
    private String planStatus;

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

    public Integer getCompanionId() {
        return companionId;
    }

    public void setCompanionId(Integer companionId) {
        this.companionId = companionId;
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public String getPlanAddress() {
        return planAddress;
    }

    public void setPlanAddress(String planAddress) {
        this.planAddress = planAddress;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }
}
