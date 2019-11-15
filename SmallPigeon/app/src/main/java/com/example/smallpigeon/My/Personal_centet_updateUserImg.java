package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.smallpigeon.R;

public class Personal_centet_updateUserImg extends AppCompatActivity {
    private ImageView personal_center_updateImg_back;
    private CustomeClickListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_centet_update_user_img);

        personal_center_updateImg_back=findViewById(R.id.personal_center_updateImg_back);
        listener=new CustomeClickListener();
        personal_center_updateImg_back.setOnClickListener(listener);
    }


    class CustomeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.personal_center_updateImg_back:
                    Intent intent = new Intent(Personal_centet_updateUserImg.this, PersonalCenter.class);
                    startActivity(intent);
                    //返回到个人中心界面
                    break;


            }


        }
    }
}
