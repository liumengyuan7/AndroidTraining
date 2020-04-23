package com.smallpigeon.interest.dao;

import com.smallpigeon.entity.Interest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InterestMapper {
    public List<Interest> getAllInterests();
    public void addInterest(Interest interest);
    public void deleteInterest(@Param("id") int id);
    public Interest getInterest(@Param("id") int id);
    public void updateInterest(Interest interest);
}
