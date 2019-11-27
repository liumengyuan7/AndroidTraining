package user.dao;

import com.google.gson.Gson;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import bean.Interest;
import bean.User;
import interest.service.InterestService;

public class UserDao {

	private String interest = "";

	//用户的登录
	public String userLogin(String email,String password) {
		List<User> list = User.dao.find("select * from user where user_email=? and user_password=?",email,password);
		if(! list.isEmpty()) {
		    List<User> list1 = User.dao.find("select interest.* from interest,user where user.id =?",list.get(0).getStr("id"));
			User user = list1.get(0);
			interestSet(user,"outdoor");
			interestSet(user,"society");
			interestSet(user,"music");
			interestSet(user,"star");
			interestSet(user,"science");
			interestSet(user,"film");
			interestSet(user,"comic");
			interestSet(user,"delicacy");
			Gson gson = new Gson();
			return gson.toJson(list)+";"+interest;
		}
		return null;
	}

	//用户兴趣的设置
	private void interestSet(User user,String in){
		if(user.getStr(in).equals("1")){
			interest += in+",";
		}
	}

	//用户的注册
	public boolean userRegister(String userEmail,String userPassword,String userNickname,String userSex,String userInterest){
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		boolean result = new User().set("user_email",userEmail)
				.set("user_password",userPassword).set("user_nickname",userNickname)
				.set("user_register_time",timestamp).set("user_sex",userSex).save();
		new InterestService().insertInterest(userInterest
				,new User().dao().find("select id from user where user_email=?",userEmail).get(0).getStr("id"));
		return result;
	}

}
