package com.example.smallpigeon.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallpigeon.Community.Comment.CommentAdapter;
import com.example.smallpigeon.Entity.CommentContent;
import com.example.smallpigeon.Entity.DynamicContent;
import com.example.smallpigeon.R;

import java.util.ArrayList;
import java.util.List;

public class PeopleAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private int itemLayoutID;
    private List<DynamicContent> list = new ArrayList<>();

    public PeopleAdapter(Context context, int itemLayoutID, List<DynamicContent> list) {
        this.context = context;
        this.itemLayoutID = itemLayoutID;
        this.list = list;
    }

    private btnOnclick btnOnclick;

    public btnOnclick getBtnOnclick() {
        return btnOnclick;
    }

    public void setBtnOnclick(btnOnclick btnOnclick) {
        this.btnOnclick = btnOnclick;
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
            holder.ll_toComment = convertView.findViewById(R.id.ll_toComment);
            holder.ll_forward = convertView.findViewById(R.id.ll_forward);
            holder.ll_like = convertView.findViewById(R.id.ll_like);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        DynamicContent dynamicContent = list.get(position);
        holder.tv_nickName.setText(dynamicContent.getUserContent().getUserNickname());
        holder.tv_date.setText(dynamicContent.getDate());
        holder.device.setText(dynamicContent.getDevice());
        holder.dynamic_item_txt.setText(dynamicContent.getContent());
        holder.ll_toComment.setOnClickListener(this);
        holder.ll_like.setOnClickListener(this);
        holder.ll_forward.setOnClickListener(this);
        holder.ll_toComment.setTag(position);
        holder.ll_forward.setTag(position);
        holder.ll_like.setTag(position);

        return convertView;
    }
    @Override
    public void onClick(View view) {
        btnOnclick.click(view,(int)view.getTag());
    }


    static class ViewHolder{
        ImageView iv_icon;//用户头像
        TextView tv_nickName;//用户昵称
        TextView tv_date;//发表时间
        TextView device;//设备名称
        TextView dynamic_item_txt;//发表内容
        ImageView dynamic_item_img;//发表内容配图
        LinearLayout ll_toComment;//评论
        LinearLayout ll_forward;//转发
        LinearLayout ll_like;//点赞
    }

    public interface btnOnclick{
        public void click(View view,int index);
    }
}