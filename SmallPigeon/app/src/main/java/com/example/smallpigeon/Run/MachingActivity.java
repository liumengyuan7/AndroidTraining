package com.example.smallpigeon.Run;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.smallpigeon.Adapter.RankAdapter;
import com.example.smallpigeon.Entity.PlanContent;
import com.example.smallpigeon.MainActivity;
import com.example.smallpigeon.R;
import com.example.smallpigeon.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
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

    //未完成plan列表
    private List<PlanContent> planContents = new ArrayList<>(  );
    //视图控件
    private ImageView machingBack;
    private ListView lvMatchingTask;
    private Button btnRematch;
    private MyClickListener listener;

    List<Map<String,String>> information;
    PlanAdapter customAdapter1;
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
                        information.add(item);
                    }
                    ListView listView1 = findViewById(R.id.lv_machingTask);
                    customAdapter1 = new PlanAdapter(getApplicationContext(),information,R.layout.run_maching_listitem);
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
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_maching );

        //获取视图控件
        getViews();
        //注册监听器
        listener = new MyClickListener();
        registerListener();
        setStatusBar();//状态栏隐藏
        //获取未完成计划的方法
        String result=new Utils().getConnectionResult("plan","getUnfinishedPlan");
        Message message = new Message();
        message.obj = result;
        handler.sendMessage(message);


    }
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }

    private void registerListener() {
        btnRematch.setOnClickListener( listener );
        machingBack.setOnClickListener( listener );
    }

    /**
     * @Descripe: 获取视图控件id
     */
    private void getViews() {
        lvMatchingTask = findViewById( R.id.lv_machingTask );
        btnRematch = findViewById( R.id.btn_rematch );
        machingBack = findViewById( R.id.maching_back );
    }

    private class MyClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_rematch:
                    //重新匹配按钮
                    Intent intent1 = new Intent( MachingActivity.this, RemachingActivity.class );
                    startActivity( intent1 );
                    break;
                case R.id.maching_back:
                    //返回按钮
                    Intent intent2 = new Intent( MachingActivity.this, MainActivity.class );
                    startActivity( intent2 );
                    break;
            }
        }
    }
}