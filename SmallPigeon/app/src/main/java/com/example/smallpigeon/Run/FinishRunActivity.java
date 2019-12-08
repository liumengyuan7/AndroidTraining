package com.example.smallpigeon.Run;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.example.smallpigeon.MainActivity;
import com.example.smallpigeon.R;

import java.util.List;

public class FinishRunActivity extends AppCompatActivity {
    private TextView tvTime;
    private TextView tvDistance;
    private TextView tvSpeed;

    private Button btn_back;
    private Button btn_share;
    private ImageView iv_back;

    private List<LatLng> lists;//轨迹点集合
    private BaiduMap baiduMap;
    private MapView mapView;

    private CustomOnClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_run);

        mapView = findViewById(R.id.mapView);
        baiduMap = mapView.getMap();

        initView();
        registerListeners();

        Intent intent = getIntent();

        tvTime.setText(intent.getStringExtra("time")+"");
        tvDistance.setText(intent.getStringExtra("distance")+"");
        tvSpeed.setText(intent.getStringExtra("speed")+"");
        lists = intent.getParcelableArrayListExtra("list");
        Log.e("zt",lists.toString()+lists.size());

        drawLine(lists);
    }
    private void registerListeners() {
        listener = new CustomOnClickListener();
        btn_share.setOnClickListener(listener);
        btn_back.setOnClickListener(listener);
        iv_back.setOnClickListener(listener);
    }

    private void initView() {
        tvDistance = findViewById(R.id.tvDistance);
        tvSpeed = findViewById(R.id.tvSpeed);
        tvTime = findViewById(R.id.tvTime);
        btn_back = findViewById(R.id.btn_back);
        btn_share = findViewById(R.id.btn_share);
        iv_back = findViewById(R.id.iv_back);
    }

    private void drawLine(List<LatLng> lists) {
        Log.e("ztt",lists.toString()+lists.size());
        // 添加起点图标
        OverlayOptions startOptions = new MarkerOptions()
                .position(lists.get(0))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_start))
                .zIndex(9).draggable(true);
        // 添加终点图标
        OverlayOptions endOptions = new MarkerOptions().position(lists.get(lists.size()-1))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_end)).zIndex(9).draggable(true);

        // 添加路线（轨迹）
        OverlayOptions polylineOptions = new PolylineOptions().width(10)
                .color(Color.BLUE).points(lists);

        baiduMap.addOverlay(startOptions);
        baiduMap.addOverlay(endOptions);
        baiduMap.addOverlay(polylineOptions);

        animateMapStatus(lists);
    }

    private void animateMapStatus(List<LatLng> points) {
        if (null == points || points.isEmpty()) {
            return;
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng point : points) {
            builder.include(point);
        }
        MapStatusUpdate msUpdate = MapStatusUpdateFactory.newLatLngBounds(builder.build());
        baiduMap.animateMapStatus(msUpdate);
    }

    class CustomOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_back:
                    Intent back = new Intent(FinishRunActivity.this, MainActivity.class);
                    startActivity(back);
                    finish();
                    break;
                case R.id.btn_share:
                    //TODO:点击分享可将自己的跑步轨迹分享给其他人
                    break;
                case R.id.iv_back:
                    Intent backi = new Intent(FinishRunActivity.this, MainActivity.class);
                    startActivity(backi);
                    finish();
                    break;
            }
        }
    }
}
