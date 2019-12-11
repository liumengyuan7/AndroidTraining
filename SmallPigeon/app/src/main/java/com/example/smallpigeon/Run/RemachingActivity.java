package com.example.smallpigeon.Run;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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

import com.example.smallpigeon.Entity.UserContent;
import com.example.smallpigeon.MainActivity;
import com.example.smallpigeon.R;

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
    private String interestC = "";
    private ImageView match_userImg;
    private TextView match_userName;
    private TextView match_userSex;
    private TextView match_userPoints;
    private TextView match_userInteres;
    private TextView match_userEmail;
    private Button goChat;
    private Handler matchHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String re = msg.obj+"";
            if(re.equals("false")){
                Toast.makeText(getApplicationContext(),"匹配失败！",Toast.LENGTH_SHORT).show();
            }else{
                try {
                    String result = re.split(";")[0];
                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject json1 = jsonArray.getJSONObject(0);
                    JSONObject json2 = new JSONObject(json1.getString("attrs"));
                    match_userName.setText(json2.getString("user_nickname"));
                    match_userEmail.setText(json2.getString("user_email"));
                    match_userInteres.setText(re.split(";")[1]);
                    match_userPoints.setText(json2.getString("user_points"));
                    match_userSex.setText(json2.getString("user_sex"));
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_remaching );

        //获取视图控件
        getViews();
        //获取用户的兴趣爱好用于匹配
        getInteres();
        //匹配方法
        getCompany(interestC);
        //状态栏隐藏
        setStatusBar();

        //去聊天方法
        goChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //返回按钮
        remachingBack.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( RemachingActivity.this, MachingActivity.class );
                startActivity( intent );
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
    private void getCompany(final String interest) {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+getResources().getString(R.string.ip_address)
                            +":8080/smallpigeon/match/matchUser?interest="+interest);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String result = reader.readLine();
                    Message message = new Message();
                    message.obj = result;
                    matchHandler.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
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
        match_userInteres=findViewById(R.id.match_userInteres);
        match_userEmail=findViewById(R.id.match_userEmail);
        goChat=findViewById(R.id.match_goChat);

    }
    private void getInteres() {
        SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
        getInterestTranslate(pre.getString("user_interest",""));
    }
    //获取兴趣的翻译
    private void getInterestTranslate(String interestE){
        String[] in = interestE.split(",");
        for(int i = 0;i<in.length;i++){
            switch (in[i]){
                case "outdoor":
                    interestC += "outdoor,";
                    break;
                case "film":
                    interestC += "film,";
                    break;
                case "comic":
                    interestC += "comic,";
                    break;
                case "science":
                    interestC += "science,";
                    break;
                case "society":
                    interestC += "society,";
                    break;
                case "music":
                    interestC += "music,";
                    break;
                case "star":
                    interestC += "star,";
                    break;
                case "delicacy":
                    interestC += "delicacy,";
                    break;
            }
        }
    }
}
