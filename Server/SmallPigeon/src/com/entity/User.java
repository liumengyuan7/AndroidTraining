package com.entity;

import java.util.Date;

public class User {

	private int id;
	private String userName;
	private String userNickname;
	private String userPassword;
	private String userSex;
	private String userEmail;
	private Date userRegisterTime;
	private String userSno;
	private int userPoints;
	private String userMatcher;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Date getUserRegisterTime() {
		return userRegisterTime;
	}

	public void setUserRegisterTime(Date userRegisterTime) {
		this.userRegisterTime = userRegisterTime;
	}

	public String getUserSno() {
		return userSno;
	}

	public void setUserSno(String userSno) {
		this.userSno = userSno;
	}

	public int getUserPoints() {
		return userPoints;
	}

	public void setUserPoints(int userPoints) {
		this.userPoints = userPoints;
	}

	public String getUserMatcher() {
		return userMatcher;
	}

	public void setUserMatcher(String userMatcher) {
		this.userMatcher = userMatcher;
	}

}
