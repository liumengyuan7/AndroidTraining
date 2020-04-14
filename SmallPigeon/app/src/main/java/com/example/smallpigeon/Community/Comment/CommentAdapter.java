package com.example.smallpigeon.Community.Comment;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smallpigeon.Entity.CommentContent;
import com.example.smallpigeon.R;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends BaseAdapter {

    private Context context;
    private List<CommentContent> data = new ArrayList<>( );

    public CommentAdapter(Context c, List<CommentContent> data){
        this.context = c;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        // 重用convertView
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate( R.layout.list_item_comment, null);
            holder.comment_name = (TextView) convertView.findViewById(R.id.tv_comment_username);
            holder.comment_content = (TextView) convertView.findViewById(R.id.tv_comment_content);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        // 适配数据
        holder.comment_name.setText(data.get(i).getUser_id());
        holder.comment_content.setText(data.get(i).getComment_content());

        return convertView;
    }

    /**
     * 添加一条评论,刷新列表
     * @param comment
     */
    public void addComment(CommentContent comment){
        data.add(comment);
        notifyDataSetChanged();
    }

    /**
     * 静态类，便于GC回收
     */
    public static class ViewHolder{
        TextView comment_name;
        TextView comment_content;
    }
}