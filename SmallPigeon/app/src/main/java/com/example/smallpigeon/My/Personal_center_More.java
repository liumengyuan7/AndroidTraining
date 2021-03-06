package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smallpigeon.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Personal_center_More extends AppCompatActivity {

    private ImageView personal_center_more_back;
    private TextView personal_center_more_userSex;
    private TextView personal_center_more_userInterest;
    private TextView personal_center_more_userRegisterTime;
    private String interestC = "";
    private CustomClickListener listener = new CustomClickListener();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center__more);
        setStatusBar();
        getViews();
        registerListener();
        getSexAndInterest();
    }

    //隐藏状态栏
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }

    //获取性别和爱好以及注册时间输入到框里
    private void getSexAndInterest(){
        SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
        String nickname = pre.getString("user_nickname","");
        if(!nickname.equals("") && nickname!=null){
            if(pre.getString("user_sex","").equals("man")){
                personal_center_more_userSex.setText("男");
            }else{
                personal_center_more_userSex.setText("女");
            }
            getInterestTranslate(pre.getString("user_interest",""));
            Date d = new Date(pre.getString("user_register_time",""));
            SimpleDateFormat sdf  = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
            personal_center_more_userRegisterTime.setText(sdf.format(d));
//            personal_center_more_userRegisterTime.setText(pre.getString("user_register_time","")
//                    .substring(0,pre.getString("user_register_time","").length()-2));
            personal_center_more_userInterest.setText(interestC.substring(0,interestC.length()-1));
        }
    }

    //获取兴趣的翻译
    private void getInterestTranslate(String interestE){
        String[] in = interestE.split(",");
        for(int i = 0;i<in.length;i++){
            switch (in[i]){
                case "outdoor":
                    interestC += "户外,";
                    break;
                case "film":
                    interestC += "电影,";
                    break;
                case "comic":
                    interestC += "动漫,";
                    break;
                case "science":
                    interestC += "科学,";
                    break;
                case "society":
                    interestC += "人文,";
                    break;
                case "music":
                    interestC += "音乐,";
                    break;
                case "star":
                    interestC += "明星,";
                    break;
                case "delicacy":
                    interestC += "美食,";
                    break;
            }
        }
    }

    //获取视图的控件
    private void getViews() {
        personal_center_more_back=findViewById(R.id.personal_center_more_back);//返回控件
        personal_center_more_userSex=findViewById(R.id.personal_center_more_userSex);//性别控件
        personal_center_more_userInterest=findViewById(R.id.personal_center_more_userInterest);//兴趣爱好控件
        personal_center_more_userRegisterTime=findViewById(R.id.personal_center_more_userRegisterTime);//注册时间
    }

    //注册监听器
    private void registerListener(){
        personal_center_more_back.setOnClickListener(listener);
    }

    //监听器类
    class CustomClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.personal_center_more_back:
                    //返回到个人中心界面
                    Intent intent3 = new Intent(Personal_center_More.this, PersonalCenter.class);
                    startActivity(intent3);
                    finish();
                    break;
            }
        }
    }
}