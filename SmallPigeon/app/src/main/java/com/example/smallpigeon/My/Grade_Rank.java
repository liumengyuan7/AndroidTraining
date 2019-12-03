package com.example.smallpigeon.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.smallpigeon.Adapter.RankAdapter;
import com.example.smallpigeon.Fragment.RunFragment;
import com.example.smallpigeon.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grade_Rank extends AppCompatActivity {
    private Button goRun;

    private List<Map<String,String>> information;
    private RankAdapter customAdapter1;
    private String[] result1;
    private String[] result2;

    private  Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String info = (String) msg.obj;
            if(!info.equals("false")) {
                result1 = info.split(";");
                int a=1;
                for (int i = 0; i < result1.length; i++) {
                    Map<String, String> itemData = new HashMap<>();
                    result2 = result1[i].split(",");
                    itemData.put("userName", result2[0]);
                    itemData.put("userPoints", result2[1]);
                    itemData.put("rank",a+"");
                    information.add(itemData);
                    a++;
                    customAdapter1.notifyDataSetChanged();
                }
            }
            else {
                Toast toastTip = Toast.makeText(getApplicationContext(),"获取排行榜失败！请检查网络！",Toast.LENGTH_LONG);
                toastTip.setGravity(Gravity.CENTER, 0, 0);
                toastTip.show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade__rank);
        findViews();

        information = new ArrayList<>();
        Map<String,String> item = new HashMap<>();
        ListView listView1 = findViewById(R.id.rank_points);
        customAdapter1 = new RankAdapter(this,information,R.layout.grade_rank_listitem);
        listView1.setAdapter(customAdapter1);

        getPoints();

        //点击按钮进入跑步界面
        goRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Grade_Rank.this, RunFragment.class);
                startActivity(intent);
                finish();
            }
        });
    }
    //向后台获取分数
    public void getPoints(){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+getResources().getString(R.string.ip_address)
                            +":8080/smallpigeon/user/gradeRank");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String result = reader.readLine();
                    Message message = new Message();
                    message.obj = result;
                    handler.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public void findViews(){
        goRun=findViewById(R.id.rank_GoRun);

    }
}
