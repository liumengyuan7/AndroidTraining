package bean;

import com.jfinal.plugin.activerecord.Model;

/**
 * @auther angel
 * @description 1
 * @date 2019/12/16 15:42
 */

public class Record extends Model<Record> {
    public static final Record dao = new Record().dao();
}
