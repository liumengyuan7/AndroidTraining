package friend.service;


import friend.dao.FriendDao;

public class FriendService {
    //查询所有用户信息
     public String searchAllUser() {
		return new FriendDao().searchAllUser();
	}
	//添加好友
    public boolean addContact(int myId,int friendId){
        return new FriendDao().addContact(myId,friendId);
    }
    //得到我的好友列表
    public String getContactList(int myId){
        return new FriendDao().getContactList(myId);
    }
    //模糊查询用户
     public String getLikeUser(String userEmail){
         return new FriendDao().getLikeUser(userEmail);
     }
}
