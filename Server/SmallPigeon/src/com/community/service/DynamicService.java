package com.community.service;

import com.community.dao.CommentMapper;
import com.community.dao.DynamicMapper;
import com.community.dao.ReplyMapper;
import com.community.dao.ZanNumMapper;
import com.entity.Comment;
import com.entity.Dynamics;
import com.entity.Reply;
import com.google.gson.Gson;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

/**
 * @className DynamicService
 * @auther 刘梦圆
 * @description
 * @date 2020/04/15 16:45
 */
@Service
public class DynamicService {
    @Resource
    private DynamicMapper dynamicMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private ReplyMapper replyMapper;
    @Resource
    private ZanNumMapper zanNumMapper;

    public String addDynamic(String userId, String pushTime, String pushContent, String pushImg) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        Date date = sdf.parse(pushTime);
        int result = this.dynamicMapper.insertDynamic(userId,date,pushContent,pushImg);
        if(result>0){
            return "true";
        }else{
            return "false";
        }
    }
    //得到所有用户的动态
    public String queryAllDynamic(){
        List<Dynamics> dynamics = this.dynamicMapper.queryAllDynamic();
        for (int i =0;i<dynamics.size();i++){
            int dynamicId = dynamics.get(i).getId();
            List<Comment> comments = this.commentMapper.selectCommnetByDynamicId(dynamicId);
            dynamics.get(i).setComments(comments);
            for (int j=0;j<comments.size();j++){
                int commentId = comments.get(j).getId();
                List<Reply> replies = this.replyMapper.queryReplyByCommentId(commentId);
                comments.get(j).setReplies(replies);
            }
        }
        System.out.println(new Gson().toJson(dynamics));
        return new Gson().toJson(dynamics);
    }
      //得到自己发布的所有动态
    public String queryAllDynamicAndComment(String userId){
        List<Dynamics> dynamics = this.dynamicMapper.queryAllDynamicByUserId(userId);
        System.out.println(dynamics.toString());
        for (int i =0;i<dynamics.size();i++){
            int dynamicId = dynamics.get(i).getId();
            List<Comment> comments = this.commentMapper.selectCommnetByDynamicId(dynamicId);
            dynamics.get(i).setComments(comments);
            for (int j=0;j<comments.size();j++){
                int commentId = comments.get(j).getId();
                List<Reply> replies = this.replyMapper.queryReplyByCommentId(commentId);
                comments.get(j).setReplies(replies);
            }
        }
        System.out.println(new Gson().toJson(dynamics));
        return new Gson().toJson(dynamics);
    }
    //添加评论
     public String addComment(String dynamicId,String userId,String content,String contentTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(contentTime);
        int result = this.commentMapper.insertComment(dynamicId,userId,content,date);
        if(result>0){
            return "true";
        }else{
            return "false";
        }
    }
    //给评论添加回复
    public String addReply(String commentId,String fromId,String toId,String replyContent,String replyTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(replyTime);
        int result = this.replyMapper.insertReply(commentId,fromId,toId,replyContent,date);
        if(result>0){
            return "true";
        }else{
            return "false";
        }
    }
    //点赞
    public String addZan(String dynamicId, String userId, String zanNumAfter){
        int n =this.zanNumMapper.insertZan(dynamicId,userId);
        if(n>0){
            int m =this.dynamicMapper.updateZanNum(dynamicId,zanNumAfter);
            if(m>0) return "true";
            else return "fasle";
        }else {
            return "false";
        }
    }
    //取消点赞
    public String decZan(String dynamicId, String userId, String zanNumAfter){
        int n =this.zanNumMapper.deleteZan(dynamicId,userId);
        if(n>0){
            int m =this.dynamicMapper.updateZanNum(dynamicId,zanNumAfter);
            if(m>0) return "true";
            else return "fasle";
        }else {
            return "false";
        }
    }
}
