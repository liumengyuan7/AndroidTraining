package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallpigeon.Fragment.MyFragment;
import com.example.smallpigeon.LoginOrRegister.LoginActivity;
import com.example.smallpigeon.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class PersonalCenter extends AppCompatActivity {
    private ImageView personal_center_back;
    private ImageView user_Img;

    private TextView personal_center_user_email;
    private TextView personal_center_nickName;
    private TextView personal_center_user_points;
    private CustomClickListener listener;

    private LinearLayout userImageLayout;
    private LinearLayout userEmailLayout;
    private LinearLayout userNicknameLayout;
    private LinearLayout userPointsLayout;
    private LinearLayout userMoreLayout;
    private LinearLayout userSecurityLayout;

    private Button SignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        setStatusBar();
        getViews();
        registListeners();
        preferencesEvent();

    }

    @Override
    protected void onResume() {
        super.onResume();
        preferencesEvent();
        getAvatar();
    }

    //获取头像
    private void getAvatar(){
        String userEmail = getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("user_email","");
        if(! userEmail.equals("") && userEmail != null){
            String path = getFilesDir().getAbsolutePath()+"/avatar/"+userEmail+".jpg";
            File file = new File(path);
            if(!file.exists()){
                user_Img.setImageDrawable(getResources().getDrawable(R.drawable.woman));
            }else{
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                user_Img.setImageBitmap(bitmap);
            }
        }else{
            user_Img.setImageDrawable(getResources().getDrawable(R.drawable.woman));
        }
    }

    //隐藏状态栏
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }

    //prefer
    private void preferencesEvent() {

        //注销按钮的动态设置
        SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
        String s = pre.getString("user_nickname","");
        if(s.equals("") || s == null){
            personal_center_user_email.setText("");
            personal_center_nickName.setText("");
            personal_center_user_points.setText("");
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
                    String path = getFilesDir().getAbsolutePath()+"/avatar/"+pre.getString("user_email","")+".jpg";
                    File file = new File(path);
                    if(file.exists()){
                        file.delete();
                    }
                    pre.edit().clear().commit();
                    finish();
                    Toast.makeText(getApplicationContext(),"注销成功！",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    //获取视图的控件
    private void getViews() {
        personal_center_back=findViewById(R.id.personal_center_back);

        personal_center_user_email=findViewById(R.id.personal_center_user_email);
        personal_center_nickName=findViewById(R.id.personal_center_nickName);
        personal_center_user_points=findViewById(R.id.personal_center_user_points);
        SignOut=findViewById(R.id.personal_center_SignOut);

        userImageLayout = findViewById(R.id.userImageLayout);
        userEmailLayout = findViewById(R.id.userEmailLayout);
        userNicknameLayout = findViewById(R.id.userNicknameLayout);
        userPointsLayout = findViewById(R.id.userPointsLayout);
        userMoreLayout = findViewById(R.id.userMoreLayout);
        userSecurityLayout = findViewById(R.id.userSecurityLayout);

        user_Img=findViewById(R.id.user_Img);
    }

    //注册监听器
    private void registListeners() {
        listener = new CustomClickListener();
        personal_center_back.setOnClickListener(listener);
        userImageLayout.setOnClickListener(listener);
        userImageLayout.setOnTouchListener(listener);
        userEmailLayout.setOnClickListener(listener);
        userEmailLayout.setOnTouchListener(listener);
        userNicknameLayout.setOnClickListener(listener);
        userNicknameLayout.setOnTouchListener(listener);
        userPointsLayout.setOnClickListener(listener);
        userPointsLayout.setOnTouchListener(listener);
        userMoreLayout.setOnClickListener(listener);
        userMoreLayout.setOnTouchListener(listener);
        userSecurityLayout.setOnClickListener(listener);
        userSecurityLayout.setOnTouchListener(listener);


    }

    //设置监听事件
    class CustomClickListener implements View.OnClickListener,View.OnTouchListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.personal_center_back://个人信息页面返回到-我的-activity
                    Intent intent = new Intent(PersonalCenter.this, MyFragment.class);
                    finish();
                    break;
                case R.id.userImageLayout://进入修改头像activity
                    String email = getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("user_email","");
                    if(!email.equals("") && email != null){
                        Intent intent1 = new Intent(PersonalCenter.this, Personal_centet_updateUserImg.class);
                        startActivity(intent1);
                        finish();
                    } else{
                        Toast.makeText(getApplicationContext(),"请先登录哦！",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.userNicknameLayout://进入修改昵称activity
                    Intent intent2 = new Intent(PersonalCenter.this, Personal_center_updateUserNickname.class);
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.userMoreLayout://进入更多修改activity
                    Intent intent3 = new Intent(PersonalCenter.this, Personal_center_More.class);
                    startActivity(intent3);
                    finish();
                    break;
                case R.id.userSecurityLayout:
                    Intent intent4 = new Intent(PersonalCenter.this, anquan.class);
                    startActivity(intent4);
                    finish();
                    break;
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()){
                case R.id.userImageLayout:
                    motionEvent(userImageLayout,event);
                    break;
                case R.id.userEmailLayout:
                    motionEvent(userEmailLayout,event);
                    break;
                case R.id.userNicknameLayout:
                    motionEvent(userNicknameLayout,event);
                    break;
                case R.id.userPointsLayout:
                    motionEvent(userPointsLayout,event);
                    break;
                case R.id.userMoreLayout:
                    motionEvent(userMoreLayout,event);
                    break;
                case R.id.userSecurityLayout:
                    motionEvent(userSecurityLayout,event);
                    break;
            }
            return false;
        }

    }

    //动态事件
    private void motionEvent(View view,MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                view.setBackgroundColor(Color.parseColor("#C0C0C0"));
                break;
            case MotionEvent.ACTION_UP:
                view.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
        }
    }

}


