package user.control;

import com.jfinal.core.Controller;

import java.io.UnsupportedEncodingException;

import user.dao.UserDao;
import user.service.UserService;

public class UserController extends Controller {
	
	//用户登录
	public void userLogin() {
		String email = getPara("useremail");
		String password = getPara("password");
		String result = new UserService().userLogin(email, password);
		if(result==null) {
			renderText("false");
		}else {
			renderText(result);
		}
	}

	//用户注册
	public void userRegister(){
		try {
			getRequest().setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String userEmail = getPara("userEmail");
        String userPassword = getPara("userPassword");
        String userNickname = getPara("userNickname");
        String userSex = getPara("userSex");
        String userInterest = getPara("userInterest");
		String result = new UserService().userRegister(userEmail,userPassword,userNickname,userSex,userInterest);
		if(result.equals("true")){
			renderText("true");
		}else if(result.equals("repeat")){
			renderText("repeat");
		}else {
			renderText("false");
		}
		System.out.println(userInterest);
	}
	
	
}
