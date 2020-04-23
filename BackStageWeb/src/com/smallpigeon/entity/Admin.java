package com.smallpigeon.entity;

/**
 * @className Admin
 * @auther 刘梦圆
 * @description 后台管理员
 * @date 2020/04/13 9:51
 */

public class Admin {
    private Integer id;
    private String email;
    private String password;
    private String nickName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
