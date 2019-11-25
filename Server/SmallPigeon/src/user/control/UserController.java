package user.control;

import com.google.gson.Gson;
import com.jfinal.core.Controller;

import org.json.JSONObject;

import java.util.List;

import bean.User;
import user.service.UserService;

public class UserController extends Controller {
	
	//ÓÃ»§µÇÂ¼
	public void userLogin() {
		String username = getPara("username");
		String password = getPara("password");
		String result = new UserService().userLogin(username, password);
		if(result==null) {
			renderText("false");
		}else {
			renderText(result);
		}
	}
	
	
	
}
