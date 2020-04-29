package com.example.smallpigeon.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallpigeon.BaiduMap.activity.TracingActivity;
import com.example.smallpigeon.R;
import com.example.smallpigeon.Run.MatchingActivity;
import com.example.smallpigeon.Utils;


public class RunFragment extends Fragment {

    private Button btnPersonal;
    private Button btnMatching;
    private TextView TodayNum;//今日总公里数
    private MyClickListener listener;
    private Handler handler  = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj+"";
            if(result==null || result.equals("null")){
                TodayNum.setText("0.0");
            }else{
                TodayNum.setText(result);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_run,container,false);
        getViews(view);
        listener = new MyClickListener();
        registerListener();
        if(loginOrNot()){
            //TODO:从数据库中查询今日该用户的总公里数
            selectLength();
        }else { }
        return view;
    }

    private void selectLength() {
        new Thread(){
            @Override
            public void run() {
                //TODO：根据日期查询该用户跑步的总公里数
                String id = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString ("user_id","");
                Log.e("dsadsadsadas",id);
                String result = new Utils().getConnectionResult("record","getTotalKm","id="+id);
                Message message = new Message();
                message.obj = result;
                handler.sendMessage(message);
            }
        }.start();
    }


    //点击事件监听器
    class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.PersonalButton:
                    //个人模式
                    if(loginOrNot()){
                        Intent intentP = new Intent( getContext(), TracingActivity.class );
                        startActivity( intentP );
                    }else{
                        Toast.makeText(getContext(),"请先登录哦！",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.MatchingButton:
                    //匹配模式
                    if(loginOrNot()){
                        Intent intentM = new Intent( getContext(), MatchingActivity.class );
                        startActivity( intentM );
                    }else{
                        Toast.makeText(getContext(),"请先登录哦！",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    //判断是否登录的方法
    private boolean loginOrNot(){
        SharedPreferences pre = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String userEmail = pre.getString("user_email","");
        if(userEmail.equals("")||userEmail==null){
            return false;
        }else{
            return true;
        }
    }

    //注册监听器
    private void registerListener() {
        btnPersonal.setOnClickListener( listener );
        btnMatching.setOnClickListener( listener );
    }

    //获取视图控件
    private void getViews(View view) {
        btnPersonal = view.findViewById( R.id.PersonalButton );
        btnMatching = view.findViewById( R.id.MatchingButton );
        TodayNum = view.findViewById(R.id.TodayNum);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(loginOrNot()){
            //TODO:从数据库中查询今日该用户的总公里数
            selectLength();
        }else { }
    }
}