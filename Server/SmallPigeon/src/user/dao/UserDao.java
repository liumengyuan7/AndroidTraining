package user.dao;

import com.jfinal.json.Json;

import java.util.List;

import bean.User;

public class UserDao {
	public List<User> userLogin(String username,String password) {
		List<User> list = User.dao.find("select * from user where user_name=? and user_password=?",username,password);
		if(! list.isEmpty()) {
			return list;
		}
		return null;
	}
}
