package friend.control;

import com.jfinal.core.Controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import friend.service.FriendService;
import user.service.UserService;

/**
 * @auther 好友表的操作
 * @description
 * @date 2019/12/10 20:24
 */

public class FriendController extends Controller {
    //   public void saveContactList(){
////        Gson gson = new Gson();
////        String contactList = getPara("contactList");
////        Type type = new TypeToken<List<Integer>>(){}.getType();
////        List<Integer> friends = gson.fromJson(contactList,type);
////
////    }
    //查找所有用户信息
	public void searchAllUser() throws IOException {
	    System.out.println("zhixing");
		String result = new FriendService().searchAllUser();
		if(result==null) {
			renderText("false");
		}else {
			HttpServletResponse response = getResponse();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(result);
			renderNull();
		}
	}
	//添加好友
    public void addContact(){
	    int myId = Integer.parseInt(getPara("myId"));
        int friendId = Integer.parseInt(getPara("friendId"));
        boolean judge = new FriendService().addContact(myId,friendId);
        if(judge){
            renderText("true");
        }else{
            renderText("false");
        }
	}
	//得到我的好友列表
    public void getContactList() throws IOException {
	    int myId = Integer.parseInt(getPara("myId"));
	    String result = new FriendService().getContactList(myId);
	    if(result==null) {
			renderText("false");
		}else {
			HttpServletResponse response = getResponse();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(result);
			renderNull();
		}
	}
}
