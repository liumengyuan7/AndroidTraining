package com.community.dao;

import com.entity.Comment;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CommentMapper {
    public int insertComment(@Param("dynamicId") String dynamicId, @Param("userId") String userId, @Param("content") String content,@Param("contentTime") Date contentTime);
    public List<Comment> selectCommnetByDynamicId(Integer dynamicId);
    public int updateZanNumByComment(@Param("dynamicId") String dynamicId,@Param("commentId") String commentId,@Param("zanNum") String zanNum);
}
