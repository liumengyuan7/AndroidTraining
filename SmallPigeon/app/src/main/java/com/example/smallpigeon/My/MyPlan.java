package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyPlan extends AppCompatActivity {
    private TextView plan_status;
    private TextView plan_time;
    private TextView plan_address;
    private TextView plan_email;
    private TextView plan_nickname;
    private ImageView delete;
    private ImageView plan_back;


    List<Map<String,String>> information;
    MyplanAdapter customAdapter1;
    String[] result1;
    String[] result2;
    private Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.obj + "";
            if(!result.equals("false")){
                try {
                    information = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject json1 = jsonArray.getJSONObject(i);
                        JSONObject json2 = json1.getJSONObject("attrs");
                        Map<String, String> item = new HashMap<>();
                        item.put("plan_time",json2.getString("plan_time"));
                        item.put("plan_address",json2.getString("plan_address"));
                        item.put("plan_email",json2.getString("plan_email"));
                        item.put("plan_nickname",json2.getString("plan_nickname"));
                        //状态：0表示未完成 1表示完成
                        item.put("plan_status",json2.getString("plan_status"));
                        information.add(item);
                    }
                    ListView listView1 = findViewById(R.id.plan_list);
                    customAdapter1 = new MyplanAdapter(getApplicationContext(),information,R.layout.layout_plan_listitem);
                    listView1.setAdapter(customAdapter1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast toastTip = Toast.makeText(getApplicationContext(),"获取失败！请检查网络！",Toast.LENGTH_LONG);
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
        //获取未完成计划的方法
        String result=new Utils().getConnectionResult("plan","getAllPlans");
        Message message = new Message();
        message.obj = result;
        handler.sendMessage(message);
        setStatusBar();//状态栏隐藏

        plan_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void getViews(){
        plan_back=findViewById(R.id.myplan_back);

    }
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }
}
