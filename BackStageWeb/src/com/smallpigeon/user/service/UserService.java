package com.smallpigeon.user.service;

import com.smallpigeon.entity.User;
import com.smallpigeon.user.dao.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className UserService
 * @auther 刘梦圆
 * @description 用户service
 * @date 2020/04/14 13:19
 */

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> getAllUsers(){
            return  this.userMapper.getAllUsers();
    }
    public void addUser(User user){
        this.userMapper.addUser(user);
    }
    public void deleteUser(int id){
        this.userMapper.deleteUser(id);
    }
    public User getUser(int id){
        return this.userMapper.getUser(id);
    }
    public void updateUser(User user){
        this.userMapper.updateUser(user);
    }
}
