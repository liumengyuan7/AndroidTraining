package com.record.service;

import com.entity.Record;
import com.record.dao.RecordMapper;
import com.user.dao.UserMapper;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.sql.Timestamp;

import javax.annotation.Resource;

/**
 * @auther angel
 * @description 3
 * @date 2019/12/16 15:40
 */
@Service
public class RecordService {

    @Resource
    private RecordMapper recordMapper;
    @Resource
    private UserMapper userMapper;

    //更新用户的记录,并向记录表中插入用户此次的奔跑数据
    public boolean addUserRecord(String id,String distance,String time,String speed){
        int point = Integer.parseInt(this.userMapper.selectUserById(id).get("user_points")+"");
        this.userMapper.updateUserPointsById(id,point+6);
        Record record = new Record();
        record.setUserId(Integer.parseInt(id));
        record.setRecordTime(time);
        record.setRecordDistance(Float.parseFloat(distance));
        record.setRecordSpeed(Float.parseFloat(speed));
        record.setRecordDate(new Timestamp(new Date().getTime()));
        record.setRecordPoints(6);
        int result = this.recordMapper.insertRecord(record);
        if(result>0) return true;
        else return false;
    }

    //获得用户今天总的奔跑里程
    public String getTotalKm(String id){
        return this.recordMapper.getUserTodayKm(id)+"";
    }

}
