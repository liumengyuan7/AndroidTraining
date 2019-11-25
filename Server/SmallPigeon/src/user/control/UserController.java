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
		List<User> result = new UserService().userLogin(username, password);
		Gson gson = new Gson();
		if(result!=null) {
			renderText(gson.toJson(result));
		}else {
			renderText("false");
		}
	}
	
	
	
}
