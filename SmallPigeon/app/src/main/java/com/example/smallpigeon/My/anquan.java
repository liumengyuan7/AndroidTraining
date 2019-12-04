package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
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
        findviews();
        registerListener();
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
            switch (v.getId()){
                case R.id.anquan_back:
                    Intent intent = new Intent(anquan.this,PersonalCenter.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.userChangeEmail:
                    Intent intent1 = new Intent(anquan.this,update_email.class);
                    startActivity(intent1);
                    finish();
                    break;
                case R.id.userChangePassword:
                    Intent intent2 = new Intent(anquan.this,update_pwd.class);
                    startActivity(intent2);
                    finish();
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
