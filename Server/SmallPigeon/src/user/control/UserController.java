package user.control;

import com.jfinal.core.Controller;
import user.service.UserService;

public class UserController extends Controller {
	
	//�û���¼
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

	//�û�ע��
	public void userRegister(){
        String userEmail = getPara("userEmail");
        String userPassword = getPara("userPassword");
        String userNicknam = getPara("userNickname");
        String userSex = getPara("userSex");
        String userInterest = getPara("userInterest");

	}
	
	
}
