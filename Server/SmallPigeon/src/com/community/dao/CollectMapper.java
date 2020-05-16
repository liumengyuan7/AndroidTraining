package com.community.dao;

import com.entity.Dynamics;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectMapper {
    //我的所有收藏
    public List<Dynamics> queryAllCollectByUserId(@Param("userId") String userId);
    //得到收藏动态的评论数量
    public int getCommentNum(@Param("dynamicId") int dynamicId);
    //得到收藏动态的收藏数量
    public int getCollectNum(@Param("dynamicId") int dynamicId);
    //收藏
    public int insertCollect(@Param("dynamicId") String dynamicId,@Param("userId") String userId);
    //取消收藏
    public int deleteCollect(@Param("dynamicId") String dynamicId,@Param("userId") String userId);
    //批量删除收藏数据
    public int deleteCollects(@Param(value = "idList") List<String> idList,@Param("userId") String userId);
}
