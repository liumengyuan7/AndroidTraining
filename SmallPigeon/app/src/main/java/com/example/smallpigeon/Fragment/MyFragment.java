package com.example.smallpigeon.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallpigeon.Adapter.RankAdapter;
import com.example.smallpigeon.LoginOrRegister.LoginActivity;
import com.example.smallpigeon.My.Grade_Rank;
import com.example.smallpigeon.My.Paihang;
import com.example.smallpigeon.My.PersonalCenter;
import com.example.smallpigeon.My.UpdatePersonalMsg;
import com.example.smallpigeon.R;


public class MyFragment extends Fragment {
    private ImageView my_Settings;
    private Button loginOrRegister;
    private LinearLayout btnAuthenticate;
    private LinearLayout btnCommunity;
    private LinearLayout btnGradeRank;
    private LinearLayout btnPlan;
    private CustomButtonListener listener;
    private boolean is = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my,container,false);
        getViews(view);
        registerListener();
        loginEvent();
        return view;


    }

    //注册监听器
    private void registerListener() {
        listener = new CustomButtonListener();
        my_Settings.setOnClickListener(listener);
        btnAuthenticate.setOnClickListener(listener);
        btnAuthenticate.setOnTouchListener(listener);
        btnCommunity.setOnClickListener(listener);
        btnCommunity.setOnTouchListener(listener);
        btnGradeRank.setOnClickListener(listener);
        btnGradeRank.setOnTouchListener(listener);
        btnPlan.setOnClickListener(listener);
        btnPlan.setOnTouchListener(listener);
    }

    class CustomButtonListener implements View.OnClickListener,View.OnTouchListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.my_Settings:
                    Intent intent = new Intent(getContext(), PersonalCenter.class);
                    startActivity(intent);
                    break;
                case R.id.right_Authentication:
                    Toast.makeText(getContext(),"认证！",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.right_community:
                    Toast.makeText(getContext(),"社区！",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.right_gradeRank:
                    Toast.makeText(getContext(),"积分榜！",Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(getContext(), Paihang.class);
                    startActivity(intent1);
                    break;
                case R.id.right_plan:
                    Toast.makeText(getContext(),"计划！",Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()){
                case R.id.right_Authentication:
                    motionEvent(btnAuthenticate,event);
                    break;
                case R.id.right_community:
                    motionEvent(btnCommunity,event);
                    break;
                case R.id.right_gradeRank:
                    motionEvent(btnGradeRank,event);
                    break;
                case R.id.right_plan:
                    motionEvent(btnPlan,event);
                    break;
            }
            return false;
        }

    }

    //动态事件
    private void motionEvent(View view,MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                view.setBackgroundColor(Color.parseColor("#C0C0C0"));
                break;
            case MotionEvent.ACTION_UP:
                view.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
        }
    }

    //登录按钮
    private void loginEvent(){
        //跳转登录界面
        SharedPreferences pre = getContext().getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        String nickname = pre.getString("user_nickname","");
        if(nickname.equals("") || nickname == null){
            loginOrRegister.setText("登录/注册");
            loginOrRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            loginOrRegister.setText("欢迎登录："+nickname);
            loginOrRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { }
            });
        }
    }

    //获取视图的控件
    private void getViews(View view) {
        //获取-我的-页面右上角小工具控件id
        my_Settings=view.findViewById(R.id.my_Settings);
        loginOrRegister = view.findViewById(R.id.LoginOrRegister);
        btnAuthenticate = view.findViewById(R.id.right_Authentication);
        btnCommunity = view.findViewById(R.id.right_community);
        btnGradeRank = view.findViewById(R.id.right_gradeRank);
        btnPlan = view.findViewById(R.id.right_plan);
    }

    @Override
    public void onResume() {
        super.onResume();
        loginEvent();
    }

}
