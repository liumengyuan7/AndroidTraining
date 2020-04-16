package com.community.service;

import com.community.dao.DynamicMapper;
import com.entity.Dynamic;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

/**
 * @className DynamicService
 * @auther 刘梦圆
 * @description
 * @date 2020/04/15 16:45
 */
@Service
public class DynamicService {
    @Resource
    private DynamicMapper dynamicMapper;

    public String addDynamic(String userId, String pushTime, String pushContent, String pushImg) throws ParseException {
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        Date date = sdf.parse(pushTime);
        int result = this.dynamicMapper.insertDynamic(userId,pushTime,pushContent,pushImg);
        if(result>0){
            return "true";
        }else{
            return "false";
        }
    }
}
