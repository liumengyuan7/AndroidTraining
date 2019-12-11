package com.example.smallpigeon.Chat;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.smallpigeon.Entity.UserContent;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class ChatModel {
    private Utils utils = new Utils();
    private Context context;
//    private Map<String,UserContent> userContents = new HashMap<>();
private Map<String,EaseUser> userContents = new HashMap<>();
    private Handler handlerLogin = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    String re = msg.obj + "";
                    if (re.equals("false")) {
                        ////                Toast.makeText(,"您的账号或者密码错误，登录失败！",Toast.LENGTH_SHORT).show();
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
                                userContents.put("easeUI"+i,userContent);
                            }
                            JSONObject JSONObject1 = jsonArray.getJSONObject(0);
                            Log.e("ssss", JSONObject1.toString());
                            JSONObject json2 = new JSONObject(JSONObject1.getString("attrs"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    };
    public Map<String, EaseUser> getContactList() {
//        Map<String, EaseUser> users = new Hashtable<String, EaseUser>();
//        for (int i=0;i<userContents.size();i++){
//
//        }
//
//
//            Map<String, EaseUser> contacts = new HashMap<String, EaseUser>();
//            for(int i = 1; i <= 10; i++){
//                EaseUser user = new EaseUser("easeuitest" + i);
//                contacts.put("easeuitest" + i, user);
//            }

        return userContents;
    }
//    public Map<String, UserContent> getContactList() {
//        return userContents;
//    }
//    //向服务器发送数据
    public void sendMessageToServer(Context context){
        new Thread(){
            @Override
            public void run() {
                String s = utils.getConnectionResult("friend","searchAllUser");
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
                    Log.e("信息",result);
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
