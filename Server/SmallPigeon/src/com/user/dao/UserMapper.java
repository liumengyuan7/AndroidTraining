package com.user.dao;

import com.entity.User;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author syf
 * @ClassName UserMapper
 * @description
 * @date 2020/04/08 15:00
 */

public interface UserMapper {

    public Map selectUserByEmailAndPassword(@Param("userEmail") String userEmail, @Param("userPassword") String userPassword);

    public Map selectUserByEmail(String userEmail);

    public Map selectUserById(String id);

    public Map selectUserByMatcher(String matcher);

    public List<Map> selectAllUserForPoints();

    public List<Map> selectAllUser();

    public List<Map> selectUserByEmailLike(String userEmailLike);

    public List<Map> selectUserByMatcherAndId(@Param("matcher") String matcher, @Param("id") String id);

    public int insertUserInfo(User user);

    public int updateUserPasswordById(@Param("userId") String userId, @Param("userPassword") String userPassword);

    public int updateUserNicknameById(@Param("userId") String userId, @Param("userNickname") String userNickname);

    public int updateUserEmailById(@Param("userId") String userId, @Param("userEmail") String userEmail);

    public int updateUserMatcherStateById(@Param("id") String id, @Param("state") String state);

    public int updateUserPointsById(@Param("userId") String userId, @Param("point") int point);

}
