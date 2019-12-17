package com.example.smallpigeon.BaiduMap.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.api.entity.OnEntityListener;
import com.baidu.trace.api.fence.FenceAlarmPushInfo;
import com.baidu.trace.api.fence.MonitoredAction;
import com.baidu.trace.api.track.DistanceRequest;
import com.baidu.trace.api.track.DistanceResponse;
import com.baidu.trace.api.track.HistoryTrackRequest;
import com.baidu.trace.api.track.HistoryTrackResponse;
import com.baidu.trace.api.track.LatestPoint;
import com.baidu.trace.api.track.LatestPointResponse;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.api.track.SupplementMode;
import com.baidu.trace.model.CoordType;
import com.baidu.trace.model.LocationMode;
import com.baidu.trace.model.OnTraceListener;
import com.baidu.trace.model.ProcessOption;
import com.baidu.trace.model.PushMessage;
import com.baidu.trace.model.SortType;
import com.baidu.trace.model.StatusCodes;
import com.baidu.trace.model.TraceLocation;
import com.baidu.trace.model.TransportMode;
import com.example.smallpigeon.BaiduMap.model.CurrentLocation;
import com.example.smallpigeon.BaiduMap.receiver.TrackReceiver;
import com.example.smallpigeon.BaiduMap.utils.CommonUtil;
import com.example.smallpigeon.BaiduMap.utils.Constants;
import com.example.smallpigeon.BaiduMap.utils.MapUtil;
import com.example.smallpigeon.BaiduMap.utils.ViewUtil;
import com.example.smallpigeon.MainActivity;
import com.example.smallpigeon.R;
import com.example.smallpigeon.Run.FinishRunActivity;
import com.example.smallpigeon.TrackApplication;
import com.example.smallpigeon.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class TracingActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView tracingBack;

    //计时
    private boolean isPause = false;
    private Handler handler  = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                long t = (long) msg.obj;
                currentSecond = t;
                tvTime.setText("用时："+getFormat(currentSecond));
            }else if (msg.what==2){

            }
        }
    };
    private String lock = "";
    private long currentSecond = 0;
    private MyRunnable myRunnable;
    private Thread thread;

    private TrackApplication trackApp = null;

    private ViewUtil viewUtil = null;

    private Button traceBtn = null;//开始/暂停按钮
    private Button continueBtn = null;//继续按钮
    private Button gatherBtn = null;//停止按钮
    private ImageView tracing_back = null;//返回按钮

    private NotificationManager notificationManager = null;

    private PowerManager powerManager = null;

    private PowerManager.WakeLock wakeLock = null;

    private TrackReceiver trackReceiver = null;

    /**
     * 地图工具
     */
    private MapUtil mapUtil = null;

    /**
     * 轨迹服务监听器
     */
    private OnTraceListener traceListener = null;

    /**
     * 轨迹监听器(用于接收纠偏后实时位置回调)
     */
    private OnTrackListener trackListener = null;

    /**
     * Entity监听器(用于接收实时定位回调)
     */
    private OnEntityListener entityListener = null;

    /**
     * 实时定位任务
     */
    private RealTimeHandler realTimeHandler = new RealTimeHandler();

    private RealTimeLocRunnable realTimeLocRunnable = null;

    private boolean isRealTimeRunning = true;

    private int notifyId = 0;

    /**
     * 打包周期
     */
    public int packInterval = Constants.DEFAULT_PACK_INTERVAL;

    /*轨迹----------------------------------------------------------------------------------*/
    private HistoryTrackRequest historyTrackRequest;//历史轨迹请求
    private DistanceRequest distanceRequest;//查询里程
    /**
     * 查询历史轨迹开始时间
     */
    public long startTime = CommonUtil.getCurrentTime();

    /**
     * 查询历史轨迹结束时间
     */
    public long endTime = CommonUtil.getCurrentTime();

    private int pageIndex = 1;

    /**
     * 轨迹点集合
     */
    private List<LatLng> trackPoints;

    private boolean firstLocate = true;
    private SDKReceiver mReceiver;
    private int mCurrentDirection = 0;

    //获取TextView
    private TextView tvTime;
    private TextView tvDistance;
    private TextView tvSpeed;

    private ImageView btn_activity_options;

    private double distance = 0;//公里数
    private double speed = 0;//速度
    private long time;//本次跑步时长


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * 构造广播监听类，监听 SDK key 验证以及网络异常广播
     */
    public class SDKReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            String s = intent.getAction();

            if (s.equals( SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
                Toast.makeText(TracingActivity.this,"AK验证失败，地图功能无法正常使用", Toast.LENGTH_SHORT).show();
            } else if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK)) {
                Toast.makeText(TracingActivity.this,"AK验证成功", Toast.LENGTH_SHORT).show();
            } else if (s.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                Toast.makeText(TracingActivity.this,"网络错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tracing );


        init();

        tracingBack = findViewById( R.id.tracing_back );

        tracingBack.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
    }

    private void init() {
        initListener();
        trackApp = (TrackApplication) getApplicationContext();
        viewUtil = new ViewUtil();
        mapUtil = MapUtil.getInstance();
        mapUtil.init((MapView) findViewById(R.id.tracing_mapView));
        mapUtil.setCenter(trackApp);//设置地图中心点
        //开启实时定位
        startRealTimeLoc(Constants.LOC_INTERVAL);
        powerManager = (PowerManager) trackApp.getSystemService(Context.POWER_SERVICE);

        traceBtn = (Button) findViewById(R.id.btn_trace);
        gatherBtn = (Button) findViewById(R.id.btn_gather);
        continueBtn = findViewById(R.id.btn_trace1);
        tracing_back = findViewById(R.id.tracing_back);

        traceBtn.setOnClickListener(this);
        gatherBtn.setOnClickListener(this);
        continueBtn.setOnClickListener(this);
        tracing_back.setOnClickListener(this);
        setTraceBtnStyle();
//        setGatherBtnStyle();

        btn_activity_options = findViewById(R.id.btn_activity_options);
        btn_activity_options.setOnClickListener(this);

        tvDistance = findViewById(R.id.tvDistance);
        tvSpeed = findViewById(R.id.tvSpeed);
        tvTime = findViewById(R.id.tvTime);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //初始化轨迹点集合
        trackPoints = new ArrayList<>();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // 追踪选项设置
            case R.id.btn_activity_options:
                ViewUtil.startActivityForResult(TracingActivity.this, TracingOptionsActivity.class, Constants
                        .REQUEST_CODE);
                break;

            case R.id.btn_trace:
                if (traceBtn.getText().equals("开始")){
                    trackApp.mClient.startTrace(trackApp.mTrace, traceListener);
                    startRealTimeLoc(packInterval);
                    trackApp.mClient.startGather(traceListener);//开始采集

                }else {
                    trackApp.mClient.stopTrace(trackApp.mTrace, traceListener);//停止服务
                    trackApp.mClient.stopGather(traceListener);//停止采集
                    stopRealTimeLoc();
                    Log.e("暂停onclick","暂停");
                }

                break;

            case R.id.btn_gather:
                //结束按钮，关闭采集追踪服务并将跑步信息插入到数据库中
                //点击结束按钮显示分享和返回按钮
                thread.interrupt();
                //将跑步记录插入数据库中
                insertRunMsg();
                Intent intent = new Intent(TracingActivity.this, FinishRunActivity.class);
                intent.putExtra("distance",tvDistance.getText().toString());
                intent.putExtra("speed",tvSpeed.getText().toString());
                intent.putExtra("time",tvTime.getText().toString());
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) trackPoints);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_trace1://继续按钮
                trackApp.mClient.startTrace(trackApp.mTrace, traceListener);
                startRealTimeLoc(packInterval);
                trackApp.mClient.startGather(traceListener);//开始采集
                break;
            case R.id.tracing_back:
                finish();
                break;
            default:
                break;
        }

    }

    private void insertRunMsg() {
        String id = getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString ("user_id","");
        new Thread(){
            @Override
            public void run() {
                //TODO：将此次跑步的相关信息插入到数据库中，包括用户id、本次跑步时长(time)、公里数(distance)、速度(speed)、当前日期(date)、本次跑步积分
                String params = "id="+id+"&&time="+time+"&&distance="+distance+"&&speed="+speed;
                String result = new Utils().getConnectionResult("record","addUserRecord",params);
            }
        }.start();
    }

    /**
     * 查询里程
     */
    private void queryDistances(){
        distanceRequest = new DistanceRequest();
        distanceRequest.setServiceId(trackApp.serviceId);//设置轨迹服务id，Trace中的id
        distanceRequest.setEntityName(trackApp.entityName);//Trace中的entityName
        distanceRequest.setTag(trackApp.getTag());
        distanceRequest.setStartTime(startTime);
        distanceRequest.setEndTime(endTime);
        distanceRequest.setProcessed(true);//纠偏

        ProcessOption processOption = new ProcessOption();//纠偏选项
        processOption.setRadiusThreshold(50);//精度过滤
        processOption.setTransportMode( TransportMode.walking);//交通方式，默认为驾车
        processOption.setNeedDenoise(true);//去噪处理，默认为false，不处理
        processOption.setNeedVacuate(true);//设置抽稀，仅在查询历史轨迹时有效，默认需要false
        processOption.setNeedMapMatch(true);//绑路处理，将点移到路径上，默认不需要false
        distanceRequest.setProcessOption(processOption);
        trackApp.initRequest(distanceRequest);
        distanceRequest.setSupplementMode(SupplementMode.no_supplement);

        Log.e("zt:distanceRequest",distanceRequest.toString());
        trackApp.mClient.queryDistance(distanceRequest,trackListener);
    }

    /*---------------------------------轨迹开始-----------------------------------------*/
    /**
     * 查询历史轨迹
     */
    private void queryHistoryTrack() {

        historyTrackRequest = new HistoryTrackRequest();
        ProcessOption processOption = new ProcessOption();//纠偏选项
        processOption.setRadiusThreshold(50);//精度过滤
        processOption.setTransportMode( TransportMode.walking);//交通方式，默认为驾车
        processOption.setNeedDenoise(true);//去噪处理，默认为false，不处理
        processOption.setNeedVacuate(true);//设置抽稀，仅在查询历史轨迹时有效，默认需要false
        processOption.setNeedMapMatch(true);//绑路处理，将点移到路径上，默认不需要false
        historyTrackRequest.setProcessOption(processOption);
        trackApp.initRequest(historyTrackRequest);
        /**
         * 设置里程补偿方式，当轨迹中断5分钟以上，会被认为是一段中断轨迹，默认不补充
         * 比如某些原因造成两点之间的距离过大，相距100米，那么在这两点之间的轨迹如何补偿
         * SupplementMode.driving：补偿轨迹为两点之间最短驾车路线
         * SupplementMode.riding：补偿轨迹为两点之间最短骑车路线
         * SupplementMode.walking：补偿轨迹为两点之间最短步行路线
         * SupplementMode.straight：补偿轨迹为两点之间直线
         */
        historyTrackRequest.setSupplementMode( SupplementMode.no_supplement);
        historyTrackRequest.setSortType( SortType.asc);//设置返回结果的排序规则，默认升序排序；升序：集合中index=0代表起始点；降序：结合中index=0代表终点。
        historyTrackRequest.setCoordTypeOutput( CoordType.bd09ll);//设置返回结果的坐标类型，默认为百度经纬度

        /**
         * 设置是否返回纠偏后轨迹，默认不纠偏
         * true：打开轨迹纠偏，返回纠偏后轨迹;
         * false：关闭轨迹纠偏，返回原始轨迹。
         * 打开纠偏时，请求时间段内轨迹点数量不能超过2万，否则将返回错误。
         */
        historyTrackRequest.setProcessed(true);
        historyTrackRequest.setServiceId(trackApp.serviceId);//设置轨迹服务id，Trace中的id
        historyTrackRequest.setEntityName(trackApp.entityName);//Trace中的entityName

        /**
         * 设置startTime和endTime，会请求这段时间内的轨迹数据;
         * 这里查询采集开始到采集结束之间的轨迹数据
         */
        historyTrackRequest.setStartTime(startTime);
        historyTrackRequest.setEndTime(endTime);
        historyTrackRequest.setPageIndex(pageIndex);
        historyTrackRequest.setPageSize(Constants.PAGE_SIZE);
        trackApp.mClient.queryHistoryTrack(historyTrackRequest, trackListener);//发起请求，设置回调监听

    }

    /*-----------------------------轨迹结束---------------------------------------------------------------------*/


    /**
     * 设置服务按钮样式
     */
    private void setTraceBtnStyle() {
        boolean isTraceStarted = trackApp.trackConf.getBoolean("is_trace_started", false);
        if (isTraceStarted && traceBtn.getText().equals("开始")) {
            LinearLayout show = findViewById(R.id.layout_tracing_show);
            show.setVisibility(View.VISIBLE);
            traceBtn.setText("暂停");
            traceBtn.setTextColor(ResourcesCompat.getColor(getResources(), R.color
                    .white, null));
            myRunnable = new MyRunnable();
            thread = new Thread(myRunnable);
            thread.start();

        }else if(!isTraceStarted && traceBtn.getText().equals("暂停")){
            LinearLayout bottom = findViewById(R.id.layout_tracing_bottom);
            bottom.setVisibility(View.INVISIBLE);
            LinearLayout bottom1 = findViewById(R.id.layout_tracing_bottom1);
            bottom1.setVisibility(View.VISIBLE);
            Log.e("zt","暂停"+isPause);
            isPause = true;
        }else if(isTraceStarted && continueBtn.getText().equals("继续")){
            LinearLayout bottom = findViewById(R.id.layout_tracing_bottom);
            bottom.setVisibility(View.VISIBLE);
            LinearLayout bottom1 = findViewById(R.id.layout_tracing_bottom1);
            bottom1.setVisibility(View.INVISIBLE);
            Log.e("zt","继续"+isPause);
            isPause = false;
            new Thread(new MyRunnable()).start();
            Log.e("zt","继续"+isPause);
        } else {
            traceBtn.setText("开始");
            traceBtn.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
            Log.e("else","暂停");
        }
    }

    /**
     * 设置采集按钮样式
     */
    private void setGatherBtnStyle() {
        boolean isGatherStarted = trackApp.trackConf.getBoolean("is_gather_started", false);
        if (isGatherStarted) {
            gatherBtn.setText(R.string.stop_gather);
            gatherBtn.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        } else {
            gatherBtn.setText(R.string.stop_gather);
            gatherBtn.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        }
    }

    /**
     * 实时定位任务
     *
     * @author baidu
     */
    class RealTimeLocRunnable implements Runnable {

        private int interval = 0;

        public RealTimeLocRunnable(int interval) {
            this.interval = interval;
        }

        @Override
        public void run() {
            if (isRealTimeRunning) {
                trackApp.getCurrentLocation(entityListener, trackListener);
                realTimeHandler.postDelayed(this, interval * 1000);
            }
        }
    }

    public void startRealTimeLoc(int interval) {
        isRealTimeRunning = true;
        realTimeLocRunnable = new RealTimeLocRunnable(interval);
        realTimeHandler.post(realTimeLocRunnable);
    }

    public void stopRealTimeLoc() {
        isRealTimeRunning = false;
        if (null != realTimeHandler && null != realTimeLocRunnable) {
            realTimeHandler.removeCallbacks(realTimeLocRunnable);
        }
        trackApp.mClient.stopRealTimeLoc();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null == data) {
            return;
        }

        if (data.hasExtra("locationMode")) {
            LocationMode locationMode = LocationMode.valueOf(data.getStringExtra("locationMode"));
            trackApp.mClient.setLocationMode(locationMode);
        }

        if (data.hasExtra("isNeedObjectStorage")) {
            boolean isNeedObjectStorage = data.getBooleanExtra("isNeedObjectStorage", false);
            trackApp.mTrace.setNeedObjectStorage(isNeedObjectStorage);
        }

        if (data.hasExtra("gatherInterval") || data.hasExtra("packInterval")) {
            int gatherInterval = data.getIntExtra("gatherInterval", Constants.DEFAULT_GATHER_INTERVAL);
            int packInterval = data.getIntExtra("packInterval", Constants.DEFAULT_PACK_INTERVAL);
            TracingActivity.this.packInterval = packInterval;
            trackApp.mClient.setInterval(gatherInterval, packInterval);
        }

    }

    private void initListener() {

        trackListener = new OnTrackListener() {

            @Override
            public void onHistoryTrackCallback(HistoryTrackResponse historyTrackResponse) {
                queryDistances();//查询里程
            }


            /**
             * 里程回调接口
             * @param distanceResponse
             */
            @Override
            public void onDistanceCallback(DistanceResponse distanceResponse) {
                distance = distanceResponse.getDistance()/1000;//里程，单位：米
                speed = distance/(endTime-startTime)*3600;
                time = endTime - startTime;
                Log.e("zt","里程数："+distanceResponse.getDistance()+"速度："+speed+"时间："+(endTime-startTime));
                tvDistance.setText("里程："+doubleToString(distance)+"KM");
                tvSpeed.setText("速度："+doubleToString(speed)+"KM/h");
            }

            @Override
            //经过服务端纠偏后的最新的一个位置点，回调
            public void onLatestPointCallback(LatestPointResponse response) {
                if (StatusCodes.SUCCESS != response.getStatus()) {
                    return;
                }

                LatestPoint point = response.getLatestPoint();
                if (null == point || CommonUtil.isZeroPoint(point.getLocation().getLatitude(), point.getLocation()
                        .getLongitude())) {
                    return;
                }

                LatLng currentLatLng = mapUtil.convertTrace2Map(point.getLocation());
                if (null == currentLatLng) {
                    return;
                }

                /*轨迹查询开始-------------------------------------------------------*/
                if (firstLocate){
                    firstLocate = false;
                    Toast.makeText(TracingActivity.this, "起点获取中，请稍后...", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*轨迹查询结束-------------------------------------------------------------------*/

                //当前经纬度
                CurrentLocation.locTime = point.getLocTime();
                CurrentLocation.latitude = currentLatLng.latitude;
                CurrentLocation.longitude = currentLatLng.longitude;

                /*轨迹开始-----------------------------------------------------------------------*/
                if (trackPoints == null) {
                    return;
                }
                trackPoints.add(currentLatLng);
                endTime = CommonUtil.getCurrentTime();
                queryHistoryTrack();//通过查询历史轨迹筛选轨迹点进行绘制
                mapUtil.drawHistoryTrack(trackPoints, SortType.asc);// 时时动态的画出运动轨迹

                /*轨迹结束----------------------------------------------------------------------*/

                if (null != mapUtil) {
                    mapUtil.updateStatus(currentLatLng, true);
                }
            }
        };



        entityListener = new OnEntityListener() {

            @Override
            public void onReceiveLocation(TraceLocation location) {

                if (StatusCodes.SUCCESS != location.getStatus() || CommonUtil.isZeroPoint(location.getLatitude(),
                        location.getLongitude())) {
                    return;
                }
                LatLng currentLatLng = mapUtil.convertTraceLocation2Map(location);
                if (null == currentLatLng) {
                    return;
                }
                CurrentLocation.locTime = CommonUtil.toTimeStamp(location.getTime());
                CurrentLocation.latitude = currentLatLng.latitude;
                CurrentLocation.longitude = currentLatLng.longitude;

                if (null != mapUtil) {
                    mapUtil.updateStatus(currentLatLng, true);
                }
            }

        };

        traceListener = new OnTraceListener() {

            /**
             * 绑定服务回调接口
             * @param errorNo  状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功 </pre>
             *                <pre>1：失败</pre>
             */
            @Override
            public void onBindServiceCallback(int errorNo, String message) {
                viewUtil.showToast(TracingActivity.this,
                        String.format("onBindServiceCallback, errorNo:%d, message:%s ", errorNo, message));
            }

            /**
             * 开启服务回调接口
             * @param errorNo 状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功 </pre>
             *                <pre>10000：请求发送失败</pre>
             *                <pre>10001：服务开启失败</pre>
             *                <pre>10002：参数错误</pre>
             *                <pre>10003：网络连接失败</pre>
             *                <pre>10004：网络未开启</pre>
             *                <pre>10005：服务正在开启</pre>
             *                <pre>10006：服务已开启</pre>
             */
            @Override
            public void onStartTraceCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.START_TRACE_NETWORK_CONNECT_FAILED <= errorNo) {
                    trackApp.isTraceStarted = true;
                    SharedPreferences.Editor editor = trackApp.trackConf.edit();
                    editor.putBoolean("is_trace_started", true);
                    editor.apply();
                    setTraceBtnStyle();
                    registerReceiver();
                }
                viewUtil.showToast(TracingActivity.this,
                        String.format("onStartTraceCallback, errorNo:%d, message:%s ", errorNo, message));
            }

            /**
             * 停止服务回调接口
             * @param errorNo 状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功</pre>
             *                <pre>11000：请求发送失败</pre>
             *                <pre>11001：服务停止失败</pre>
             *                <pre>11002：服务未开启</pre>
             *                <pre>11003：服务正在停止</pre>
             */
            @Override
            public void onStopTraceCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.CACHE_TRACK_NOT_UPLOAD == errorNo) {
                    trackApp.isTraceStarted = false;
                    trackApp.isGatherStarted = false;
                    // 停止成功后，直接移除is_trace_started记录（便于区分用户没有停止服务，直接杀死进程的情况）
                    SharedPreferences.Editor editor = trackApp.trackConf.edit();
                    editor.remove("is_trace_started");
                    editor.remove("is_gather_started");
                    editor.apply();
                    setTraceBtnStyle();
                    setGatherBtnStyle();
                    unregisterPowerReceiver();
                }
                viewUtil.showToast(TracingActivity.this,
                        String.format("onStopTraceCallback, errorNo:%d, message:%s ", errorNo, message));
            }

            /**
             * 开启采集回调接口
             * @param errorNo 状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功</pre>
             *                <pre>12000：请求发送失败</pre>
             *                <pre>12001：采集开启失败</pre>
             *                <pre>12002：服务未开启</pre>
             */
            @Override
            public void onStartGatherCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.GATHER_STARTED == errorNo) {
                    trackApp.isGatherStarted = true;
                    SharedPreferences.Editor editor = trackApp.trackConf.edit();
                    editor.putBoolean("is_gather_started", true);
                    editor.apply();
                    setGatherBtnStyle();
                }
                viewUtil.showToast(TracingActivity.this,
                        String.format("onStartGatherCallback, errorNo:%d, message:%s ", errorNo, message));
            }

            /**
             * 停止采集回调接口
             * @param errorNo 状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功</pre>
             *                <pre>13000：请求发送失败</pre>
             *                <pre>13001：采集停止失败</pre>
             *                <pre>13002：服务未开启</pre>
             */
            @Override
            public void onStopGatherCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.GATHER_STOPPED == errorNo) {
                    trackApp.isGatherStarted = false;
                    SharedPreferences.Editor editor = trackApp.trackConf.edit();
                    editor.remove("is_gather_started");
                    editor.apply();
                    setGatherBtnStyle();
                }
                viewUtil.showToast(TracingActivity.this,
                        String.format("onStopGatherCallback, errorNo:%d, message:%s ", errorNo, message));
            }

            /**
             * 推送消息回调接口
             *
             * @param messageType 状态码
             * @param pushMessage 消息
             *                  <p>
             *                  <pre>0x01：配置下发</pre>
             *                  <pre>0x02：语音消息</pre>
             *                  <pre>0x03：服务端围栏报警消息</pre>
             *                  <pre>0x04：本地围栏报警消息</pre>
             *                  <pre>0x05~0x40：系统预留</pre>
             *                  <pre>0x41~0xFF：开发者自定义</pre>
             */
            @Override
            public void onPushCallback(byte messageType, PushMessage pushMessage) {
                if (messageType < 0x03 || messageType > 0x04) {
                    viewUtil.showToast(TracingActivity.this, pushMessage.getMessage());
                    return;
                }
                FenceAlarmPushInfo alarmPushInfo = pushMessage.getFenceAlarmPushInfo();
                if (null == alarmPushInfo) {
                    viewUtil.showToast(TracingActivity.this,
                            String.format("onPushCallback, messageType:%d, messageContent:%s ", messageType,
                                    pushMessage));
                    return;
                }
                StringBuffer alarmInfo = new StringBuffer();
                alarmInfo.append("您于")
                        .append(CommonUtil.getHMS(alarmPushInfo.getCurrentPoint().getLocTime() * 1000))
                        .append(alarmPushInfo.getMonitoredAction() == MonitoredAction.enter ? "进入" : "离开")
                        .append(messageType == 0x03 ? "云端" : "本地")
                        .append("围栏：").append(alarmPushInfo.getFenceName());

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                    Notification notification = new Notification.Builder(trackApp)
//                            .setContentTitle(getResources().getString(R.string.alarm_push_title))
                            .setContentText(alarmInfo.toString())
                            .setSmallIcon(R.mipmap.biao)
                            .setWhen(System.currentTimeMillis()).build();
                    notificationManager.notify(notifyId++, notification);
                }
            }

            @Override
            public void onInitBOSCallback(int errorNo, String message) {
                viewUtil.showToast(TracingActivity.this,
                        String.format("onInitBOSCallback, errorNo:%d, message:%s ", errorNo, message));
            }


        };

    }

    static class RealTimeHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    /**
     * 注册广播（电源锁、GPS状态）
     */
    private void registerReceiver() {
        if (trackApp.isRegisterReceiver) {
            return;
        }

        if (null == wakeLock) {
//            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "track upload");
        }
        if (null == trackReceiver) {
            trackReceiver = new TrackReceiver(wakeLock);
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        filter.addAction(StatusCodes.GPS_STATUS_ACTION);
        trackApp.registerReceiver(trackReceiver, filter);
        trackApp.isRegisterReceiver = true;

    }

    private void unregisterPowerReceiver() {
        if (!trackApp.isRegisterReceiver) {
            return;
        }
        if (null != trackReceiver) {
            trackApp.unregisterReceiver(trackReceiver);
        }
        trackApp.isRegisterReceiver = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        startRealTimeLoc(packInterval);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapUtil.onResume();

        // 在Android 6.0及以上系统，若定制手机使用到doze模式，请求将应用添加到白名单。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = trackApp.getPackageName();
            boolean isIgnoring = powerManager.isIgnoringBatteryOptimizations(packageName);
            if (!isIgnoring) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                try {
                    startActivity(intent);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapUtil.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopRealTimeLoc();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapUtil.clear();
        stopRealTimeLoc();
    }

    protected int getContentViewId() {
        return R.layout.activity_tracing;
    }


    public static String doubleToString(double num){
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }

    private class MyRunnable implements Runnable{

        @Override
        public void run() {
            currentSecond = currentSecond + 1000;
            Message message = new Message();
            message.what = 1;
            message.obj = currentSecond;
            handler.sendMessage(message);

            Log.e("zt","线程启动");
            if (!isPause){
                handler.postDelayed(this,1000);
                Log.e("zt","handler"+isPause);
            }
        }
    }

    public String getFormat(long sec){
        sec = sec/1000;
        int second = (int) (sec%60);
        int minute = (int) (sec/60);
        int hour = (int) (sec/3600);
        return String .format("%02d:%02d:%02d",hour,minute,second);
    }
}
