package friend.dao;


import com.google.gson.Gson;

import java.util.List;

import bean.Friend;
import bean.User;


public class FriendDao {
//    //添加好友
//     public String saveContactList(int myselfId,int friendId) {
//         List<Friend> list = Friend.dao.find("select * from friends where friend_id=?",friendId);
//        if(list.isEmpty()){
//            return "false";
//        }else{
//            boolean result = new Friend().set("my_id",myselfId).set("friend_id",friendId).save();
//            if(result){
//                return "true";
//            }else{
//                return "false";
//            }
//        }
//     }

	//添加时查找所有用户
//	public String searchAllUser(String userAccount) {
//		List<User> list = User.dao.find("select * from user where user_eamil like ?",userAccount);
//		if(! list.isEmpty()) {
//			Gson gson = new Gson();
//			System.out.println(gson.toJson(list));
//			return gson.toJson(list);
//		}
//		return null;
//	}
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
    //删除好友
//    public String deleteContact(int myId,int friendId){
//        boolean result = Friend.dao.find("select *"myId,friendId);
//    }
}
