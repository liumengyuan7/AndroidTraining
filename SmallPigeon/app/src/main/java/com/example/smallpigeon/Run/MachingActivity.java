package com.example.smallpigeon.Run;

import android.content.Context;
import android.content.Intent;

import android.os.Build;

import android.content.SharedPreferences;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallpigeon.R;
import com.example.smallpigeon.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @Time: 2019/11/27
 * @Author: 程璐
 * @Descripe: 点击匹配模式后跳转的activity，显示未完成plan
 */
public class MachingActivity extends AppCompatActivity {

    //视图控件
    private ImageView machingBack;
    private Button btnRematch;
    private MyClickListener listener;

    private List<Map<String,String>> information;
    private PlanAdapter planAdapter;

    private Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.obj + "";
            if(!result.equals("empty")){
                try {
                    information = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject json = jsonArray.getJSONObject(i);
                        Map<String, String> item = new HashMap<>();
                        Date d = new Date(json.getString("plan_time"));
                        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
                        item.put("plan_time",sdf.format(d));
                        item.put("plan_address",json.getString("plan_address"));
                        item.put("plan_email",json.getString("plan_email"));
                        item.put("plan_nickname",json.getString("plan_nickname"));
                        information.add(item);
                    }
                    ListView listView1 = findViewById(R.id.lv_machingTask);
                    planAdapter = new PlanAdapter(MachingActivity.this,information,R.layout.run_maching_listitem);
                    listView1.setAdapter(planAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast toastTip = Toast.makeText(getApplicationContext(),"你的计划为空哦！",Toast.LENGTH_LONG);
                toastTip.setGravity(Gravity.CENTER, 0, 0);
                toastTip.show();
            }
        }
    };

    private Handler nearbyUserHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj + "";
            if(result==null){
                Toast toastTip = Toast.makeText(getApplicationContext(),"匹配失败！",Toast.LENGTH_SHORT);
                toastTip.setGravity(Gravity.CENTER, 0, 0);
                toastTip.show();
            }else{
                Intent request = new Intent();
                request.setClass(MachingActivity.this,NearbyUserActivity.class);
                request.putExtra("nearbyUser",result);
                startActivity(request);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_maching );
        //获取视图控件
        getViews();
        //注册监听器
        listener = new MyClickListener();
        registerListener();
        //状态栏隐藏
        setStatusBar();
        //获取未完成计划的方法
        getUserUnfinishedPlan();
    }

    //设置手机的状态栏
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }

    //注册监听器
    private void registerListener() {
        btnRematch.setOnClickListener( listener );
        machingBack.setOnClickListener( listener );
    }

    /**
     * @Descripe: 获取视图控件id
     */
    private void getViews() {
        btnRematch = findViewById( R.id.btn_rematch );
        machingBack = findViewById( R.id.maching_back );
    }

    //自定义listener
    private class MyClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_rematch:
                    getNearbyUser();
                    break;
                case R.id.maching_back:
                    //返回按钮
                    finish();
                    break;
            }
        }
    }

    //获取用户未完成的计划
    private void getUserUnfinishedPlan(){
        new Thread(){
            @Override
            public void run() {
                SharedPreferences pre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                String userId = pre.getString("user_id","");
                String result=new Utils().getConnectionResult("plan","getUnfinishedPlan","userId="+userId);
                Message message = new Message();
                message.obj = result;
                handler.sendMessage(message);
            }
        }.start();
    }

    //根据后台获取附近的用户
    public void getNearbyUser(){
        new Thread(){
            @Override
            public void run() {
                SharedPreferences pre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                String userId = pre.getString("user_id","");
                Intent response = getIntent();
                String location = response.getStringExtra("location");
                Log.e("经度和纬度",location);
                String result = new Utils().getConnectionResult("user","getNearbyUser",
                        "location="+location+"&&userId="+userId);
                Message message = new Message();
                message.obj = result;
                nearbyUserHandler.sendMessage(message);
            }
        }.start();
    }

}