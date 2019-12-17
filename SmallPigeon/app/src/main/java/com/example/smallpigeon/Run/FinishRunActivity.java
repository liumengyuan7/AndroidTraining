package com.example.smallpigeon.Run;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.example.smallpigeon.MainActivity;
import com.example.smallpigeon.R;
import com.example.smallpigeon.TrackApplication;

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
    private MapStatusUpdate msUpdate = null;
    private CustomOnClickListener listener;
    private MapStatus mapStatus = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_run);

        mapView = findViewById(R.id.mapView);
        baiduMap = mapView.getMap();
        zoomLevelOp();
        setStatusBar();
        initView();
        registerListeners();

        Intent intent = getIntent();

        tvTime.setText(intent.getStringExtra("time")+"");
        tvDistance.setText(intent.getStringExtra("distance")+"");
        tvSpeed.setText(intent.getStringExtra("speed")+"");
        lists = intent.getParcelableArrayListExtra("list");
        Log.e("zt",lists.toString()+lists.size());


        for (int i=0;i<lists.size();i++){
            setMapStatus(lists.get(i), 18);
        }
        drawLine(lists);
    }
    //隐藏状态栏
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
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
        zoomLevelOp();
        if (lists.size() == 1) {
            OverlayOptions startOptions = new MarkerOptions().position(lists.get(0))
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_start))
                    .zIndex(9).draggable(true);
            baiduMap.addOverlay(startOptions);
            animateMapStatus(lists.get(0), 18.0f);
            return;
        }
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
        msUpdate = MapStatusUpdateFactory.zoomTo(18.0f);
        msUpdate = MapStatusUpdateFactory.newLatLngBounds(builder.build());
        baiduMap.animateMapStatus(msUpdate);
    }

    public void setMapStatus(LatLng point, float zoom) {
        MapStatus.Builder builder = new MapStatus.Builder();
        mapStatus = builder.target(point).zoom(zoom).build();
        baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(mapStatus));
    }

    public void animateMapStatus(LatLng point, float zoom) {
        MapStatus.Builder builder = new MapStatus.Builder();
        mapStatus = builder.target(point).zoom(zoom).build();
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mapStatus));
    }

    private void zoomLevelOp() {
        baiduMap.setMaxAndMinZoomLevel(21,15);
        //设置默认比例为100m
        MapStatusUpdate mpu = MapStatusUpdateFactory.zoomTo(21);
        baiduMap.setMapStatus(mpu);
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
