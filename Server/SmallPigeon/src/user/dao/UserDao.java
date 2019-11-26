package user.dao;

import com.google.gson.Gson;

import java.util.List;

import bean.User;

public class UserDao {

	public String userLogin(String username,String password) {
		List<User> list = User.dao.find("select * from user where user_name=? and user_password=?",username,password);
		if(! list.isEmpty()) {
			Gson gson = new Gson();
			return gson.toJson(list);
		}
		return null;
	}

}
