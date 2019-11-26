package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallpigeon.Fragment.MyFragment;
import com.example.smallpigeon.LoginOrRegister.LoginActivity;
import com.example.smallpigeon.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class PersonalCenter extends AppCompatActivity {
    private ImageView personal_center_userImg;
    private ImageView personal_center_updateImg;
    private ImageView personal_center_updateNickname;
    private ImageView personal_center_more;
    private ImageView personal_center_back;

    private TextView personal_center_user_email;
    private TextView personal_center_nickName;
    private TextView personal_center_user_points;
    private CustomeClickListener listener;

    private Button SignOut;
    private Handler getUserBasicMsgHandler;
    String[] result1;
    String[] result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        getViews();
        registListeners();
        preferencesEvent();
        getUserBasicMsg();//从user表中获取user_email  nickname points



        getUserBasicMsgHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String info = (String) msg.obj;
                result1 = info.split(";");
                for (int i = 0; i < result1.length; i++) {
                    Map<String, Object> itemData = new HashMap<>();
                    result2 = result1[i].split(",");
                    personal_center_user_email.setText(result2[0]);
                    personal_center_nickName.setText(result2[1]);
                    personal_center_user_points.setText(result2[2]);
                }
            }
        };

    }

    //prefer
    private void preferencesEvent() {

        //注销按钮的动态设置
        SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
        String s = pre.getString("user_nickname","");
        if(s.equals("") || s == null){
            SignOut.setText("去登录");
            SignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }else{
            personal_center_user_email.setText(pre.getString("user_email",""));
            personal_center_nickName.setText(pre.getString("user_nickname",""));
            personal_center_user_points.setText(pre.getString("user_points",""));
            SignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
                    pre.edit().clear().commit();
                    finish();
                    Toast.makeText(getApplicationContext(),"注销成功！",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void getViews() {
        personal_center_userImg=findViewById(R.id.personal_center_userImg);
        personal_center_updateImg=findViewById(R.id.personal_center_updateImg);
        personal_center_updateNickname=findViewById(R.id.personal_center_updateNickname);
        personal_center_more=findViewById(R.id.personal_center_more);
        personal_center_back=findViewById(R.id.personal_center_back);


        personal_center_user_email=findViewById(R.id.personal_center_user_email);
        personal_center_nickName=findViewById(R.id.personal_center_nickName);
        personal_center_user_points=findViewById(R.id.personal_center_user_points);

        SignOut=findViewById(R.id.personal_center_SignOut);
    }

    private void registListeners() {
        listener = new CustomeClickListener();
        personal_center_back.setOnClickListener(listener);
        personal_center_updateImg.setOnClickListener(listener);
        personal_center_updateNickname.setOnClickListener(listener);
        personal_center_more.setOnClickListener(listener);
    }


    class CustomeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.personal_center_back://个人信息页面返回到-我的-activity
                    Intent intent = new Intent(PersonalCenter.this, MyFragment.class);
                    finish();
                    break;
                case R.id.personal_center_updateImg://进入修改头像activity
                   // Intent intent1 = new Intent(PersonalCenter.this, Personal_centet_updateUserImg.class);
                    //startActivity(intent1);
                    break;
                case R.id.personal_center_updateNickname://进入修改昵称activity
                    Intent intent2 = new Intent(PersonalCenter.this, Personal_center_updateUserNickname.class);
                    startActivity(intent2);
                    break;
                case R.id.personal_center_more://进入更多修改activity
                    SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
                    Intent intent3 = new Intent(PersonalCenter.this, Personal_center_More.class);
                    intent3.putExtra("sex",pre.getString("user_sex",""));
                    intent3.putExtra("time",pre.getString("user_register_time",""));
                    intent3.putExtra("interest",pre.getString("user_interest",""));
                    startActivity(intent3);
                    break;
            }


            }
        }

    //从user表中获取user_email  nickname points
    public void getUserBasicMsg(){
            new Thread(){
                @Override
                public void run() {
                    try {
                        URL url = new URL("http://"+getResources().getString(R.string.ip_address)
                                +":8080/SmallPigeon/user/getUserBasicMsg");
                        URLConnection conn = url.openConnection();
                        InputStream in = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                        String result = reader.readLine();
                        Message message = new Message();
                        message.obj = result;
                        getUserBasicMsgHandler.sendMessage(message);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }


}


