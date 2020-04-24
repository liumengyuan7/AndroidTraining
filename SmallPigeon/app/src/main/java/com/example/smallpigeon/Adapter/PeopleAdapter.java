package com.example.smallpigeon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.smallpigeon.Entity.DynamicContent;
import com.example.smallpigeon.R;

import java.util.ArrayList;
import java.util.List;

public class PeopleAdapter extends BaseAdapter  implements View.OnClickListener{

    private Context context;
    private int itemLayoutID;
    private List<DynamicContent> list = new ArrayList<>();
    private ImageView ivLike;
    private TextView tvLikeNum;

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
            holder.dynamic_item_img2 = convertView.findViewById( R.id.dynamic_item_img2 );
            holder.ll_forward = convertView.findViewById( R.id.ll_forward );
            holder.ll_toComment = convertView.findViewById( R.id.ll_toComment );
            holder.ll_like = convertView.findViewById( R.id.ll_like );
            holder.iv_like = convertView.findViewById( R.id.iv_like );
            holder.tv_likeNum = convertView.findViewById( R.id.tv_likeNum );
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        ivLike = holder.iv_like;
        tvLikeNum = holder.tv_likeNum;

        DynamicContent dynamicContent = list.get(position);
        holder.tv_nickName.setText(dynamicContent.getUserContent().getUserNickname());
        holder.tv_date.setText(dynamicContent.getDate());
        holder.device.setText(dynamicContent.getDevice());
        holder.dynamic_item_txt.setText(dynamicContent.getContent());
        //todo:与后台交互，查询give_to表中的点赞状态
//        if (点赞状态为1){
//            ivLike.setImageResource( R.drawable.heart );
//        } else {
//            ivLike.setImageResource( R.drawable.good );
//        }

        //缓存图片
        showImage(dynamicContent.getUserContent().getUserImage(),holder.iv_icon);

        //点击事件
        holder.ll_like.setOnClickListener( new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //todo：从数据库获取点赞状态
//            ivLike.setImageResource( R.drawable. );
            Toast.makeText(context,"点赞",Toast.LENGTH_SHORT).show();
            if (false){
                ivLike.setImageResource( R.drawable.good );
                //todo：从数据库获取数据并更改点赞数
//                    tvLikeNum.setText(  - 1 );
            } else {
                ivLike.setImageResource( R.drawable.heart );
                //todo：从数据库获取数据并更改点赞数
//                    tvLikeNum.setText(  + 1 );
            }
        }
        });
        holder.ll_toComment.setOnClickListener(this);
        holder.ll_forward.setOnClickListener(this);
        holder.ll_toComment.setTag(position);
        holder.ll_forward.setTag(position);
        notifyDataSetChanged();
        return convertView;
    }

    //缓存动态图片
    private void showImges(String imgName,ImageView imageView) {
        String url = "http://"+this.context.getResources().getString(R.string.ip_address)
                +":8080/smallpigeon/dynamic/"+imgName;
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
        LinearLayout ll_forward;//转发
        LinearLayout ll_toComment;//评论
        LinearLayout ll_like;//赞
        ImageView iv_like;//点赞图标
        TextView tv_likeNum;//点赞数
    }

    public interface btnOnclick{
        public void click(View view,int index);
    }

    //缓存头像图片
    private void showImage(String imgName,ImageView imageView) {
        String url = "http://"+this.context.getResources().getString(R.string.ip_address)
                +":8080/smallpigeon/avatar/"+imgName+".jpg";
        Glide.with(this.context).load(url).into(imageView);
    }
}