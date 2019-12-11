package com.example.smallpigeon.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smallpigeon.R;
import com.example.smallpigeon.Run.MachingActivity;
import com.example.smallpigeon.Run.TracingActivity;

import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class MyplanAdapter extends BaseAdapter{
    private Context context;
    private List<Map<String,String>> dataSourse;
    private int stringId;

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
        plan_status.setText(dataSourse.get(position).get("plan_status").toString());
        if(plan_status.equals("1")){
            plan_status.setText("已完成");
            plan_status.setTextColor(Color.parseColor("#108A10"));
            go_wancheng.setText("");
        }
        else {
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
        plan_time.setText(dataSourse.get(position).get("plan_time").toString());
        plan_address.setText(dataSourse.get(position).get("plan_address").toString());
        plan_email.setText(dataSourse.get(position).get("plan_email").toString());
        plan_nickname.setText(dataSourse.get(position).get("plan_nickname").toString());

        notifyDataSetChanged();
        LinearLayout linearLayout = convertView.findViewById(R.id.item_box);
        //删除计划事件
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSourse.remove(linearLayout);
            }
        });

        return convertView;
    }
}