package com.plan.control;

import java.io.IOException;
import java.text.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.plan.service.PlanService;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @auther angel
 * @description 计划表的方法集合体
 * @date 2019/12/10 14:55
 */
@Controller
@RequestMapping("plan")
public class PlanController {

    @Resource
    private PlanService planService;

    //获取用户所有的计划
    @ResponseBody
    @RequestMapping(value = "getAllPlan",produces = "text/html;charset=UTF-8")
    public String getAllPlan(@RequestParam("userId") String userId) {
        return this.planService.getMyAllPlan(userId);
    }

    //获取用户所有未完成的计划
    @ResponseBody
    @RequestMapping(value = "getUnfinishedPlan",produces = "text/html;charset=UTF-8")
    public String getUnfinishedPlan(@RequestParam("userId") String userId) {
        return this.planService.getMyUnfinishedPlan(userId);
    }

    //删除用户的计划
    @ResponseBody
    @RequestMapping("deleteUserPlan")
    public String deleteUserPlan(@RequestParam("planId") String planId){
        boolean result = this.planService.deleteUserPlan(planId);
        if(result){
            return "true";
        }else{
            return "false";
        }
    }

    //添加用户的计划
    @ResponseBody
    @RequestMapping("addUserPlan")
    public String addUserPlan(@RequestParam("myId") int myId,
                              @RequestParam("friendId") int friendId,
                              @RequestParam("datetime") String datetime,
                              @RequestParam("address") String address) throws ParseException {
        boolean result = this.planService.addUserPlan(myId,friendId,datetime,address);
        if(result){
            return "true";
        }else{
            return "false";
        }
    }

}
