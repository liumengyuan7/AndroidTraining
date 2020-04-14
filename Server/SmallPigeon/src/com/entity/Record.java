package com.entity;

import java.util.Date;

/**
 * @auther angel
 * @description 1
 * @date 2019/12/16 15:42
 */

public class Record {

    private int id;
    private int userId;
    private String recordTime;
    private float recordDistance;
    private float recordSpeed;
    private Date recordDate;
    private int recordPoints;

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

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public float getRecordDistance() {
        return recordDistance;
    }

    public void setRecordDistance(float recordDistance) {
        this.recordDistance = recordDistance;
    }

    public float getRecordSpeed() {
        return recordSpeed;
    }

    public void setRecordSpeed(float recordSpeed) {
        this.recordSpeed = recordSpeed;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public int getRecordPoints() {
        return recordPoints;
    }

    public void setRecordPoints(int recordPoints) {
        this.recordPoints = recordPoints;
    }
}
