package com.smallpigeon.friend.service;

import com.smallpigeon.entity.Friend;
import com.smallpigeon.entity.User;
import com.smallpigeon.friend.dao.FriendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className FriendService
 * @auther 吴东枚
 * @description 朋友service
 * @date 2020/04/27 13:19
 */

@Service
public class FriendService {
    @Autowired
    private FriendMapper friendMapper;

    public List<Friend> getAllFriends(){
            return  this.friendMapper.getAllFriends();
    }
    public void addFriend(Friend friend){
        this.friendMapper.addFriend(friend);
    }
    public void deleteFriend(int id){
        this.friendMapper.deleteFriend(id);
    }
    public Friend getFriend(int id){
        return this.friendMapper.getFriend(id);
    }
    public void updateFriend(Friend friend){
        this.friendMapper.updateFriend(friend);
    }
}
