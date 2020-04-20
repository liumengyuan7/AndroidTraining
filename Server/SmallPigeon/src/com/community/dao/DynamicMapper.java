package com.community.dao;

import com.entity.Dynamic;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DynamicMapper {
//    public int insertDynamic(Dynamic dynamic);

    public int insertDynamic(@Param("userId") String userId, @Param("pushTime") String pushTime, @Param("pushContent") String pushContent, @Param("pushImg") String pushImg);
    public List<Map> queryAllDynamic();
}
