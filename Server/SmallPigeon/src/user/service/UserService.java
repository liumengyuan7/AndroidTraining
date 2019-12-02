package user.service;

import user.dao.UserDao;

public class UserService {

	public String userLogin(String email, String password) {
		return new UserDao().userLogin(email, password);
	}

	public String userRegister(String userEmail,String userPassword,String userNickname,String userSex,String userInterest){
		return new UserDao().userRegister(userEmail,userPassword,userNickname,userSex,userInterest);
	}

	public boolean updatePassword(String id,String password){
		return new UserDao().updatePassword(id,password);
	}

	public boolean updateNickname(String id,String nickname){
		return new UserDao().updateNickname(id,nickname);
	}

	public boolean updateEmail(String id,String userEmail){
		return new UserDao().updateEmail(id,userEmail);
	}

}
