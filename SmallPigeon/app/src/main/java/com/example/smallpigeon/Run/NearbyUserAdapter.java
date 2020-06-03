package com.example.smallpigeon.Run;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.smallpigeon.R;
import com.example.smallpigeon.RoundImageView;

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
        RoundImageView nearbyUserAvatar = convertView.findViewById(R.id.nearby_user_avatar);
        TextView nearbyUserEmail = convertView.findViewById(R.id.nearby_user_email);
        TextView nearbyUserNickname = convertView.findViewById(R.id.nearby_user_nickname);
        String userEmail= item.get("user_email");
        if(userEmail.length()>17){
            nearbyUserEmail.setTextSize(17);
            nearbyUserEmail.setText("邮箱/账号："+userEmail.substring(0,16));
        }
        else nearbyUserEmail.setText("邮箱/账号："+userEmail);
        nearbyUserNickname.setText("昵称："+item.get("user_nickname"));
        RequestOptions requestOptions = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context).load("http://"+context.getResources().getString(R.string.ip_address)
                +":8080/smallpigeon/avatar/"+item.get("user_email")+".jpg").apply(requestOptions).into(nearbyUserAvatar);
        return convertView;
    }
}
