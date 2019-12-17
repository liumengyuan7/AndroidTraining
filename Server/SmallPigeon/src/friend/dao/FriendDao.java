package friend.dao;


import com.google.gson.Gson;

import java.util.List;

import bean.Friend;
import bean.User;


public class FriendDao {
    //查找所有用户
    public String searchAllUser() {
        List<User> list = User.dao.find("select * from user");
        if(! list.isEmpty()) {
            Gson gson = new Gson();
            System.out.println(gson.toJson(list));
            return gson.toJson(list);
        }
        return null;
    }
	//添加好友
    public boolean addContact(int myId,int friendId){
        boolean my = new Friend().set("my_id",myId).set("friend_id",friendId).save();

        boolean friend = new Friend().set("my_id",friendId).set("friend_id",myId).save();
        if(my && friend){
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
         List<Friend> myFriends = Friend.dao.find("select user.* from user,friend where friend_id =user.id and my_id=?;",myId);
         if(!myFriends.isEmpty()){
            Gson gson = new Gson();
            System.out.println(gson.toJson(myFriends));
            return gson.toJson(myFriends);
         }
         return null;
    }
     //模糊查询用户
    public String getLikeUser(String userEmail){
        List<User> list = User.dao.find("select * from user where user_email like '%"+userEmail+"%'");
		if(! list.isEmpty()) {
			Gson gson = new Gson();
			System.out.println(gson.toJson(list));
			return gson.toJson(list);
		}
		return null;
    }
}
