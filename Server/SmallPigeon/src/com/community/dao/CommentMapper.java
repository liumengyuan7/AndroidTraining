package com.community.dao;

import com.entity.Comment;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CommentMapper {
    public int insertComment(@Param("dynamicId") String dynamicId, @Param("userId") String userId, @Param("content") String content);
    public List<Comment> selectCommnetByDynamicId(Integer dynamicId);
}
