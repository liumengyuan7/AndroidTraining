package com.example.smallpigeon.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.smallpigeon.Entity.DynamicContent;
import com.example.smallpigeon.Entity.UserContent;
import com.example.smallpigeon.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.ActionBar;

public class PeopleAdapter extends BaseAdapter {
    private Context context;
    private int itemLayoutID;
    private List<DynamicContent> list = new ArrayList<>();

    private PopupWindow mPopWindow;

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
            holder.ll_forward = convertView.findViewById( R.id.ll_forward );
            holder.ll_toComment = convertView.findViewById( R.id.ll_toComment );
            holder.ll_like = convertView.findViewById( R.id.ll_like );
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

        Log.e( "erro1", "error?" );
        holder.ll_toComment.setOnClickListener( v -> new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e( "erro2", "error?" );
                showPopupWindow();
            }
        } );

        return convertView;
    }

    @SuppressLint("WrongConstant")
    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(context).inflate(R.layout.comment_popuwindow, null);
        mPopWindow = new PopupWindow(contentView,
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        //防止PopupWindow被软件盘挡住（可能只要下面一句，可能需要这两句）
        mPopWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopWindow.setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置软键盘弹出
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(1000, InputMethodManager.HIDE_NOT_ALWAYS);//这里给它设置了弹出的时间
        //设置各个控件的点击响应
//        final EditText editText = contentView.findViewById(R.id.pop_editText);
//        Button btn = contentView.findViewById(R.id.pop_btn);

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String inputString = editText.getText().toString();
//                Toast.makeText( getContext(), inputString, Toast.LENGTH_SHORT).show();
//                mPopWindow.dismiss();//让PopupWindow消失
//            }
//        });
        //是否具有获取焦点的能力
        mPopWindow.setFocusable(true);
        //显示PopupWindow
        View rootview = LayoutInflater.from(context).inflate(R.layout.activity_main, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    static class ViewHolder{
        ImageView iv_icon;//用户头像
        ImageView dynamic_item_img;//发表内容配图
        LinearLayout ll_forward;//转发
        ImageView iv_unfold;
        LinearLayout ll_toComment;//评论
        ImageView iv_comment;
        LinearLayout ll_like;//赞
        ImageView iv_praise;//赞
        TextView tv_nickName;//用户昵称
        TextView tv_date;//发表时间
        TextView device;//设备名称
        TextView dynamic_item_txt;//发表内容
    }
}
