package com.example.smallpigeon.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallpigeon.Entity.DynamicContent;
import com.example.smallpigeon.R;

import java.util.ArrayList;
import java.util.List;

public class PeopleAdapter extends BaseAdapter {
    private Context context;
    private int itemLayoutID;
    private List<DynamicContent> list = new ArrayList<>();
    private MyClickListener listener = new MyClickListener();

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
            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            holder.tv_nickName = convertView.findViewById(R.id.tv_nickName);
            holder.tv_date = convertView.findViewById(R.id.tv_date);
            holder.device = convertView.findViewById(R.id.device);
            holder.dynamic_item_txt = convertView.findViewById(R.id.dynamic_item_txt);
            holder.dynamic_item_img = convertView.findViewById( R.id.dynamic_item_img );
            holder.ll_forward = convertView.findViewById( R.id.ll_forward );
            holder.ll_toComment = convertView.findViewById( R.id.ll_toComment );
            holder.ll_like = convertView.findViewById( R.id.ll_like );
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        DynamicContent dynamicContent = list.get(position);
        holder.tv_nickName.setText(dynamicContent.getUserContent().getUserNickname());
        holder.tv_date.setText(dynamicContent.getDate());
        holder.device.setText(dynamicContent.getDevice());
        holder.dynamic_item_txt.setText(dynamicContent.getContent());

        //点击事件
        holder.ll_forward.setOnClickListener( listener );
        holder.ll_toComment.setOnClickListener( listener );
        holder.ll_like.setOnClickListener( listener );

        return convertView;
    }

    class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_forward:
                    //TODO:转发
                    Toast.makeText( context, "转发", Toast.LENGTH_SHORT ).show();
                    break;
                case R.id.ll_toComment:
                    //TODO：评论
                    Toast.makeText( context, "评论", Toast.LENGTH_SHORT ).show();
                    break;
                case R.id.ll_like:
                    //TODO：点赞
                    Toast.makeText( context, "点赞", Toast.LENGTH_SHORT ).show();
                    break;
            }
        }
    }

    static class ViewHolder{
        ImageView iv_icon;//用户头像
        TextView tv_nickName;//用户昵称
        TextView tv_date;//发表时间
        TextView device;//设备名称
        TextView dynamic_item_txt;//发表内容
        ImageView dynamic_item_img;//发表内容配图
        LinearLayout ll_forward;//转发
        LinearLayout ll_toComment;//评论
        LinearLayout ll_like;//赞
    }
}