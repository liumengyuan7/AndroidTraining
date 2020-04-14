package com.user.control;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.user.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	@Resource
	private UserService userService;
	@Resource
	private ServletContext servletContext;

	//用户登录
	@ResponseBody
	@RequestMapping(value = "userLogin",produces = "text/html;charset=UTF-8")
	public String userLogin(@RequestParam("useremail") String email,
							@RequestParam("password") String password) {
		String result = this.userService.userLogin(email, password);
		if(result==null) {
			return "false";
		}else {
			System.out.println(result);
			return result;
		}
	}

	//用户注册
	@ResponseBody
	@RequestMapping("userRegister")
	public String userRegister(@RequestParam("userEmail") String userEmail,
							   @RequestParam("userPassword") String userPassword,
							   @RequestParam("userNickname") String userNickname,
							   @RequestParam("userSex") String userSex,
							   @RequestParam("userInterest") String userInterest) {
		System.out.println(userEmail+userPassword+userNickname+userSex+userInterest);
		String result = this.userService.userRegister(userEmail,userPassword,userNickname,userSex,userInterest);
		if(result.equals("repeat")){
			return "repeat";
		}else if(result.equals("false")){
			return "false";
		}else {
			return result;
		}
	}
	
	//验证码的接收以及邮箱的验证
	@ResponseBody
	@RequestMapping("verifyCodeAndEmail")
	public String verifyCodeAndEmail(@RequestParam("userEmail") String userEmail,
									 @RequestParam("code") String code,
									 @RequestParam("tag") String tag){
		return this.userService.emailSendAndEmailConfirm(userEmail,code,tag);
	}

	//验证码的接收
	@ResponseBody
	@RequestMapping("verifyCode")
	public String verifyCode(@RequestParam("userEmail") String userEmail,
							 @RequestParam("code") String code){
		boolean result = this.userService.emailSend(userEmail, code);
		if(result){
			return "true";
		}else{
			return "false";
		}
	}

	//用户密码的修改
	@ResponseBody
	@RequestMapping("updatePassword")
	public String updatePassword(@RequestParam("userId") String id,
							   @RequestParam("password") String password){
		boolean result = this.userService.updatePassword(id,password);
		if(result){
			return "true";
		}else{
			return "false";
		}
	}

	//用户昵称的修改
	@ResponseBody
	@RequestMapping("updateNickname")
	public String updateNickname(@RequestParam("userId") String id,
								 @RequestParam("nickname") String nickname){
		boolean result = this.userService.updateNickname(id,nickname);
		if(result){
			return "true";
		}else{
			return "false";
		}
	}

	//用户忘记密码
	@ResponseBody
	@RequestMapping("forgetPassword")
	public String forgetPassword(@RequestParam("userEmail") String email,
							   @RequestParam("password") String password){
		boolean result = this.userService.forgetPassword(email,password);
		if(result){
			return "true";
		}else{
			return "false";
		}
	}

	//邮箱的更新
	@ResponseBody
	@RequestMapping("updateEmail")
	public String updateEmail(@RequestParam("userId") String id,
							@RequestParam("userEmail") String email){
		boolean result = this.userService.updateEmail(id,email);
		if(result){
			return "true";
		}else{
			return "false";
		}
	}

	//积分榜
	@ResponseBody
	@RequestMapping(value = "gradeRank",produces = "text/html;charset=UTF-8")
	public String gradeRank() {
		String result = this.userService.gradeRank();
		if(result == null || result.equals("")){
			return "false";
		}else{
			return result;
		}
	}

	//获取的图片存入out中
	@ResponseBody
	@RequestMapping("getPicture")
	public String getPicture(HttpServletRequest request,@RequestParam("userEmail") String email) throws Exception {
		String path = servletContext.getRealPath("")+"avatar\\";
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> items = upload.parseRequest(request);
        FileItem item = items.get(0);
		item.write(new File(path+item.getName()));
		return "true";
	}

	//post图片
	@ResponseBody
	@RequestMapping("postPicture")
	public File postPicture(@RequestParam("userEmail") String userEmail){
		String path = servletContext.getRealPath("")+"avatar\\"+userEmail+".jpg";
		File file = new File(path);
		return file;
	}

	//将matcher标识为匹配状态，若matcher有值，获取值返回客户端，
	//并将matcher与对应的人的matcher标为未匹配状态
	@ResponseBody
	@RequestMapping("randomMatchFirst")
	public String randomMatchFirst(@RequestParam("id") String id,HttpServletResponse response) throws IOException {
        return this.userService.randomMatchFirst(id,response);
    }

    //获取出自己以外的正在匹配的人的信息，并返回给客户端
	@ResponseBody
	@RequestMapping("randomMatchSecond")
    public String randomMatchSecond(@RequestParam("id") String id,HttpServletResponse response) throws IOException {
        return this.userService.randomMatchSecond(id, response);
    }

    //修改匹配的状态
	@ResponseBody
	@RequestMapping("fixMatcherStatus")
	public void fixMatcherStatus(@RequestParam("id") String id){
		this.userService.fixMatcherStatus(id);
	}

}
