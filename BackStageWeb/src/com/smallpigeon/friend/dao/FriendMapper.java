package com.smallpigeon.friend.dao;

import com.smallpigeon.entity.Friend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendMapper {
    public List<Friend> getAllFriends();
    public void addFriend(Friend friend);
    public void deleteFriend(@Param("id") int id);
    public Friend getFriend(@Param("id") int id);
    public void updateFriend(Friend friend);
}
