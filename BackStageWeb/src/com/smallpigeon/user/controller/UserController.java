package com.smallpigeon.user.controller;

import com.smallpigeon.entity.User;
import com.smallpigeon.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @className UserController
 * @auther 刘梦圆
 * @description 用户控制类
 * @date 2020/04/14 13:20
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/getAll")
    public String getAllUsers(Model model){
        try {
            model.addAttribute("users", this.userService.getAllUsers());
            return "/tables/table_user";
        }catch(Exception e){
            e.printStackTrace();
            return "/welcome";
        }
    }
    @GetMapping("/addUsers")
    public String toAdd(Model model){
        return "/tables/add_user";
    }
    @PostMapping("/addUsers")
    public String addUser(User user){
        try {
            this.userService.addUser(user);
            return "redirect:/user/getAll";
        }catch(Exception e){
            e.printStackTrace();
            return "redirect:/tables/add_user";
        }
    }
   @GetMapping("/deleteUsers/{id}")
    public String deleteUser(@PathVariable("id") int id){
        try {
            this.userService.deleteUser(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/user/getAll";
    }
    @GetMapping("/editUsers/{id}")
    public String toEdit(@PathVariable("id") int id,Model model){
        model.addAttribute("users",this.userService.getUser(id));
        return "/tables/edit_user";
    }
    @PostMapping("/editUsers")
    public String updateUser(User user){
        try{
            this.userService.updateUser(user);
            return "redirect:/user/getAll";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/user/editUsers"+user.getId();
        }

    }
}
