package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smallpigeon.R;

public class anquan extends AppCompatActivity {
    private ImageView back;
    private  ImageView update_email;
    private  ImageView update_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anquan);
        findviews();
        //点击返回到个人中心界面
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(anquan.this,PersonalCenter.class);
                startActivity(intent4);
                finish();
            }
        });



        //点击进入修改绑定邮箱界面
        update_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(anquan.this,update_email.class);
                startActivity(intent4);
                finish();
            }
        });

        //点击进入修改密码界面
        update_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(anquan.this,update_pwd.class);
                startActivity(intent4);
                finish();
            }
        });

    }
    //获取视图控件
    public void findviews(){
        back=findViewById(R.id.anquan_back);
        update_email=findViewById(R.id.update_email);
        update_pwd=findViewById(R.id.update_password);
    }

}
