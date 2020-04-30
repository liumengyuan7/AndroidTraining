package com.smallpigeon.friend.controller;

import com.smallpigeon.entity.Friend;
import com.smallpigeon.entity.Interest;
import com.smallpigeon.friend.service.FriendService;
import com.smallpigeon.interest.service.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @className FriendController
 * @auther 吴东枚
 * @description 朋友控制类
 * @date 2020/04/27 13:20
 */
@Controller
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private FriendService friendService;
    @RequestMapping("/getAll")
    public String getAllFriends(Model model){
        try {
            model.addAttribute("friends", this.friendService.getAllFriends());
            return "/tables/table_friend";
        }catch(Exception e){
            e.printStackTrace();
            return "/welcome";
        }
    }
    @GetMapping("/addFriends")
    public String toAdd(Model model){
        return "/tables/add_friend";
    }
    @PostMapping("/addFriends")
    public String addFriend(Friend friend){
        try {
            this.friendService.addFriend(friend);
            return "redirect:/friend/getAll";
        }catch(Exception e){
            e.printStackTrace();
            return "redirect:/tables/add_friend";
        }
    }
   @GetMapping("/deleteFriends/{id}")
    public String deleteFriend(@PathVariable("id") int id){
        try {
            this.friendService.deleteFriend(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/friend/getAll";
    }
    @GetMapping("/editFriends/{id}")
    public String toEdit(@PathVariable("id") int id,Model model){
        model.addAttribute("friends",this.friendService.getFriend(id));
        return "/tables/edit_friend";
    }
    @PostMapping("/editFriends")
    public String updateFriend(Friend friend){
        try{
            this.friendService.updateFriend(friend);
            return "redirect:/friend/getAll";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/friend/editFriends"+friend.getId();
        }

    }
}
