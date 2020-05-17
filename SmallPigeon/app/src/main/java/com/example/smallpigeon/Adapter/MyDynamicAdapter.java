package com.example.smallpigeon.Adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smallpigeon.Entity.DynamicContent;
import com.example.smallpigeon.R;

import java.util.ArrayList;
import java.util.List;

public class MyDynamicAdapter extends BaseAdapter  implements View.OnClickListener{

    private Context context;
    private int itemLayoutID;
    private List<DynamicContent> list = new ArrayList<>();
    private TextView tvLikeNum;
    private boolean judgeZan = false;
    private static final int VIEWTYPFIRST = 0;
    private static final int VIEWTYPSECOND = 1;
    private ViewHolder holder;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj+"";
            switch (msg.what){
                case 0:
                    if(result.equals("true")){
                        Toast.makeText(context,"点赞成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context,"点赞失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 1:
                    if(result.equals("true")){
                        Toast.makeText(context,"取消点赞成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context,"取消点赞失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    public MyDynamicAdapter(Context context, int itemLayoutID, List<DynamicContent> list) {
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
    public int getItemViewType(int position) {
        if (list.get(position).getType() == 0){
            return VIEWTYPFIRST;
        }else {
            return VIEWTYPSECOND;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = null;
        int type = getItemViewType(position);
        Log.e("type",type+"");
        if (convertView == null){
            holder = new ViewHolder();
            switch (type){
                case VIEWTYPFIRST:
                    Log.e( "e", "0" );
                    LayoutInflater inflater = LayoutInflater.from(context);
                    convertView = inflater.inflate(R.layout.people_dynamic_listitem,null);
                    holder.iv_icon = convertView.findViewById(R.id.iv_icon);
                    holder.tv_nickName = convertView.findViewById(R.id.tv_nickName);
                    holder.tv_date = convertView.findViewById(R.id.tv_date);
                    holder.device = convertView.findViewById(R.id.device);
                    holder.dynamic_item_txt = convertView.findViewById(R.id.dynamic_item_txt);
                    holder.dynamic_item_img = convertView.findViewById( R.id.dynamic_item_img );
                    holder.dynamic_item_img2 = convertView.findViewById( R.id.dynamic_item_img2 );
//                    holder.ll_forward = convertView.findViewById( R.id.ll_forward );
                    holder.tv_forwardNum = convertView.findViewById(R.id.tv_forwardNum);
//                    holder.ll_toComment = convertView.findViewById( R.id.ll_toComment );
                    holder.tv_commentNum = convertView.findViewById(R.id.tv_commentNum);
//                    holder.ll_like = convertView.findViewById( R.id.ll_like );
                    holder.iv_like = convertView.findViewById( R.id.iv_like );
                    holder.tv_likeNum = convertView.findViewById( R.id.tv_likeNum );
                    break;
                case VIEWTYPSECOND:
                    Log.e( "e", "1" );
                    convertView = LayoutInflater.from(context).inflate(R.layout.people_dynamic_listitem2,null);
                    holder.iv_icon = convertView.findViewById(R.id.iv_icon);
                    holder.tv_nickName = convertView.findViewById(R.id.tv_nickName);
                    holder.tv_date = convertView.findViewById(R.id.tv_date);
                    holder.device = convertView.findViewById(R.id.device);
                    holder.dynamic_item_txt = convertView.findViewById(R.id.dynamic_item_txt);
                    holder.dynamic_item_img = convertView.findViewById( R.id.dynamic_item_img );
                    holder.dynamic_item_img2 = convertView.findViewById( R.id.dynamic_item_img2 );
//                    holder.ll_forward = convertView.findViewById( R.id.ll_forward );
                    holder.tv_forwardNum = convertView.findViewById(R.id.tv_forwardNum);
//                    holder.ll_toComment = convertView.findViewById( R.id.ll_toComment );
                    holder.tv_commentNum = convertView.findViewById(R.id.tv_commentNum);
//                    holder.ll_like = convertView.findViewById( R.id.ll_like );
                    holder.iv_like = convertView.findViewById( R.id.iv_like );
                    holder.tv_likeNum = convertView.findViewById( R.id.tv_likeNum );
                    holder.tv_user = convertView.findViewById(R.id.tv_user);
                    holder.tv_user_txt = convertView.findViewById(R.id.tv_user_txt);
                    break;
            }
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        DynamicContent dynamicContent = list.get(position);
        switch (type) {
            case VIEWTYPFIRST:
                holder.tv_nickName.setText( dynamicContent.getUserContent().getUserNickname() );
                holder.tv_date.setText( dynamicContent.getDate() );
                holder.device.setText( dynamicContent.getDevice() );
                holder.dynamic_item_txt.setText( dynamicContent.getContent() );
                holder.tv_forwardNum.setText( dynamicContent.getForward_Num() + "" );
                holder.tv_commentNum.setText( dynamicContent.getComment_Num() + "" );
                holder.tv_likeNum.setText( dynamicContent.getZan_num() + "" );
                //缓存发布的动态图片
                if (!"".equals( dynamicContent.getImg() )) {
                    showImges( dynamicContent.getImg(), holder.dynamic_item_img );
                }
                if (!"".equals( dynamicContent.getImg2() )) {
                    showImges( dynamicContent.getImg2(), holder.dynamic_item_img2 );
                }
                break;
            case VIEWTYPSECOND:
                holder.tv_nickName.setText( dynamicContent.getUserContent().getUserNickname() );
                holder.tv_date.setText( dynamicContent.getDate() );
                holder.device.setText( dynamicContent.getDevice() );
                holder.dynamic_item_txt.setText( dynamicContent.getContent() );
                holder.tv_commentNum.setText( dynamicContent.getComment_Num() + "" );
                holder.tv_likeNum.setText( dynamicContent.getZan_num() + "" );
                holder.tv_user.setText( dynamicContent.getForwardContent().getDuserNickname() + "：" );
                holder.tv_user_txt.setText( dynamicContent.getForwardContent().getDpushContent() );

//                holder.ll_toComment.setOnClickListener( this );
//                holder.ll_forward.setOnClickListener( this );
//                holder.ll_forward.setVisibility( View.GONE );
//                holder.ll_toComment.setTag( position );
//                holder.ll_forward.setTag( position );
                //缓存发布的动态图片 来自被转发动态
                if (!"".equals( dynamicContent.getForwardContent().getDpushImage1() )) {
                    showImges( dynamicContent.getForwardContent().getDpushImage1(), holder.dynamic_item_img );
                }
                if (!"".equals( dynamicContent.getForwardContent().getDpushImage2() )) {
                    showImges( dynamicContent.getForwardContent().getDpushImage2(), holder.dynamic_item_img2 );
                }
                break;
        }
        //缓存头像
        showImage( dynamicContent.getUserContent().getUserImage(), holder.iv_icon );
        return convertView;
    }

    //缓存动态图片
    private void showImges(String imgName,ImageView imageView) {
        String url = "http://"+this.context.getResources().getString(R.string.ip_address)
                +":8080/smallpigeon/dynamic/"+imgName;
//        ViewGroup.LayoutParams layoutParams =  imageView.getLayoutParams();
//        layoutParams.height=500;
//        imageView.setLayoutParams(layoutParams);
        Glide.with(this.context).load(url).into(imageView);
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
        ImageView dynamic_item_img2;//发表内容配图2
        TextView tv_forwardNum;//转发数
        TextView tv_commentNum;//评论数
        ImageView iv_like;//点赞图标
        TextView tv_likeNum;//点赞数
        TextView tv_user;
        TextView tv_user_txt;
    }

    public interface btnOnclick{
        public void click(View view, int index);
    }

    //缓存头像图片
    private void showImage(String imgName,ImageView imageView) {
        String url = "http://"+this.context.getResources().getString(R.string.ip_address)
                +":8080/smallpigeon/avatar/"+imgName+".jpg";
        Glide.with(this.context).load(url).into(imageView);
    }

}