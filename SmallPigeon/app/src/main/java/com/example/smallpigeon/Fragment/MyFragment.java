package com.example.smallpigeon.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.smallpigeon.LoginOrRegister.LoginActivity;
import com.example.smallpigeon.My.IdentifyActivity;
import com.example.smallpigeon.My.IsIdentifyActivity;
import com.example.smallpigeon.My.MyCollectActivity;
import com.example.smallpigeon.My.MyCommunity;
import com.example.smallpigeon.My.MyCommunity.MyCommunity;
import com.example.smallpigeon.My.MyPlan;
import com.example.smallpigeon.My.Paihang;
import com.example.smallpigeon.My.PersonalCenter;
import com.example.smallpigeon.R;
import com.example.smallpigeon.RoundImageView;
import com.example.smallpigeon.Utils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


public class MyFragment extends Fragment {

    private RoundImageView myAvatar;
    private ImageView my_Settings;
    private Button loginOrRegister;
    private LinearLayout btnAuthenticate;
    private LinearLayout btnCommunity;
    private LinearLayout btnGradeRank;
    private LinearLayout btnPlan;
    private LinearLayout right_collect;
    private CustomButtonListener listener;
    private String useId;
    private int is_accreditation;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String status = msg.obj + "";
            is_accreditation = Integer.parseInt(status);
        }
    };
//    private Integer is_accreditation;
    private String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my,container,false);
        getViews(view);
        registerListener();
        loginEvent();
        selectUserInfo(useId);
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
        right_collect.setOnClickListener(listener);
    }

    class CustomButtonListener implements View.OnClickListener,View.OnTouchListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.my_Settings:
                    if(loginOrNot()){
                        Intent intent = new Intent(getContext(), PersonalCenter.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getContext(),"请先登录哦！",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.right_Authentication:
                    if(loginOrNot()){
                        if(is_accreditation==0) {
                            Intent intentAuthor = new Intent(getContext(), IdentifyActivity.class);
                            startActivity(intentAuthor);
                        }else{
                            Intent intentJudge = new Intent(getContext(), IsIdentifyActivity.class);
                            intentJudge.putExtra("identify",is_accreditation);
                            startActivity(intentJudge);
                        }
                    }else{
                        Toast.makeText(getContext(),"请先登录哦！",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.right_community:
                    if(loginOrNot()){
                        Intent intent3 = new Intent(getContext(), MyCommunity.class);
                        startActivity(intent3);
                    }else{
                        Toast.makeText(getContext(),"请先登录哦！",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.right_gradeRank:
                    if(loginOrNot()){
                        Intent intent1 = new Intent(getContext(), Paihang.class);
                        startActivity(intent1);
                    }else{
                        Toast.makeText(getContext(),"请先登录哦！",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.right_plan:
                    if(loginOrNot()){
                        Intent intent2 = new Intent(getContext(), MyPlan.class);
                        startActivity(intent2);
                    }else {
                        Toast.makeText(getContext(),"请先登录哦！",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.right_collect:
//                    if(loginOrNot()){
                        Intent intent = new Intent(getContext(), MyCollectActivity.class);
                        startActivity(intent);
//                    }else {
//                        Toast.makeText(getContext(),"请先登录哦！",Toast.LENGTH_SHORT).show();
//                    }
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
                case R.id.right_collect:
                    motionEvent(right_collect,event);
                    break;
            }
            return false;
        }

    }

    //判断是否登录的方法
    private boolean loginOrNot(){
        SharedPreferences pre = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String userEmail = pre.getString("user_email","");
        String userId = pre.getString("user_id","");
    
        if(userEmail.equals("")||userEmail==null){
            return false;
        }else{
            return true;
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
        useId = pre.getString("user_id","");
        if(nickname.equals("") || nickname == null){
            loginOrRegister.setText("登录/注册");
            loginOrRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        } else{
            signIn(useId);
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
        myAvatar = view.findViewById(R.id.myAvatar);
        right_collect = view.findViewById(R.id.right_collect);
    }

    @Override
    public void onResume() {
        super.onResume();
        loginEvent();
        getAvatar();
        selectUserInfo(useId);
    }

    //获取头像
    private void getAvatar(){
        String userEmail = getContext().getSharedPreferences("userInfo",Context.MODE_PRIVATE).getString("user_email","");
        if(! userEmail.equals("") && userEmail != null){
            RequestOptions requestOptions = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
            Glide.with(getActivity()).load("http://"+getResources().getString(R.string.ip_address)
                    +":8080/smallpigeon/avatar/"+userEmail+".jpg").apply(requestOptions).into(myAvatar);
        }else{
            myAvatar.setImageDrawable(getResources().getDrawable(R.drawable.woman));
        }
    }

    /*
     * 登录  异步
     * */
    private void signIn(String userId) {
        EMClient.getInstance().login(userId, userId, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.e("MyFragment环信登录","登录成功");
            }
            @Override
            public void onError(int i, String s) {
                Log.e("MyFragment环信登录","登录失败"+i+","+s);
            }
            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
    //根据用户id得到 用户是否认证成功
    private void selectUserInfo(String userId) {
        new Thread(){
            @Override
            public void run() {
                String result = new Utils().getConnectionResult("user","getStatusByUserId", "userId="+ userId);
                Message message = new Message();
                message.obj = result;
                message.what=1;
                handler.sendMessage(message);
            }
        }.start();
    }
}