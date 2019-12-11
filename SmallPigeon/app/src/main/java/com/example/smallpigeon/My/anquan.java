package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.smallpigeon.R;

public class anquan extends AppCompatActivity {

    private ImageView back;
    private LinearLayout userChangeEmail;
    private LinearLayout userChangePassword;
    private CustomButtonListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anquan);
        setStatusBar();
        findviews();
        registerListener();

    }

    //隐藏状态栏
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }

    //注册监听器
    private void registerListener() {
        listener = new CustomButtonListener();
        back.setOnClickListener(listener);
        userChangePassword.setOnClickListener(listener);
        userChangePassword.setOnTouchListener(listener);
        userChangeEmail.setOnTouchListener(listener);
        userChangeEmail.setOnClickListener(listener);
    }

    //监听器
    class CustomButtonListener implements View.OnClickListener, View.OnTouchListener{
        @Override
        public void onClick(View v) {
            String email = getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("user_email","");
            switch (v.getId()){
                case R.id.anquan_back:
                    Intent intent = new Intent(anquan.this,PersonalCenter.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.userChangeEmail:
                    if(!email.equals("") && email != null){
                        Intent intent1 = new Intent(anquan.this,update_email.class);
                        startActivity(intent1);
                        finish();
                    } else{
                        Toast.makeText(getApplicationContext(),"您还未登录，请先登录哦！",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.userChangePassword:
                    if(!email.equals("") && email != null){
                        Intent intent2 = new Intent(anquan.this,update_pwd.class);
                        startActivity(intent2);
                        finish();
                    } else{
                        Toast.makeText(getApplicationContext(),"您还未登录，请先登录哦！",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()){
                case R.id.userChangeEmail:
                    motionEvent(userChangeEmail,event);
                    break;
                case R.id.userChangePassword:
                    motionEvent(userChangePassword,event);
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

    //获取视图控件
    public void findviews(){
        back = findViewById(R.id.anquan_back);
        userChangeEmail = findViewById(R.id.userChangeEmail);
        userChangePassword = findViewById(R.id.userChangePassword);
    }

}
