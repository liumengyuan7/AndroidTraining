package com.example.smallpigeon.Run;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smallpigeon.R;

import java.util.List;
import java.util.Map;

/**
 * @author syf
 * @ClassName NearbyUserAdapter
 * @description
 * @date 2020/04/30 11:12
 */

public class NearbyUserAdapter extends BaseAdapter {

    private List<Map<String,String>> dataSource;
    private Context context;
    private int itemId;

    public NearbyUserAdapter(List<Map<String,String>> dataSource,Context context,int itemId){
        this.dataSource = dataSource;
        this.context = context;
        this.itemId = itemId;
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(itemId, null);
        }
        Map<String,String> item = dataSource.get(position);
        TextView textView = convertView.findViewById(R.id.nearby_user_email);
        textView.setText(item.get("user_email"));
        Intent intent = new Intent(context,RemachingActivity.class);
        intent.putExtra("user_nickname",item.get("user_nickname"));
        intent.putExtra("user_sex",item.get("user_sex"));
        intent.putExtra("user_points",item.get("user_points"));
        intent.putExtra("user_interest",item.get("user_interest"));
        intent.putExtra("user_email",item.get("user_email"));
        intent.putExtra("user_id",item.get("id"));
        context.startActivity(intent);
        return convertView;
    }
}
