package com.smallpigeon.interest.service;

import com.smallpigeon.entity.Interest;
import com.smallpigeon.interest.dao.InterestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className InterestService
 * @auther 吴东枚
 * @description 兴趣service
 * @date 2020/04/22 13:19
 */

@Service
public class InterestService {
    @Autowired
    private InterestMapper interestMapper;

    public List<Interest> getAllInterests(){
            return  this.interestMapper.getAllInterests();
    }
    public void addInterest(Interest interest){
        this.interestMapper.addInterest(interest);
    }
    public void deleteInterest(int id){
        this.interestMapper.deleteInterest(id);
    }
    public Interest getInterest(int id){
        return this.interestMapper.getInterest(id);
    }
    public void updateInterest(Interest interest){
        this.interestMapper.updateInterest(interest);
    }
}
