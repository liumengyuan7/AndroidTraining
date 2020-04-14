package com.friend.dao;

import com.entity.User;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author syf
 * @ClassName FriendMapper
 * @description
 * @date 2020/04/10 18:14
 */

public interface FriendMapper {

    public int insertFriendInfo(@Param("myId") int myId, @Param("friendId") int friendId);

    public List<Map> getMyFriendById(int id);

}
