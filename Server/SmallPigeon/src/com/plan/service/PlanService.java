package com.plan.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.entity.Plan;
import com.google.gson.Gson;
import com.plan.dao.PlanMapper;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @auther angel
 * @description service
 * @date 2019/12/11 14:02
 */
@Service
public class PlanService {

    @Resource
    private PlanMapper planMapper;

    //获取我的所有的计划
    public String getMyAllPlan(String userId){
        List<Map> plans = this.planMapper.getMyAllPlanByUserId(userId);
        if(plans.isEmpty()) return "empty";
        else return new Gson().toJson(plans);
    }

    //获得我的所有未完成的计划
    public String getMyUnfinishedPlan(String userId){
        List<Map> plans = this.planMapper.getMyAllUnfinishedPlanByUserId(userId);
        if(plans.isEmpty()) return "empty";
        else return new Gson().toJson(plans);
    }

    //删除用户的计划
    public boolean deleteUserPlan(String planId){
        int result = this.planMapper.deletePlanById(planId);
        if(result>0) return true;
        else return false;
    }

    //给用户添加计划
    public boolean addUserPlan(int userId,int friendId,String datetime,String address) throws ParseException {
        String year = datetime.substring(0, datetime.indexOf("年"));
        String month = datetime.substring(datetime.indexOf("年")+1, datetime.indexOf("月"));
        String day = datetime.substring(datetime.indexOf("月")+1, datetime.indexOf("日"));
        String hour = datetime.substring(datetime.indexOf("日")+1, datetime.indexOf("时"));
        String time =year+"-"+month+"-"+day+" "+hour+":00:00";
        System.out.println(year+" "+month+" "+day+" "+hour);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(time);
        Timestamp timeStamp = new Timestamp(date.getTime());
        Plan myPlan = new Plan();
        myPlan.setUserId(userId);
        myPlan.setCompanionId(friendId);
        myPlan.setPlanTime(timeStamp);
        myPlan.setPlanAddress(address);
        myPlan.setStatus(0);
        Plan friendPlan = new Plan();
        friendPlan.setUserId(friendId);
        friendPlan.setCompanionId(userId);
        friendPlan.setPlanTime(timeStamp);
        friendPlan.setPlanAddress(address);
        friendPlan.setStatus(0);
        int result1 = this.planMapper.insertPlanInfo(myPlan);
        int result2 = this.planMapper.insertPlanInfo(friendPlan);
        if(result1>0 && result2>0) return true;
        else return false;
    }

}
