package user.control;

import com.jfinal.core.Controller;

import user.service.UserService;

public class UserController extends Controller {
	
	//ÓÃ»§µÇÂ¼
	public void userLogin() {
		String username = getPara("username");
		String password = getPara("password");
		boolean result = new UserService().userLogin(username, password);
		if(result) {
			renderText("true");
		}else {
			renderText("false");
		}
	}
	
	
	
}
