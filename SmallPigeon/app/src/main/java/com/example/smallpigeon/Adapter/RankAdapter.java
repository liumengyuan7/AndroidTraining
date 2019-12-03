package com.example.smallpigeon.Adapter;

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

public class RankAdapter extends BaseAdapter{
    private AppCompatActivity context;
    private List<Map<String,String>> dataSourse;
    private int stringId;

    public RankAdapter(AppCompatActivity context,List<Map<String,String>> dataSourse,int id){
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
        //nickname
        TextView userName = convertView.findViewById(R.id.gradeRank_userName);
        userName.setText(dataSourse.get(position).get("userName").toString());
       // 积分
        TextView userPoints = convertView.findViewById(R.id.gradeRank_userPoints);
        userPoints.setText(dataSourse.get(position).get("userPoints").toString());
        //
        String rank=dataSourse.get(position).get("rank").toString();
        ImageView img= convertView.findViewById(R.id.user_ranking);

        switch (rank){
            case "1":
                img.setImageDrawable(context.getResources().getDrawable(R.drawable.frist));
                break;
            case "2":
                img.setImageDrawable(context.getResources().getDrawable(R.drawable.second));
                break;
            case "3":
                img.setImageDrawable(context.getResources().getDrawable(R.drawable.thrid));
                break;
        }


        notifyDataSetChanged();

        return convertView;
    }
}