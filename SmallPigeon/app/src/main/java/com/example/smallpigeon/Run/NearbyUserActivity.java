package com.example.smallpigeon.Run;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.smallpigeon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NearbyUserActivity extends AppCompatActivity {

    private ListView nearbyUserList;
    private NearbyUserAdapter nearbyUserAdapter;
    private List<Map<String,String>> dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_user);

        setStatusBar();
        getViews();
        prepareDataSource();
        nearbyUserAdapter = new NearbyUserAdapter(dataSource,getApplicationContext(),R.layout.nearby_user_item);
        nearbyUserList.setAdapter(nearbyUserAdapter);
        itemClickListener();
    }

    //获取视图中的控件
    private void getViews() {
        nearbyUserList = findViewById(R.id.nearby_user_list);
    }

    //准备dataSource里面的数据
    public void prepareDataSource(){
        dataSource = new ArrayList<>();
        try {
            String[] nearbyUserAndInterest = getIntent().getStringExtra("nearbyUser").split(";");
            JSONArray jsonArray = new JSONArray(nearbyUserAndInterest[0]);
            String[] interest = nearbyUserAndInterest[1].split("\\+");
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject json = jsonArray.getJSONObject(i);
                Map<String, String> item = new HashMap<>();
                item.put("user_nickname",json.getString("user_nickname"));
                item.put("user_sex",json.getString("user_sex"));
                item.put("user_points",json.getString("user_points"));
                item.put("user_interest",interest[i]);
                item.put("user_email",json.getString("user_email"));
                item.put("user_id",json.getString("id"));
                dataSource.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //item的点击事件
    public void itemClickListener(){
        nearbyUserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,String> item = dataSource.get(position);
                Intent intent = new Intent(getApplicationContext(),RemachingActivity.class);
                intent.putExtra("user_nickname",item.get("user_nickname"));
                intent.putExtra("user_sex",item.get("user_sex"));
                intent.putExtra("user_points",item.get("user_points"));
                intent.putExtra("user_interest",item.get("user_interest"));
                intent.putExtra("user_email",item.get("user_email"));
                intent.putExtra("user_id",item.get("id"));
                startActivity(intent);
            }
        });
    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }

}
