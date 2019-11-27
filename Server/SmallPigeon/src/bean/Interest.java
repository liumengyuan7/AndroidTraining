package bean;


import com.jfinal.plugin.activerecord.Model;

/**
 * @auther angel
 * @description as
 * @date 2019/11/26 16:52
 */

public class Interest extends Model<Interest> {
    public static final Interest dao = new Interest().dao();
}
