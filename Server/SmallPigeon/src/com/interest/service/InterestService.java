package com.interest.service;

import com.entity.Interest;
import com.interest.dao.InterestMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @auther angel
 * @description service
 * @date 2019/11/26 17:04
 */
@Service
@Transactional
public class InterestService {

    @Resource
    private InterestMapper interestMapper;

    public Interest selectInterestByUserId(int userId){
        return this.interestMapper.selectInterestByUserId(userId);
    }

    public int insertInterestInfo(String[] interests,int userId){
        int result = this.interestMapper.insertInterestInfo(userId);
        for(int i = 0;i<interests.length;i++){
            this.interestMapper.updateInterestState(interests[i],userId);
        }
        return result;
    }

    public int updateInterestState(String interest, int userId){
        return this.interestMapper.updateInterestState(interest,userId);
    }

}
