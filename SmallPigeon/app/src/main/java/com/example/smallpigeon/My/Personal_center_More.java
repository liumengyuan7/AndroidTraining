package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
    private CustomeClickListener listener=new CustomeClickListener();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center__more);

        getViews();
        registerListener();
        getSexAndInterest();

    }

    //获取性别和爱好
    private void getSexAndInterest(){
        Intent intent = getIntent();
        personal_center_more_userSex.setText(intent.getStringExtra("sex"));
        personal_center_more_userInterest.setText(intent.getStringExtra("interest"));
    }

    //获取视图的控件
    private void getViews() {
        personal_center_more_back=findViewById(R.id.personal_center_more_back);//返回控件
        personal_center_more_userSex=findViewById(R.id.personal_center_more_userSex);//性别控件
        personal_center_more_userInterest=findViewById(R.id.personal_center_more_userInterest);//兴趣爱好控件
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
