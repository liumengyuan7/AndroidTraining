package interest.dao;

import bean.Interest;

/**
 * @auther angel
 * @description dao
 * @date 2019/11/26 17:04
 */

public class InterestDao {

    public boolean insertInterest(String interest,String userId){
        String[] single = interest.split(",");
        new Interest().set("user_id",Integer.parseInt(userId));
        return false;
    }

}