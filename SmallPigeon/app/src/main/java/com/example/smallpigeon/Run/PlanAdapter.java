package com.example.smallpigeon.Run;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smallpigeon.BaiduMap.activity.TracingActivity;
import com.example.smallpigeon.R;
import com.example.smallpigeon.RoundImageView;

import java.util.List;
import java.util.Map;

public class PlanAdapter extends BaseAdapter{
    private Context context;
    private List<Map<String,String>> dataSourse;
    private int stringId;
    private RoundImageView matchUserImg;

    public PlanAdapter(Context context,List<Map<String,String>> dataSourse,int id){
        this.context = context;
        this.dataSourse = dataSourse;
        this.stringId = id;
    }

    @Override
    public int getCount() {
        return dataSourse.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSourse.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(stringId, null);
        }
        //获取控件id
        matchUserImg = convertView.findViewById(R.id.match_userImg);
        TextView plan_time = convertView.findViewById( R.id.plan_time );
        TextView plan_address = convertView.findViewById( R.id.plan_matchAddress );
        TextView plan_email = convertView.findViewById( R.id.plan_matchEmail );
        TextView plan_nickname = convertView.findViewById( R.id.plan_matchNickname );
        Button btnToFinish = convertView.findViewById( R.id.goFinish );
        //添加数据
        Glide.with(context).load("http://"+context.getResources().getString(R.string.ip_address)+":8080/smallpigeon/avatar/"+
                dataSourse.get(position).get("plan_email")+".jpg").into(matchUserImg);
        plan_time.setText(dataSourse.get(position).get("plan_time"));
        plan_address.setText(dataSourse.get(position).get("plan_address"));
        if(dataSourse.get(position).get("plan_email").length()>17)
            plan_email.setText(dataSourse.get(position).get("plan_email").substring(0,17));
        else plan_email.setText(dataSourse.get(position).get("plan_email"));
        plan_nickname.setText(dataSourse.get(position).get("plan_nickname"));



        //添加按钮点击的事件
        btnToFinish.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示同伴是否到达的AlertDialog
                showAlertDialog();
            }
        } );

        return convertView;
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder( context );
        builder.setTitle( "你们的小约定" );
        builder.setMessage( "您的同伴是否已到达？" );
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 跳转到跑步界面
                Intent intent = new Intent( context, TracingActivity.class );
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder( context );
                builder2.setMessage( "您是否愿意继续等待同伴？若不等待则开始个人模式。" );
                builder2.setPositiveButton("是", null);
                builder2.setNegativeButton("否", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到跑步界面
                        Intent intent = new Intent( context, TracingActivity.class );
                        context.startActivity(intent);
                    }
                });
                AlertDialog alertDialog2 = builder2.create();
                alertDialog2.show();
            }
        });
        AlertDialog alertDialog = builder.create( );
        alertDialog.show();
    }
}