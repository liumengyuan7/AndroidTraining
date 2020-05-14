package com.smallpigeon.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @className User
 * @auther 刘梦圆
 * @description 用户信息
 * @date 2020/04/14 12:58
 */

public class User {
    private Integer id;
    private String userName;
    private String userNickname;
    private String password;
    private String userSex;
    private String userEmail;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date userRegisterTime;
    private String userSno;
    private Integer userPoints;
    private Integer isAcc;
    private double longitude;//经度
    private double latitude;//纬度
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getUserRegisterTime() {
        return userRegisterTime;
    }

    public void setUserRegisterTime(Date userRegisterTime) {
        this.userRegisterTime = userRegisterTime;
    }

    public String getUserSno() {
        return userSno;
    }

    public void setUserSno(String userSno) {
        this.userSno = userSno;
    }

    public Integer getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(Integer userPoints) {
        this.userPoints = userPoints;
    }

    public Integer getIsAcc() {
        return isAcc;
    }

    public void setIsAcc(Integer isAcc) {
        this.isAcc = isAcc;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
