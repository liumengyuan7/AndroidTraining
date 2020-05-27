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
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.smallpigeon.LoginOrRegister.LoginActivity;
import com.example.smallpigeon.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.io.File;

public class PersonalCenter extends AppCompatActivity {

    private ImageView personal_center_back;
    private ImageView user_Img;

    private TextView personal_center_user_email;
    private TextView personal_center_nickName;
    private TextView personal_center_user_points;
    private CustomClickListener listener;

    private RelativeLayout userImageLayout;
    private LinearLayout userEmailLayout;
    private LinearLayout userNicknameLayout;
    private LinearLayout userPointsLayout;
    private LinearLayout userMoreLayout;
    private LinearLayout userSecurityLayout;

    private Button SignOut;
    private String email;

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
            RequestOptions requestOptions = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
            Glide.with(this).load("http://"+getResources().getString(R.string.ip_address)
                    +":8080/smallpigeon/avatar/"+userEmail+".jpg").apply(requestOptions).into(user_Img);
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
        } else{
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
                    signOut();//环信退出登录
                    finish();
                    Toast toast=Toast.makeText(getApplicationContext(),"注销成功！",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    LinearLayout toastView = (LinearLayout) toast.getView();
                    ImageView imageCodeProject = new ImageView(getApplicationContext());
                    imageCodeProject.setImageResource(R.drawable.r);
                    toastView.addView(imageCodeProject, 0);
                    toast.show();
                }
            });
        }
    }

    //环信退出登录
    private void signOut() {
        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.e("退出成功","退出登录");
            }

            @Override
            public void onProgress(int progress, String status) { }

            @Override
            public void onError(int code, String message) {
                Log.e("退出失败",code+","+message);
            }
        });
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
            email = getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("user_email","");
            switch (v.getId()){
                case R.id.personal_center_back://个人信息页面返回到-我的-activity
                    finish();
                    break;
                case R.id.userImageLayout://进入修改头像activity
                    if(!email.equals("") && email != null){
                        Intent intent1 = new Intent(PersonalCenter.this, Personal_centet_updateUserImg.class);
                        startActivity(intent1);
                        finish();
                    } else{
                        Toast toast=Toast.makeText(getApplicationContext(),"请先登录哦！",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                    break;
                case R.id.userNicknameLayout://进入修改昵称activity
                    if(!email.equals("") && email != null){
                        Intent intent2 = new Intent(PersonalCenter.this, Personal_center_updateUserNickname.class);
                        startActivity(intent2);
                        finish();
                    }else {
                        Toast toast=Toast.makeText(getApplicationContext(),"请先登录哦！",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                    break;
                case R.id.userMoreLayout://进入更多修改activity
                    if(loginOrNot()){
                        Intent intent3 = new Intent(PersonalCenter.this, Personal_center_More.class);
                        startActivity(intent3);
                        finish();
                    }else {
                        Toast toast=Toast.makeText(getApplicationContext(),"请先登录哦！",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                    break;
                case R.id.userSecurityLayout:
                    if(loginOrNot()){
                        Intent intent4 = new Intent(PersonalCenter.this, AnQuan.class);
                        startActivity(intent4);
                        finish();
                    }else {
                        Toast toast=Toast.makeText(getApplicationContext(),"请先登录哦！",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
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

    //判断是否登录的方法
    private boolean loginOrNot(){
        SharedPreferences pre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String userEmail = pre.getString("user_email","");
        if(userEmail.equals("")||userEmail==null){
            return false;
        }else{
            return true;
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


