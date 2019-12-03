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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.smallpigeon.Adapter.RankAdapter;
import com.example.smallpigeon.Fragment.MyFragment;
import com.example.smallpigeon.Fragment.RunFragment;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Paihang extends AppCompatActivity {
    private Button goRun;
    private ImageView img;

    List<Map<String,String>> information;
    RankAdapter customAdapter1;
    String[] result1;
    String[] result2;

    private  Handler handler=new Handler() {
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
                        Log.e("dsadsadsa",json2.toString());
                        Map<String, String> item = new HashMap<>();
                        item.put("userName",json2.getString("user_nickname"));
                        item.put("userPoints",json2.getString("user_points"));
                        item.put("rank",i+1+"");
                        information.add(item);
                    }
                    ListView listView1 = findViewById(R.id.rank_points);
                    customAdapter1 = new RankAdapter(getApplicationContext(),information,R.layout.grade_rank_listitem);
                    listView1.setAdapter(customAdapter1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast toastTip = Toast.makeText(getApplicationContext(),"获取排行榜失败！请检查网络！",Toast.LENGTH_LONG);
                toastTip.setGravity(Gravity.CENTER, 0, 0);
                toastTip.show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paihang);
        findViews();

        getPoints();

        //点击按钮进入跑步界面
        goRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Paihang.this, RunFragment.class);
                startActivity(intent);
                finish();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Paihang.this, MyFragment.class);
                startActivity(intent1);
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
        img=findViewById(R.id.grade_back);
    }
}
