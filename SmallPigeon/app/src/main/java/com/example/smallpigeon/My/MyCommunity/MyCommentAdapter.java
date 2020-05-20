package com.example.smallpigeon.My.MyCommunity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.smallpigeon.Entity.CommentDetailBean;
import com.example.smallpigeon.Entity.DynamicContent;
import com.example.smallpigeon.Entity.ReplyDetailBean;
import com.example.smallpigeon.R;
import com.example.smallpigeon.Utils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyCommentAdapter extends BaseExpandableListAdapter {

    private static final String TAG = "MyCommentAdapter";
    private List<CommentDetailBean> commentBeanList;
    private List<ReplyDetailBean> replyBeanList = new ArrayList<>();
    private Context context;
    private int pageIndex = 1;
    private DynamicContent dynamicContent;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj+"";
            switch (msg.what){
                case 0:
                    if(result.equals("true")){
                        Toast.makeText(context,"评论成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context,"评论失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 1:
                    if(result.equals("true")){
                        Toast.makeText(context,"回复成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context,"回复失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    if(result.equals("true")){
                        Toast.makeText(context,"评论点赞成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context,"评论点赞失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 3:
                    if(result.equals("true")){
                        Toast.makeText(context,"取消评论点赞成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context,"取消评论点赞失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

        }
    };

    public MyCommentAdapter(Context context, DynamicContent dynamicContent) {
        this.context = context;
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
        RequestOptions requestOptions = new RequestOptions()
                .error(R.drawable.user_logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        String url = "http://"+this.context.getResources().getString(R.string.ip_address)
                +":8080/smallpigeon/avatar/"+dynamicContent.getCommentDetailBeans().get(groupPosition).getUserLogo()+".jpg";
        Glide.with(context).load(url)
                .apply(requestOptions)
                .into(groupHolder.logo);
        groupHolder.tv_name.setText(dynamicContent.getCommentDetailBeans().get(groupPosition).getNickName());

        //得到点赞用户id
        SharedPreferences pre = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
//        int userId = Integer.parseInt(pre.getString("user_id",""));
        int userId = 10;
        String time = dynamicContent.getCommentDetailBeans().get(groupPosition).getCreateDate();
        groupHolder.tv_time.setText(time.substring(0,7));
        groupHolder.tv_content.setText(dynamicContent.getCommentDetailBeans().get(groupPosition).getContent());
        groupHolder.tv_likeNum.setText(dynamicContent.getCommentDetailBeans().get(groupPosition).getComomentZanNum()+"");
        groupHolder.iv_like.setTag(false);
        groupHolder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if(isLike){
                    isLike = false;
                    groupHolder.iv_like.setColorFilter(Color.parseColor("#aaaaaa"));
                }else {
                    isLike = true;
                    groupHolder.iv_like.setColorFilter(Color.parseColor("#FF5C5C"));
                }*/
                Log.e("iv_like.getTag()",groupHolder.iv_like.getTag()+"");
                if((boolean)groupHolder.iv_like.getTag()){
                    groupHolder.iv_like.setColorFilter(Color.parseColor("#aaaaaa"));
                    int zanNumBefore = dynamicContent.getCommentDetailBeans().get(groupPosition).getComomentZanNum();
                    int zanNumAfter = zanNumBefore-1;
                    decZanNumByComment(dynamicContent.getDynamicId(),dynamicContent.getCommentDetailBeans().get(groupPosition).getId(),userId,zanNumAfter);
                    dynamicContent.getCommentDetailBeans().get(groupPosition).setComomentZanNum(zanNumAfter);
                    groupHolder.tv_likeNum.setText(zanNumAfter+"");
                    groupHolder.iv_like.setTag(false);
                }else {
                    groupHolder.iv_like.setColorFilter(Color.parseColor("#FF5C5C"));
                    int zanNumBefore = dynamicContent.getCommentDetailBeans().get(groupPosition).getComomentZanNum();
                    int zanNumAfter = zanNumBefore+1;
                    addZanNumByComment(dynamicContent.getDynamicId(),dynamicContent.getCommentDetailBeans().get(groupPosition).getId(),userId,zanNumAfter);
                    dynamicContent.getCommentDetailBeans().get(groupPosition).setComomentZanNum(zanNumAfter);
                    groupHolder.tv_likeNum.setText(zanNumAfter+"");
                    groupHolder.iv_like.setTag(true);
                }
            }
        });
        notifyDataSetChanged();
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
//        childHolder.tv_time.setText(dynamicContent.getCommentDetailBeans().get(groupPosition).getReplyList().get(childPosition).getCreateDate().substring(0,19));
        childHolder.tv_time.setText(dynamicContent.getCommentDetailBeans().get(groupPosition).getReplyList().get(childPosition).getCreateDate());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private class GroupHolder{
        private CircleImageView logo;
        private TextView tv_name, tv_content, tv_time,tv_likeNum;
        private ImageView iv_like;
        public GroupHolder(View view) {
            logo = (CircleImageView) view.findViewById(R.id.comment_item_logo);
            tv_content = (TextView) view.findViewById(R.id.comment_item_content);
            tv_name = (TextView) view.findViewById(R.id.comment_item_userName);
            tv_time = (TextView) view.findViewById(R.id.comment_item_time);
            iv_like = (ImageView) view.findViewById(R.id.comment_item_like);
            tv_likeNum = (TextView)view.findViewById(R.id.comment_item_liketext);
        }
    }

    private class ChildHolder{
        private TextView tv_name, tv_content,tv_time;
        public ChildHolder(View view) {
            tv_name = (TextView) view.findViewById(R.id.reply_item_user);
            tv_content = (TextView) view.findViewById(R.id.reply_item_content);
            tv_time = (TextView) view.findViewById(R.id.reply_item_time);
        }
    }

    public void addTheCommentData(CommentDetailBean commentDetailBean){
        if(commentDetailBean!=null){
            dynamicContent.getCommentDetailBeans().add(commentDetailBean);
            int dynamicId = commentDetailBean.getDynamicId();
            int userId = commentDetailBean.getCommentFromId();
            String content = commentDetailBean.getContent();
            String  contentTime = commentDetailBean.getCreateDate();
            addComment(dynamicId,userId,content,contentTime);
            notifyDataSetChanged();
        }else {
            throw new IllegalArgumentException("评论数据为空!");
        }

    }
    public void addTheReplyData(ReplyDetailBean replyDetailBean, int groupPosition){
        notifyDataSetChanged();
        if(replyDetailBean!=null){
            Log.e(TAG, "addTheReplyData: >>>>该刷新回复列表了:"+replyDetailBean.toString() );
            Log.e("向数据库插入回复数据",replyDetailBean.toString());
            int commentId =Integer.parseInt(replyDetailBean.getCommentId());
            int fromId = replyDetailBean.getFromId();
            int toId = replyDetailBean.getToId();
            String replyContent = replyDetailBean.getContent();
            String replyTime = replyDetailBean.getCreateDate();
            if(dynamicContent.getCommentDetailBeans().get(groupPosition).getReplyList() != null ){
                dynamicContent.getCommentDetailBeans().get(groupPosition).getReplyList().add(replyDetailBean);
            }else {
                List<ReplyDetailBean> replyList = new ArrayList<>();
                replyList.add(replyDetailBean);
                dynamicContent.getCommentDetailBeans().get(groupPosition).setReplyList(replyList);
            }
            addReply(commentId,fromId,toId,replyContent,replyTime);
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
    //向数据库中插入评论信息
    private void addComment(int dynamicId, int userId, String content, String contentTime) {
        new Thread(){
            @Override
            public void run() {
                String result = new Utils().getConnectionResult("dynamic","addComment",
                        "dynamicId="+dynamicId+"&userId="+userId+"&content="+content+"&contentTime="+contentTime);
                Message message = new Message();
                message.obj = result;
                message.what=0;
                handler.sendMessage(message);
            }
        }.start();
    }
    //向数据库中插入回复信息
    private void addReply(int commentId, int fromId, int toId, String replyContent, String replyTime) {
        Log.e("向数据库插入回复数据","run");
        new Thread(){
            @Override
            public void run() {
                String result = new Utils().getConnectionResult("dynamic","addReply",
                        "commentId="+commentId+"&fromId="+fromId+"&toId="+toId+"&replyContent="+replyContent+"&replyTime="+replyTime);
                Message message = new Message();
                message.obj = result;
                message.what=1;
                handler.sendMessage(message);
            }
        }.start();
    }

    //对评论点赞
    private void addZanNumByComment(int dynamicId, int commentId, int userId, int zanNumAfter) {
        new Thread(){
            @Override
            public void run() {
                Log.e("评论的id",commentId+",点赞数"+zanNumAfter);
                String result = new Utils().getConnectionResult("dynamic","addZanNumByComment","dynamicId="+dynamicId+"&&commentId="+commentId
                        +"&&userId="+userId+"&&zanNumAfter="+zanNumAfter);
                Message message = new Message();
                message.obj = result;
                message.what=2;
                handler.sendMessage(message);
            }
        }.start();
    }
    //对评论取消点赞
    private void decZanNumByComment(int dynamicId, int commentId, int userId, int zanNumAfter) {
        new Thread(){
            @Override
            public void run() {
                Log.e("取消点赞评论的id",commentId+",点赞数"+zanNumAfter);
                String result = new Utils().getConnectionResult("dynamic","decZanNumByComment","dynamicId="+dynamicId+"&&commentId="+commentId
                        +"&&userId="+userId+"&&zanNumAfter="+zanNumAfter);
                Message message = new Message();
                message.obj = result;
                message.what=3;
                handler.sendMessage(message);
            }
        }.start();
    }
}
