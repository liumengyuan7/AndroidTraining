package com.example.smallpigeon.Run;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Build;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.smallpigeon.Chat.activity.ChatActivity;
import com.example.smallpigeon.Entity.UserContent;
import com.example.smallpigeon.MainActivity;
import com.example.smallpigeon.R;
import com.example.smallpigeon.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @Time: 2019/11/27
 * @Author: 程璐
 * @Descripe: 点击匹配模式——重新匹配好友后跳转的页面，根据匹配机制重新匹配，显示并聊天
 */
public class RemachingActivity extends AppCompatActivity {
    private ImageView remachingBack;
    private ImageView match_userImg;
    private TextView match_userName;
    private TextView match_userSex;
    private TextView match_userPoints;
    private TextView match_userInterest;
    private TextView match_userEmail;
    private Button goChat;
    private String myId;
    private String userId;
    private String userNickName;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_remaching );

        //获取视图控件
        getViews();
        //状态栏隐藏
        setStatusBar();
        getCompany();
        getAvatar();
        SharedPreferences pre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        myId = pre.getString("user_id","");
//        ChatHelper.getInstance().sendMessageToGetContactList(getContext(), myId);
//        if(!myId.equals("") && myId!=null) {
//
//        }
        Intent intent = getIntent();
        userId = intent.getStringExtra("user_id");
        userNickName = intent.getStringExtra("user_nickname");
//        myId = intent.getStringExtra("");

        //去聊天方法
        goChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), ChatActivity.class).putExtra("userId", userId)
                        .putExtra("myId",myId).putExtra("userNickName",userNickName));
                finish();
            }
        });

        //返回按钮
        remachingBack.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );


    }


    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }


    //获取匹配对象的头像
    private void getAvatar() {
        RequestOptions requestOptions = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(this).load("http://"+getResources().getString(R.string.ip_address)
                +":8080/smallpigeon/avatar/"+userEmail+".jpg").apply(requestOptions).into(match_userImg);
    }

    //获取匹配对象的信息
    private void getCompany() {
        Intent intent = getIntent();
        if(intent.getStringExtra("user_sex").equals("man")){
            match_userSex.setText("男");
        }else{
            match_userSex.setText("女");
        }
        String interest = getInterestTranslate(intent.getStringExtra("user_interest"));
        match_userName.setText(intent.getStringExtra("user_nickname"));

        match_userPoints.setText(intent.getStringExtra("user_points"));
        match_userInterest.setText(interest.substring(0,interest.length()-1));
        if(intent.getStringExtra("user_email").length()>17)
            match_userEmail.setText(intent.getStringExtra("user_email").substring(0,16));
        else match_userEmail.setText(intent.getStringExtra("user_email"));
        userEmail = intent.getStringExtra("user_email");
    }

    /**
     * @Descripe: 获取视图控件id
     */
    private void getViews() {
        remachingBack = findViewById( R.id.remaching_back );
        match_userImg=findViewById(R.id.match_userImg);
        match_userName=findViewById(R.id.match_userName);
        match_userSex=findViewById(R.id.match_userSex);
        match_userPoints=findViewById(R.id.match_userPoints);
        match_userInterest=findViewById(R.id.match_userInteres);
        match_userEmail=findViewById(R.id.match_userEmail);
        goChat=findViewById(R.id.match_goChat);

    }

    //获取兴趣的翻译
    private String getInterestTranslate(String interestE){
        String[] in = interestE.split(",");
        String interest = "";
        for(int i = 0;i<in.length;i++){
            switch (in[i]){
                case "outdoor":
                    interest += "户外,";
                    break;
                case "film":
                    interest += "电影,";
                    break;
                case "comic":
                    interest += "动漫,";
                    break;
                case "science":
                    interest += "科学,";
                    break;
                case "society":
                    interest += "人文,";
                    break;
                case "music":
                    interest += "音乐,";
                    break;
                case "star":
                    interest += "明星,";
                    break;
                case "delicacy":
                    interest += "美食,";
                    break;
            }
        }
        return interest;
    }
}
