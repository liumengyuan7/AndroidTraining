package com.smallpigeon.plan.service;

import com.smallpigeon.entity.Plan;
import com.smallpigeon.plan.dao.PlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className PlanService
 * @auther 吴东枚
 * @description 计划service
 * @date 2020/04/28 13:19
 */
@Service
public class PlanService {
    @Autowired
    private PlanMapper planMapper;
    public List<Plan> getAllPlans(){
        return this.planMapper.getAllPlans();
    }
    public void addPlan(Plan plan){
        this.planMapper.addPlan(plan);
    }
    public void deletePlan(int id){
        this.planMapper.deletePlan(id);
    }
    public Plan getPlan(int id){
        return this.planMapper.getPlan(id);
    }
    public void updatePlan(Plan plan){
        this.planMapper.updatePlan(plan);
    }
}
