package plan.dao;

import com.google.gson.Gson;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    //添加用户计划
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
        boolean my = new Plan().set("user_id",userId).set("companion_id",friendId)
                 .set("plan_time",timeStamp).set("plan_address",address)
                 .set("plan_status",0).save();
        boolean friend = new Plan().set("user_id",friendId).set("companion_id",userId)
                 .set("plan_time",timeStamp).set("plan_address",address)
                 .set("plan_status",0).save();

        if(my && friend){
            return true;
        }else{
            return false;
        }
    }
}
