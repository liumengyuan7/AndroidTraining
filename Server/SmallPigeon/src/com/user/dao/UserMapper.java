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

    public List<Map> selectAllUserForPoints();

    public List<Map> selectAllUser();

    public List<Map> selectUserByEmailLike(String userEmailLike);

    public List<Map> selectNearbyUserByLocation(@Param("minLongitude") double minLongitude, @Param("maxLongitude") double maxLongitude,
                                                @Param("minLatitude") double minLatitude, @Param("maxLatitude") double maxLatitude,
                                                @Param("userId") String userId);

    public int insertUserInfo(User user);

    public int updateUserPasswordById(@Param("userId") String userId, @Param("userPassword") String userPassword);

    public int updateUserNicknameById(@Param("userId") String userId, @Param("userNickname") String userNickname);

    public int updateUserEmailById(@Param("userId") String userId, @Param("userEmail") String userEmail);

    public int updateUserPointsById(@Param("userId") String userId, @Param("point") int point);

    public int updateUserLocation(@Param("longitude") double longitude,@Param("latitude") double latitude,@Param("userId") String userId);
    //学生认证
    public int updateUserByMsg(@Param("userId") String userId,@Param("userName") String userName,@Param("userSno") String userSno,
                               @Param("userSchool") String userSchool,@Param("identifyImages") String identifyImages,@Param("status") String status);
    //认证状态
    public String getStatusByUserId(@Param("userId") String userId);
}
