package user.service;

import user.dao.UserDao;

public class UserService {

	public String userLogin(String email, String password) {
		return new UserDao().userLogin(email, password);
	}

	public boolean userRegister(String userEmail,String userPassword,String userNickname,String userSex,String userInterest){
		return new UserDao().userRegister(userEmail,userPassword,userNickname,userSex,userInterest);
	}

}
