package user.control;

import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

}
