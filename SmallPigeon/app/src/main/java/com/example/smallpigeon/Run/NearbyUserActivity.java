package com.example.smallpigeon.Run;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        getViews();
        prepareDataSource();
        nearbyUserAdapter = new NearbyUserAdapter(dataSource,getApplicationContext(),R.layout.nearby_user_item);
        nearbyUserList.setAdapter(nearbyUserAdapter);
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
            String[] interest = nearbyUserAndInterest[1].split("+");
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

}
