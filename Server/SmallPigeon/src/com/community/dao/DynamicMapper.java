package com.community.dao;

import com.entity.Dynamics;
import com.entity.ForwardContent;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface DynamicMapper {
//    public int insertDynamic(@Param("userId") String userId, @Param("pushTime") Date pushTime, @Param("pushContent") String pushContent, @Param("pushImg") String pushImg);
    public List<Dynamics> queryAllDynamic();
    public int updateZanNum(@Param("dynamicId") String dynamicId,@Param("zanNum") String zanNum);
    public List<Dynamics> queryAllDynamicByUserId(@Param("userId") String userId);
    //插入转发信息
//    public int insertForwardMsg(@Param("userId") String userId, @Param("pushTime") Date pushTime, @Param("pushContent") String pushContent, @Param("forwardId") String forwardId);
    public int insertDynamic(@Param("userId") String userId,
                         @Param("pushTime") Date pushTime,
                         @Param("pushContent") String pushContent,
                         @Param("pushImg") String pushImg,
                         @Param("forwardId") String forwardId,
                         @Param("type") String type);
    //查询转发的信息
    public ForwardContent queryDynamicByForwardId(@Param("forwardId") int forwardId);
}
