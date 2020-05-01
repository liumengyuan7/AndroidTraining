package com.smallpigeon.reply.service;

import com.smallpigeon.entity.Record;
import com.smallpigeon.entity.Reply;
import com.smallpigeon.record.dao.RecordMapper;
import com.smallpigeon.reply.dao.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className ReplyService
 * @auther 吴东枚
 * @description 回复service
 * @date 2020/05/01 13:19
 */
@Service
public class ReplyService {
    @Autowired
    private ReplyMapper replyMapper;
    public List<Reply> getAllReply(){
        return  this.replyMapper.getAllReply();
    }
    public void addReply(Reply reply){
        this.replyMapper.addReply(reply);
    }
    public void deleteReply(int id){
        this.replyMapper.deleteReply(id);
    }
    public Reply getReply(int id){
        return this.replyMapper.getReply(id);
    }
    public void updateReply(Reply reply){
        this.replyMapper.updateReply(reply);
    }
}
