package interest.control;

import com.jfinal.core.Controller;

import java.util.List;
import java.util.Random;

import bean.Interest;

/**
 * @auther angel
 * @description 兴趣表的控制
 * @date 2019/11/26 17:02
 */

public class InterestController extends Controller {

    public void randomMatch(){
        System.out.println("1111111111111111111111111111");
        String id = getPara("id");
        new Interest().findById(id).set("matcher","yes").update();
        List<Interest> list = new Interest().dao.find("select * from matcher where matcher=?",id);
        if(list.isEmpty()){
            renderText("empty");
        }else{
            String n = list.get(0).getStr("id");
            new Interest().findById(id).set("matcher",n).update();
            renderText(n);
        }
    }

    public void randomMatch1(){
        String id = getPara("id");
        List<Interest> list = new Interest().dao
                .find("select * from matcher where matcher=? and id!=?","yes",id);
        if(list.isEmpty()){
            renderText("no");
        }else{
            String a = list.get(0).getStr("id");
            new Interest().findById(id).set("matcher",a).update();
            renderText(a);
        }
    }

}
