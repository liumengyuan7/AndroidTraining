package com.record.control;

import com.record.service.RecordService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @auther angel
 * @description 1
 * @date 2019/12/16 15:37
 */
@Controller
@RequestMapping("record")
public class RecordController {

    @Resource
    private RecordService recordService;

    //添加用户的跑步记录
    @ResponseBody
    @RequestMapping("addUserRecord")
    public String addUserRecord(@RequestParam("id") String id,
                              @RequestParam("distance") String distance,
                              @RequestParam("time") String time,
                              @RequestParam("speed") String speed){
        boolean result = this.recordService.addUserRecord(id,distance,time,speed);
        if (result){
            return "true";
        }else{
            return "false";
        }
    }

    //获取总的公里数
    @ResponseBody
    @RequestMapping("getTotalKm")
    public String getTotalKm(@RequestParam("id") String id){
        return this.recordService.getTotalKm(id);
    }

}
