package com.community.dao;

import com.entity.Reply;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ReplyMapper {
    public List<Reply> queryReplyByCommentId(Integer commentId);
    public int insertReply(@Param("commentId") String commentId,@Param("fromId") String fromId,@Param("toId") String toId,
                           @Param("replyContent") String replyContent, @Param("replyTime") Date replyTime);
}
