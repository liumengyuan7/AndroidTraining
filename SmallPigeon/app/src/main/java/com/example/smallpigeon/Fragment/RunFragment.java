package com.example.smallpigeon.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.smallpigeon.BaiduMap.activity.TracingActivity;
import com.example.smallpigeon.R;
import com.example.smallpigeon.Run.MachingActivity;


public class RunFragment extends Fragment {

    private Button btnPersonal;
    private Button btnMatching;

    private MyClickListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_run,container,false);
        getViews(view);
        listener = new MyClickListener();
        registerListener();
        return view;
    }


    //点击事件监听器
    class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.PersonalButton:
                    //个人模式
                    //TODO:先判断用户是否登陆，若没有登陆则提示用户先登录，用户登陆后才能进行跳转
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
                        Intent intentM = new Intent( getContext(), MachingActivity.class );
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
    }
}