package bean;

import com.jfinal.plugin.activerecord.Model;

/**
 * @auther angel
 * @description 计划
 * @date 2019/12/10 14:52
 */

public class Plan extends Model<Plan> {
    public static final Plan dao = new Plan().dao();
}
