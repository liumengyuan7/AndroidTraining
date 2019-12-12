package com.example.smallpigeon.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidubce.http.HttpMethodName;
import com.example.smallpigeon.R;
import com.example.smallpigeon.RoundImageView;
import com.example.smallpigeon.Run.MachingActivity;
import com.example.smallpigeon.Run.TracingActivity;
import com.example.smallpigeon.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

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
                Toast.makeText(context,"删除成功！",Toast.LENGTH_SHORT).show();
            }else if(result.equals("false")){
                Toast.makeText(context,"删除失败！",Toast.LENGTH_SHORT).show();
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
        //获取控件id
        TextView plan_time = convertView.findViewById( R.id.plan_time );
        TextView plan_address = convertView.findViewById( R.id.plan_matchAddress );
        TextView plan_email = convertView.findViewById( R.id.plan_matchEmail );
        TextView plan_nickname = convertView.findViewById( R.id.plan_matchNickname );

        //计划状态-默认为未完成
        TextView plan_status = convertView.findViewById( R.id.plan_status );
        TextView go_wancheng = convertView.findViewById( R.id.go_wancheng );
        String status = dataSourse.get(position).get("plan_status");
        if(status.equals("1")){
            plan_status.setText("已完成");
            plan_status.setTextColor(Color.parseColor("#108A10"));
            go_wancheng.setVisibility(View.INVISIBLE);
        }
        else if(status.equals("0")){
            plan_status.setText("未完成");
            plan_status.setTextColor(Color.parseColor("#ff0000"));
            go_wancheng.setVisibility(View.VISIBLE);
            //跳转到匹配界面完成跑步
            go_wancheng.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( context, MachingActivity.class );
                    context.startActivity(intent);
                }
            });
        }

        //删除按钮
        ImageView img=convertView.findViewById(R.id.plan_delete);

        //添加数据
        plan_time.setText(dataSourse.get(position).get("plan_time"));
        plan_address.setText(dataSourse.get(position).get("plan_address"));
        plan_email.setText(dataSourse.get(position).get("plan_email"));
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