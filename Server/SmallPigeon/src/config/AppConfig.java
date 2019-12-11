package config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;

import bean.Friend;
import bean.Interest;
import bean.Plan;
import bean.User;
import friend.control.FriendController;
import interest.control.InterestController;
import plan.control.PlanController;
import user.control.UserController;

/**
 * @auther angel
 * @description 1
 * @date 2019/11/15 16:14
 */

public class AppConfig extends JFinalConfig {

    @Override
    public void configConstant(Constants constants) {
        constants.setEncoding("UTF-8");
        constants.setEncoding("GB2312");
    }

    @Override
    public void configRoute(Routes routes) {
        routes.add("user",UserController.class);
        routes.add("interest", InterestController.class);
        routes.add("plan", PlanController.class);
//        routes.add("friend", FriendController.class);
    }

    @Override
    public void configEngine(Engine engine) {

    }

    @Override
    public void configPlugin(Plugins plugins) {
        DruidPlugin druidPlugin = new DruidPlugin("jdbc:mysql://localhost:3306/smallpigeon?useUnicode=true&characterEncoding=utf8", "root", "");
        plugins.add(druidPlugin);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		plugins.add(arp);
		arp.setDialect(new MysqlDialect());
		arp.addMapping("user", User.class);
		arp.addMapping("interest", Interest.class);
		arp.addMapping("plan", Plan.class);
//		arp.addMapping("friends", Friend.class);
    }

    @Override
    public void configInterceptor(Interceptors interceptors) {

    }

    @Override
    public void configHandler(Handlers handlers) {

    }
}
