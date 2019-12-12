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
//private Map<String, UserContent> contactList;
//    private List<UserContent> contactList;
    private Context appContext;
    public synchronized static ChatHelper getInstance() {
        if (instance == null) {
            instance = new ChatHelper();
        }
        return instance;
    }
    /**
     * get contact list
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
//        public Map<String, UserContent> getContactList() {
//        if (isLoggedIn() && contactList == null) {
//            contactList = chatModel.getContactList();
//        }
//        // return a empty non-null object to avoid app crash
//        if(contactList == null){
//            return new Hashtable<String, UserContent>();
//        }
//
//        return contactList;
//    }
    /**
     * if ever logged in
     *
     * @return
     */
    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }
    public void sendMessageToServer(Context context){
        chatModel.sendMessageToServer(context);
    }

}
