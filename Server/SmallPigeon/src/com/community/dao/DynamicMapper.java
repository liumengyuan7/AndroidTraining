package com.community.dao;

import com.entity.Dynamics;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface DynamicMapper {
    public int insertDynamic(@Param("userId") String userId, @Param("pushTime") Date pushTime, @Param("pushContent") String pushContent, @Param("pushImg") String pushImg);
    public List<Dynamics> queryAllDynamic();
}
