package com.community.service;

import com.community.dao.CollectMapper;
import com.community.dao.CommentMapper;
import com.community.dao.DynamicMapper;
import com.community.dao.ReplyMapper;
import com.community.dao.ZanNumMapper;
import com.entity.Comment;
import com.entity.Dynamics;
import com.entity.ForwardContent;
import com.entity.Reply;
import com.google.gson.Gson;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Resource
    private CollectMapper collectMapper;

//    public String addDynamic(String userId, String pushTime, String pushContent, String pushImg) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
//        Date date = sdf.parse(pushTime);
//        int result = this.dynamicMapper.insertDynamic(userId,date,pushContent,pushImg);
//        if(result>0){
//            return "true";
//        }else{
//            return "false";
//        }
//    }
    public String addDynamic(String userId, String pushTime, String pushContent, String pushImg, String forwardId,String type) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        Date date = sdf.parse(pushTime);
        int result = this.dynamicMapper.insertDynamic(userId,date,pushContent,pushImg,forwardId,type);
        if(result>0){
            return "true";
        }else{
            return "false";
        }
    }
    //得到所有用户的动态
 /*   public String queryAllDynamic(){
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
    }*/
      public String queryAllDynamic(){
        List<Dynamics> dynamics = this.dynamicMapper.queryAllDynamic();
        for (int i =0;i<dynamics.size();i++){
            int forwardId = dynamics.get(i).getForwardId();
            if(forwardId!=0) {
                ForwardContent  forwardContent= this.dynamicMapper.queryDynamicByForwardId(forwardId);
                dynamics.get(i).setForwardContent(forwardContent);
            }
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
    //对动态进行点赞
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
    //对动态取消点赞
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

    //对评论进行点赞
    public String addZanNumByComment(String dynamicId, String commentId, String userId, String zanNumAfter){
        int n =this.zanNumMapper.insertZanByComment(dynamicId,commentId,userId);
        if(n>0){
            int m =this.commentMapper.updateZanNumByComment(dynamicId,commentId,zanNumAfter);
            if(m>0) return "true";
            else return "fasle";
        }else {
            return "false";
        }
    }
    //对评论取消点赞
    public String decZanNumByComment(String dynamicId, String commentId, String userId, String zanNumAfter){
        int n =this.zanNumMapper.deleteZanByComment(dynamicId,commentId,userId);
        if(n>0){
            int m =this.commentMapper.updateZanNumByComment(dynamicId,commentId,zanNumAfter);
            if(m>0) return "true";
            else return "fasle";
        }else {
            return "false";
        }
    }

    //某用户所有收藏的动态
    public String queryAllCollectByUserId(String userId){
        List<Dynamics> dynamics = this.collectMapper.queryAllCollectByUserId(userId);
        for (int i =0;i<dynamics.size();i++){
            System.out.println(dynamics.get(i).toString());
            int dynamicId = dynamics.get(i).getId();
            System.out.println(dynamicId);
            int commentNum = this.collectMapper.getCommentNum(dynamicId);
            System.out.println(this.collectMapper.getCommentNum(dynamicId));
            dynamics.get(i).setCommentNum(commentNum);
            int collectNum = this.collectMapper.getCollectNum(dynamicId);
            System.out.println(this.collectMapper.getCollectNum(dynamicId));
            dynamics.get(i).setCollectNum(collectNum);
        }
        System.out.println(new Gson().toJson(dynamics));
        return new Gson().toJson(dynamics);
    }
    //收藏动态
    public String addCollect(String dynamicId, String userId){
        int result = this.collectMapper.insertCollect(dynamicId,userId);
        if(result>0){
            return "true";
        }else{
            return "false";
        }
    }
    //取消收藏动态
    public String decCollect(String dynamicId, String userId){
        int result = this.collectMapper.deleteCollect(dynamicId,userId);
        if(result>0){
            return "true";
        }else{
            return "false";
        }
    }
}
