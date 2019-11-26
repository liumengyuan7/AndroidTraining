package user.dao;

import com.google.gson.Gson;

import java.util.List;

import bean.User;

public class UserDao {

	private String interest = "";

	public String userLogin(String username,String password) {
		List<User> list = User.dao.find("select * from user where user_name=? and user_password=?",username,password);
		List<User> list1 = User.dao.find("select interest.* from interest,user where user.id =?",list.get(0).getStr("id"));
		if(! list.isEmpty()) {
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

	private void interestSet(User user,String in){
		if(user.getStr(in).equals("1")){
			interest += in+",";
		}
	}

}
