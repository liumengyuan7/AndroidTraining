package com.smallpigeon.admin.controller;

import com.smallpigeon.entity.Admin;
import com.smallpigeon.admin.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @className AdminController
 * @auther 刘梦圆
 * @description 管理员控制器类
 * @date 2020/04/13 10:48
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
    private AdminService adminService;
    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpSession session){
        String email = request.getParameter("email");
		String password = request.getParameter("password");
		Admin admin = this.adminService.login(email, password);
		if(admin!=null) {
			session.setAttribute("admin", admin);
			return "index";
//			return "'result':'ok'";
		}else {
			return "login";
		}
    }
    @RequestMapping("/register")
	public String register(HttpServletRequest request){
    	String nickName=request.getParameter("nickName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		this.adminService.insertUser(nickName,email,password);
		return "login";
	}
	@RequestMapping("/getAll")
	public String getAllAdmins(Model model){
		try {
			model.addAttribute("admin", this.adminService.getAllAdmin());
			return "/tables/table_admin";
		}catch(Exception e){
			e.printStackTrace();
			return "/welcome";
		}
	}
}
