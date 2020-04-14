package com.friend.service;


import com.friend.dao.FriendMapper;
import com.google.gson.Gson;
import com.user.dao.UserMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Service
public class FriendService {

    @Resource
    private FriendMapper friendMapper;
    @Resource
    private UserMapper userMapper;

    //查询所有用户信息
     public String searchAllUser() {
         List<Map> users = this.userMapper.selectAllUser();
         if(! users.isEmpty()){
             return new Gson().toJson(users);
         }
		 return null;
	}

	//添加好友
    public boolean addContact(int myId,int friendId){
         int result1 = this.friendMapper.insertFriendInfo(myId, friendId);
         int result2 = this.friendMapper.insertFriendInfo(friendId,myId);
         if(result1>0 && result2>0){
             return true;
         }
         return false;
    }

    //删除好友
//    public boolean deleteContact(int myId,int friendId){
//        boolean my = new Friend().set("my_id",myId).set("friend_id",friendId).save();
//
//        boolean friend = new Friend().set("my_id",friendId).set("friend_id",myId).save();
//        if(my && friend){
//            return true;
//        }
//        return false;
//    }

    //得到我的好友列表
    public String getContactList(int myId){
         List<Map> myFriends = this.friendMapper.getMyFriendById(myId);
         if(! myFriends.isEmpty()) return new Gson().toJson(myFriends);
         else return null;
    }

    //模糊查询用户
     public String getLikeUser(String userEmail){
         List<Map> userList = this.userMapper.selectUserByEmailLike(userEmail);
         if(! userList.isEmpty()) return new Gson().toJson(userList);
         else return null;
     }

}
