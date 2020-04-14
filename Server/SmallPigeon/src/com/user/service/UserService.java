package com.user.service;

import com.entity.Interest;
import com.entity.User;
import com.google.gson.Gson;
import com.interest.dao.InterestMapper;
import com.user.dao.UserMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.annotation.Resource;
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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

@Service
@Transactional
public class UserService {

	@Resource
	private UserMapper userMapper;
	@Resource
	private InterestMapper interestMapper;
	@Resource
	private ServletContext servletContext;
	private String interestString = "";

	//用户的登录操作
	public String userLogin(String email, String password) {
		Map user = this.userMapper.selectUserByEmailAndPassword(email, password); //查询是否存在用户
		List<Map> userList = new ArrayList<>();
		userList.add(user);
		if(user != null){
			//根据用户的id查询用户的所有的兴趣，按照逗号将所有的兴趣拼接起来
			Interest interest = this.interestMapper.selectInterestByUserId(Integer.parseInt(user.get("id")+""));
			interestSet(interest.getOutdoor());
			interestSet(interest.getSociety());
			interestSet(interest.getMusic());
			interestSet(interest.getStar());
			interestSet(interest.getScience());
			interestSet(interest.getFilm());
			interestSet(interest.getComic());
			interestSet(interest.getDelicacy());
		}
		//返回用户信息和兴趣的字符串
		return new Gson().toJson(userList)+";"+interestString;
	}

	//用户兴趣字符串的拼接
	private void interestSet(int interest){
		if(interest == 1){
			interestString += interest+",";
		}
	}

	//用户注册
	public String userRegister(String userEmail,String userPassword,String userNickname,String userSex,String userInterest){
		Map userConfirm = this.userMapper.selectUserByEmail(userEmail);
		if(userConfirm == null){
			//添加用户
			User user = new User();
			user.setUserEmail(userEmail);
			user.setUserPassword(userPassword);
			user.setUserNickname(userNickname);
			user.setUserSex(userSex);
			user.setUserRegisterTime(new Timestamp(new Date().getTime()));
			int result1 = this.userMapper.insertUserInfo(user);

			//为用户添加兴趣
			int result2 = this.interestMapper.insertInterestInfo(user.getId());
			String[] in = userInterest.split(",");
			for(int i = 0;i<in.length;i++){
				this.interestMapper.updateInterestState(in[i],user.getId());
			}
			if(result1>0 && result2>0) return user.getId()+"";
			else return "false";
		}else {
			//如果注册用户信息重复，则会返回repeat
			return "repeat";
		}
	}

	//邮件的发送和邮件的确认
	public String emailSendAndEmailConfirm(String userEmail,String code,String tag){
		Map user = this.userMapper.selectUserByEmail(userEmail);
		if(tag.equals("nr")){
			if(user == null){
				if(emailSend(userEmail,code)){
					return "true";
				}else{
					return "false";
				}
			}else {
				return "repeat";
			}
		}else if(tag.equals("re")){
			if(user == null){
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

	//用户修改密码的操作
	public boolean updatePassword(String id,String password){
		int result = this.userMapper.updateUserPasswordById(id,password);
		if(result>0) return true;
		else return false;
	}

	//用户昵称更新的操作
	public boolean updateNickname(String id,String nickname){
		int result = this.userMapper.updateUserNicknameById(id,nickname);
		if(result>0) return true;
		else return false;
	}

	//用户更新邮箱的操作
	public boolean updateEmail(String id,String userEmail){
		Map user = this.userMapper.selectUserById(id);
		String yPath = servletContext.getRealPath("")+"avatar\\"+user.get("user_email")+".jpg";
		String nPath = servletContext.getRealPath("")+"avatar\\"+userEmail+".jpg";
		File file = new File(yPath);
		file.renameTo(new File(nPath));
		int result = this.userMapper.updateUserEmailById(id,userEmail);
		if(result>0) return true;
		else return false;
	}

	//用户忘记密码的操作
	public boolean forgetPassword(String email,String password){
		String id = this.userMapper.selectUserByEmail(email).get("id")+"";
		if(updatePassword(id,password)) return true;
		else return false;
	}

	//获得积分排行前十的用户的昵称和分数
	public String gradeRank(){
		return new Gson().toJson(this.userMapper.selectAllUserForPoints());
	}

	//将matcher标识为匹配状态，若matcher有值，获取值返回客户端，
	//并将matcher与对应的人的matcher标为未匹配状态
	public String randomMatchFirst(String id,HttpServletResponse response) throws IOException {
		this.userMapper.updateUserMatcherStateById(id,"yes");
        Map user = this.userMapper.selectUserByMatcher(id);
        if(user == null){
            return "empty";
        }else{
        	this.userMapper.updateUserMatcherStateById(user.get("id")+"","no");
        	this.userMapper.updateUserMatcherStateById(id,"no");
            String result = this.userLogin(user.get("user_email")+"",user.get("user_password")+"");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(result);
			return null;
        }
	}

	//获取出自己以外的正在匹配的人的信息，并返回给客户端
	public String randomMatchSecond(String id,HttpServletResponse response) throws IOException {
		List<Map> users = this.userMapper.selectUserByMatcherAndId("yes",id);
        if(users.isEmpty()){
            return "no";
        }else{
        	Map user = null;
        	if(users.size()==1){
        		user = users.get(0);
			}else{
        		int s = new Random().nextInt(users.size());
        		user = users.get(s);
			}
        	this.userMapper.updateUserMatcherStateById(id,user.get("id")+"");
            String result = this.userLogin(user.get("user_email")+"",user.get("user_password")+"");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(result);
			return null;
        }
	}

	//修改匹配的状态
	public void fixMatcherStatus(String id){
		this.userMapper.updateUserMatcherStateById(id,"no");
	}

}
