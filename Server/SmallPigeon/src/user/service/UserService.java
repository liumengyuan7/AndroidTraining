package user.service;

import user.dao.UserDao;

public class UserService {

	public String userLogin(String useremail, String password) {
		return new UserDao().userLogin(useremail, password);
	}

	public boolean userRegister(){
		return new UserDao().userRegister();
	}

}
