package com.example.smallpigeon.Community.Comment;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.smallpigeon.Entity.CommentDetailBean;
import com.example.smallpigeon.Entity.DynamicContent;
import com.example.smallpigeon.Entity.ReplyDetailBean;
import com.example.smallpigeon.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class CommentExpandAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "CommentExpandAdapter";
    private List<CommentDetailBean> commentBeanList;
    private List<ReplyDetailBean> replyBeanList = new ArrayList<>();
    private Context context;
    private int pageIndex = 1;
    private DynamicContent dynamicContent;
    public CommentExpandAdapter(Context context, DynamicContent dynamicContent) {
        this.context = context;
//        this.commentBeanList = commentBeanList;
        this.dynamicContent = dynamicContent;
    }

    @Override
    public int getGroupCount() {
        return dynamicContent.getCommentDetailBeans().size();
    }

    @Override
    public int getChildrenCount(int i) {
        if(dynamicContent.getCommentDetailBeans().get(i).getReplyList() == null){
            return 0;
        }else {
            return dynamicContent.getCommentDetailBeans().get(i).getReplyList().size()>0 ? dynamicContent.getCommentDetailBeans().get(i).getReplyList().size():0;
        }

    }

    @Override
    public Object getGroup(int i) {
        return dynamicContent.getCommentDetailBeans().get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return dynamicContent.getCommentDetailBeans().get(i).getReplyList().get(i1);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
    boolean isLike = false;

    @Override
    public View getGroupView(final int groupPosition, boolean isExpand, View convertView, ViewGroup viewGroup) {
        final GroupHolder groupHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_item_layout, viewGroup, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        }else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(R.drawable.user_other)
                .apply(requestOptions)
                .into(groupHolder.logo);
        groupHolder.tv_name.setText(dynamicContent.getCommentDetailBeans().get(groupPosition).getNickName());
        groupHolder.tv_time.setText(dynamicContent.getCommentDetailBeans().get(groupPosition).getCreateDate());
        groupHolder.tv_content.setText(dynamicContent.getCommentDetailBeans().get(groupPosition).getContent());
        groupHolder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isLike){
                    isLike = false;
                    groupHolder.iv_like.setColorFilter(Color.parseColor("#aaaaaa"));
                }else {
                    isLike = true;
                    groupHolder.iv_like.setColorFilter(Color.parseColor("#FF5C5C"));
                }
            }
        });



        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        final ChildHolder childHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_reply_item_layout,viewGroup, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        }
        else {
            childHolder = (ChildHolder) convertView.getTag();
        }

        String replyUser = dynamicContent.getCommentDetailBeans().get(groupPosition).getReplyList().get(childPosition).getNickName();
        if(!TextUtils.isEmpty(replyUser)){
            childHolder.tv_name.setText(replyUser + ":");
        }else {
            childHolder.tv_name.setText("无名"+":");
        }

        childHolder.tv_content.setText(dynamicContent.getCommentDetailBeans().get(groupPosition).getReplyList().get(childPosition).getContent());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private class GroupHolder{
        private CircleImageView logo;
        private TextView tv_name, tv_content, tv_time;
        private ImageView iv_like;
        public GroupHolder(View view) {
            logo = (CircleImageView) view.findViewById(R.id.comment_item_logo);
            tv_content = (TextView) view.findViewById(R.id.comment_item_content);
            tv_name = (TextView) view.findViewById(R.id.comment_item_userName);
            tv_time = (TextView) view.findViewById(R.id.comment_item_time);
            iv_like = (ImageView) view.findViewById(R.id.comment_item_like);
        }
    }

    private class ChildHolder{
        private TextView tv_name, tv_content;
        public ChildHolder(View view) {
            tv_name = (TextView) view.findViewById(R.id.reply_item_user);
            tv_content = (TextView) view.findViewById(R.id.reply_item_content);
        }
    }

    public void addTheCommentData(CommentDetailBean commentDetailBean){
        if(commentDetailBean!=null){
            dynamicContent.getCommentDetailBeans().add(commentDetailBean);
            notifyDataSetChanged();
        }else {
            throw new IllegalArgumentException("评论数据为空!");
        }

    }
    public void addTheReplyData(ReplyDetailBean replyDetailBean, int groupPosition){
        notifyDataSetChanged();
        if(replyDetailBean!=null){
            Log.e(TAG, "addTheReplyData: >>>>该刷新回复列表了:"+replyDetailBean.toString() );
            Log.e(TAG,"addTheReplyData:"+dynamicContent.getCommentDetailBeans().toString());

            if(dynamicContent.getCommentDetailBeans().get(groupPosition).getReplyList() != null ){
                Log.e(TAG,"addTheReplyData:"+dynamicContent.getCommentDetailBeans().get(groupPosition).getReplyList().toString()+"");
                dynamicContent.getCommentDetailBeans().get(groupPosition).getReplyList().add(replyDetailBean);
            }else {
                List<ReplyDetailBean> replyList = new ArrayList<>();
                replyList.add(replyDetailBean);
                dynamicContent.getCommentDetailBeans().get(groupPosition).setReplyList(replyList);
                Log.e(TAG,"addTheReplyData:"+dynamicContent.getCommentDetailBeans().get(groupPosition).getReplyList().toString()+"");
            }
            notifyDataSetChanged();
        }else {
            throw new IllegalArgumentException("回复数据为空!");
        }

    }
    private void addReplyList(List<ReplyDetailBean> replyBeanList, int groupPosition){
        if(dynamicContent.getCommentDetailBeans().get(groupPosition).getReplyList() != null ){
            dynamicContent.getCommentDetailBeans().get(groupPosition).getReplyList().clear();
            dynamicContent.getCommentDetailBeans().get(groupPosition).getReplyList().addAll(replyBeanList);
        }else {

            dynamicContent.getCommentDetailBeans().get(groupPosition).setReplyList(replyBeanList);
        }

        notifyDataSetChanged();
    }



}
