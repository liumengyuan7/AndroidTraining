package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.smallpigeon.R;

public class IsIdentifyActivity extends AppCompatActivity {
    private TextView tv_identify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_is_identify);
        //0 表示未进行认证  1表示认证成功 2表示提交过认证，等待管理员确认
        tv_identify = findViewById(R.id.tv_identify);
        Intent intent = getIntent();
        int identfy = intent.getIntExtra("identify", 0);
        if (identfy==2){
            tv_identify.setText("管理员正在审核中.....");
        }else if (identfy == 1){
            tv_identify.setText("审核成功");
        }
    }
}
