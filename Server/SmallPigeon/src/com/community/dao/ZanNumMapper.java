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
    public int insertZan(@Param("dynamicId") String dynamicId, @Param("userId") String userId);
    public int deleteZan(@Param("dynamicId") String dynamicId,@Param("userId") String userId);
}
