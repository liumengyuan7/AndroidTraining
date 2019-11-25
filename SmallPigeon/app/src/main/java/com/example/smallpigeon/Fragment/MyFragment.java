package com.example.smallpigeon.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smallpigeon.LoginOrRegister.LoginActivity;
import com.example.smallpigeon.My.PersonalCenter;
import com.example.smallpigeon.My.UpdatePersonalMsg;
import com.example.smallpigeon.R;


public class MyFragment extends Fragment {
    private ImageView my_Settings;
    private Button loginOrRegister;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my,container,false);
        getViews(view);
        btnEvent();

        return view;


    }

    //按钮的点击事件
    private void btnEvent() {
        //点击事件 跳转到修改个人资料页面
        my_Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PersonalCenter.class);
                startActivity(intent);
            }
        });

        loginOrRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    //获取视图的控件
    private void getViews(View view) {
        //获取-我的-页面右上角小工具控件id
        my_Settings=view.findViewById(R.id.my_Settings);
        loginOrRegister = view.findViewById(R.id.LoginOrRegister);
    }

}
