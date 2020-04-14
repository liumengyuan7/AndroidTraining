package com.plan.dao;

import com.entity.Plan;

import java.util.List;
import java.util.Map;

/**
 * @author syf
 * @ClassName PlanMapper
 * @description
 * @date 2020/04/10 16:39
 */

public interface PlanMapper {

    public List<Map> getMyAllPlanByUserId(String userId);

    public List<Map> getMyAllUnfinishedPlanByUserId(String userId);

    public int deletePlanById(String planId);

    public int insertPlanInfo(Plan plan);

}
