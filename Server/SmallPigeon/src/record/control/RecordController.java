package record.control;

import com.jfinal.core.Controller;

import record.service.RecordService;

/**
 * @auther angel
 * @description 1
 * @date 2019/12/16 15:37
 */

public class RecordController extends Controller {

    //添加用户的跑步记录
    public void addUserRecord(){
        String id = getPara("id");
        String distance = getPara("distance");
        String time = getPara("time");
        String speed = getPara("speed");
        boolean result = new RecordService().addUserRecord(id,distance,time,speed);
        if (result){
            renderText("true");
        }else{
            renderText("false");
        }
    }

    //获取总的公里数
    public void getTotalKm(){
        String id = getPara("id");
        renderText(new RecordService().getTotalKm(id));
    }

}
