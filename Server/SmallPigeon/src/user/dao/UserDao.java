package user.dao;

import com.google.gson.Gson;
import com.jfinal.kit.PathKit;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import bean.Interest;
import bean.User;

public class UserDao {

	private String interest = "";

	//用户的登录
	public String userLogin(String email,String password) {
		List<User> list = User.dao.find("select * from user where user_email=? and user_password=?",email,password);
		if(! list.isEmpty()) {
		    List<User> list1 = User.dao.find("select * from interest where user_id =?",list.get(0).getStr("id"));
		    User user = list1.get(0);
			interestSet(user,"outdoor");
			interestSet(user,"society");
			interestSet(user,"music");
			interestSet(user,"star");
			interestSet(user,"science");
			interestSet(user,"film");
			interestSet(user,"comic");
			interestSet(user,"delicacy");
			Gson gson = new Gson();
			return gson.toJson(list)+";"+interest;
		}
		return null;
	}

	//用户兴趣的设置
	private void interestSet(User user,String in){
		if(user.getStr(in).equals("1")){
			interest += in+",";
		}
	}

	//用户的注册
	public String userRegister(String userEmail,String userPassword,String userNickname,String userSex,String userInterest){
		List<User> confirm = new User().dao().find("select * from user where user_email=?",userEmail);
		if(confirm.isEmpty()){
			return dataBaseOperation(userEmail,userPassword,userNickname,userSex,userInterest);
		}else{
			return "repeat";
		}
	}

	//注册中数据库的操作
	public String dataBaseOperation(String userEmail,String userPassword,String userNickname,String userSex,String userInterest){
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		boolean result = new User().set("user_email",userEmail)
				.set("user_password",userPassword).set("user_nickname",userNickname)
				.set("user_register_time",timestamp).set("user_sex",userSex).save();
		String userId = new User().dao().find("select id from user where user_email=?",userEmail).get(0).getStr("id");
		new Interest().set("user_id",userId).save();
		String[] in = userInterest.split(",");
		Interest interest = Interest.dao.findById(new Interest().dao().find("select id from interest where user_id=?",userId).get(0).getStr("id"));
		for(int i=0;i<in.length;i++){
			interest.set(in[i],1);
		}
		interest.update();
		if(result){
			return "true";
		}else{
			return "false";
		}
	}

	//邮件发送和邮箱的重复确认
	public String emailSendAndEmailConfirm(String userEmail,String code,String tag){
		//tag判断是注册或者换绑邮箱还是忘记密码
		List<User> confirm = new User().dao().find("select * from user where user_email=?",userEmail);
		if(tag.equals("nr")){
			if(confirm.isEmpty()){
				if(emailSend(userEmail,code)){
					return "true";
				}else{
					return "false";
				}
			}else{
				return "repeat";
			}
		}else if(tag.equals("re")){
			if(confirm.isEmpty()){
				return "notRepeat";
			}else{
				if(emailSend(userEmail,code)){
					return "true";
				}else{
					return "false";
				}
			}
		}

		return "false";
	}

	//邮件的发送
	public boolean emailSend(String userEmail,String code){
		Properties props = System.getProperties();
		props.put("mail.smtp.host", "smtp.qq.com");
		props.put("mail.smtp.auth", "true");
		Session session = Session.getInstance(props, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("2642611193@qq.com", "tvmwxbsavkdvecga"); //tvmwxbsavkdvecga   18731180200
			}
		});
		Message msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress("2642611193@qq.com","小鸽快跑","UTF-8"));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
			msg.setSubject("为保证您的账号安全请验证邮箱");
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setContent("<h3>【小鸽快跑】</h3><p>您的验证码是"+code+",您正在进行邮箱验证注册,1分钟内有效。(请勿向任何人提供您收到的验证码)</p>","text/html;charset=UTF-8");
			MimeMultipart mimeMultipart = new MimeMultipart();
			mimeMultipart.addBodyPart(mbp1);
			msg.setContent(mimeMultipart);
			msg.setHeader("X-Mailer", "smtpsend");
			Transport.send(msg);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}

	//用户密码的修改
	public boolean updatePassword(String id,String password){
		boolean result = new User().findById(id).set("user_password",password).update();
		return result;
	}

	//用户昵称的修改
	public boolean updateNickname(String id,String nickname){
		boolean result = new User().findById(id).set("user_nickname",nickname).update();
		return result;
	}

	//用户忘记密码
	public boolean forgetPassword(String email,String password){
		String id = new User().dao().find("select id from user where user_email=?",email).get(0).getStr("id");
		if(updatePassword(id,password)){
			return true;
		}else{
			return false;
		}
	}

	//用户邮箱的修改
	public boolean updateEmail(String id,String userEmail){
		List<User> list = User.dao.find("select * from user where id=?",id);
		String yPath = PathKit.getWebRootPath()+"\\avatar\\"+list.get(0).getStr("user_email")+".jpg";
		String nPath = PathKit.getWebRootPath()+"\\avatar\\"+userEmail+".jpg";
		File file = new File(yPath);
		file.renameTo(new File(nPath));
		boolean result = new User().findById(id).set("user_email",userEmail).update();
		return result;
	}

	//积分榜
	public String gradeRank(){
		List<User> list = new User().dao()
				.find("select user_nickname,user_points from user order by user_points desc limit 0,10");
		return new Gson().toJson(list);
	}

}
