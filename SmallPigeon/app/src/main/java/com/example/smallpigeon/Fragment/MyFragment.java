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
import android.widget.ImageView;

import com.example.smallpigeon.My.UpdatePersonalMsg;
import com.example.smallpigeon.R;


public class MyFragment extends Fragment {
    private ImageView my_Settings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my,container,false);
        //获取-我的-页面右上角小工具控件id
        my_Settings=view.findViewById(R.id.my_Settings);
        //注册监听事件
        my_Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),UpdatePersonalMsg.class);
                startActivity(intent);
            }
        });



        return view;


    }
}
