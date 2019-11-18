package user.service;

import user.dao.UserDao;

public class UserService {
	public boolean userLogin(String username,String password) {
		return new UserDao().userLogin(username, password);
	}
}
