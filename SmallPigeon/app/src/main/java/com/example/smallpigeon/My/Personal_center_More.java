package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

public class Personal_center_More extends AppCompatActivity {
    private ImageView personal_center_more_back;
    private TextView personal_center_more_userSex;
    private TextView personal_center_more_userInterest;
    private TextView personal_center_more_userRegisterTime;
    private CustomeClickListener listener=new CustomeClickListener();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center__more);

        getViews();
        registerListener();
        getSexAndInterest();

    }

    //获取性别和爱好以及注册时间输入到框里
    private void getSexAndInterest(){
        SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
        String nickname = pre.getString("user_nickname","");
        if(!nickname.equals("") && nickname!=null){
            if(pre.getString("user_sex","").equals("man")){
                personal_center_more_userSex.setText("男");
            }else{
                personal_center_more_userSex.setText("女");
            }
            personal_center_more_userRegisterTime.setText(pre.getString("user_register_time",""));
            personal_center_more_userInterest.setText(pre.getString("user_interest","")
                    .substring(0,pre.getString("user_interest","").length()-1));
        }
    }

    //获取视图的控件
    private void getViews() {
        personal_center_more_back=findViewById(R.id.personal_center_more_back);//返回控件
        personal_center_more_userSex=findViewById(R.id.personal_center_more_userSex);//性别控件
        personal_center_more_userInterest=findViewById(R.id.personal_center_more_userInterest);//兴趣爱好控件
        personal_center_more_userRegisterTime=findViewById(R.id.personal_center_more_userRegisterTime);//注册时间
    }

    //注册监听器
    private void registerListener(){
        personal_center_more_back.setOnClickListener(listener);
    }

    //监听器类
    class CustomeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.personal_center_more_back:
                    finish();
                    //返回到个人中心界面
                    break;
            }
        }
    }

}
