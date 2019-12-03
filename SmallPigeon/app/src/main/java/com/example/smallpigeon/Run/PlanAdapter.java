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

import com.example.smallpigeon.BaiduMap.activity.TracingActivity;
import com.example.smallpigeon.Entity.PlanContent;
import com.example.smallpigeon.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class PlanAdapter extends BaseAdapter {
    // 数据源
    private List<PlanContent> contents;
    private int itemLayoutId;
    private Context context;

    //构造方法
    public PlanAdapter(List<PlanContent> contents, int itemLayoutId, Context context) {
        this.contents = contents;
        this.itemLayoutId = itemLayoutId;
        this.context = context;
    }


    @Override
    //数据条数
    public int getCount(){
        if (null != contents){
            return contents.size();
        }
        else{
            return 0;
        }
    }

    @Override
    //每一项要显示的数据
    public Object getItem(int position){
        if (null != contents){
            return contents.get(position);
        }
        else{
            return null;
        }
    }

    @Override
    //获得id参数值
    public long getItemId(int position) {
        return position;
    }

    @Override
    //加载item布局文件，生成每项item对应的视图对象
    public View getView(final int position, View convertView, ViewGroup parent){
        //加载item对应的布局文件
        if (null == convertView){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(itemLayoutId,null);
        }
        //获取每个item中各种视图控件的对象
        TextView tvStatus = convertView.findViewById( R.id.tv_status );
        TextView tvTime = convertView.findViewById( R.id.tv_time );
        TextView tvAddress = convertView.findViewById( R.id.tv_address );
        TextView tvCompanion = convertView.findViewById( R.id.tv_companion );
        Button btnToFinish = convertView.findViewById( R.id.btn_toFinish );
        //显示
        PlanContent planContent = contents.get( position );
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        tvTime.setText( formatter.format( planContent.getPlanTime() ) );
        tvAddress.setText( planContent.getPlanAddress() );
        //todo 此处应该从数据库查询出伙伴的用户名
        tvCompanion.setText( planContent.getCompanion_id()+"" );
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