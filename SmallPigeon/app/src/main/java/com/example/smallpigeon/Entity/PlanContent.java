package com.example.smallpigeon.Entity;

import java.sql.Date;

/**
 * @Time: 2019/11/27
 * @Author: 程璐
 * @Descripe: Plan实体类
 */
public class PlanContent {

    private int id;
    private int userId;
    private int companion_id;
    private Date planTime;
    private String planAddress;
    private String planStatus;

    public PlanContent() {
    }

    public PlanContent(int id, int userId, int companion_id, Date planTime, String planAddress, String planStatus) {
        this.id = id;
        this.userId = userId;
        this.companion_id = companion_id;
        this.planTime = planTime;
        this.planAddress = planAddress;
        this.planStatus = planStatus;
    }

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

    public int getCompanion_id() {
        return companion_id;
    }

    public void setCompanion_id(int companion_id) {
        this.companion_id = companion_id;
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
