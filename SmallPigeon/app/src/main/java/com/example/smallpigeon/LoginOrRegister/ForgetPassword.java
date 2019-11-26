package com.example.smallpigeon.LoginOrRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallpigeon.R;

public class ForgetPassword extends AppCompatActivity {
    private EditText forget_password_email;
    private EditText forget_password_checkCode;
    private Button forget_password_getCode;
    private ImageView img;
    private ImageView intoNextImg;
    private TextView intoNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        getViews();

        forget_password_getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取邮箱验证码


            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(ForgetPassword.this, LoginActivity.class);
                finish();
            }
        });
        intoNextImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckCode();
            }
        });
        intoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckCode();
            }
        });
    }


    private void getViews() {
        img=findViewById(R.id.forget_password_return);
        forget_password_email=findViewById(R.id.forget_password_email);//获取邮箱地址
        forget_password_checkCode=findViewById(R.id.forget_password_checkCode);
        forget_password_getCode=findViewById(R.id.forget_password_getCode);
        intoNext=findViewById(R.id.intoNext);
        intoNextImg=findViewById(R.id.intoNextImg);
    }
    private void CheckCode(){
        String code = forget_password_checkCode.getText().toString();
        Log.e("log"," "+code);
            if(code.equals("1234")){
                Intent intent3 = new Intent(ForgetPassword.this, ResetPwd.class);
                startActivity(intent3);
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(),"验证码有误",Toast.LENGTH_SHORT).show();
            }



    }
}
