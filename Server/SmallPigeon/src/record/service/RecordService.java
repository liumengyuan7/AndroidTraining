package record.service;

import record.dao.RecordDao;

/**
 * @auther angel
 * @description 3
 * @date 2019/12/16 15:40
 */

public class RecordService {

    public boolean addUserRecord(String id,String distance,String time,String speed){
        return new RecordDao().addUserRecord(id,distance,time,speed);
    }

}
