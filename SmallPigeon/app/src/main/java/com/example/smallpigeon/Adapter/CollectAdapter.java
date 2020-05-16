package com.example.smallpigeon.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smallpigeon.Entity.DynamicContent;
import com.example.smallpigeon.R;

import java.util.ArrayList;
import java.util.List;

public class CollectAdapter extends BaseAdapter {
    List<DynamicContent> list = new ArrayList<>();
    private Context context;
    private boolean isShowCheckBox = false;//表示当前是否是多选状态。
    private SparseBooleanArray stateCheckedMap = new SparseBooleanArray();//用来存放CheckBox的选中状态，true为选中,false为没有选中
    private static final int VIEWTYPFIRST = 0;
    private static final int VIEWTYPSECOND = 1;
    private String userId;
    private ViewHolder holder = null;

    public CollectAdapter(List<DynamicContent> list, Context context, SparseBooleanArray stateCheckedMap) {
        this.list = list;
        this.context = context;
        this.stateCheckedMap = stateCheckedMap;
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
    public int getViewTypeCount() {
        return 2;
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

        int type = getItemViewType(position);
        Log.e("type:",type+"");
        if (convertView == null){
            holder = new ViewHolder();
            switch (type){
                case VIEWTYPFIRST:
                    LayoutInflater inflater = LayoutInflater.from(context);
                    convertView = inflater.inflate(R.layout.my_collect_listitem,null);
                    holder.iv_icon = convertView.findViewById(R.id.iv_icon);
                    holder.tv_nickName = convertView.findViewById(R.id.tv_nickName);
                    holder.tv_date = convertView.findViewById(R.id.tv_date);
                    holder.device = convertView.findViewById(R.id.device);
                    holder.dynamic_item_txt = convertView.findViewById(R.id.dynamic_item_txt);
                    holder.dynamic_item_img = convertView.findViewById( R.id.dynamic_item_img );
                    holder.dynamic_item_img2 = convertView.findViewById( R.id.dynamic_item_img2 );
                    holder.checkBox = convertView.findViewById(R.id.chb_select_way_point);
                    //评论收藏数量
                    holder.commentNum = convertView.findViewById(R.id.commentNum);
                    holder.collectNum = convertView.findViewById(R.id.collectNum);
                    showAndHideCheckBox();//控制CheckBox的那个的框显示与隐藏
                    break;
                case VIEWTYPSECOND:
                    convertView = LayoutInflater.from(context).inflate(R.layout.my_collect_listitem2,null);
                    holder.iv_icon = convertView.findViewById(R.id.iv_icon);
                    holder.tv_nickName = convertView.findViewById(R.id.tv_nickName);
                    holder.tv_date = convertView.findViewById(R.id.tv_date);
                    holder.device = convertView.findViewById(R.id.device);
                    holder.dynamic_item_txt = convertView.findViewById(R.id.dynamic_item_txt);
                    holder.dynamic_item_img = convertView.findViewById( R.id.dynamic_item_img );
                    holder.dynamic_item_img2 = convertView.findViewById( R.id.dynamic_item_img2 );
                    holder.tv_user_txt = convertView.findViewById(R.id.tv_user_txt);
                    holder.tv_user = convertView.findViewById(R.id.tv_user);
                    holder.checkBox = convertView.findViewById(R.id.chb_select_way_point);
                    //评论收藏数量
                    holder.commentNum = convertView.findViewById(R.id.commentNum);
                    holder.collectNum = convertView.findViewById(R.id.collectNum);
                    break;
            }
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        DynamicContent dynamicContent = list.get(position);
        Log.e("content：",dynamicContent.toString());
        switch (type){
            case VIEWTYPFIRST:
                showAndHideCheckBox();//控制CheckBox的那个的框显示与隐藏
                holder.tv_nickName.setText(dynamicContent.getUserContent().getUserNickname());
                holder.tv_date.setText(dynamicContent.getDate());
                holder.device.setText(dynamicContent.getDevice());
                //显示评论和收藏数量
                holder.commentNum.setText(dynamicContent.getComment_Num()+"");
                holder.collectNum.setText(dynamicContent.getCollect_Num()+"");
                holder.dynamic_item_txt.setText(dynamicContent.getContent());
                holder.checkBox.setChecked(stateCheckedMap.get(position));
                //缓存发布的动态图片
                if(!"".equals(dynamicContent.getImg())) {
                    showImges(dynamicContent.getImg(), holder.dynamic_item_img);
                }
                if(!"".equals(dynamicContent.getImg2())) {
                    showImges(dynamicContent.getImg2(), holder.dynamic_item_img2);
                }
                break;
            case VIEWTYPSECOND:
                showAndHideCheckBox();//控制CheckBox的那个的框显示与隐藏
                holder.tv_nickName.setText(dynamicContent.getUserContent().getUserNickname());
                holder.tv_date.setText(dynamicContent.getDate());
                holder.device.setText(dynamicContent.getDevice());
                holder.dynamic_item_txt.setText(dynamicContent.getContent());
//                holder.tv_user.setText(dynamicContent.getForwardContent().getUserContent().getUserNickname()+"：");
//                holder.tv_user_txt.setText(dynamicContent.getForwardContent().getDynamicContent().getContent());
                holder.tv_user.setText(dynamicContent.getForwardContent().getDuserNickname()+"：");
                holder.tv_user_txt.setText(dynamicContent.getForwardContent().getDpushContent());
                holder.checkBox.setChecked(stateCheckedMap.get(position));
                //显示评论和收藏数量
                holder.commentNum.setText(dynamicContent.getComment_Num()+"");
                holder.collectNum.setText(dynamicContent.getCollect_Num()+"");
                //缓存发布的动态图片 来自被转发动态
                if(!"".equals(dynamicContent.getForwardContent().getDpushImage1())) {
                    showImges(dynamicContent.getForwardContent().getDpushImage1(), holder.dynamic_item_img);
                }
                if(!"".equals(dynamicContent.getForwardContent().getDpushImage2())) {
                    showImges(dynamicContent.getForwardContent().getDpushImage2(), holder.dynamic_item_img2);
                }
                break;
        }

        //缓存头像
        showImage(dynamicContent.getUserContent().getUserImage(),holder.iv_icon);
//        //缓存发布的动态图片
//        if(!"".equals(dynamicContent.getImg())) {
//            showImges(dynamicContent.getImg(), holder.dynamic_item_img);
//        }
//        if(!"".equals(dynamicContent.getImg2())) {
//            showImges(dynamicContent.getImg2(), holder.dynamic_item_img2);
//        }
        //得到点赞用户id
        SharedPreferences pre = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userId = pre.getString("user_id","");

        return convertView;
    }

    //缓存动态图片
    private void showImges(String imgName,ImageView imageView) {
        String url = "http://"+this.context.getResources().getString(R.string.ip_address)
                +":8080/smallpigeon/dynamic/"+imgName;
        Glide.with(this.context).load(url).into(imageView);
    }

    private void showAndHideCheckBox() {
        if (isShowCheckBox) {
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }
    }

    public static class ViewHolder{
        ImageView iv_icon;//用户头像
        TextView tv_nickName;//用户昵称
        TextView tv_date;//发表时间
        TextView device;//设备名称
        TextView dynamic_item_txt;//发表内容
        ImageView dynamic_item_img;//发表内容配图
        ImageView dynamic_item_img2;//发表内容配图2
        TextView commentNum;//收藏动态的评论数量
        TextView collectNum;//收藏动态的转发数量
        TextView tv_user;
        TextView tv_user_txt;
        public CheckBox checkBox;
    }

    public boolean isShowCheckBox() {
        return isShowCheckBox;
    }

    public void setShowCheckBox(boolean showCheckBox) {
        isShowCheckBox = showCheckBox;
    }

    //缓存头像图片
    private void showImage(String imgName,ImageView imageView) {
        String url = "http://"+this.context.getResources().getString(R.string.ip_address)
                +":8080/smallpigeon/avatar/"+imgName+".jpg";
        Glide.with(this.context).load(url).into(imageView);
    }
    private boolean loginOrNot(){
        SharedPreferences pre = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String userEmail = pre.getString("user_email","");
        if(userEmail.equals("")||userEmail==null){
            return false;
        }else{
            return true;
        }
    }
}

