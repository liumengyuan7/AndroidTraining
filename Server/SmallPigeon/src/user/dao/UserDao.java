package user.dao;

import com.google.gson.Gson;

import java.util.List;

import bean.User;

public class UserDao {

	private String interest = "";

	//用户的登录
	public String userLogin(String useremail,String password) {
		List<User> list = User.dao.find("select * from user where user_email=? and user_password=?",useremail,password);
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

	//用户的注册
	public boolean userRegister(){

		return false;
	}

	//用户兴趣的设置
	private void interestSet(User user,String in){
		if(user.getStr(in).equals("1")){
			interest += in+",";
		}
	}

}
