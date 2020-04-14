package com.entity;

import java.util.Date;

/**
 * @auther angel
 * @description 计划
 * @date 2019/12/10 14:52
 */

public class Plan {

    private int id;
    private int userId;
    private int companionId;
    private Date planTime;
    private String planAddress;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCompanionId() {
        return companionId;
    }

    public void setCompanionId(int companionId) {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
