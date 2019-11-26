package user.service;

import user.dao.UserDao;

public class UserService {

	public String userLogin(String email, String password) {
		return new UserDao().userLogin(email, password);
	}

	public boolean userRegister(){
		return new UserDao().userRegister();
	}

}
