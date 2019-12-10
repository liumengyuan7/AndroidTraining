package user.control;

import com.google.gson.Gson;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import bean.User;
import user.dao.UserDao;
import user.service.UserService;

public class UserController extends Controller {
	
	//用户登录
	public void userLogin() throws IOException {
		String email = getPara("useremail");
		String password = getPara("password");
		String result = new UserService().userLogin(email, password);
		if(result==null) {
			renderText("false");
		}else {
			HttpServletResponse response = getResponse();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(result);
			renderNull();
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
		System.out.println(userNickname);
	}
	
	//验证码的接收以及邮箱的验证
	public void verifyCodeAndEmail(){
		String result = new UserDao().emailSendAndEmailConfirm(getPara("userEmail"),getPara("code"),getPara("tag"));
		renderText(result);
	}

	//验证码的接收
	public void verifyCode(){
		boolean result = new UserDao().emailSend(getPara("userEmail"),getPara("code"));
		if(result){
			renderText("true");
		}else{
			renderText("false");
		}
	}

	//用户密码的修改
	public void updatePassword(){
		String id = getPara("userId");
		String password = getPara("password");
		boolean result = new UserService().updatePassword(id,password);
		if(result){
			renderText("true");
		}else{
			renderText("false");
		}
	}

	//用户昵称的修改
	public void updateNickname(){
		String id = getPara("userId");
		String nickname = getPara("nickname");
		boolean result = new UserService().updateNickname(id,nickname);
		if(result){
			renderText("true");
		}else{
			renderText("false");
		}
	}

	//用户忘记密码
	public void forgetPassword(){
		String email = getPara("userEmail");
		String password = getPara("password");
		boolean result = new UserService().forgetPassword(email,password);
		if(result){
			renderText("true");
		}else{
			renderText("false");
		}
	}

	//邮箱的更新
	public void updateEmail(){
		String id = getPara("userId");
		String email = getPara("userEmail");
		boolean result = new UserService().updateEmail(id,email);
		if(result){
			renderText("true");
		}else{
			renderText("false");
		}
	}

	//积分榜
	public void gradeRank() throws IOException {
		String result = new UserService().gradeRank();
		if(result == null || result.equals("")){
			renderText("false");
		}else{
			HttpServletResponse response = getResponse();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(result);
			renderNull();
		}
	}

	//获取的图片存入out中
	public void getPicture() throws Exception {
		String path = PathKit.getWebRootPath()+"\\avatar\\";
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> items = upload.parseRequest(getRequest());
        FileItem item = items.get(0);
        String email = getPara("userEmail");
		item.write(new File(path+item.getName()));
		renderText("true");
	}

	//post图片
	public void postPicture(){
		String path = PathKit.getWebRootPath()+"\\avatar\\"+getPara("userEmail")+".jpg";
		File file = new File(path);
		renderFile(file);
	}

	//将matcher标识为匹配状态，若matcher有值，获取值返回客户端，
	//并将matcher与对应的人的matcher标为未匹配状态
	public void randomMatchFirst(){
        String id = getPara("id");
        new User().findById(id).set("matcher","yes").update();
        List<User> list = new User().dao.find("select * from user where matcher=?",id);
        if(list.isEmpty()){
            renderText("empty");
        }else{
        	new User().findById(list.get(0).getStr("id")).set("matcher","no").update();
            new User().findById(id).set("matcher","no").update();
            renderText(new Gson().toJson(list));
        }
    }

    //获取出自己以外的正在匹配的人的信息，并返回给客户端
    public void randomMatchSecond(){
        String id = getPara("id");
        List<User> list = new User().dao
                .find("select * from user where matcher=? and id!=?","yes",id);
        if(list.isEmpty()){
            renderText("no");
        }else{
            String u = list.get(0).getStr("id");  //此处应为随机。
            new User().findById(id).set("matcher",u).update();
            renderText(new Gson().toJson(list));
        }
    }


}
