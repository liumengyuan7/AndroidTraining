package plan.control;

import com.jfinal.core.Controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import bean.Plan;
import plan.service.PlanService;

/**
 * @auther angel
 * @description 计划表的方法集合体
 * @date 2019/12/10 14:55
 */

public class PlanController extends Controller {

    //获取用户所有的计划
    public void getAllPlan() throws IOException {
        String userId = getPara("userId");
        String result = new PlanService().getMyAllPlan(userId);
        HttpServletResponse response = getResponse();
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(result);
        renderNull();
    }

    //获取用户所有未完成的计划
    public void getUnfinishedPlan() throws IOException {
        String userId = getPara("userId");
        String result = new PlanService().getMyUnfinishedPlan(userId);
        System.out.println(result);
        HttpServletResponse response = getResponse();
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(result);
        renderNull();
    }

    //删除用户的计划
    public void deleteUserPlan(){
        String planId = getPara("planId");
        boolean result = new PlanService().deleteUserPlan(planId);
        if(result){
            renderText("true");
        }else{
            renderText("false");
        }
    }
    public void addUserPlan() throws ParseException {
        int userId= Integer.parseInt(getPara("myId"));
        int friendId = Integer.parseInt(getPara("friendId"));
        String datetime = getPara("datetime");
        String address = getPara("address");
        boolean result = new PlanService().addUserPlan(userId,friendId,datetime,address);
        if(result){
            renderText("true");
        }else{
            renderText("false");
        }
    }

}
