package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smallpigeon.R;

public class update_pwd extends AppCompatActivity {
    private TextView userEmail;
    private EditText code;//输入的验证码
    private TextView getcode;//获取验证码
    private TextView code_error;//验证码输入错误时提示文字
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd);
        //获取视图控件
        getViews();
        //从本地获取用户存入的邮箱账号 为userEmail设置值
        getUserEmail();


        //  1. 获取验证码事件  检验验证码 正确自动转入update_pwd_final设置新密码  否则给code_error设置“验证码有误，重新输入”
        getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
       //返回事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(update_pwd.this,anquan.class);
                startActivity(intent4);
            }
        });

    }

    public  void  getViews(){
        userEmail=findViewById(R.id.user_email);
        getcode=findViewById(R.id.updpwd_getcode);
        code=findViewById(R.id.updPwd_edt_code);
        code_error=findViewById(R.id.code_error);
        back=findViewById(R.id.updpwd_back);
    }
    //设置useremail的值
    public void getUserEmail(){
        SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
        String s = pre.getString("user_email","");
        userEmail.setText(s);
    }
}
