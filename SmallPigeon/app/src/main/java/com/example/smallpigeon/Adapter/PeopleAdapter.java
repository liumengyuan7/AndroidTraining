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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.smallpigeon.Community.Comment.CommentAdapter;
import com.example.smallpigeon.Entity.CommentContent;
import com.example.smallpigeon.Entity.DynamicContent;
import com.example.smallpigeon.R;

import java.util.ArrayList;
import java.util.List;

public class PeopleAdapter extends BaseAdapter {
    private Context context;
    private int itemLayoutID;
    private List<DynamicContent> list = new ArrayList<>();
    private MyClickListener listener = new MyClickListener();
    private RelativeLayout rl_comment;
    private ImageView hide_down;
    private EditText comment_content;
    private Button comment_send;

    private CommentAdapter commentAdapter;

    private String comment = null;

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
            holder.dynamic_item_img1 = convertView.findViewById( R.id.dynamic_item_img1 );
            holder.dynamic_item_img2 = convertView.findViewById( R.id.dynamic_item_img2 );
            holder.ll_forward = convertView.findViewById( R.id.ll_forward );
            holder.ll_toComment = convertView.findViewById( R.id.ll_toComment );
            holder.ll_like = convertView.findViewById( R.id.ll_like );
            holder.rl_comment = convertView.findViewById( R.id.rl_comment );
            holder.hide_down = convertView.findViewById( R.id.hide_down );
            holder.comment_send = convertView.findViewById( R.id.comment_send );
            holder.comment_content = convertView.findViewById( R.id.comment_content );
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        rl_comment = holder.rl_comment;
        hide_down = holder.hide_down;
        comment_send = holder.comment_send;
        comment_content = holder.comment_content;

        DynamicContent dynamicContent = list.get(position);
        holder.tv_nickName.setText(dynamicContent.getUserContent().getUserNickname());
        holder.tv_date.setText(dynamicContent.getDate());
        holder.device.setText(dynamicContent.getDevice());
        holder.dynamic_item_txt.setText(dynamicContent.getContent());
        //缓存图片
        if(!"".equals(dynamicContent.getImg())) {
            showImges(dynamicContent.getImg(), holder.dynamic_item_img1);
        }
        if(!"".equals(dynamicContent.getImg2())) {
            showImges(dynamicContent.getImg2(), holder.dynamic_item_img2);
        }
        rl_comment.setVisibility(View.GONE);

//        comment_content.addTextChangedListener( new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.e("content7", comment_content.getText().toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                Log.e("content8", comment_content.getText().toString());
//                comment = comment_content.getText().toString();
//            }
//        } );

        //点击事件
        holder.ll_forward.setOnClickListener( listener );
        holder.ll_toComment.setOnClickListener( listener );
        holder.ll_like.setOnClickListener( listener );
        holder.rl_comment.setOnClickListener( listener );
        holder.hide_down.setOnClickListener( listener );
        holder.comment_send.setOnClickListener( listener );
        notifyDataSetChanged();
        return convertView;
    }
    //缓存动态图片
    private void showImges(String imgName,ImageView imageView) {
        String url = "http://"+this.context.getResources().getString(R.string.ip_address)
                +":8080/smallpigeon/dynamic/"+imgName;
        Glide.with(this.context).load(url).into(imageView);
    }

    class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                /*case R.id.ll_forward:
                    //TODO:转发
                    Toast.makeText( context, "转发", Toast.LENGTH_SHORT ).show();
                    break;
                case R.id.ll_toComment:
                    //TODO：评论
                    Toast.makeText( context, "评论", Toast.LENGTH_SHORT ).show();
                    // 显示评论框
                    rl_comment.setVisibility(View.VISIBLE);
                    // 弹出输入法
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

                    break;
                case R.id.ll_like:
                    //TODO:点赞
                    Toast.makeText( context, "点赞", Toast.LENGTH_SHORT ).show();
                    break;
                case R.id.hide_down:
                    //TODO:隐藏评论框
                    Toast.makeText( context, "weishenme", Toast.LENGTH_SHORT ).show();
                    //fixme:实现失败
                    rl_comment.setVisibility(View.GONE);
                    // 隐藏输入法，然后暂存当前输入框的内容，方便下次使用
                    InputMethodManager im = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(comment_content.getWindowToken(), 0);
                    break;
                case R.id.comment_send:
                    //TODO：发送评论
                    sendComment();
                    break;*/
            }
        }
    }

    static class ViewHolder{
        ImageView iv_icon;//用户头像
        TextView tv_nickName;//用户昵称
        TextView tv_date;//发表时间
        TextView device;//设备名称
        TextView dynamic_item_txt;//发表内容
        ImageView dynamic_item_img1;//发表内容配图1
        ImageView dynamic_item_img2;//发表内容配图2
        LinearLayout ll_forward;//转发
        LinearLayout ll_toComment;//评论
        LinearLayout ll_like;//赞
        RelativeLayout rl_comment;//评论框
        ImageView hide_down;//隐藏评论框
        Button comment_send;//发送评论
        EditText comment_content;//评论内容
    }

    //发送评论
    public void sendComment(){
        //FIXME:获取不到实时更改的评论内容
        Log.e("content2", comment_content.getText().toString());
        Log.e("error", "111");
        if(comment_content.getText().toString().equals("")){
            Toast.makeText(context, "评论不能为空！", Toast.LENGTH_SHORT).show();
        } else{
            //TODO:与后台交互，获取评论人的id，被评论的动态id
            //生成评论数据
//            CommentContent comment = new CommentContent();
//            comment.setUser_id( "1" );
//            comment.setDynamics_id( 1 );
//            comment.setComment_content( "评论的内容为哈哈哈哈哈" );
//            commentAdapter.addComment(comment);
//            // 发送完，清空输入框
//            comment_content.setText("");
            Toast.makeText(context, "评论成功！", Toast.LENGTH_SHORT).show();
        }
    }
}