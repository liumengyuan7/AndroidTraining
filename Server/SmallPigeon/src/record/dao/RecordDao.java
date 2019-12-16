package record.dao;

import java.sql.Timestamp;
import java.util.Date;

import bean.Record;
import bean.User;

/**
 * @auther angel
 * @description 1
 * @date 2019/12/16 15:39
 */

public class RecordDao {

    public boolean addUserRecord(String id,String distance,String time,String speed){
        Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		int point = Integer.parseInt(User.dao.find("select * from user where id=?",id).get(0).getStr("user_points"));
		new User().set("user_points",point+6).update();
        boolean result = new Record().set("user_id",id).set("record_time",time).set("record_distance",distance)
                .set("record_speed",speed).set("record_date",timestamp).set("record_points",6).save();
        return result;
    }

}
