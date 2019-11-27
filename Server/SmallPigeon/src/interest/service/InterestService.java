package interest.service;

import interest.dao.InterestDao;

/**
 * @auther angel
 * @description service
 * @date 2019/11/26 17:04
 */

public class InterestService {
    public boolean insertInterest(String interest,String userId){
        return new InterestDao().insertInterest(interest,userId);
    }
}
