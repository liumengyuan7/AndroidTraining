package com.friend.control;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.friend.service.FriendService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @auther 好友表的操作
 * @description
 * @date 2019/12/10 20:24
 */

@Controller
@RequestMapping("friend")
public class FriendController {

	@Resource
	private FriendService friendService;

	//查找相关用户信息
	@ResponseBody
	@RequestMapping(value = "searchAllUser",produces = "text/html;charset=UTF-8")
	public String searchAllUser() {
        String result = this.friendService.searchAllUser();
		if(result==null) {
			return "false";
		}else {
			return result;
		}
	}

	//添加好友
	@ResponseBody
	@RequestMapping("addContact")
    public String addContact(@RequestParam("myId") int myId,@RequestParam("friendId") int friendId){
        boolean judge = this.friendService.addContact(myId,friendId);
        if(judge){
            return "true";
        }else{
            return "false";
        }
	}

	//得到我的好友列表
	@ResponseBody
	@RequestMapping(value = "getContactList",produces = "text/html;charset=UTF-8")
    public String getContactList(@RequestParam("myId") int myId) {
	    System.out.println("我的id去找好友列表"+myId);
	    String result = this.friendService.getContactList(myId);
	    if(result==null) {
			return "false";
		}else {
			System.out.println("好友列表"+result);
			return result;
		}
	}

	//模糊查询用户
	@ResponseBody
	@RequestMapping(value = "getLikeContactList",produces = "text/html;charset=UTF-8")
    public String getLikeContactList(@RequestParam("userEmail") String userEmail) {
	    System.out.println("模糊查询账号"+userEmail);
	    String result = this.friendService.getLikeUser(userEmail);
	    if(result==null) {
			return "false";
		}else {
			System.out.println("模糊查询结果"+result);
			return result;
		}
	}
}
