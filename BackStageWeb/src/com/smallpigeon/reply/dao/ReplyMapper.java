package com.smallpigeon.reply.dao;

import com.smallpigeon.entity.Record;
import com.smallpigeon.entity.Reply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReplyMapper {
    public List<Reply> getAllReply();
    public void addReply(Reply reply);
    public void deleteReply(@Param("id") int id);
    public Reply getReply(@Param("id") int id);
    public void updateReply(Reply reply);
}
