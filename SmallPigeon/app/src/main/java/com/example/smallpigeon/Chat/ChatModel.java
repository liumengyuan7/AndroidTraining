package com.example.smallpigeon.Chat;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.example.smallpigeon.R;
import com.example.smallpigeon.Utils;
import com.hyphenate.easeui.domain.EaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class ChatModel {
    private Utils utils = new Utils();
    private Context context;
    private Map<String,EaseUser> allUser = new HashMap<>();
    private Map<String,EaseUser> contactList = new HashMap<>();
    private Handler handlerLogin = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String friends = msg.obj.toString();
                    if (friends.equals("false")) {
                        Log.e("没有查到朋友数据", "朋友数据");
                    } else {
                        try {
                            JSONArray jsonArray = new JSONArray(friends);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                JSONObject jsonObject1 = new JSONObject(jsonObject.getString("attrs"));
                                EaseUser contact = new EaseUser(jsonObject1.get("user_nickname").toString());
                                contact.setId(Integer.parseInt(jsonObject1.get("id").toString()));
                                contact.setUserEmail(jsonObject1.get("user_email").toString());
                                contact.setNickname(jsonObject1.get("user_nickname").toString());
                                contact.setUserSex(jsonObject1.get("user_sex").toString());
                                contact.setUserPoints(Integer.parseInt(jsonObject1.get("user_points").toString()));
                                contactList.put("easeUI"+i,contact);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    String re = msg.obj.toString();
                    if (re.equals("false")) {
                        Log.e("没有查到用户数据", "用户数据");
                    } else {
                        try {
                            JSONArray jsonArray = new JSONArray(re);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                JSONObject jsonObject1 = new JSONObject(jsonObject.getString("attrs"));
                                EaseUser userContent = new EaseUser(jsonObject1.get("user_nickname").toString());
                                userContent.setId(Integer.parseInt(jsonObject1.get("id").toString()));
                                userContent.setUserEmail(jsonObject1.get("user_email").toString());
                                userContent.setNickname(jsonObject1.get("user_nickname").toString());
                                userContent.setUserSex(jsonObject1.get("user_sex").toString());
                                userContent.setUserPoints(Integer.parseInt(jsonObject1.get("user_points").toString()));
                                allUser.put("easeUI"+i,userContent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    };
    public Map<String, EaseUser> getAllUser() {
        return allUser;
    }
    public Map<String, EaseUser> getContactList(){
        return contactList;
    }


    //向服务器发送查找我的好友的数据
    public void sendMessageToGetContactList(Context context,int myId){
        new Thread(){
            @Override
            public void run() {
//                String s = utils.getConnectionResult("friend","getContactList");
                try {
                    URL url = new URL("http://"+context.getResources().getString(R.string.ip_address)
                            +":8080/smallpigeon/friend/getContactList?myId="+myId);
                    Log.e("url",url.toString());
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String result = reader.readLine();
                    Message message = new Message();
                    message.obj = result;
                    message.what = 1;
                    Log.e("我的朋友",result);
                    handlerLogin.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    //向服务器发送模糊查找好友的数据
    public void sendMessageToSearchAllUser(Context context){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+context.getResources().getString(R.string.ip_address)
                            +":8080/smallpigeon/friend/searchAllUser");
                    Log.e("url",url.toString());
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String result = reader.readLine();
                    Message message = new Message();
                    message.obj = result;
                    message.what = 2;
                    Log.e("所有用户",result);
                    handlerLogin.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void deleteContact(Context context,int myId,int friendId){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+context.getResources().getString(R.string.ip_address)
                            +":8080/smallpigeon/friend/deleteContact?myId="+myId+"&friendId="+friendId);
                    Log.e("url",url.toString());
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String result = reader.readLine();
                    Message message = new Message();
                    message.obj = result;
                    message.what = 4;
                    Log.e("删除好友",result);
                    handlerLogin.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
