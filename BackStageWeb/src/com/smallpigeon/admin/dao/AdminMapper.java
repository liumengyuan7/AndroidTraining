package com.smallpigeon.admin.dao;

import com.smallpigeon.entity.Admin;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper {
    public int insertUser(@Param("nickName") String nickName,@Param("email") String email, @Param("password") String password);
    public Admin queryAdmin(@Param("email") String email, @Param("password") String password);
    public List<Admin> getAllAdmin();
}
