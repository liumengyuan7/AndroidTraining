package com.example.smallpigeon.LoginOrRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smallpigeon.R;

public class ForgetPassword extends AppCompatActivity {
    private EditText forget_password_email;
    private EditText forget_password_checkCode;
    private Button forget_password_getCode;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        getViews();
        CheckCode();//检验验证码
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
    }


    private void getViews() {
        img=findViewById(R.id.forget_password_return);
        forget_password_email=findViewById(R.id.forget_password_email);//获取邮箱地址
        forget_password_checkCode=findViewById(R.id.forget_password_checkCode);
        forget_password_getCode=findViewById(R.id.forget_password_getCode);
    }
    private void CheckCode(){
        String code = forget_password_checkCode.getText().toString();
        if(code.length()==4){
            if(code=="验证码"){
                Intent intent3 = new Intent(ForgetPassword.this, ResetPwd.class);
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(),"验证码有误",Toast.LENGTH_SHORT).show();
            }

        }
        else{

        }
    }
}
