package bean;


import com.jfinal.plugin.activerecord.Model;

public class Friend extends Model<Friend> {
    public static final Friend dao = new Friend().dao();
}
