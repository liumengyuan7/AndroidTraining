package user.service;

import java.util.List;

import bean.User;
import user.dao.UserDao;

public class UserService {

	public String userLogin(String username, String password) {
		return new UserDao().userLogin(username, password);
	}

	public boolean userRegister(){
		return new UserDao().userRegister();
	}

}
