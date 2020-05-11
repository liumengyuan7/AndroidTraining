package com.community.dao;

import org.apache.ibatis.annotations.Param;

/*
 * @Description  点赞mapper
 * @Auther 刘梦圆
 * @Date 8:51 2020/04/29
 * @Param
 * @return
 */
public interface ZanNumMapper {
    public int getZanByDynamicId(@Param("dynamicId") String dynamicId);
    //对动态进行点赞  取消点赞
    public int insertZan(@Param("dynamicId") String dynamicId, @Param("userId") String userId);
    public int deleteZan(@Param("dynamicId") String dynamicId,@Param("userId") String userId);
    //对评论进行点赞 取消点赞
    public int insertZanByComment(@Param("dynamicId") String dynamicId, @Param("commentId") String commentId, @Param("userId") String userId);
    public int deleteZanByComment(@Param("dynamicId") String dynamicId, @Param("commentId") String commentId, @Param("userId") String userId);

}
