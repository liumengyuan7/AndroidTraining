package com.smallpigeon.plan.dao;

import com.smallpigeon.entity.Interest;
import com.smallpigeon.entity.Plan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlanMapper {
    public List<Plan> getAllPlans();
    public void addPlan(Plan plan);
    public void deletePlan(@Param("id") int id);
    public Plan getPlan(@Param("id") int id);
    public void updatePlan(Plan plan);
}
