package com.example.smallpigeon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smallpigeon.R;

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
        ImageView img=convertView.findViewById(R.id.plan_delete);

        //添加数据
        plan_time.setText(dataSourse.get(position).get("plan_time").toString());
        plan_address.setText(dataSourse.get(position).get("plan_address").toString());
        plan_email.setText(dataSourse.get(position).get("plan_email").toString());
        plan_nickname.setText(dataSourse.get(position).get("plan_nickname").toString());

        notifyDataSetChanged();
        //删除计划事件
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }
}