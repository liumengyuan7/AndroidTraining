package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        getViews();
        registListeners();


    }

    private void getViews() {
        personal_center_userImg=findViewById(R.id.personal_center_userImg);
    }
    private void registListeners() {
    }
}
