package com.example.smallpigeon.Adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smallpigeon.Chat.ChatHelper;
import com.example.smallpigeon.R;
import com.hyphenate.easeui.domain.EaseUser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class FindfriendAdapter extends BaseAdapter {
    private Context context;
    private int itemLayoutId;
    private List<EaseUser> users;
    private int myId;
    private String myEmail;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj.toString();
            if(result.equals("false")){
                Toast.makeText(context,"添加好友失败",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"成功加为好友，快去聊天吧！",Toast.LENGTH_SHORT).show();
            }
        }
    };
    public FindfriendAdapter(Context context, int itemLayoutId, List<EaseUser> users,int myId,String myEmail) {
        this.context = context;
        this.itemLayoutId = itemLayoutId;
        this.users = users;
        this.myId = myId;
        this.myEmail = myEmail;
    }

    @Override
    public int getCount() {
        if (null != users) {
            return users.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(null==convertView){
            convertView = LayoutInflater.from(context).inflate(itemLayoutId,null);
            viewHolder = new ViewHolder();
            viewHolder.userImage = convertView.findViewById(R.id.avatar);
            viewHolder.userName = convertView.findViewById(R.id.name);
            viewHolder.btnAdd = convertView.findViewById(R.id.indicator);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        //填充数据
        EaseUser easeUser = users.get(position);
        viewHolder.userName.setText(easeUser.getUserEmail().toString());
        viewHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact(users.get(position).getUserEmail(),users.get(position).getId(),easeUser);
            }
        });
        notifyDataSetChanged();
        return convertView;
    }
    static final class ViewHolder{//定义用于缓存item布局中子控件对象的类
        private ImageView userImage;
        private TextView userName;
        private Button btnAdd;
    }
    public void addContact(String friendEmail,int friendId,EaseUser easeUser){
        if(myEmail.equals(friendEmail)){//比较是不是加自己
            Toast.makeText(context,R.string.not_add_myself,Toast.LENGTH_SHORT).show();
            return;
        }
		if(ChatHelper.getInstance().getContactList().containsValue(easeUser)){
            Toast.makeText(context,R.string.This_user_is_already_your_friend,Toast.LENGTH_SHORT).show();
			return;
		}
        sendMessageToAddContact(context,myId,friendId);
    }
    public void sendMessageToAddContact(Context context,int myId,int friendId){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+context.getResources().getString(R.string.ip_address)
                            +":8080/smallpigeon/friend/addContact?myId="+myId+"&friendId="+friendId);
                    Log.e("url",url.toString());
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String result = reader.readLine();
                    Message message = new Message();
                    message.obj = result;
                    message.what = 3;
                    Log.e("添加好友",result);
                    handler.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
