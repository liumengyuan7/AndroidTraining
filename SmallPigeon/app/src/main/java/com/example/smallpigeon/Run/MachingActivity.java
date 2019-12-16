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

    //视图控件
    private ImageView machingBack;
    private Button btnRematch;
    private MyClickListener listener;
    private PopupWindow pop;
    private TextView secondDown;
    private boolean flag = true;

    private Handler matchHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try {
                String re = msg.obj + "";
                String result = re.split(";")[0];
                JSONArray jsonArray = new JSONArray(result);
                JSONObject json1 = jsonArray.getJSONObject(0);
                JSONObject json2 = new JSONObject(json1.getString("attrs"));
                Intent intent = new Intent(getApplicationContext(),RemachingActivity.class);
                intent.putExtra("user_nickname",json2.getString("user_nickname"));
                intent.putExtra("user_sex",json2.getString("user_sex"));
                intent.putExtra("user_points",json2.getString("user_points"));
                intent.putExtra("user_interest",re.split(";")[1]);
                intent.putExtra("user_email",json2.getString("user_email"));
                startActivity(intent);
                finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

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

    private Handler handleSecond = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String second = msg.obj + "";
            if(second.equals("0")){
                pop.dismiss();
                Toast.makeText(getApplicationContext(),"匹配失败！",Toast.LENGTH_SHORT).show();
            }else{
                secondDown.setText("正在匹配中... "+second+"秒");
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

    private class MyClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_rematch:
                    //popupwindow的显示
                    appearPopupWindow();
                    //重新匹配按钮
                    randomMatch();
                    break;
                case R.id.maching_back:
                    //返回按钮
                    finish();
                    break;
            }
        }
    }

    //随机匹配
    private void randomMatch(){
        new Thread(){
            @Override
            public void run() {
                SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
                String id = pre.getString("user_id","");
                for(int i=0;i<10;i++){
                    if(flag){
                        String result = new Utils().getConnectionResult("user","randomMatchFirst","id="+id);
                        if(result.equals("empty")){
                            String r = new Utils().getConnectionResult("user","randomMatchSecond","id="+id);
                            if(r.equals("no")){
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                continue;
                            }else{
                                secondDown.setText("匹配成功！");
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Message message = new Message();
                                message.obj = r;
                                matchHandler.sendMessage(message);
                                break;
                            }
                        }else{
                            secondDown.setText("匹配成功！");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Message message = new Message();
                            message.obj = result;
                            matchHandler.sendMessage(message);
                            break;
                        }
                    }
                }
            }
        }.start();
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

    //popupwindow的显示
    private void appearPopupWindow(){
        flag = true;
        View popView = getLayoutInflater().inflate(R.layout.match_pop,null);
        pop = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,true);
        pop.setAnimationStyle(R.style.popmatch_anim_style);
        pop.setOutsideTouchable(false);
        pop.showAtLocation(getWindow().getDecorView(),Gravity.CENTER,0,0);
        secondDown = popView.findViewById(R.id.secondDown);
        secondDown();
        final WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                flag = false;
                new Thread(){
                    @Override
                    public void run() {
                        SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
                        String id = pre.getString("user_id","");
                        new Utils().getConnectionResult("user","fixMatcherStatus","id="+id);
                    }
                }.start();
                Toast.makeText(getApplicationContext(),"取消匹配！",Toast.LENGTH_SHORT).show();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
        }});
    }

    //处理倒计时的方法
    private void secondDown(){
        new Thread(){
            @Override
            public void run() {
                for(int i = 10;i>=0;i--){
                    if(flag){
                        try {
                            if(secondDown.getText().toString().equals("匹配成功！")){
                                break;
                            }else{
                                Message message = new Message();
                                message.obj = i;
                                handleSecond.sendMessage(message);
                            }
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }

}