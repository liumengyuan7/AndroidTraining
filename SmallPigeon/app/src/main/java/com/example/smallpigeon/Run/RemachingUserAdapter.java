package com.example.smallpigeon.Run;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smallpigeon.Entity.UserContent;
import com.example.smallpigeon.R;

import java.util.List;

/**
 * @Time: 2019/11/27
 * @Author: 程璐
 * @Descripe:
 */
public class RemachingUserAdapter extends BaseAdapter {
    // 数据源
    private List<UserContent> contents;
    private int itemLayoutId;
    private Context context;

    //构造方法
    public RemachingUserAdapter(List<UserContent> contents, int itemLayoutId, Context context) {
        this.contents = contents;
        this.itemLayoutId = itemLayoutId;
        this.context = context;
    }


    @Override
    //数据条数
    public int getCount(){
        if (null != contents){
            return contents.size();
        }
        else{
            return 0;
        }
    }

    @Override
    //每一项要显示的数据
    public Object getItem(int position){
        if (null != contents){
            return contents.get(position);
        }
        else{
            return null;
        }
    }

    @Override
    //获得id参数值
    public long getItemId(int position) {
        return position;
    }

    @Override
    //加载item布局文件，生成每项item对应的视图对象
    public View getView(final int position, View convertView, ViewGroup parent){
        //加载item对应的布局文件
        if (null == convertView){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(itemLayoutId,null);
        }
        //获取每个item中各种视图控件的对象
        ImageView ivUserImg = convertView.findViewById( R.id.iv_userImg );
        TextView ivUserName = convertView.findViewById( R.id.iv_userName );
        Button btnToChat = convertView.findViewById( R.id.btn_toChat );

        //显示
        UserContent userContent = contents.get( position );
        //todo 用户头像应从数据库查询调用

        ivUserName.setText( userContent.getUserNickname() );

        //添加按钮点击的事件
        btnToChat.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 点击开始聊天按钮，应跳转到聊天界面
            }
        } );
        return convertView;
    }

}
