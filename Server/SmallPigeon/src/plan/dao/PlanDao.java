package plan.dao;

import com.google.gson.Gson;

import java.util.List;

import bean.Plan;

/**
 * @auther angel
 * @description dao
 * @date 2019/12/11 14:02
 */

public class PlanDao {

    //获取计划表中的我的所有计划
    public String getMyAllPlan(String userId){
        List<Plan> list = Plan.dao.find("select plan.*,user.user_email 'plan_email',user.user_nickname 'plan_nickname' " +
                "from plan,user where plan.companion_id=user.id and user_id=?",userId);
        if(list.isEmpty()){
            return "empty";
        }else{
            return new Gson().toJson(list);
        }
    }

    //获取计划表中的我的未完成的计划
    public String getMyUnfinishedPlan(String userId){
        List<Plan> list = Plan.dao.find("select plan.*,user.user_email 'plan_email',user.user_nickname 'plan_nickname' " +
                "from plan,user where plan.companion_id=user.id and user_id=? and plan_status=?",userId,0);
        if(list.isEmpty()){
            return "empty";
        }else{
            return new Gson().toJson(list);
        }
    }

    //删除计划表中的用户计划
    public boolean deleteUserPlan(String planId){
        boolean result = Plan.dao.deleteById(planId);
        return result;
    }

}
