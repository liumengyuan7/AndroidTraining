package friend.dao;


import java.util.List;

import bean.Friend;

public class FriendDao {
    //添加好友
     public String saveContactList(int myselfId,int friendId) {
         List<Friend> list = Friend.dao.find("select * from friends where friend_id=?",friendId);
        if(list.isEmpty()){
            return "false";
        }else{
            boolean result = new Friend().set("my_id",myselfId).set("friend_id",friendId).save();
            if(result){
                return "true";
            }else{
                return "false";
            }
        }
     }
}
