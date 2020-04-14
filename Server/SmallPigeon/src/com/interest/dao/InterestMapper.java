package com.interest.dao;

import com.entity.Interest;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author syf
 * @ClassName InterestMapper
 * @description
 * @date 2020/04/08 16:58
 */

public interface InterestMapper {

    public Interest selectInterestByUserId(int userId);

    public int insertInterestInfo(int userId);

    public int updateInterestState(@Param("interest") String interest, @Param("userId") int userId);

}
