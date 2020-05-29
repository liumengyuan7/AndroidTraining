package com.example.smallpigeon.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smallpigeon.R;
import com.example.smallpigeon.Run.MachingActivity;
import com.example.smallpigeon.Utils;

import java.util.List;
import java.util.Map;

public class MyplanAdapter extends BaseAdapter{
    private Context context;
    private List<Map<String,String>> dataSourse;
    private int stringId;
    private Handler handlePlan = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj+"";
            if(result.equals("true")){
                dataSourse.remove(msg.what);
                notifyDataSetChanged();
                Toast toast=Toast.makeText(context,"删除成功！",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }else if(result.equals("false")){
                Toast toast=Toast.makeText(context,"删除失败！",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    };

    public MyplanAdapter(Context context,List<Map<String,String>> dataSourse,int id){
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
        Log.e("dateSourse",dataSourse.toString());
        //获取控件id
        TextView plan_time = convertView.findViewById( R.id.plan_time );
        TextView plan_address = convertView.findViewById( R.id.plan_matchAddress );
//        TextView plan_email = convertView.findViewById( R.id.plan_matchEmail );
        TextView plan_nickname = convertView.findViewById( R.id.plan_matchNickname );
        ImageView match_userImg=convertView.findViewById(R.id.match_userImg);
        Glide.with(context).load(dataSourse.get(position).get("plan_email")).into(match_userImg);
        //计划状态-默认为未完成
        TextView plan_status = convertView.findViewById( R.id.plan_status );
        String status = dataSourse.get(position).get("plan_status");
        if(status.equals("1")){
            plan_status.setText("已完成");
            plan_status.setTextColor(Color.parseColor("#108A10"));
        }
        else if(status.equals("0")){
            plan_status.setText("未完成");
            plan_status.setTextColor(Color.parseColor("#ff0000"));
        }

        //删除按钮
        ImageView img=convertView.findViewById(R.id.plan_delete);

        //添加数据
        plan_time.setText(dataSourse.get(position).get("plan_time"));
        plan_address.setText(dataSourse.get(position).get("plan_address"));
//        match_userImg.setText(dataSourse.get(position).get("plan_email"));
        plan_nickname.setText(dataSourse.get(position).get("plan_nickname"));
        String planId = dataSourse.get(position).get("plan_id");
        notifyDataSetChanged();
        //删除计划事件
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.obj = new Utils().getConnectionResult("plan","deleteUserPlan","planId="+planId);
                        message.what = position;
                        handlePlan.sendMessage(message);
                    }
                }.start();
            }
        });

        return convertView;
    }

}