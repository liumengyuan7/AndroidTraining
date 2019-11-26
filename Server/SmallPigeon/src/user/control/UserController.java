package user.control;

import com.jfinal.core.Controller;
import user.service.UserService;

public class UserController extends Controller {
	
	//用户登录
	public void userLogin() {
		String useremail = getPara("useremail");
		String password = getPara("password");
		String result = new UserService().userLogin(useremail, password);
		if(result==null) {
			renderText("false");
		}else {
			renderText(result);
		}
	}

	//用户注册
	public void userRegister(){
        String
	}
	
	
}
