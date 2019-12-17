package com.example.smallpigeon.Chat;

import android.content.Context;

import com.baidubce.model.User;
import com.example.smallpigeon.Entity.UserContent;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class ChatHelper {
    /**
     * data sync listener
     */
    public interface DataSyncListener {
        /**
         * sync complete
         * @param success true：data sync successful，false: failed to sync data
         */
        void onSyncComplete(boolean success);
    }
    private static ChatHelper instance = null;
    private ChatModel chatModel = new ChatModel();
    private Map<String, EaseUser> contactList;
    private Map<String, EaseUser> allUser;
    private Map<String, EaseUser> likeUser;
    public synchronized static ChatHelper getInstance() {
        if (instance == null) {
            instance = new ChatHelper();
        }
        return instance;
    }
    /**
     * get contact list 好友列表
     *
     * @return
     */
    public Map<String, EaseUser> getContactList() {
        if (isLoggedIn() && contactList == null) {
            contactList = chatModel.getContactList();
        }
        // return a empty non-null object to avoid app crash
        if(contactList == null){
            return new Hashtable<String, EaseUser>();
        }

        return contactList;
    }
    /*
    * 所有用户
    * */
    public Map<String, EaseUser> getAllUser() {
        if (isLoggedIn() && allUser == null) {
            allUser = chatModel.getAllUser();
        }
        // return a empty non-null object to avoid app crash
        if(allUser == null){
            return new Hashtable<String, EaseUser>();
        }

        return allUser;
    }
    /*
     * 模糊查询用户
     * */
    public Map<String, EaseUser> getLikeUser() {
        if (isLoggedIn() && likeUser == null) {
            likeUser = chatModel.getLikeContactList();
        }
        // return a empty non-null object to avoid app crash
        if(likeUser == null){
            return new Hashtable<String, EaseUser>();
        }

        return likeUser;
    }
    /**
     * if ever logged in
     *
     * @return
     */
    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }
    //向服务器发送查找我的好友的数据
    public void sendMessageToGetContactList(Context context,int myId){
        chatModel.sendMessageToGetContactList(context,myId);
    }
    //查找所有用户
    public void sendMessageToSearchAllUser(Context context){
        chatModel.sendMessageToSearchAllUser(context);
    }
    public void deleteContact(Context context,int myId,int friendId){
        chatModel.deleteContact(context,myId,friendId);
    }
    //模糊查询用户
    public void sendMessageToGetLikeContactList(Context context,String userEmail){
        chatModel.sendMessageToGetLikeContactList(context,userEmail);
    }
}
