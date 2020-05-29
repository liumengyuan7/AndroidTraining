package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallpigeon.Adapter.MyplanAdapter;
import com.example.smallpigeon.Adapter.RankAdapter;
import com.example.smallpigeon.R;
import com.example.smallpigeon.Run.PlanAdapter;
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

public class MyPlan extends AppCompatActivity {

    private ImageView plan_back;
    private List<Map<String,String>> information;
    private MyplanAdapter myplanAdapter;
    private Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj + "";
            if(!result.equals("empty")){
                try {
                    information = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject json = jsonArray.getJSONObject(i);
                        Log.e("当前第"+i+"个计划",json.toString());
                        Map<String, String> item = new HashMap<>();
                        item.put("plan_id",json.getString("id"));
                        String time = json.get("plan_time").toString();
                        Date d = new Date(time);
                        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
                        item.put("plan_time",sdf.format(d));//时间转换
                        item.put("plan_address",json.getString("plan_address"));
                        item.put("plan_email",json.getString("plan_email"));
                        item.put("plan_nickname",json.getString("plan_nickname"));
                        //状态：0表示未完成 1表示完成
                        item.put("plan_status",json.getString("plan_status"));
                        information.add(item);
                    }
                    ListView listView1 = findViewById(R.id.plan_list);
                    myplanAdapter = new MyplanAdapter(getApplicationContext(),information,R.layout.layout_plan_listitem);
                    listView1.setAdapter(myplanAdapter);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plan);
        getViews();
        getMyAllPlan();
        setStatusBar();//状态栏隐藏

        plan_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    //向后台发送请求，获取所有未完成的计划
    private void getMyAllPlan() {
        new Thread(){
            @Override
            public void run() {
                SharedPreferences pre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                String userId = pre.getString("user_id","");
                //获取未完成计划的方法
                String result=new Utils().getConnectionResult("plan","getAllPlan","userId="+userId);
                Message message = new Message();
                message.obj = result;
                handler.sendMessage(message);
            }
        }.start();
    }

    //获取视图的控件
    public void getViews(){
        plan_back=findViewById(R.id.myplan_back);

    }

    //设置手机的状态栏
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }

}
