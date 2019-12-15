package plan.service;

import java.text.ParseException;

import plan.dao.PlanDao;

/**
 * @auther angel
 * @description service
 * @date 2019/12/11 14:02
 */

public class PlanService {

    public String getMyAllPlan(String userId){
        return new PlanDao().getMyAllPlan(userId);
    }

    public String getMyUnfinishedPlan(String userId){
        return new PlanDao().getMyUnfinishedPlan(userId);
    }

    public boolean deleteUserPlan(String planId){
        return new PlanDao().deleteUserPlan(planId);
    }

    public boolean addUserPlan(int userId,int friendId,String datetime,String address) throws ParseException {
        return new PlanDao().addUserPlan(userId,friendId,datetime,address);
    }
}
