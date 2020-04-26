package com.example.smallpigeon.Entity;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Time: 2019/11/27
 * @Author: 程璐
 * @Descripe: User实体类
 */
public class UserContent implements Serializable {
    private  int id;
    private String userName;
    private String userNickname;
    private String userPassword;
    private String userSex;
    private String userEmail;
    private String userImage;
    private Date userRegisterTime;
    private String userSno;
    private int userPoints;

    public UserContent() {
    }

    public UserContent(int id, String userName, String userNickname, String userPassword, String userSex, String userEmail, String userImage, Date userRegisterTime, String userSno, int userPoints) {
        this.id = id;
        this.userName = userName;
        this.userNickname = userNickname;
        this.userPassword = userPassword;
        this.userSex = userSex;
        this.userEmail = userEmail;
        this.userImage = userImage;
        this.userRegisterTime = userRegisterTime;
        this.userSno = userSno;
        this.userPoints = userPoints;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
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

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
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

    public int getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(int userPoints) {
        this.userPoints = userPoints;
    }
}
