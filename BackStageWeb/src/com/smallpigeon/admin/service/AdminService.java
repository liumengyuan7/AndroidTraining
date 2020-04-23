package com.smallpigeon.admin.service;

import com.smallpigeon.admin.dao.AdminMapper;
import com.smallpigeon.entity.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @className AdminService
 * @auther 刘梦圆
 * @description
 * @date 2020/04/13 10:48
 */
@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;
    public Admin login(String email,String password){
       return  this.adminMapper.queryAdmin(email,password);
    }
    public int insertUser(String nickName,String email,String password){
        return  this.adminMapper.insertUser(nickName,email,password);
    }
}
