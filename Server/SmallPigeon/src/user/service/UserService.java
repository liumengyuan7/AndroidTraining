package user.service;

import java.util.List;

import bean.User;
import user.dao.UserDao;

public class UserService {
	public List<User> userLogin(String username, String password) {
		return new UserDao().userLogin(username, password);
	}
}
