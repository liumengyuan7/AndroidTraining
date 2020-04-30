package com.smallpigeon.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @className Record
 * @auther 吴东枚
 * @description 记录信息
 * @date 2020/04/27 12:58
 */
public class Record {
    private Integer id;
    private Integer user_id;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date recordTime;
    private float recordDistance;
    private float recordSpeed;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date recordDate;
    private Integer recordPoints;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
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

    public Integer getRecordPoints() {
        return recordPoints;
    }

    public void setRecordPoints(Integer recordPoints) {
        this.recordPoints = recordPoints;
    }
}
