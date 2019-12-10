package com.example.smallpigeon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smallpigeon.Entity.DynamicContent;
import com.example.smallpigeon.Entity.UserContent;
import com.example.smallpigeon.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PeopleAdapter extends BaseAdapter {
    private Context context;
    private int itemLayoutID;
    private List<DynamicContent> list = new ArrayList<>();

    public PeopleAdapter(Context context, int itemLayoutID, List<DynamicContent> list) {
        this.context = context;
        this.itemLayoutID = itemLayoutID;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (null == convertView) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(itemLayoutID, null);
            holder.iv_unfold = convertView.findViewById(R.id.iv_unfold);
            holder.iv_comment = convertView.findViewById(R.id.iv_comment);
            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            holder.iv_praise = convertView.findViewById(R.id.iv_praise);
            holder.tv_date = convertView.findViewById(R.id.tv_date);
            holder.tv_nickName = convertView.findViewById(R.id.tv_nickName);
            holder.dynamic_item_txt = convertView.findViewById(R.id.dynamic_item_txt);
            holder.device = convertView.findViewById(R.id.device);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        DynamicContent dynamicContent = list.get(position);
        holder.tv_nickName.setText(dynamicContent.getUserContent().getUserNickname());
        holder.tv_date.setText(dynamicContent.getDate());
        holder.device.setText(dynamicContent.getDevice());
        holder.dynamic_item_txt.setText(dynamicContent.getContent());

        return convertView;
    }

    static class ViewHolder{
         ImageView iv_icon;//用户头像
         ImageView dynamic_item_img;//发表内容配图
         ImageView iv_unfold;//转发
         ImageView iv_comment;//评论
         ImageView iv_praise;//赞
         TextView tv_nickName;//用户昵称
         TextView tv_date;//发表时间
         TextView device;//设备名称
         TextView dynamic_item_txt;//发表内容
    }
}
