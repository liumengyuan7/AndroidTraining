package user.control;

import com.jfinal.core.Controller;
import user.service.UserService;

public class UserController extends Controller {
	
	//�û���¼
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

	//�û�ע��
	public void userRegister(){
        String
	}
	
	
}
