package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smallpigeon.Fragment.MyFragment;
import com.example.smallpigeon.R;

public class PersonalCenter extends AppCompatActivity {
    private ImageView personal_center_userImg;
    private ImageView personal_center_updateImg;
    private ImageView personal_center_updateNickname;
    private ImageView personal_center_more;
    private ImageView personal_center_back;

    private TextView personal_center_user_email;
    private TextView personal_center_nickName;
    private TextView personal_center_user_points;
    private CustomeClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        getViews();
        registListeners();


    }

    private void getViews() {
        personal_center_userImg=findViewById(R.id.personal_center_userImg);
        personal_center_updateImg=findViewById(R.id.personal_center_updateImg);
        personal_center_updateNickname=findViewById(R.id.personal_center_updateNickname);
        personal_center_more=findViewById(R.id.personal_center_more);
        personal_center_back=findViewById(R.id.personal_center_back);


        personal_center_user_email=findViewById(R.id.personal_center_user_email);
        personal_center_nickName=findViewById(R.id.personal_center_nickName);
        personal_center_user_points=findViewById(R.id.personal_center_user_points);
    }
    private void registListeners() {
        listener = new CustomeClickListener();
        personal_center_back.setOnClickListener(listener);
        personal_center_updateImg.setOnClickListener(listener);
        personal_center_updateNickname.setOnClickListener(listener);
        personal_center_more.setOnClickListener(listener);
    }


    class CustomeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.personal_center_back://个人信息页面返回到-我的-activity
                    Intent intent = new Intent(PersonalCenter.this, MyFragment.class);
                    finish();
                    break;
                case R.id.personal_center_updateImg://进入修改头像activity
                    Intent intent1 = new Intent(PersonalCenter.this, Personal_centet_updateUserImg.class);
                    startActivity(intent1);
                    break;
                case R.id.personal_center_updateNickname://进入修改昵称activity
                    Intent intent2 = new Intent(PersonalCenter.this, Personal_center_updateUserNickname.class);
                    startActivity(intent2);
                    break;
                case R.id.personal_center_more://进入更多修改activity
                    Intent intent3 = new Intent(PersonalCenter.this, Personal_center_More.class);
                    startActivity(intent3);
                    break;
            }


            }
        }
    }


