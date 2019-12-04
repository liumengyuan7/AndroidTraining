package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.smallpigeon.R;

public class Personal_centet_updateUserImg extends AppCompatActivity {
    private ImageView personal_center_updateImg_back;
    private ImageView userImg;
    private ImageView camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_centet_update_user_img);
        setStatusBar();
        getViews();
        //打开相机获取图片并上传新头像
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        //返回
        personal_center_updateImg_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    //隐藏状态栏
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }
    public void getViews(){
        personal_center_updateImg_back=findViewById(R.id.personal_center_updateImg_back);
        userImg=findViewById(R.id.userImg_update);
        camera=findViewById(R.id.xiangji);
    }


}
