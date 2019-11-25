package user.dao;

import java.util.List;

import bean.User;

public class UserDao {
	public boolean userLogin(String username,String password) {
		List<User> list = User.dao.find("select * from user where user_name=? and user_password=?",username,password);
		if(! list.isEmpty()) {
			return true;
		}
		return false;
	}
}
