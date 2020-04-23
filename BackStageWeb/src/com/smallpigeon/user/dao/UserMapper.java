package com.smallpigeon.user.dao;

import com.smallpigeon.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    public List<User> getAllUsers();
    public void addUser(User user);
    public void deleteUser(@Param("id") int id);
    public User getUser(@Param("id") int id);
    public void updateUser(User user);
}
